package com.enigma.loan.service.impl;

import com.enigma.loan.dto.response.FileResponse;
import com.enigma.loan.entity.FileStorage;
import com.enigma.loan.mapper.FileMapper;
import com.enigma.loan.repository.FileStorageRepository;
import com.enigma.loan.service.FileStorageService;
import com.enigma.loan.utils.exception.BadRequestException;
import com.enigma.loan.utils.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageRepository fileStorageRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    private Path fileStorageLocation;

    // init folder dulu
    @PostConstruct
    public void init() throws IOException {
        try {
            this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

            // validasi kalau folder sudah ada, nggak perlu di create
            if(!Files.exists(this.fileStorageLocation)){
                Files.createDirectory(this.fileStorageLocation);
            }
            log.info("Direktory sudah berhasil dibuat di " + this.fileStorageLocation);

        } catch (Exception e) {
            throw new RuntimeException("Gagal dalam proses pembuatan direktori : " + e.getMessage());
        }
    }
    @Override
    @Transactional
    public FileStorage store(MultipartFile file, String category, String referenceId) {
        // validasi File
        validateFile(file);

        String folderName = getFolderName(category);

        // get original fileName
        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Generate nama file secara unik
        String fileExtension = getFileExtension(originalFilename);
        String newFileName = UUID.randomUUID() + "." + fileExtension;

        // proses penyimpanan metadata didalam database
        try {
            // validasi nama file
            if(originalFilename.contains("..")){
                throw new BadRequestException("Nama file tidak valid : " + originalFilename);
            }

            //membuat folder
            Path categoryLocation = this.fileStorageLocation.resolve(folderName).normalize();

            if (!Files.exists(categoryLocation)) {
                Files.createDirectories(categoryLocation);
            }

            // Pemindahan file ke lokasi penyimpanan
            Path targetLocation = categoryLocation.resolve(newFileName).normalize();
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Buat entity FileStorage dan simpan ke dalam database
            FileStorage fileStorage = FileStorage.builder()
                    .name(originalFilename)
                    .fileName(newFileName)
                    .filePath( Paths.get(folderName, newFileName).toString())
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .category(category)
                    .referenceId(referenceId)
                    .build();

            return fileStorageRepository.save(fileStorage);

        } catch (Exception e) {
            throw new RuntimeException("gagal menyimpan file : " + originalFilename + e.getMessage());
        }
    }

    @Override
    public Resource loadFileAsResource(Long id) {
        try {
            // get file by id untuk mendapatkan entity dari file (metadata)
            FileStorage fileStorage = fileStorageRepository.findById(id).orElseThrow( () ->
                    new ResourceNotFoundException("file", "id", id)
            );

            String folderName = getFolderName(fileStorage.getCategory());

            Path categoryLocation = this.fileStorageLocation
                    .resolve(folderName);

            // sediakan file untuk download
            Path filePath = categoryLocation
                    .resolve(fileStorage.getFileName())
                    .normalize();

            if (!filePath.startsWith(categoryLocation)) {
                throw new BadRequestException("Lokasi file tidak valid");
            }

            // mengubah file menjadi resource
            Resource resource = new UrlResource(filePath.toUri());

            // melakukan validasi apakah resource kosong atau tidak
            if(resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("file", "id", id);
            }

        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException("file", "id", id);
        }
    }

    @Override
    public FileResponse getFileById(Long id) {
        FileStorage fileStorage = fileStorageRepository.findById(id).orElseThrow( () ->
                new ResourceNotFoundException("file", "id", id)
        );
        return FileMapper.convertToFileResponse(fileStorage);
    }

    @Override
    @Transactional
    public void deleteFileById(Long id) {
        // 1. get file data
        FileStorage fileStorage = fileStorageRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("file", "id", id));



        try {
            // 2. validate apakah file exist didalam storage
            String folderName = getFolderName(fileStorage.getCategory());

            Path categoryLocation = this.fileStorageLocation
                    .resolve(folderName);

            Path filePath = categoryLocation
                    .resolve(fileStorage.getFileName())
                    .normalize();

            if (!filePath.startsWith(categoryLocation)) {
                throw new BadRequestException("Lokasi file tidak valid");
            }

            // 3. hapus file fisik didalam storage
            Files.deleteIfExists(filePath);

            // 4. hapus metadatanya
            fileStorageRepository.delete(fileStorage);

        } catch (IOException e) {
            throw new RuntimeException("Gagal menghapus file: " + e.getMessage());
        }
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private void validateFile(MultipartFile file) {
        // validasi apakah file kosong
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File tidak boleh kosong!");
        }

        // validasi tipe file -> apa saja tipe file yang diizinkan untuk disimpan
        String contentType = file.getContentType();
        List<String> allowedTypes = Arrays.asList(
                "image/jpeg", "image/jpg", "image/png", "application/pdf"
        );

        if (contentType == null || !allowedTypes.contains(contentType)) {
            throw new BadRequestException("Tipe file tidak didukung. Hanya mendukung file bertipe JPEG, JPG, PNG, PDF");
        }

        // validasi ukuran
        if(file.getSize() > 5 * 1024 * 1024) {// 5 MB
            throw new BadRequestException("Ukuran file terlalu besar. Maksimal ukuran file 5MB");
        }
    }

    private String getFolderName(String category) {

        if (category == null) {
            throw new BadRequestException("Category wajib diisi");
        }

        if ("KTP".equalsIgnoreCase(category)) {
            return "ktp";
        }

        if ("SALARY_SLIP".equalsIgnoreCase(category)) {
            return "salary-slip";
        }

        throw new BadRequestException(
                "Category tidak didukung. Gunakan KTP atau SALARY_SLIP"
        );
    }
}

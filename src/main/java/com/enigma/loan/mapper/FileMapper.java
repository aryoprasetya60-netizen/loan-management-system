package com.enigma.loan.mapper;

import com.enigma.loan.dto.response.FileResponse;
import com.enigma.loan.entity.FileStorage;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class FileMapper {
    
    public static FileResponse convertToFileResponse(FileStorage file) {
        String publicUrl = "";
        
        // menyediakan download url -> url yang digunakan untuk mengunduh file melewati suatu endpoint setelah melakukan login
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files")
                .path(file.getId().toString())
                .toUriString();
        
        // menyediakan public url
        if (file.getFileType() != null && file.getFileType().startsWith("/image")) {
            publicUrl  = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/public/images")
                    .path(file.getId().toString())
                    .toUriString();
        }
        
        return FileResponse.builder()
                .id(file.getId())
                .name(file.getName())
                .fileName(file.getFileName())
                .fileType(file.getFileType())
                .fileSize(file.getFileSize())
                .category(file.getCategory())
                .downloadUrl(downloadUrl)
                .publicUrl(publicUrl)
                .referenceId(file.getReferenceId())
                .build();
    }
}

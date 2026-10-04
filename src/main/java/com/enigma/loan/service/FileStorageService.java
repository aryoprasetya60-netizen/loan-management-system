package com.enigma.loan.service;

import com.enigma.loan.dto.response.FileResponse;
import com.enigma.loan.entity.FileStorage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    FileStorage store(MultipartFile file, String category, String referenceId);
    Resource loadFileAsResource(Long id);
    FileResponse getFileById(Long id);
    void deleteFileById(Long id);
}

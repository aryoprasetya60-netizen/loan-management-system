package com.enigma.loan.repository;

import com.enigma.loan.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface FileStorageRepository extends JpaRepository<FileStorage, Long>, JpaSpecificationExecutor<FileStorage> {
    Optional<FileStorage> findByReferenceIdAndCategory(
            String referenceId,
            String category
    );
}

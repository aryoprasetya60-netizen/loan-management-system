package com.enigma.loan.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
    private Long id;

    private String name; // nama original dari file

    private String fileName; // nama file yang tersimpan didalam database

    private String fileType;

    private Long fileSize;

    private String category;

    // digunakan untuk mengunduh file melalui endpoint yang memerlukan autentikasi
    private String downloadUrl;

    // digunakan untuk mengakses file melalui endpoint public (tanpa auth)
    // contohnya gambar produk didalam ecommerce
    private String publicUrl;

    // sebuah id untuk menandakan bahwa file tertentu mereferen terhadap data lain
    private String referenceId;
}

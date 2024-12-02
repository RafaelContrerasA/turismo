package com.example.Integradoraturismo.dto;

import lombok.Data;

@Data
public class ImagenProductoDto {
    private Long id;
    private String fileName;
    private String fileType;
    private String imagePath;
    private String downloadUrl;
}
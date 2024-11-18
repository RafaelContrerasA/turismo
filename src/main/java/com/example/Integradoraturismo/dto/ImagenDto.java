package com.example.Integradoraturismo.dto;

import lombok.Data;

@Data
public class ImagenDto {
    private Long imageId;
    private String fileName;
    private String fileType;
    private String imagePath;
    private String downloadUrl;
}


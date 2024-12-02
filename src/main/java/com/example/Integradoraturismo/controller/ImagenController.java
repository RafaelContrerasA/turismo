package com.example.Integradoraturismo.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Integradoraturismo.dto.ImagenDto;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import com.example.Integradoraturismo.models.Imagen;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.ImagenService;

import org.springframework.core.io.FileSystemResource;





import jakarta.transaction.Transactional;

import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
@RequestMapping("/imagenes")
public class ImagenController {
    
    private final ImagenService imagenService;
    

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImagens(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try {
            List<ImagenDto> imagens = imagenService.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponse("Upload Success", imagens));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed", e.getMessage()));
        }
    }
        @Transactional
        @GetMapping("/download/{imagenId}")
        public ResponseEntity<Resource> downloadImagen(@PathVariable Long imagenId) {
            Imagen imagen = imagenService.getImageById(imagenId);
            FileSystemResource resource = new FileSystemResource(imagen.getImagePath());
        
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(imagen.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagen.getFileName() + "\"")
                    .body(resource);
        }

    @PutMapping("/{imagenId}/update")
    public ResponseEntity<ApiResponse> updateImagen(@PathVariable Long imagenId, @RequestBody MultipartFile file) {
        try {
            Imagen imagen = imagenService.getImageById(imagenId);
            if(imagen != null) {
                imagenService.updateImage(file, imagenId);
                return ResponseEntity.ok(new ApiResponse("Update success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!", HttpStatus.INTERNAL_SERVER_ERROR));
    }


    @DeleteMapping("{imagenId}/delete")
    public ResponseEntity<ApiResponse> deleteImagen(@PathVariable Long imagenId) {
        try {
            Imagen imagen = imagenService.getImageById(imagenId);
            if(imagen != null) {
                imagenService.deleteImageById( imagenId);
                return ResponseEntity.ok(new ApiResponse("Delete success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", HttpStatus.INTERNAL_SERVER_ERROR));
    }
    
}

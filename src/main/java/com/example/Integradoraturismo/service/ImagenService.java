// package com.example.Integradoraturismo.service;

// import java.io.IOException;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;


// import javax.sql.rowset.serial.SerialBlob;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.multipart.MultipartFile;

// import com.example.Integradoraturismo.dto.ImageDto;
// import com.example.Integradoraturismo.exception.ResourceNotFoundException;
// import com.example.Integradoraturismo.models.Imagen;
// import com.example.Integradoraturismo.models.Reservacion;
// import com.example.Integradoraturismo.repository.ImagenRepository;

// //import com.rcontreras.techstore.service.reservacion.IReservacionService;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class ImagenService{
    
//     private final ImagenRepository imageRepository;
//     private final ReservacionService reservacionService;
//     private final String uploadDir = "images/";
    
    
    
   
//     public Imagen getImageById(Long id) {
//         return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found"));
//     }

    
//     public void deleteImageById(Long id) {
//         imageRepository.findById(id)
//             .ifPresentOrElse(imageRepository::delete, () -> {
//                 throw new ResourceNotFoundException("Image not found");});
//     }

    
//     public List<ImageDto> saveImages(List<MultipartFile> files, Long reservacionId) {
//         Reservacion reservacion = reservacionService.getReservacionById(reservacionId);
//         List<ImageDto> savedImageDto = new ArrayList<>();

//         for (MultipartFile file : files) {
//             try {
//                 // Crear la ruta para guardar el archivo
//                 String fileName =System.currentTimeMillis()+"_"+ file.getOriginalFilename();
//                 Path filePath = Paths.get(uploadDir, fileName);
//                 Files.createDirectories(filePath.getParent()); // Crear directorio si no existe

//                 // Guardar archivo en el servidor
//                 Files.write(filePath, file.getBytes());

//                 // Crear objeto Image con la ruta y URL de descarga
//                 Imagen image = new Imagen();
//                 image.setFileName(fileName);
//                 image.setFileType(file.getContentType());
//                 image.setImagePath(filePath.toString()); // Guardar la ruta en el modelo
//                 image.setReservacion(reservacion);

//                 // Guardar el objeto Image en la base de datos
//                 Imagen savedImage = imageRepository.save(image);
//                 savedImage.setDownloadUrl("/api/v1/images/download/" + savedImage.getId());
//                 savedImage = imageRepository.save(savedImage);

//                 ImageDto imageDto = new ImageDto();
//                 imageDto.setImageId(savedImage.getId());
//                 imageDto.setImageName(savedImage.getFileName());
//                 imageDto.setDownloadUrl(savedImage.getDownloadUrl());
//                 savedImageDto.add(imageDto);
                
//             } catch (IOException e) {
//                 throw new RuntimeException("Error al guardar la imagen", e);
//             }
//         }
//         return savedImageDto;
//     }

    
//     public void updateImage(MultipartFile file, Long imageId) {
//         Imagen image = getImageById(imageId);
    
//         try {
//             // Eliminar la imagen anterior si existe
//             if (image.getImagePath() != null) {
//                 Path oldImagePath = Paths.get(image.getImagePath());
//                 Files.deleteIfExists(oldImagePath);
//             }
    
//             // Guardar la nueva imagen en el servidor
//             String newFileName = System.currentTimeMillis()+"_"+ file.getOriginalFilename();
//             Path newFilePath = Paths.get(uploadDir, newFileName);
//             Files.createDirectories(newFilePath.getParent()); // Crear el directorio si no existe
//             Files.write(newFilePath, file.getBytes());
    
//             // Actualizar los datos de la imagen
//             image.setFileName(newFileName);
//             image.setFileType(file.getContentType());
//             image.setImagePath(newFilePath.toString());
    
//             // Guardar la actualización en la base de datos
//             imageRepository.save(image);
    
//         } catch (IOException e) {
//             throw new RuntimeException("Error al actualizar la imagen", e);
//         }
//     }
    
    
// }


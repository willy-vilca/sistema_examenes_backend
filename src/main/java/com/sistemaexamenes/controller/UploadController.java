package com.sistemaexamenes.controller;

import com.sistemaexamenes.dto.upload.UploadResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/uploads")
@RequiredArgsConstructor
public class UploadController {
    @Value("${app.upload.dir}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<UploadResponseDTO>
    uploadImagen(
            @RequestParam("file")
            MultipartFile file
    ) throws Exception {

        String extension =
                StringUtils.getFilenameExtension(
                        file.getOriginalFilename());

        String nombreArchivo =
                UUID.randomUUID() +
                        "." +
                        extension;

        Path ruta =
                Paths.get(uploadDir)
                        .resolve(nombreArchivo);

        Files.copy(
                file.getInputStream(),
                ruta
        );

        String url =
                "http://localhost:8080/uploads/"
                        + nombreArchivo;

        return ResponseEntity.ok(
                new UploadResponseDTO(url)
        );
    }
}

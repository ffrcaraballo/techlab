package com.tuempresa.tuapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenesController {

    private static final String RUTA_IMAGENES = "imagenes"; // carpeta real fuera de src/

    @PostMapping("/subir")
    public ResponseEntity<String> subirImagen(@RequestParam("archivo") MultipartFile archivo) {
        try {
            String nombre = UUID.randomUUID() + "-" + archivo.getOriginalFilename();
            Path destino = Paths.get(RUTA_IMAGENES).resolve(nombre);
            Files.createDirectories(destino.getParent()); // crea carpeta si no existe
            Files.copy(archivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
            return ResponseEntity.ok("/imagenes/" + nombre);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al guardar imagen: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<String>> listarImagenes() {
        try {
            Path carpeta = Paths.get(RUTA_IMAGENES);
            if (!Files.exists(carpeta)) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            List<String> nombres = Files.list(carpeta)
                    .filter(Files::isRegularFile)
                    .map(path -> "/imagenes/" + path.getFileName().toString())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(nombres);
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(Collections.singletonList("Error: " + e.getMessage()));
        }
    }
}


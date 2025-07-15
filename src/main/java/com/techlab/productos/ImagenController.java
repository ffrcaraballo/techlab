package com.techlab.productos;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

    // Ruta absoluta o relativa fuera de /resources/static
    private static final String RUTA_IMAGENES = "imagenes/";

    @PostMapping("/subir")
    public ResponseEntity<String> subirImagen(@RequestParam("archivo") MultipartFile archivo) {
        try {
            if (archivo.isEmpty()) return ResponseEntity.badRequest().body("Archivo vacío");

            String nombreArchivo = UUID.randomUUID() + "-" + archivo.getOriginalFilename();

            // Crear carpeta si no existe
            Files.createDirectories(Paths.get(RUTA_IMAGENES));

            Path rutaDestino = Paths.get(RUTA_IMAGENES, nombreArchivo);
            Files.copy(archivo.getInputStream(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);

            // Devuelve la URL para acceder a esa imagen (ajusta la URL pública)
            String url = "/imagenes/" + nombreArchivo;

            return ResponseEntity.ok(url);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al guardar la imagen: " + e.getMessage());
        }
    }
}



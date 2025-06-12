package com.miapp.springboot_app.controller;

import com.miapp.springboot_app.service.MappingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/elasticsearch")
public class MappingController {

    private final MappingService mappingService;

    public MappingController(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    @GetMapping("/mapping-simple/{indice}")
    public ResponseEntity<?> getMappingSimple(@PathVariable String indice) {
        try {
            return ResponseEntity.ok(mappingService.obtenerCamposSimplificados(indice));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}

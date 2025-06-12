package com.miapp.springboot_app.controller;

import com.miapp.springboot_app.service.BusquedaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/elasticsearch")
public class BusquedaController {

    private final BusquedaService busquedaService;

    public BusquedaController(BusquedaService busquedaService) {
        this.busquedaService = busquedaService;
    }

    @PostMapping("/buscar")
    public ResponseEntity<?> buscarPorFiltros(
            @RequestParam String indice,
            @RequestBody Map<String, Object> filtros) {
        try {
            return ResponseEntity.ok(busquedaService.buscarPorFiltros(indice, filtros));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}

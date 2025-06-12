package com.miapp.springboot_app.controller;

import com.miapp.springboot_app.model.Documento;
import com.miapp.springboot_app.service.IndexadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/indexador")
public class IndexadorController {

    private final IndexadorService indexadorService;

    public IndexadorController(IndexadorService indexadorService) {
        this.indexadorService = indexadorService;
    }

    @PostMapping("/cargar-prueba")
    public ResponseEntity<?> cargarDocumentoPrueba() {
        try {
            Documento doc = new Documento();
            doc.setId(UUID.randomUUID().toString());
            doc.setTitulo("Ejemplo de título");
            doc.setContenido("Este es un documento de prueba para indexación");
            doc.setAutor("Jonnathan Arrué");
            doc.setActivo(true);

            indexadorService.indexarDocumento(doc);
            return ResponseEntity.ok("Documento indexado con éxito");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al indexar: " + e.getMessage());
        }
    }

    @PostMapping("/cargar-lote")
    public ResponseEntity<?> cargarDocumentosPrueba() {
        try {
            indexadorService.indexarDocumentosPrueba();
            return ResponseEntity.ok("✅ Documentos de prueba cargados");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Error: " + e.getMessage());
        }
    }
}

package com.miapp.springboot_app.controller;

import com.miapp.springboot_app.service.ElasticsearchPingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElasticsearchController {

    private final ElasticsearchPingService pingService;

    public ElasticsearchController(ElasticsearchPingService pingService) {
        this.pingService = pingService;
    }

    @GetMapping("/ping-elasticsearch")
    public ResponseEntity<String> ping() {
        return pingService.estaVivo()
            ? ResponseEntity.ok("✅ Elasticsearch está disponible")
            : ResponseEntity.status(500).body("❌ Error de conexión");
    }

}

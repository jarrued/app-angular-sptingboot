package com.miapp.springboot_app.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchPingService {

    private final ElasticsearchClient client;

    public ElasticsearchPingService(ElasticsearchClient client) {
        this.client = client;
    }

    public boolean estaVivo() {
        try {
            return client.ping().value();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

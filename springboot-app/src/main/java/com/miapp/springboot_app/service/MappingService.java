package com.miapp.springboot_app.service;

import com.miapp.springboot_app.dto.CampoMappingDTO;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch.indices.GetMappingResponse;
import co.elastic.clients.elasticsearch.indices.get_mapping.IndexMappingRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class MappingService {

    private final ElasticsearchClient client;

    public MappingService(ElasticsearchClient client) {
        this.client = client;
    }

    public List<CampoMappingDTO> obtenerCamposSimplificados(String indice) throws IOException {
        GetMappingResponse response = client.indices().getMapping(m -> m.index(indice));
        IndexMappingRecord mapping = response.result().get(indice);
        if (mapping == null) return List.of();

        Map<String, Property> propiedades = mapping.mappings().properties();
        List<CampoMappingDTO> campos = new ArrayList<>();

        for (Map.Entry<String, Property> entry : propiedades.entrySet()) {
            String campo = entry.getKey();
            Property prop = entry.getValue();

            String tipo = prop._kind().jsonValue();  // Devuelve el tipo como "text", "keyword", etc.
            campos.add(new CampoMappingDTO(campo, tipo));
        }

        return campos;
    }
}

package com.miapp.springboot_app.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BusquedaService {

    private final ElasticsearchClient client;

    public BusquedaService(ElasticsearchClient client) {
        this.client = client;
    }

    public List<Map<String, Object>> buscarPorFiltros(String indice, Map<String, Object> filtros) throws Exception {
        List<Query> mustQueries = new ArrayList<>();

        for (Map.Entry<String, Object> entry : filtros.entrySet()) {
            String campo = entry.getKey();
            Object valor = entry.getValue();

            if (valor == null || valor.toString().trim().isEmpty()) continue;

            if (valor instanceof Boolean) {
                mustQueries.add(QueryBuilders.term(t -> t.field(campo).value((Boolean) valor)));
            } else {
                mustQueries.add(QueryBuilders.match(m -> m.field(campo).query(valor.toString())));
            }
        }

        BoolQuery boolQuery = QueryBuilders.bool().must(mustQueries).build();

        SearchRequest request = SearchRequest.of(s -> s
                .index(indice)
                .query(boolQuery._toQuery())
        );

        SearchResponse<Map> response = client.search(request, Map.class);

        List<Map<String, Object>> resultados = new ArrayList<>();
        for (Hit<Map> hit : response.hits().hits()) {
            resultados.add(hit.source());
        }

        return resultados;
    }
}

package com.miapp.springboot_app.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.miapp.springboot_app.model.Documento;

import java.util.UUID;

import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class IndexadorService {

    private final ElasticsearchClient client;

    public IndexadorService(ElasticsearchClient client) {
        this.client = client;
    }

    public void indexarDocumento(Documento doc) throws Exception {
        client.index(IndexRequest.of(i -> i
            .index("documentos")  // Asegúrate de usar el nombre correcto del índice
            .id(doc.getId())      // Si no pones ID, Elasticsearch lo genera
            .document(doc)
        ));
    }
public void indexarDocumentosPrueba() throws Exception {
    List<Documento> docs = List.of(
        crear("Informe financiero 2024", "Resumen de ingresos y egresos del año.", "Carlos Soto", true),
        crear("Auditoría interna", "Informe de cumplimiento normativo.", "María Contreras", true),
        crear("Reglamento interno", "Actualización del reglamento de la empresa.", "Ana López", false),
        crear("Acta de reunión", "Reunión anual de planificación estratégica.", "Pedro Núñez", true),
        crear("Carta de renuncia", "Documento formal presentado por empleado.", "Laura Mena", false),
        crear("Manual de procedimientos", "Normas operativas actualizadas.", "Gabriel Rivas", true),
        crear("Contrato laboral", "Ejemplo de contrato indefinido.", "Soledad Paredes", true),
        crear("Boletín informativo", "Novedades mensuales de la organización.", "Iván Chávez", false),
        crear("Evaluación de desempeño", "Informe anual de evaluación de personal.", "Verónica Díaz", true),
        crear("Informe de viaje", "Detalles del viaje técnico a regiones.", "Felipe Moraga", true)
    );

    for (Documento doc : docs) {
        client.index(IndexRequest.of(i -> i
            .index("documentos")
            .id(doc.getId())
            .document(doc)
        ));
    }
}

private Documento crear(String titulo, String contenido, String autor, boolean activo) {
    Documento doc = new Documento();
    doc.setId(UUID.randomUUID().toString());
    doc.setTitulo(titulo);
    doc.setContenido(contenido);
    doc.setAutor(autor);
    doc.setActivo(activo);
    return doc;
}
}

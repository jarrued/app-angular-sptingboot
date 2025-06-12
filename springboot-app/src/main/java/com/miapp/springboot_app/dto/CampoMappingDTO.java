package com.miapp.springboot_app.dto;

public class CampoMappingDTO {
    private String campo;
    private String tipo;

    public CampoMappingDTO(String campo, String tipo) {
        this.campo = campo;
        this.tipo = tipo;
    }

    public String getCampo() {
        return campo;
    }

    public String getTipo() {
        return tipo;
    }
}

package br.com.ifgoiano.simplestock.model;

import java.util.HashMap;
import java.util.Map;

public class CategoriaModel {

    private String id;
    private String categoria;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("categoria",categoria);
        return map;
    }
}

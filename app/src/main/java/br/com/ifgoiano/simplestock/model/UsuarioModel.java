package br.com.ifgoiano.simplestock.model;


import java.util.HashMap;
import java.util.Map;

public class UsuarioModel{

    private String id;
    private String nome;
    private String acessibilidade;
    private String email;
    private String senha;

    public UsuarioModel() {

    }

    public UsuarioModel(String nome, String acessibilidade, String email, String senha) {
        this.nome = nome;
        this.acessibilidade = acessibilidade;
        this.email = email;
        this.senha = senha;
    }

    public UsuarioModel(String id, String nome, String acessibilidade, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.acessibilidade = acessibilidade;
        this.email = email;
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAcessibilidade() {
        return acessibilidade;
    }

    public void setAcessibilidade(String acessibilidade) {
        this.acessibilidade = acessibilidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("nome",nome);
        map.put("acessibilidade",acessibilidade);
        map.put("email",email);
        return map;
    }
}

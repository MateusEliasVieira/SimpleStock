package br.com.ifgoiano.simplestock.model;


import java.util.HashMap;
import java.util.Map;

public class FornecedorModel {

    private String id;
    private String fornecedor;
    private String cnpj;
    private String email;
    private String telefone;

    public FornecedorModel() {
    }

    public FornecedorModel(String fornecedor, String cnpj, String email, String telefone) {
        this.fornecedor = fornecedor;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
    }

    public FornecedorModel(String id, String fornecedor, String cnpj, String email, String telefone) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("fornecedor",fornecedor);
        map.put("cnpj",cnpj);
        map.put("email",email);
        map.put("telefone",telefone);
        return map;
    }
}

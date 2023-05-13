package br.com.ifgoiano.simplestock.model;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;


public class ProdutoModel {

    private String id;
    private String produto;
    private String categoria;
    private String fornecedor;
    private int quantidade;
    private double varejo;
    private double venda;
    private String descricao;
    private String urlImage;
    private Bitmap imagem;

    public ProdutoModel() {

    }

    public ProdutoModel(String produto, String categoria, String fornecedor, int quantidade, double varejo, double venda, String descricao, Bitmap imagem) {
        this.produto = produto;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.quantidade = quantidade;
        this.varejo = varejo;
        this.venda = venda;
        this.descricao = descricao;
        this.urlImage = "";
        this.imagem = imagem;
    }

    public ProdutoModel(String produto, String categoria, String fornecedor, int quantidade, double varejo, double venda, String descricao, String urlImage, Bitmap imagem) {
        this.produto = produto;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.quantidade = quantidade;
        this.varejo = varejo;
        this.venda = venda;
        this.descricao = descricao;
        this.urlImage = urlImage;
        this.imagem = imagem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getVarejo() {
        return varejo;
    }

    public void setVarejo(double varejo) {
        this.varejo = varejo;
    }

    public double getVenda() {
        return venda;
    }

    public void setVenda(double venda) {
        this.venda = venda;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("produto", produto);
        map.put("categoria", categoria);
        map.put("fornecedor", fornecedor);
        map.put("quantidade", quantidade);
        map.put("varejo", varejo);
        map.put("venda", venda);
        map.put("descricao", descricao);
        map.put("urlimage", urlImage);
        return map;
    }

}

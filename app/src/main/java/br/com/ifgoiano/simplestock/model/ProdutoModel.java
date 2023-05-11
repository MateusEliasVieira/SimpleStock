package br.com.ifgoiano.simplestock.model;

import android.graphics.Bitmap;


public class ProdutoModel{

    private String id;
    private String produto;
    private String categoria;
    private String fornecedor;
    private int quantidade;
    private double varejo;
    private double venda;
    private String descricao;
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
        this.imagem = imagem;
    }

    public ProdutoModel(String id, String produto, String categoria, String fornecedor, int quantidade, double varejo, double venda, String descricao, Bitmap imagem) {
        this.id = id;
        this.produto = produto;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.quantidade = quantidade;
        this.varejo = varejo;
        this.venda = venda;
        this.descricao = descricao;
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

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }
}

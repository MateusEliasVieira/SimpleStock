package br.com.ifgoiano.simplestock.dao;

import com.google.android.gms.tasks.OnCompleteListener;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import br.com.ifgoiano.simplestock.model.ProdutoModel;

public interface ProdutoService {

    public void save(String document, ProdutoModel produtoModel, OnCompleteListener<Boolean> listenerResult);

    public void update(ProdutoModel produtoModel);

    public CompletableFuture<List<ProdutoModel>> findAll();

    public CompletableFuture<List<ProdutoModel>> findByName(String name);

    public void delete(String name_document,OnCompleteListener<Boolean> listener);

}

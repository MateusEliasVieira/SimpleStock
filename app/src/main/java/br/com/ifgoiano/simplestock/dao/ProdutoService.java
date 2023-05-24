package br.com.ifgoiano.simplestock.dao;

import com.google.android.gms.tasks.OnCompleteListener;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import br.com.ifgoiano.simplestock.model.ProdutoModel;

public interface ProdutoService {

    public boolean save(ProdutoModel produtoModel, OnCompleteListener<Boolean> listenerResult);

    public void update(ProdutoModel produtoModel);

    public CompletableFuture<List<ProdutoModel>> findAll();

    public void findByName();

    public void delete(String name_document);

}

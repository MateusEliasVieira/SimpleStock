package br.com.ifgoiano.simplestock.dao;

import com.google.android.gms.tasks.OnCompleteListener;

import br.com.ifgoiano.simplestock.model.ProdutoModel;

public interface ProdutoDaoInterface {

    public boolean save(ProdutoModel produtoModel, OnCompleteListener<Boolean> listenerResult);

    public void update(ProdutoModel produtoModel);

    public void findAll();

    public void findByName();

    public void delete(String name_document);

}

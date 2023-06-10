package br.com.ifgoiano.simplestock.dao;

import com.google.android.gms.tasks.OnCompleteListener;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import br.com.ifgoiano.simplestock.model.CategoriaModel;

public interface CategoriaService {

    public void save(CategoriaModel categoriaModel, OnCompleteListener onCompleteListener);
    public CompletableFuture<List<CategoriaModel>> findAll();

}

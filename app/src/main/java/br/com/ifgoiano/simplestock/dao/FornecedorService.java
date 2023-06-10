package br.com.ifgoiano.simplestock.dao;

import com.google.android.gms.tasks.OnCompleteListener;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import br.com.ifgoiano.simplestock.model.FornecedorModel;

public interface FornecedorService {
    public void save(FornecedorModel fornecedorModel, OnCompleteListener listener);

    public void update();

    public CompletableFuture<List<FornecedorModel>> findAll();

    public void delete();
}

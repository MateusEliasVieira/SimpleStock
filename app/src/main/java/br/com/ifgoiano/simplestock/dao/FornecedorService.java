package br.com.ifgoiano.simplestock.dao;

import com.google.android.gms.tasks.OnCompleteListener;

import java.util.List;

import br.com.ifgoiano.simplestock.model.FornecedorModel;

public interface FornecedorService {
    public boolean save(FornecedorModel fornecedorModel, OnCompleteListener listener);

    public boolean update();

    public List<FornecedorModel> findAll();

    public void delete();
}

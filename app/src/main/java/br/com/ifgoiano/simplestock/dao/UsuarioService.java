package br.com.ifgoiano.simplestock.dao;

import com.google.android.gms.tasks.OnCompleteListener;

import br.com.ifgoiano.simplestock.model.UsuarioModel;

public interface UsuarioService {

    public void save(UsuarioModel usuarioModel, OnCompleteListener<Boolean> listenerResult);


}

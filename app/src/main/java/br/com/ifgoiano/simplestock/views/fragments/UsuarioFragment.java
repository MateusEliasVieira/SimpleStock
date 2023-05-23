package br.com.ifgoiano.simplestock.views.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.dao.UsuarioService;
import br.com.ifgoiano.simplestock.dao.impl.UsuarioServiceImpl;
import br.com.ifgoiano.simplestock.model.UsuarioModel;

public class UsuarioFragment extends Fragment {

    private UsuarioService usuarioService;
    private EditText editTextNomeUsuario;
    private Spinner spinnerNivelAcessoUsuario;
    private EditText editTextEmailUsuario;
    private EditText editTextSenhaUsuario;
    private Button buttonCadastrarUsuario;

    public UsuarioFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario, container, false);
        usuarioService = new UsuarioServiceImpl();
        editTextNomeUsuario = view.findViewById(R.id.editTextNomeUsuario);
        spinnerNivelAcessoUsuario = view.findViewById(R.id.spinnerNivelAcessoUsuario);
        editTextEmailUsuario = view.findViewById(R.id.editTextEmailUsuario);
        editTextSenhaUsuario = view.findViewById(R.id.editTextSenhaUsuario);
        buttonCadastrarUsuario = view.findViewById(R.id.buttonCadastrarUsuario);
        addUser();
        return view;
    }

    private void addUser() {
        buttonCadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextNomeUsuario.getText().toString();
                String acess = spinnerNivelAcessoUsuario.getSelectedItem().toString();
                String email = editTextEmailUsuario.getText().toString();
                String password = editTextSenhaUsuario.getText().toString();
                if (name.isEmpty() || acess.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    alert("Aviso", "Preencha todos os campos!", "OK");
                } else {
                    if (password.length() < 6) {
                        alert("Aviso", "A senha deve ter pelo menos 6 dígitos!", "OK");
                    } else {
                        // salvar
                        UsuarioModel usuarioModel = new UsuarioModel();
                        usuarioModel.setNome(name);
                        usuarioModel.setEmail(email);
                        usuarioModel.setAcessibilidade(acess);
                        usuarioModel.setSenha(password);
                        usuarioService.addUser(usuarioModel, new OnCompleteListener<Boolean>() {
                            @Override
                            public void onComplete(@NonNull Task<Boolean> task) {
                                if (task.getResult()) {
                                    clean();
                                    alert("Sucesso", "Novo usuário cadastrado com sucesso!", "OK");
                                } else {
                                    alert("Falhou", "Houve um problema ao salvar o novo usuário, por favor tente novamente!", "OK");
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void alert(String title, String message, String button) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(button, null);
        alert.show();
    }

    private void clean() {
        editTextNomeUsuario.setText("");
        spinnerNivelAcessoUsuario.setSelection(0);
        editTextEmailUsuario.setText("");
        editTextSenhaUsuario.setText("");
    }

}
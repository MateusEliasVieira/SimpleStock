package br.com.ifgoiano.simplestock.dao.impl;

import android.app.AlertDialog;
import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import br.com.ifgoiano.simplestock.dao.UsuarioService;
import br.com.ifgoiano.simplestock.model.UsuarioModel;

public class UsuarioServiceImpl implements UsuarioService {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Context context;

    public UsuarioServiceImpl(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public UsuarioServiceImpl(Context context){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.context = context;
    }

    @Override
    public boolean save(UsuarioModel usuarioModel,OnCompleteListener<Boolean> listenerResult) {
        // adicionar com ao firebase authentication
        Task<AuthResult> task = firebaseAuth.createUserWithEmailAndPassword(usuarioModel.getEmail(), usuarioModel.getSenha());
        task.addOnSuccessListener(result -> {
            if(task.isSuccessful()){
                // salvamos na collection o usuario(nome,acess e email)
                Map<String,Object> map = usuarioModel.toMap();
                firebaseFirestore.collection("usuario")
                        .document(usuarioModel.getEmail()).set(map)
                        .addOnSuccessListener(res -> {
                            listenerResult.onComplete(Tasks.forResult(true));
                        })
                        .addOnFailureListener(failure -> {
                            listenerResult.onComplete(Tasks.forResult(false));
                        });
            }
        }).addOnFailureListener(failure -> {
            if(failure instanceof FirebaseAuthUserCollisionException){
                alert("Falhou", "Já existe um usuário com este email cadastrado!", "OK");
            } else if(failure instanceof FirebaseAuthEmailException){
                alert("Falhou", "Email inválido!", "OK");
            } else if(failure instanceof FirebaseAuthWeakPasswordException){
                alert("Falhou", "A senha está muito fraca, por favor tente usar vários tipos de caracteres, como letras, números e símbolos!", "OK");
            } else if(failure instanceof FirebaseAuthException){
                alert("Falhou", "Houve um problema ao salvar o novo usuário, por favor tente novamente!", "OK");
            }
            listenerResult.onComplete(Tasks.forResult(false));
        });
        return false;
    }

    private void alert(String title, String message, String button) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(button, null);
        alert.show();
    }


}

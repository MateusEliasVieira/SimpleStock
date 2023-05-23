package br.com.ifgoiano.simplestock.dao.impl;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.ifgoiano.simplestock.dao.UsuarioService;
import br.com.ifgoiano.simplestock.model.UsuarioModel;

public class UsuarioServiceImpl implements UsuarioService {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public UsuarioServiceImpl(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public boolean addUser(UsuarioModel usuarioModel,OnCompleteListener<Boolean> listenerResult) {
        // adicionar com ao firebase authentication
        Task<AuthResult> task = firebaseAuth.createUserWithEmailAndPassword(usuarioModel.getEmail(), usuarioModel.getSenha());
        task.addOnSuccessListener(result -> {
            if(task.isSuccessful()){
                listenerResult.onComplete(Tasks.forResult(true));
            }
        }).addOnFailureListener(failure -> {
            listenerResult.onComplete(Tasks.forResult(false));
        });
        return false;
    }


}

package br.com.ifgoiano.simplestock.dao.impl;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import br.com.ifgoiano.simplestock.dao.FornecedorService;
import br.com.ifgoiano.simplestock.model.FornecedorModel;

public class FornecedorServiceImpl implements FornecedorService {

    private final String COLLECTION = "fornecedor";
    private FirebaseFirestore firebaseFirestore;

    public FornecedorServiceImpl(Context context){
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public boolean save(FornecedorModel fornecedorModel, OnCompleteListener listener) {
        firebaseFirestore.collection(COLLECTION)
                .document(fornecedorModel.getCnpj())
                .set(fornecedorModel.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onComplete(Tasks.forResult(true));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onComplete(Tasks.forResult(false));
                    }
                });
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public List<FornecedorModel> findAll() {
        return null;
    }

    @Override
    public void delete() {

    }
}

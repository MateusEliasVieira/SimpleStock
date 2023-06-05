package br.com.ifgoiano.simplestock.dao.impl;

import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

import br.com.ifgoiano.simplestock.dao.FornecedorService;
import br.com.ifgoiano.simplestock.model.FornecedorModel;

public class FornecedorServiceImpl implements FornecedorService {

    private final String COLLECTION = "fornecedor";
    private FirebaseFirestore firebaseFirestore;
    private Context context;

    public FornecedorServiceImpl(Context context) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.context = context;
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
    public CompletableFuture<List<FornecedorModel>> findAll() {
        CompletableFuture<List<FornecedorModel>> completableFuture = new CompletableFuture<>();
        List<FornecedorModel> fornecedorModelList = new ArrayList<>();
        Task<QuerySnapshot> taskQuerySnapshot = firebaseFirestore.collection(COLLECTION).get();
        taskQuerySnapshot.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documentSnapshotList = queryDocumentSnapshots.getDocuments();
                        documentSnapshotList.forEach(documentSnapshot -> {
                            FornecedorModel fornecedorModel = new FornecedorModel();
                            fornecedorModel.setFornecedor((String) documentSnapshot.get("fornecedor"));
                            fornecedorModel.setCnpj((String) documentSnapshot.get("cnpj"));
                            fornecedorModel.setEmail((String) documentSnapshot.get("email"));
                            fornecedorModel.setTelefone((String) documentSnapshot.get("telefone"));
                            fornecedorModelList.add(fornecedorModel);
                        });
                        completableFuture.complete(fornecedorModelList);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completableFuture.completeExceptionally(e);
                    }
                });

        return completableFuture;
    }

    @Override
    public void delete() {

    }

}

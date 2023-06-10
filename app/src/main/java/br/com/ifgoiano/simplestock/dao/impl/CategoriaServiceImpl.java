package br.com.ifgoiano.simplestock.dao.impl;

import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import br.com.ifgoiano.simplestock.dao.CategoriaService;
import br.com.ifgoiano.simplestock.model.CategoriaModel;

public class CategoriaServiceImpl implements CategoriaService {

    private final String COLLECTION_CATEGORIA = "categoria";
    private FirebaseFirestore firebaseFirestore;

    public CategoriaServiceImpl(Context context){
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void save(CategoriaModel categoriaModel, OnCompleteListener onCompleteListener) {
        firebaseFirestore.collection(COLLECTION_CATEGORIA).document(categoriaModel.getCategoria()).set(categoriaModel.toMap())
                .addOnSuccessListener(result -> {
                    onCompleteListener.onComplete(Tasks.forResult(true));
                })
                .addOnFailureListener(failure -> {
                    onCompleteListener.onComplete(Tasks.forResult(false));
                });
    }

    @Override
    public CompletableFuture<List<CategoriaModel>> findAll() {
        CompletableFuture<List<CategoriaModel>> future = new CompletableFuture<>();

        firebaseFirestore.collection(COLLECTION_CATEGORIA).get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<CategoriaModel> listCategoriaModel = new ArrayList<>();
            List<DocumentSnapshot> listDocuments = queryDocumentSnapshots.getDocuments();
            int length = listDocuments.size();
            for (int i = 0; i < length; i++) {
                DocumentSnapshot ds = listDocuments.get(i);

                String categoria = ds.getString("categoria");
                CategoriaModel categoriaModel = new CategoriaModel();
                categoriaModel.setCategoria(categoria);

                listCategoriaModel.add(categoriaModel);
            }

            future.complete(listCategoriaModel);
        }).addOnFailureListener(e -> future.completeExceptionally(e));

        return future;
    }


}

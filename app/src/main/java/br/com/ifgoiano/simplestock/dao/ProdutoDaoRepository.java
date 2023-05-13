package br.com.ifgoiano.simplestock.dao;

import android.app.AlertDialog;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import br.com.ifgoiano.simplestock.model.ProdutoModel;
import br.com.ifgoiano.simplestock.util.Util;
import br.com.ifgoiano.simplestock.views.HomeActivity;

public class ProdutoDaoRepository {

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private boolean result;
    private byte[] imageBytes;
    private String imageUrl;

    public ProdutoDaoRepository() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public void save(ProdutoModel produtoModel) {
        try {

            if (produtoModel.getImagem() != null) {

                storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                String nameImage = produtoModel.getProduto().replace(" ", "_").trim() + ".jpg";
                StorageReference imageRef = storageRef.child(nameImage);
                imageBytes = new Util().convertBitmapForBytes(produtoModel.getImagem());
                UploadTask uploadTask = imageRef.putBytes(imageBytes);// envia a imagem para ser salva

                // adiciona os listeners ao uploadTask para obter a URL de download da imagem
                uploadTask.addOnSuccessListener((taskSnapshot) -> {
                    // se salvou, obtenho a url de download e verifico se foi possível buscar essa url
                    imageRef.getDownloadUrl().addOnSuccessListener((uri) -> {
                        imageUrl = uri.toString();
                        CollectionReference collectionReference = db.collection("produto");
                        Map<String, Object> produtoMap = produtoModel.toMap();
                        produtoMap.put("imagemUrl", imageUrl);
                        produtoMap.forEach((key, value) -> {
                            Log.d("Chave " + key.toString(), value.toString());
                        });
                        Task<DocumentReference> task = collectionReference.add(produtoMap);
                        task.addOnCompleteListener((taskSave) -> {
                            if (taskSave.isSuccessful()) {
                                Log.d("OK", "Salvou");
                            }
                        });
                        task.addOnFailureListener((err) -> {
                            Log.d("Merda", "Não Salvou");
                        });

                    });
                });

            }

        } catch (Exception e) {
            Log.d("Exceção", e.getMessage());
            e.printStackTrace();
        }
    }


}

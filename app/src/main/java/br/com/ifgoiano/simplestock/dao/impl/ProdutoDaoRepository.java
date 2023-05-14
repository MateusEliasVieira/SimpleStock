package br.com.ifgoiano.simplestock.dao.impl;

import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Map;

import br.com.ifgoiano.simplestock.dao.ProdutoDaoInterface;
import br.com.ifgoiano.simplestock.model.ProdutoModel;
import br.com.ifgoiano.simplestock.util.Util;

public class ProdutoDaoRepository implements ProdutoDaoInterface {

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private CollectionReference collectionReference;
    private byte[] imageBytes;
    private Context context;

    public ProdutoDaoRepository(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        collectionReference = db.collection("produto");
    }

    public boolean save(ProdutoModel produtoModel, OnCompleteListener<Boolean> listenerResult) {

        String nameImage = produtoModel.getProduto().replace(" ", "_").trim() + ".jpg";
        StorageReference imageRef = storageRef.child(nameImage);
        imageBytes = new Util().convertBitmapForBytes(produtoModel.getImagem());
        UploadTask uploadTask = imageRef.putBytes(imageBytes);// Envia a imagem para ser salva

        // Adiciona os listeners ao uploadTask para obter a URL de download da imagem
        uploadTask.addOnSuccessListener((taskSnapshot) -> {
            // Se salvou, obtenho a url de download e verifico se foi possível buscar essa url
            imageRef.getDownloadUrl().addOnSuccessListener((uri) -> {
                produtoModel.setUrlImage(uri.toString()); // Adicionamos a url da imagem salva no storage ao nosso objeto do produto
                Map<String, Object> produtoMap = produtoModel.toMap();
                collectionReference.document(produtoModel.getProduto()).set(produtoMap).addOnSuccessListener((taskVoid) -> {
                    imageBytes = null;// Limpamos o conteudo dela
                    listenerResult.onComplete(Tasks.forResult(true));
                });
            }).addOnFailureListener((exception) -> {
                imageBytes = null;
                listenerResult.onComplete(Tasks.forResult(false)); // informa ao listener que o processo foi concluído com falha
            });
        });

        return false;
    }

    @Override
    public void update(ProdutoModel produtoModel) {

    }

    @Override
    public void findAll() {

    }

    @Override
    public void findByName() {

    }

    @Override
    public void delete(String name_document) {

    }


}
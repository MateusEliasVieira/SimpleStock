package br.com.ifgoiano.simplestock.dao.impl;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import br.com.ifgoiano.simplestock.dao.ProdutoService;
import br.com.ifgoiano.simplestock.model.ProdutoModel;
import br.com.ifgoiano.simplestock.util.Util;

public class ProdutoServiceImpl implements ProdutoService {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private CollectionReference collectionReference;
    private byte[] imageBytes;
    private Context context;

    public ProdutoServiceImpl(Context context) {
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        collectionReference = firebaseFirestore.collection("produto");
    }

    public void save(String document, ProdutoModel produtoModel, OnCompleteListener<Boolean> listenerResult) {

        String nameImage = produtoModel.getProduto().replace(" ", "_").trim() + ".png";
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

    }

    @Override
    public void update(ProdutoModel produtoModel) {

    }

    public CompletableFuture<List<ProdutoModel>> findAll() {
        CompletableFuture<List<ProdutoModel>> future = new CompletableFuture<>();

        collectionReference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<ProdutoModel> listProdutoModel = new ArrayList<>();
            List<DocumentSnapshot> listDocuments = queryDocumentSnapshots.getDocuments();
            int length = listDocuments.size();
            for (int i = 0; i < length; i++) {
                DocumentSnapshot ds = listDocuments.get(i);

                String produto = ds.getString("produto");
                int quantidade = ds.getLong("quantidade").intValue();
                String fornecedor = ds.getString("fornecedor");
                String categoria = ds.getString("categoria");
                double varejo = ds.getDouble("varejo");
                double venda = ds.getDouble("venda");
                String descricao = ds.getString("descricao");
                String urlimage = ds.getString("urlimage");

                ProdutoModel produtoModel = new ProdutoModel();
                produtoModel.setProduto(produto);
                produtoModel.setQuantidade(quantidade);
                produtoModel.setFornecedor(fornecedor);
                produtoModel.setCategoria(categoria);
                produtoModel.setVarejo(varejo);
                produtoModel.setVenda(venda);
                produtoModel.setDescricao(descricao);
                produtoModel.setUrlImage(urlimage);

                listProdutoModel.add(produtoModel);
            }

            future.complete(listProdutoModel);
        }).addOnFailureListener(e -> future.completeExceptionally(e));

        return future;
    }

    @Override
    public CompletableFuture<List<ProdutoModel>> findByName(String name) {
        CompletableFuture<List<ProdutoModel>> future = new CompletableFuture<>();

        collectionReference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<ProdutoModel> listProdutoModel = new ArrayList<>();
            List<DocumentSnapshot> listDocuments = queryDocumentSnapshots.getDocuments();
            int length = listDocuments.size();
            for (int i = 0; i < length; i++) {
                DocumentSnapshot ds = listDocuments.get(i);

                // se conter o name
                if (ds.getString("produto").toLowerCase().contains(name.toLowerCase())) {

                    String produto = ds.getString("produto");
                    int quantidade = ds.getLong("quantidade").intValue();
                    String fornecedor = ds.getString("fornecedor");
                    String categoria = ds.getString("categoria");
                    double varejo = ds.getDouble("varejo");
                    double venda = ds.getDouble("venda");
                    String descricao = ds.getString("descricao");
                    String urlimage = ds.getString("urlimage");

                    ProdutoModel produtoModel = new ProdutoModel();
                    produtoModel.setProduto(produto);
                    produtoModel.setQuantidade(quantidade);
                    produtoModel.setFornecedor(fornecedor);
                    produtoModel.setCategoria(categoria);
                    produtoModel.setVarejo(varejo);
                    produtoModel.setVenda(venda);
                    produtoModel.setDescricao(descricao);
                    produtoModel.setUrlImage(urlimage);

                    listProdutoModel.add(produtoModel);
                }

            }

            future.complete(listProdutoModel);
        }).addOnFailureListener(e -> future.completeExceptionally(e));

        return future;
    }


    @Override
    public void delete(String name_document, OnCompleteListener<Boolean> listener) {
        storageRef.child(name_document.replace(" ", "_") + ".png".trim())
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        collectionReference.document(name_document).delete()
                                .addOnSuccessListener(result -> {
                                    listener.onComplete(Tasks.forResult(true));
                                })
                                .addOnFailureListener(result -> {
                                    listener.onComplete(Tasks.forResult(false));

                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onComplete(Tasks.forResult(false));
                    }
                });
    }


}
package br.com.ifgoiano.simplestock.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.dao.ProdutoService;
import br.com.ifgoiano.simplestock.dao.impl.ProdutoServiceImpl;
import br.com.ifgoiano.simplestock.model.ProdutoModel;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private ProdutoService produtoService;
    private List<ProdutoModel> produtos; // Lista para armazenar os produtos
    private Context context;


    public ProdutoAdapter(Context context) {
        this.context = context;
        produtos = new ArrayList<>(); // Inicializa a lista vazia
        produtoService = new ProdutoServiceImpl(this.context);
        buscarProdutos(); // Chama o método para buscar os produtos
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produtos, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        ProdutoModel p = produtos.get(position); // Obtém o produto da lista
        Glide.with(context)
                .load(p.getUrlImage())
                .into(holder.imageViewProduto);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicou", "Clicou no item");
            }
        });
        holder.textViewNomeProduto.setText(p.getProduto());
        holder.textViewQuantidade.setText(String.valueOf(p.getQuantidade()) + " em estoque");
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProduto;
        TextView textViewNomeProduto;
        TextView textViewQuantidade;
        LinearLayout linearLayout;


        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayoutItemRecyclerView);
            imageViewProduto = itemView.findViewById(R.id.imageViewFotoProdutoRecyclerViewAdapter);
            textViewNomeProduto = itemView.findViewById(R.id.editTextNomeProdutoRecyclerViewAdapter);
            textViewQuantidade = itemView.findViewById(R.id.editTextQuantidadeProdutoRecyclerViewAdapter);

        }

    }

    // Método para buscar os produtos do Firebase
    private void buscarProdutos() {
        produtoService.findAll().thenAccept(listProdutoModel -> {
            produtos.addAll(listProdutoModel); // Adiciona os produtos à lista
            notifyDataSetChanged(); // Notifica o adaptador sobre a mudança nos dados
        }).exceptionally(e -> {
            // Trate exceções, se houver
            Log.d("Teste", e.getMessage());
            return null;
        });
    }

    public void setProdutos(List<ProdutoModel> produtos) {
        this.produtos = produtos;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

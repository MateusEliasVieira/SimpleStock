package br.com.ifgoiano.simplestock.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.dao.ProdutoService;
import br.com.ifgoiano.simplestock.dao.impl.ProdutoServiceImpl;
import br.com.ifgoiano.simplestock.model.ProdutoModel;
import br.com.ifgoiano.simplestock.views.fragments.EstoqueFragment;
import br.com.ifgoiano.simplestock.views.fragments.ProdutoFragment;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private ProdutoService produtoService;
    private List<ProdutoModel> produtos; // Lista para armazenar os produtos
    private Context context;
    private FragmentManager fragmentManager;


    public ProdutoAdapter(Context context) {
        this.context = context;
        produtos = new ArrayList<>(); // Inicializa a lista vazia
        produtoService = new ProdutoServiceImpl(this.context);
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        findAllProducts(); // Chama o método para buscar os produtos
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produtos, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        int positionClick = position;
        ProdutoModel p = produtos.get(position); // Obtém o produto da lista
        Glide.with(context)
                .load(p.getUrlImage())
                .into(holder.imageViewProduto);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicou", "Clicou no item = " + positionClick);
                clickItemRecyclerView(positionClick);
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
    private void findAllProducts() {
        produtoService.findAll().thenAccept(listProdutoModel -> {
            produtos.addAll(listProdutoModel); // Adiciona os produtos à lista
            notifyDataSetChanged(); // Notifica o adaptador sobre a mudança nos dados
        }).exceptionally(e -> {
            // Trate exceções, se houver
            Log.d("Teste", e.getMessage());
            return null;
        });
    }

    private void clickItemRecyclerView(int positionClick) {
        ProdutoModel produtoModel = produtos.get(positionClick);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Produto Selecionado");
        alert.setMessage("O que deseja fazer com o produto " + produtoModel.getProduto() + "?");
        alert.setNeutralButton("Fechar", null);
        // ao clicar em editar
        alert.setPositiveButton("Editar", ((dialog, which) -> {

            ProdutoFragment produtoFragment = new ProdutoFragment();

            Bundle bundle = new Bundle();
            bundle.putString("produto", produtoModel.getProduto());
            bundle.putInt("quantidade", produtoModel.getQuantidade());
            bundle.putDouble("varejo", produtoModel.getVarejo());
            bundle.putDouble("venda", produtoModel.getVenda());
            bundle.putString("descricao", produtoModel.getDescricao());
            bundle.putString("url_imagem", produtoModel.getUrlImage());

            produtoFragment.setArguments(bundle);

            // Substitui o fragmento atual pelo ProdutoFragment
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, produtoFragment) // R.id.fragment_container é o ID do contêiner onde o fragmento deve ser exibido
                    .addToBackStack(null) // Adiciona a transação à pilha de volta para permitir voltar ao fragmento anterior
                    .commit();
        }));

        alert.setNegativeButton("Deletar", null);
        alert.show();
    }

    public void setProdutos(List<ProdutoModel> produtos) {
        this.produtos = produtos;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

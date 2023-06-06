package br.com.ifgoiano.simplestock.util;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.dao.ProdutoService;
import br.com.ifgoiano.simplestock.dao.impl.ProdutoServiceImpl;
import br.com.ifgoiano.simplestock.model.ProdutoModel;
import br.com.ifgoiano.simplestock.views.fragments.ProdutoFragment;

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

    public ProdutoAdapter(Context context, List<ProdutoModel> produtos){
        this.context = context;
        this.produtos = produtos;
        produtoService = new ProdutoServiceImpl(this.context);
        notifyDataSetChanged();
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

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produtos, parent, false);
        return new ProdutoViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        ProdutoModel p = produtos.get(position); // Obtém o produto da lista
        Glide.with(context)
                .load(p.getUrlImage())
                .into(holder.imageViewProduto);
        holder.textViewNomeProduto.setText(p.getProduto());
        holder.textViewQuantidade.setText(String.valueOf(p.getQuantidade()) + " em estoque");
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProduto;
        TextView textViewNomeProduto;
        TextView textViewQuantidade;

        public ProdutoViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            imageViewProduto = itemView.findViewById(R.id.imageViewFotoProdutoRecyclerViewAdapter);
            textViewNomeProduto = itemView.findViewById(R.id.editTextNomeProdutoRecyclerViewAdapter);
            textViewQuantidade = itemView.findViewById(R.id.editTextQuantidadeProdutoRecyclerViewAdapter);
            // Evento de click ao clicar em algum item do recycler view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProdutoFragment.class);
                    ((AppCompatActivity) context).startActivity(intent);
                    Log.d("teste","clicou");
//                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
//                    alert.setTitle("Teste");
//                    alert.setMessage("O que deseja fazer?");
//                    alert.setPositiveButton("Editar",null);
//                    alert.show();
                }
            });
        }

    }


}

package br.com.ifgoiano.simplestock.util;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

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
        View viewList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produtos, parent, false);
        return new ProdutoViewHolder(viewList);
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

    private String getValueFormat(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

        String formattedValue = decimalFormat.format(value);
        return formattedValue;
    }

    public class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProduto;
        TextView textViewNomeProduto;
        TextView textViewQuantidade;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduto = itemView.findViewById(R.id.imageViewFotoProdutoRecyclerViewAdapter);
            textViewNomeProduto = itemView.findViewById(R.id.editTextNomeProdutoRecyclerViewAdapter);
            textViewQuantidade = itemView.findViewById(R.id.editTextQuantidadeProdutoRecyclerViewAdapter);
        }

    }

}

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
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.dao.ProdutoService;
import br.com.ifgoiano.simplestock.dao.impl.ProdutoServiceImpl;
import br.com.ifgoiano.simplestock.model.ProdutoModel;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private ProdutoService produtoService;
    private Context context;

    public ProdutoAdapter(Context context) {
        this.context = context;
        produtoService = new ProdutoServiceImpl(this.context);

    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produtos, parent, false);
        return new ProdutoViewHolder(viewList);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        produtoService.findAll().thenAccept(listProdutoModel -> {

            ProdutoModel p = listProdutoModel.get(position);
            // Faça algo com a lista de produtos
            Glide.with(context)
                    .load(p.getUrlImage())
                    .into(holder.imageViewProduto);
            //holder.imageViewProduto.setBackground();
           // holder.textViewDescricao.setText("Descrição: " + p.getDescricao());
            holder.textViewNomeProduto.setText(p.getProduto());
            holder.textViewQuantidade.setText(String.valueOf(p.getQuantidade()) +" em estoque");
//            holder.textViewFornecedor.setText("Fornecedor: " + p.getFornecedor());
//            holder.textViewCategoria.setText("Categoria: " + p.getCategoria());
//            holder.textViewValorVarejo.setText("Varejo: R$" + getValueFormat(p.getVarejo()));
//            holder.textViewValorVenda.setText("Venda: R$" + getValueFormat(p.getVenda()));
        }).exceptionally(e -> {
            // Trate exceções, se houver
            Log.d("Teste", e.getMessage());
            return null;
        });

    }

    @Override
    public int getItemCount() {
        return 10;
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
        TextView textViewValorVarejo;
        TextView textViewValorVenda;
        TextView textViewDescricao;
        TextView textViewFornecedor;
        TextView textViewCategoria;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduto = itemView.findViewById(R.id.imageViewFotoProdutoRecyclerViewAdapter);
            textViewNomeProduto = itemView.findViewById(R.id.editTextNomeProdutoRecyclerViewAdapter);
            textViewQuantidade = itemView.findViewById(R.id.editTextQuantidadeProdutoRecyclerViewAdapter);
//            textViewValorVarejo = itemView.findViewById(R.id.editTextValorVarejoProdutoRecyclerViewAdapter);
//            textViewValorVenda = itemView.findViewById(R.id.editTextValorVendaProdutoRecyclerViewAdapter);
//            textViewDescricao = itemView.findViewById(R.id.editTextDescricaoProdutoRecyclerViewAdapter);
//            textViewFornecedor = itemView.findViewById(R.id.editTextFornecedorProdutoRecyclerViewAdapter);
//            textViewCategoria = itemView.findViewById(R.id.editTextCategoriaProdutoRecyclerViewAdapter);
        }

    }

}

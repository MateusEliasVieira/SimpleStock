package br.com.ifgoiano.simplestock.util;

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

import java.util.List;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.dao.ProdutoService;
import br.com.ifgoiano.simplestock.dao.impl.ProdutoServiceImpl;
import br.com.ifgoiano.simplestock.model.ProdutoModel;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private ProdutoService produtoService;

    public ProdutoAdapter(Context context) {
        produtoService = new ProdutoServiceImpl(context);
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
            // Faça algo com a lista de produtos
            ProdutoModel p = listProdutoModel.get(position);
            //holder.imageViewProduto.setBackground();
            holder.textViewDescricao.setText("Descrição: "+p.getDescricao());
            holder.textViewNomeProduto.setText("Produto: "+p.getProduto());
            holder.textViewQuantidade.setText("Quantidade: "+String.valueOf(p.getQuantidade()));
            holder.textViewFornecedor.setText("Fornecedor: "+p.getFornecedor());
            holder.textViewCategoria.setText("Categoria: "+p.getCategoria());
            holder.textViewValorVarejo.setText("Varejo: R$"+String.valueOf(p.getVarejo()));
            holder.textViewValorVenda.setText("Venda: R$"+String.valueOf(p.getVenda()));
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
            textViewValorVarejo = itemView.findViewById(R.id.editTextValorVarejoProdutoRecyclerViewAdapter);
            textViewValorVenda = itemView.findViewById(R.id.editTextValorVendaProdutoRecyclerViewAdapter);
            textViewDescricao = itemView.findViewById(R.id.editTextDescricaoProdutoRecyclerViewAdapter);
            textViewFornecedor = itemView.findViewById(R.id.editTextFornecedorProdutoRecyclerViewAdapter);
            textViewCategoria = itemView.findViewById(R.id.editTextCategoriaProdutoRecyclerViewAdapter);
        }

    }


}

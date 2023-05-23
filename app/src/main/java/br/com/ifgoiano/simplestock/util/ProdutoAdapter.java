package br.com.ifgoiano.simplestock.util;

import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.ifgoiano.simplestock.R;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produtos,parent,false);
        return new ProdutoViewHolder(viewList);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        //holder.imageViewProduto.setBackground();
        holder.textViewNomeProduto.setText("Nome produto");
        holder.textViewQuantidade.setText("Quantidade produto");
        holder.textViewValorVarejo.setText("Valor varejo");
        holder.textViewValorVenda.setText("Valor venda");
        holder.textViewFornecedor.setText("Fornecedor produto");
        holder.textViewCategoria.setText("categoria produto");
        holder.textViewDescricao.setText("Descricao produto");
    }

    @Override
    public int getItemCount() {
        return 5;
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

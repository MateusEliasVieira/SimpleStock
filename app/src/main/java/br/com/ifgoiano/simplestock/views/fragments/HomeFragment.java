package br.com.ifgoiano.simplestock.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.dao.CategoriaService;
import br.com.ifgoiano.simplestock.dao.ProdutoService;
import br.com.ifgoiano.simplestock.dao.impl.CategoriaServiceImpl;
import br.com.ifgoiano.simplestock.dao.impl.ProdutoServiceImpl;

public class HomeFragment extends Fragment {
    private ProdutoService produtoService;
    private CategoriaService categoriaService;
    private TextView textViewVarejo;
    private TextView textViewVenda;
    private TextView textViewQuantidadeCategoria;
    private TextView textViewQuantidadeProduto;
    private double varejo;
    private double venda;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualizacao_produtos, container, false);
        produtoService = new ProdutoServiceImpl(getContext());
        categoriaService = new CategoriaServiceImpl(getContext());
        textViewVarejo = view.findViewById(R.id.textViewValorEstoquePrecoVarejo);
        textViewVenda = view.findViewById(R.id.textViewValorEstoquePrecoVenda);
        textViewQuantidadeCategoria = view.findViewById(R.id.textViewQuantidadeCategoria);
        textViewQuantidadeProduto = view.findViewById(R.id.textViewQuantidadeProduto);
        varejo = 0.0;
        venda = 0.0;
        setEditTexts();
        return view;
    }

    private void setEditTexts() {
        produtoService.findAll().thenAccept(produtoModelList -> {
            produtoModelList.forEach(produtoModel -> {
                varejo = varejo + produtoModel.getVarejo();
                venda = venda + produtoModel.getVenda();
            });

            textViewVarejo.setText("R$" + getValueFormat(varejo));
            textViewVenda.setText("R$" + getValueFormat(venda));
            textViewQuantidadeProduto.setText(String.valueOf(produtoModelList.size()));
        });
        categoriaService.findAll().thenAccept(categoriaModelList -> {
            textViewQuantidadeCategoria.setText(String.valueOf(categoriaModelList.size()));
        });
    }

    private String getValueFormat(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

        String formattedValue = decimalFormat.format(value);
        return formattedValue;
    }


}
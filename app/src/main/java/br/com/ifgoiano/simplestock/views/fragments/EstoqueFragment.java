package br.com.ifgoiano.simplestock.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.dao.FornecedorService;
import br.com.ifgoiano.simplestock.dao.ProdutoService;
import br.com.ifgoiano.simplestock.dao.impl.FornecedorServiceImpl;
import br.com.ifgoiano.simplestock.dao.impl.ProdutoServiceImpl;
import br.com.ifgoiano.simplestock.util.ProdutoAdapter;


public class EstoqueFragment extends Fragment {

    private FornecedorService fornecedorService;
    private ProdutoService produtoService;
    private RecyclerView recyclerView;
    private ProdutoAdapter produtoAdapter;
    private TextView textViewVarejo;
    private TextView textViewVenda;
    private EditText editTextPesquisaEstoque;
    private Spinner spinnerFornecedorEstoque;
    private Spinner spinnerCategoriaProdutoEstoque;
    private Button buttonPesquisarProdutoEstoque;

    private double varejo;
    private double venda;

    public EstoqueFragment() {

    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estoque, container, false);
        fornecedorService = new FornecedorServiceImpl(getContext());
        produtoService = new ProdutoServiceImpl(getContext());
        textViewVarejo = view.findViewById(R.id.textViewValorEstoquePrecoVarejo);
        textViewVenda = view.findViewById(R.id.textViewValorEstoquePrecoVenda);
        editTextPesquisaEstoque = view.findViewById(R.id.editTextPesquisaEstoque);
        spinnerFornecedorEstoque = view.findViewById(R.id.spinnerFornecedorEstoque);
        spinnerCategoriaProdutoEstoque = view.findViewById(R.id.spinnerCategoriaEstoque);
        buttonPesquisarProdutoEstoque = view.findViewById(R.id.buttonPesquisarProdutoEstoque);
        varejo = 0.0;
        venda = 0.0;
        produtoAdapter = new ProdutoAdapter(getContext());
        recyclerView = view.findViewById(R.id.recyclerViewProdutosEstoque);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(produtoAdapter);
        loadSpinnerFornecedor();
        addEventSearchButton();
        setEditTexts();
        return view;
    }

    private void addEventSearchButton() {
        buttonPesquisarProdutoEstoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loadSpinnerFornecedor() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        fornecedorService.findAll().thenAccept(fornecedorModelList -> {
            adapter.add("Fornecedor");
            fornecedorModelList.forEach(fornecedorModel -> {
                adapter.add(fornecedorModel.getFornecedor());
            });
            spinnerFornecedorEstoque.setAdapter(adapter);
            adapter.notifyDataSetChanged(); // Notificar o adaptador após adicionar os itens
        });
    }

    private void setEditTexts() {
        produtoService.findAll().thenAccept(produtoModelList -> {
            produtoModelList.forEach(produtoModel -> {
                varejo = varejo + produtoModel.getVarejo();
                venda = venda + produtoModel.getVenda();
            });
            textViewVarejo.setText("Varejo: R$" + getValueFormat(varejo));
            textViewVenda.setText("Venda: R$" + getValueFormat(venda));
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
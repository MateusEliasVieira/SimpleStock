package br.com.ifgoiano.simplestock.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.dao.FornecedorService;
import br.com.ifgoiano.simplestock.dao.ProdutoService;
import br.com.ifgoiano.simplestock.dao.impl.FornecedorServiceImpl;
import br.com.ifgoiano.simplestock.dao.impl.ProdutoServiceImpl;
import br.com.ifgoiano.simplestock.model.FornecedorModel;
import br.com.ifgoiano.simplestock.model.ProdutoModel;
import br.com.ifgoiano.simplestock.util.ProdutoAdapter;


public class EstoqueFragment extends Fragment {

    private FornecedorService fornecedorService;
    private RecyclerView recyclerView;
    private ProdutoAdapter produtoAdapter;
    private EditText editTextPesquisaEstoque;
    private Spinner spinnerFornecedorEstoque;
    private Spinner spinnerCategoriaProdutoEstoque;
    private Button buttonPesquisarProdutoEstoque;

    public EstoqueFragment() {

    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estoque, container, false);
        fornecedorService = new FornecedorServiceImpl(getContext());
        editTextPesquisaEstoque = view.findViewById(R.id.editTextPesquisaEstoque);
        spinnerFornecedorEstoque = view.findViewById(R.id.spinnerFornecedorEstoque);
        spinnerCategoriaProdutoEstoque = view.findViewById(R.id.spinnerCategoriaEstoque);
        buttonPesquisarProdutoEstoque = view.findViewById(R.id.buttonPesquisarProdutoEstoque);
        produtoAdapter = new ProdutoAdapter(getContext());
        recyclerView = view.findViewById(R.id.recyclerViewProdutosEstoque);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(produtoAdapter);
        loadSpinnerFornecedor();
        addEventSearchButton();
        return view;
    }

    private void addEventSearchButton(){
        buttonPesquisarProdutoEstoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loadSpinnerFornecedor(){
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

}
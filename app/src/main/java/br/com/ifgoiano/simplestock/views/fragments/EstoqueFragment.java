package br.com.ifgoiano.simplestock.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.dao.FornecedorService;
import br.com.ifgoiano.simplestock.dao.ProdutoService;
import br.com.ifgoiano.simplestock.dao.impl.FornecedorServiceImpl;
import br.com.ifgoiano.simplestock.dao.impl.ProdutoServiceImpl;
import br.com.ifgoiano.simplestock.model.ProdutoModel;
import br.com.ifgoiano.simplestock.util.ProdutoAdapter;

public class EstoqueFragment extends Fragment {

    private FornecedorService fornecedorService;
    private ProdutoService produtoService;
    private RecyclerView recyclerView;
    private ProdutoAdapter produtoAdapter;

    private EditText editTextPesquisaEstoque;


    public EstoqueFragment() {

    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estoque, container, false);
        fornecedorService = new FornecedorServiceImpl(getContext());
        produtoService = new ProdutoServiceImpl(getContext());
        editTextPesquisaEstoque = view.findViewById(R.id.editTextPesquisaEstoque);
        produtoAdapter = new ProdutoAdapter(getContext());
        recyclerView = view.findViewById(R.id.recyclerViewProdutosEstoque);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(produtoAdapter);
        loadSpinnerFornecedor();
        addEventSearchEditText();
        return view;
    }


    private void addEventSearchEditText() {
        editTextPesquisaEstoque.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ação antes de mudar o texto
                //Log.d("DIGITO ANTES: ",s+" - start: "+start+" - count: "+count+" - after: "+after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ação durante a mudança
                // Log.d("DIGITO DURANTE: ",s+" - start: "+start+" - count: "+count);
                buscarProdutos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Log.d("DIGITO DEPOIS: ",s.toString());

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
            //spinnerFornecedorEstoque.setAdapter(adapter);
            adapter.notifyDataSetChanged(); // Notificar o adaptador após adicionar os itens
        });
    }

    private void buscarProdutos(String name) {
        List<ProdutoModel> listProdutoModel = new ArrayList<>();
        produtoService.findByName(name).thenAccept(list -> {
            produtoAdapter = new ProdutoAdapter(getContext(), list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(produtoAdapter);
        }).exceptionally(e -> {
            // Trate exceções, se houver
            Log.d("Teste", e.getMessage());
            return null;
        });
    }

}
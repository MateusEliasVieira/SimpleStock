package br.com.ifgoiano.simplestock.views.fragments;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.dao.FornecedorService;
import br.com.ifgoiano.simplestock.dao.ProdutoService;
import br.com.ifgoiano.simplestock.dao.impl.FornecedorServiceImpl;
import br.com.ifgoiano.simplestock.dao.impl.ProdutoServiceImpl;
import br.com.ifgoiano.simplestock.model.ProdutoModel;

public class ProdutoFragment extends Fragment {

    private ProdutoService produtoService;
    private FornecedorService fornecedorService;
    private EditText editTextNomeProduto;
    private Spinner spinnerCategoriaProduto;
    private Spinner spinnerFornecedor;
    private EditText editTextQuantidadeProduto;
    private EditText editTextPrecoVarejo;
    private EditText editTextPrecoVenda;
    private EditText editTextDescricaoProduto;
    private TextView textViewSelecionarImagem;
    private ImageView imageViewFotoProduto;
    private Button buttonCadastrarProduto;

    public ProdutoFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produto, container, false);
        produtoService = new ProdutoServiceImpl(getContext());
        fornecedorService = new FornecedorServiceImpl(getContext());
        editTextNomeProduto = view.findViewById(R.id.editTextNomeProduto);
        spinnerCategoriaProduto = view.findViewById(R.id.spinnerCategoriaProduto);
        spinnerFornecedor = view.findViewById(R.id.spinnerFornecedorProduto);
        editTextQuantidadeProduto = view.findViewById(R.id.editTextQuantidadeProduto);
        editTextPrecoVarejo = view.findViewById(R.id.editTextPrecoVarejo);
        editTextPrecoVenda = view.findViewById(R.id.editTextPrecoVenda);
        editTextDescricaoProduto = view.findViewById(R.id.editTextDescricaoProduto);
        textViewSelecionarImagem = view.findViewById(R.id.textViewSelecionarImagem);
        imageViewFotoProduto = view.findViewById(R.id.imageViewFotoProduto);
        imageViewFotoProduto.setDrawingCacheEnabled(true); // Habilita o cache de desenho para o ImageView
        imageViewFotoProduto.buildDrawingCache(); // Constrói o cache de desenho
        buttonCadastrarProduto = view.findViewById(R.id.buttonCadastrarProduto);
        addEventImageProduct();
        addEventButtonCad();
        loadSpinnerFornecedor();
        return view;
    }

    private void addEventImageProduct() {
        textViewSelecionarImagem.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
            alert.setTitle("Selecionar Imagem");
            alert.setMessage("Deseja selecionar a imagem da galeria?");

            alert.setNeutralButton("Remover", (dialog, which) -> imageViewFotoProduto.setImageURI(null));

            alert.setNegativeButton("Não", null);
            alert.setPositiveButton("Sim", (dialog, which) -> {
                // acessa o metodo launch do objeto e passa o caminho para abrir
                selectImageLauncher.launch("image/*");
            });

            alert.show();
        });
    }

    // Retorna um objeto para abrir a galeria
    private final ActivityResultLauncher<String> selectImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        imageViewFotoProduto.setMaxWidth(70);
                        imageViewFotoProduto.setMaxHeight(70);
                        imageViewFotoProduto.setImageURI(result);
                    }
                }
            });

    private void addEventButtonCad() {
        buttonCadastrarProduto.setOnClickListener(v -> {
            try {
                String nomeProduto = editTextNomeProduto.getText().toString();
                String categoriaProduto = spinnerCategoriaProduto.getSelectedItem().toString();
                String fornecedor = spinnerFornecedor.getSelectedItem().toString();

                String qtd = editTextQuantidadeProduto.getText().toString();
                int quantidadeProduto = 0;
                if (!qtd.isEmpty()) {
                    quantidadeProduto = Integer.parseInt(qtd);
                }

                String pv = editTextPrecoVarejo.getText().toString();
                double precoVarejo = 0;
                if (!pv.isEmpty()) {
                    precoVarejo = Double.parseDouble(pv);
                }

                String pvd = editTextPrecoVenda.getText().toString();
                double precoVenda = 0;
                if (!pvd.isEmpty()) {
                    precoVenda = Double.parseDouble(pvd);
                }

                String descricao = editTextDescricaoProduto.getText().toString();
                Bitmap imagem = imageViewFotoProduto.getDrawingCache(); // Obtém a imagem do cache de desenho em um Bitmap

                // verificando apenas campos obrigatorios
                if (nomeProduto.isEmpty()) {
                    showAlert("Informe o nome do produto!");
                } else if (categoriaProduto.isEmpty() || categoriaProduto.trim().equals("Categoria")) {
                    showAlert("Selecione a categoria do produto!");
                } else if (fornecedor.isEmpty() || fornecedor.trim().equals("Fornecedor")) {
                    showAlert("Selecione o fornecedor do produto!");
                } else if (quantidadeProduto == 0) {
                    showAlert("Informe a quantidade do produto!");
                } else if (precoVarejo == 0) {
                    showAlert("Informe o preço de custo (varejo) do produto!");
                } else if (precoVenda == 0) {
                    showAlert("Informe o preço de venda do produto!");
                } else {
                    // Tudo ok
                    String urlImage = "";
                    ProdutoModel produtoModel = new ProdutoModel(nomeProduto, categoriaProduto, fornecedor, quantidadeProduto, precoVarejo, precoVenda, descricao, urlImage, imagem);
                    // Enviar para salvar
                    produtoService.save(produtoModel, new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            if (task.isSuccessful()) {
                                showAlert("Produto salvo com sucesso!");
                                cleanViews();
                            } else {
                                showAlert("Erro ao salvar produto!");
                            }
                        }
                    });

                }
            } catch (NumberFormatException e) {
                Log.d("ERRO", e.getMessage());
                e.printStackTrace();
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
            spinnerFornecedor.setAdapter(adapter);
            adapter.notifyDataSetChanged(); // Notificar o adaptador após adicionar os itens
        });
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void cleanViews() {
        editTextNomeProduto.setText("");
        spinnerCategoriaProduto.setSelection(0);
        spinnerFornecedor.setSelection(0);
        editTextQuantidadeProduto.setText("");
        editTextPrecoVarejo.setText("");
        editTextPrecoVenda.setText("");
        editTextDescricaoProduto.setText("");
        imageViewFotoProduto.setBackground(null);
        imageViewFotoProduto.setImageURI(null);
    }


}
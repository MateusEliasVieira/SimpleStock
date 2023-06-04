package br.com.ifgoiano.simplestock.views.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.dao.CategoriaService;
import br.com.ifgoiano.simplestock.dao.impl.CategoriaServiceImpl;
import br.com.ifgoiano.simplestock.model.CategoriaModel;


public class CategoriaFragment extends Fragment {

    private EditText editTextCategoria;
    private Button buttonCadastrarCategoria;
    private CategoriaService categoriaService;

    public CategoriaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoria, container, false);
        categoriaService =  new CategoriaServiceImpl(getContext());
        editTextCategoria = view.findViewById(R.id.editTextFragmentCategoriaNovaCategoria);
        buttonCadastrarCategoria = view.findViewById(R.id.buttonFragmentCategoriaNovaCategoria);
        addEvent();
        return view;
    }

    private void addEvent(){
        buttonCadastrarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoria = editTextCategoria.getText().toString();
                if(!categoria.isEmpty()){
                    // pode salvar
                    CategoriaModel categoriaModel = new CategoriaModel();
                    categoriaModel.setCategoria(categoria);
                    categoriaService.save(categoriaModel, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                clean();
                                showAlert("Sucesso!","Nova categoria cadastrada com sucesso!");
                            }else{
                                showAlert("Aviso","Não foi possível cadastrar a nova categoria!");
                            }
                        }
                    });

                }else{
                    showAlert("Aviso","Por favor, informe a categoria!");
                }
            }
        });
    }

    private void showAlert(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void clean(){
        editTextCategoria.setText("");
    }
}
package br.com.ifgoiano.simplestock.views.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.dao.FornecedorService;
import br.com.ifgoiano.simplestock.dao.impl.FornecedorServiceImpl;
import br.com.ifgoiano.simplestock.model.FornecedorModel;

public class FornecedorFragment extends Fragment {

    private FornecedorService fornecedorService;
    private EditText editTextNomeFornecedor;
    private EditText editTextCnpjFornecedor;
    private EditText editTextEmailFornecedor;
    private EditText editTextTelefoneFornecedor;
    private Button buttonSalvar;

    public FornecedorFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fornecedor, container, false);
        fornecedorService = new FornecedorServiceImpl(getContext());
        editTextNomeFornecedor = view.findViewById(R.id.editTextNomeFornecedor);
        editTextCnpjFornecedor = view.findViewById(R.id.editTextCnpjFornecedor);
        editTextEmailFornecedor =  view.findViewById(R.id.editTextEmailFornecedor);
        editTextTelefoneFornecedor = view.findViewById(R.id.editTextTelefoneFornecedor);
        buttonSalvar = view.findViewById(R.id.buttonCadastrarFornecedor);
        addEventButtonSaveFornecedor();
        return view;
    }

    private void addEventButtonSaveFornecedor(){
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editTextNomeFornecedor.getText().toString();
                String cnpj = editTextCnpjFornecedor.getText().toString();
                String email = editTextEmailFornecedor.getText().toString();
                String telefone = editTextTelefoneFornecedor.getText().toString();
                if(nome.isEmpty() || cnpj.isEmpty() || email.isEmpty() || telefone.isEmpty()){
                    alert("Aviso","Por favor preencha todos os campos!","OK");
                }else{
                    // preparar para salvar
                    FornecedorModel fornecedorModel = new FornecedorModel();
                    fornecedorModel.setFornecedor(nome);
                    fornecedorModel.setCnpj(cnpj);
                    fornecedorModel.setEmail(email);
                    fornecedorModel.setTelefone(telefone);
                    // mandar salvar
                    fornecedorService.save(fornecedorModel, new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                clean();
                                alert("Sucesso","Novo fornecedor cadastrado com sucesso!","OK");
                            }else{
                                alert("Aviso","Ops, não foi possível concluir o cadastro do fornecedor!","Vou tentar novamente :)");
                            }
                        }
                    });
                }
            }
        });
    }

    private void alert(String title, String message, String button){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(button,null);
        alert.show();
    }

    private void clean(){
        editTextNomeFornecedor.setText("");
        editTextCnpjFornecedor.setText("");
        editTextEmailFornecedor.setText("");
        editTextTelefoneFornecedor.setText("");
    }

}
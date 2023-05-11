package br.com.ifgoiano.simplestock.views.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import br.com.ifgoiano.simplestock.R;

public class ProdutoFragment extends Fragment {

    private ImageView imageViewFotoProduto;
    public ProdutoFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produto, container, false);
        imageViewFotoProduto = view.findViewById(R.id.imageViewFotoProduto);
        addEventImageProduct();
        return view;
    }

    private void addEventImageProduct(){
        imageViewFotoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Selecionar Imagem");
                alert.setMessage("Deseja selecionar a imagem da galeria?");

                alert.setNeutralButton("Remover", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imageViewFotoProduto.setImageURI(null);
                    }
                });

                alert.setNegativeButton("Não",null);
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // acessa o metodo launch do objeto e passa o caminho para abrir
                        selectImageLauncher.launch("image/*");
                    }
                });

                alert.show();
            }
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



}
package br.com.ifgoiano.simplestock.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.views.fragments.EstoqueFragment;
import br.com.ifgoiano.simplestock.views.fragments.FornecedorFragment;
import br.com.ifgoiano.simplestock.views.fragments.ProdutoFragment;
import br.com.ifgoiano.simplestock.views.fragments.UsuarioFragment;
import br.com.ifgoiano.simplestock.views.fragments.VisualizacaoProdutosFragment;

public class HomeActivity extends AppCompatActivity {

    private FragmentContainerView fragmentContainerView;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentContainerView = findViewById(R.id.fragmentContainerView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        loadNavigation();
        loadFragmentInActivityHome(new VisualizacaoProdutosFragment());
    }

    public void loadNavigation() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.item_cadastro_usuario){
                    loadFragmentInActivityHome(new UsuarioFragment());
                }
                else if(item.getItemId() == R.id.item_cadastro_fornecedor){
                    loadFragmentInActivityHome(new FornecedorFragment());
                }
                else if(item.getItemId() == R.id.item_cadastro_produto){
                    loadFragmentInActivityHome(new ProdutoFragment());
                }
                else if(item.getItemId() == R.id.item_gerenciador_estoque){
                    loadFragmentInActivityHome(new EstoqueFragment());
                }
                else if(item.getItemId() == R.id.item_visualizar_produtos){
                    loadFragmentInActivityHome(new VisualizacaoProdutosFragment());
                }
                return false;
            }

        });
    }

    private void loadFragmentInActivityHome(Fragment obj){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainerView.getId(),obj);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
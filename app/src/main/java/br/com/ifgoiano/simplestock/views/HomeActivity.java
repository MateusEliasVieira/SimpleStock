package br.com.ifgoiano.simplestock.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu_basic_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_sair) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadNavigation() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_cadastro_usuario) {
                    loadFragmentInActivityHome(new UsuarioFragment());
                } else if (item.getItemId() == R.id.item_cadastro_fornecedor) {
                    loadFragmentInActivityHome(new FornecedorFragment());
                } else if (item.getItemId() == R.id.item_cadastro_produto) {
                    loadFragmentInActivityHome(new ProdutoFragment());
                } else if (item.getItemId() == R.id.item_gerenciador_estoque) {
                    loadFragmentInActivityHome(new EstoqueFragment());
                } else if (item.getItemId() == R.id.item_visualizar_produtos) {
                    loadFragmentInActivityHome(new VisualizacaoProdutosFragment());
                }
                return false;
            }

        });
    }

    private void loadFragmentInActivityHome(Fragment obj) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainerView.getId(), obj);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut(); // Encerramos o estado de logado lá no firebase
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent); // vamos para tela de login
        finish(); // encerramos essa atividade
    }



}
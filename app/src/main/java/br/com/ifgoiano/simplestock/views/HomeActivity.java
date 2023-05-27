package br.com.ifgoiano.simplestock.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import br.com.ifgoiano.simplestock.R;
import br.com.ifgoiano.simplestock.views.fragments.CategoriaFragment;
import br.com.ifgoiano.simplestock.views.fragments.FornecedorFragment;
import br.com.ifgoiano.simplestock.views.fragments.ProdutoFragment;
import br.com.ifgoiano.simplestock.views.fragments.UsuarioFragment;
import br.com.ifgoiano.simplestock.views.fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_view);
        }

    }

    private void logout() {
        FirebaseAuth.getInstance().signOut(); // Encerramos o estado de logado lá no firebase
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent); // vamos para tela de login
        finish(); // encerramos essa atividade
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.item_cadastro_usuario:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UsuarioFragment()).commit();
                break;
            case R.id.item_cadastro_fornecedor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FornecedorFragment()).commit();
                break;
            case R.id.item_cadastro_categoria:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CategoriaFragment()).commit();
                break;
            case R.id.item_cadastro_produto:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProdutoFragment()).commit();
                break;
            case R.id.item_sair:
                logout();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
   @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
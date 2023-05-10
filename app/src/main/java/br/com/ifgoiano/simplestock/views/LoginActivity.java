package br.com.ifgoiano.simplestock.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import br.com.ifgoiano.simplestock.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();// Esconde a barra de navegação (Action Bar)
        auth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        addEventButtonLogin();
        isLogged();
    }

    private void addEventButtonLogin(){
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v,"Informe o email e senha!",Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.show();
                }else{
                    auth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        // Logou com sucesso
                                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                Snackbar snackbar;
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if(e instanceof FirebaseAuthEmailException){
                                        snackbar = Snackbar.make(v,"Email inválido!",Snackbar.LENGTH_LONG);
                                    }
                                    if(e instanceof FirebaseAuthException){
                                        snackbar = Snackbar.make(v,"Login inválido!",Snackbar.LENGTH_LONG);
                                    }
                                    snackbar.setBackgroundTint(Color.RED);
                                    snackbar.show();
                                }
                            });
                }
            }
        });
    }

    private void isLogged(){
        // Retorna o usuario logado nesse dispositivo
        // Se estiver null, é porque não tem nenhum usuario logado
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // finalizamos o contexto atual, ou seja, a tela de Login
        }
    }



}
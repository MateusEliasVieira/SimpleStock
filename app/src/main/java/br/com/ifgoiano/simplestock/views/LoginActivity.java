package br.com.ifgoiano.simplestock.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import br.com.ifgoiano.simplestock.R;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private final String ADMINISTRADOR = "ADMINISTRADOR";
    private final String USUARIO = "USUÁRIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        isLogged();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        addEventButtonLogin();
    }

    private void addEventButtonLogin() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    snackbar(v, "Informe o email e senha!");
                } else {

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Existe o usuário
                                        loginAsTypeUser(email);
                                    } else {
                                        Log.d("teste", "Erro ao logar");
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (e instanceof FirebaseAuthEmailException) {
                                        snackbar(v, "Email inválido!");
                                    } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                        snackbar(v, "Login inválido!");
                                    } else if (e instanceof FirebaseAuthException) {
                                        snackbar(v, "Não foi possível realizar o login!");
                                    }
                                }
                            });


                }
            }
        });
    }

    private void loginAsTypeUser(String email) {
        firebaseFirestore.collection("usuario")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String acessibilidade = null;
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            acessibilidade = documentSnapshot.getString("acessibilidade");
                            if (acessibilidade.equals(USUARIO)) {
                                // logar como usuário
                                login(USUARIO);
                            } else if (acessibilidade.equals(ADMINISTRADOR)) {
                                // logar como adm
                                login(ADMINISTRADOR);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("teste", e.getMessage());
                    }
                });
    }

    private void isLogged() {
        // Retorna o usuario logado nesse dispositivo
        // Se estiver null, é porque não tem nenhum usuario logado
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            loginAsTypeUser(firebaseUser.getEmail());
        }
    }

    private void login(String acess) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("acessibilidade", acess);
        startActivity(intent);
        finish();
    }

    private void snackbar(View v, String message) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.RED);
        snackbar.show();
    }


}
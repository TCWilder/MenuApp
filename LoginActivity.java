package com.example.menuproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    EditText email_edit, password_edit;
    Button loginButton;
    TextView forgotPass_text;
    FirebaseAuth auth;
    String email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_edit = findViewById(R.id.email);
        password_edit = findViewById(R.id.password);
        forgotPass_text = findViewById(R.id.forgotPass);
        loginButton = findViewById(R.id.loginButton);
        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_edit.getText().toString().trim();
                pass = password_edit.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    email_edit.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    password_edit.setError("Password is required");
                    return;
                }

                if (pass.length() < 6) {
                    password_edit.setError("Password must be at least 8 characters");
                    return;
                }

                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,"Logged in Successfully!", Toast.LENGTH_SHORT).show();
                            CartActivity cartActivity = new CartActivity();
                            cartActivity.createCart();
                            openMainMenu();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        forgotPass_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"Sorry", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void openMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

}
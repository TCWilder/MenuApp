package com.example.menuproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {
    EditText firstName_edit, lastName_edit, email_edit, phone_edit, password_edit;
    Button createButton;
    FirebaseAuth auth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firstName_edit = findViewById(R.id.first);
        lastName_edit = findViewById(R.id.last);
        email_edit = findViewById(R.id.email);
        phone_edit = findViewById(R.id.phone);
        password_edit = findViewById(R.id.password);
        createButton = findViewById(R.id.createButton);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

// commented while testing
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = firstName_edit.getText().toString();
                String lName = lastName_edit.getText().toString();
                String email = email_edit.getText().toString();
                String phone = phone_edit.getText().toString();
                String pass = password_edit.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    email_edit.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    email_edit.setError("Password is required");
                    return;
                }

                if (pass.length() < 6) {
                    password_edit.setError("Password must be at least 8 characters");
                    return;
                }

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccountActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            userID = auth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("firstName", fName);
                            user.put("lastName", lName);
                            user.put("email", email);
                            user.put("phone", phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("*****","Success!"+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("***", "onFailure: " + e.toString());
                                }
                            });
                        } else
                            Toast.makeText(CreateAccountActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }

}
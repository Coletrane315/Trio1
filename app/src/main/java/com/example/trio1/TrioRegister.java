package com.example.trio1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class TrioRegister extends Activity {

    EditText regEmailT;
    EditText regPasswordT;
    String regEmail;
    String regPassword;
    Button createAcctButton;
    Button backToLogin;

    FirebaseAuth mAuth;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

    }

    protected void onStart(){
        super.onStart();
        regEmailT = findViewById(R.id.email);
        regPasswordT = findViewById(R.id.password);
        createAcctButton = findViewById(R.id.createButton);
        backToLogin = findViewById(R.id.backToLoginButton);
        mAuth = FirebaseAuth.getInstance();

        createAcctButton.setOnClickListener(view -> createAccount());

        backToLogin.setOnClickListener(view -> startActivity(new Intent(TrioRegister.this, TrioLogin.class)));
    }

    private void createAccount(){
        regEmail = regEmailT.getText().toString();
        regPassword = regPasswordT.getText().toString();

        if(TextUtils.isEmpty(regEmail)){
            regEmailT.setError("You must enter an email address to create an account");
            regEmailT.requestFocus();
        }else if(TextUtils.isEmpty(regPassword)){
            regPasswordT.setError("You must enter a password to create an account");
            regPasswordT.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(regEmail, regPassword).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(TrioRegister.this, "User Account Created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TrioRegister.this, MainActivity.class));
                }else{
                    Toast.makeText(TrioRegister.this,
                            "User Registration Failed: " + Objects.requireNonNull(task.getException()).getMessage(),
                            Toast.LENGTH_SHORT).show();
                    reloadPage();
                }
            });
        }
    }

    private void reloadPage(){
        regEmailT.setText("");
        regPasswordT.setText("");
        onStart();
    }
}

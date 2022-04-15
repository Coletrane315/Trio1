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

public class TrioLogin extends Activity {

    private FirebaseAuth mAuth;
    private EditText emailV;
    private EditText passV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onStart(){
        super.onStart();
        setContentView(R.layout.activity_trio_login);
        mAuth = FirebaseAuth.getInstance();

        Button loginButton = findViewById(R.id.loginButton);
        Button createAcctButton = findViewById(R.id.createAcctButton);
        emailV = findViewById(R.id.email);
        passV = findViewById(R.id.password);

        loginButton.setOnClickListener(view -> userLogin());

        createAcctButton.setOnClickListener(view -> startActivity(new Intent(TrioLogin.this, TrioRegister.class)));
    }

    private void userLogin(){
        String email = emailV.getText().toString();
        String password = passV.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailV.setError("You must enter your email address to login");
            emailV.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            passV.setError("You must enter your password to login");
            passV.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(TrioLogin.this, "User Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TrioLogin.this, MainActivity.class));
                }else{
                    Toast.makeText(TrioLogin.this,
                            "User Login Failed: " + Objects.requireNonNull(task.getException()).getMessage(),
                            Toast.LENGTH_SHORT).show();
                    reloadPage();

                }
            });
        }
    }

    private void reloadPage(){
        emailV.setText("");
        passV.setText("");
        onStart();
    }

}
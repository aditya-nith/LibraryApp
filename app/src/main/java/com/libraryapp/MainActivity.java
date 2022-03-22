package com.libraryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    
    private MaterialButton continueBtn;
    private EditText emailET;
    private EditText passET;
    private TextView infoTxt;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
            launchHomePage();
        else {
            setContentView(R.layout.activity_main);
            findViews();
            setUpListeners();
        }
    }

    private void launchHomePage() {
        String email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
        assert email != null;
        Intent intent;
        intent = new Intent(this, StudentActivity.class);

        startActivity(intent);
        this.finish();
    }

    private void findViews() {
        continueBtn = findViewById(R.id.loginBtn);
        emailET = findViewById(R.id.emailEt);
        passET = findViewById(R.id.passEt);
        infoTxt = findViewById(R.id.topTe);
    }

    private void setUpListeners() {
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty()){
                    login();
                }
            }
        });
    }

    private void login() {
        disable();
        String email = emailET.getText().toString();
        String password = passET.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            launchHomePage();
                        } else {
                            enable();
                            infoTxt.setText("Some issue: " +
                                    task.getException().getMessage());
                        }
                    }
                });

    }

    private void disable(){
        passET.setEnabled(false);
        continueBtn.setEnabled(false);
        emailET.setEnabled(false);
    }

    private void enable(){
        passET.setEnabled(true);
        continueBtn.setEnabled(true);
        emailET.setEnabled(true);
    }

    private boolean isEmpty(){
        if (passET.getText().toString().isEmpty()){
            passET.setError("Password is required!");
            return true;
        }

        passET.setError(null);

        if (emailET.getText().toString().isEmpty()){
            emailET.setError("Email is required");
            return true;
        }

        emailET.setError(null);
        return false;
    }
}
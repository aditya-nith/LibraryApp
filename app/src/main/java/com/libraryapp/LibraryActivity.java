package com.libraryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.libraryapp.Models.Book;
import com.libraryapp.Models.IssueClass;

import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity {

    private Button uploadBtn;
    private EditText nameEt;
    private EditText authorEt;
    private EditText linkEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        findViews();

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process(false);
                verify();
            }
        });
    }

    private void findViews() {
        uploadBtn = findViewById(R.id.uploadBtn);
        nameEt = findViewById(R.id.nameEt);
        authorEt = findViewById(R.id.authorEt);
        linkEt = findViewById(R.id.imageLinkEt);
    }

    private void verify() {
        if (nameEt.getText().toString().isEmpty()){
            nameEt.setError("Required");
            process(true);
            return;
        }
        nameEt.setError(null);
        String id = FirebaseFirestore.getInstance().collection("books")
                .document()
                .getId();
        Book book = new Book(id, nameEt.getText().toString(),
                authorEt.getText().toString(), new ArrayList<IssueClass>(), linkEt.getText().toString());

        FirebaseFirestore.getInstance()
                .collection("books")
                .document(id)
                .set(book)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        process(true);
                        String message;
                        if (task.isSuccessful()){
                            message = "New book is successfully created with id: "
                                    + id;
                            nameEt.setText("");
                            linkEt.setText("");
                            authorEt.setText("");
                        } else {
                            message = "Some error occurred: " + task.getException().getMessage();
                        }
                        Toast.makeText(LibraryActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void process(boolean enable){
        uploadBtn.setEnabled(enable);
        nameEt.setEnabled(enable);
        authorEt.setEnabled(enable);
        linkEt.setEnabled(enable);
    }
}
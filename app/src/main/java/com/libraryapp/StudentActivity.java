package com.libraryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.libraryapp.Adapaters.BooksAdapter;
import com.libraryapp.Models.Book;
import com.libraryapp.Models.StudentExtras;
import com.libraryapp.Models.User;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        mAuth = FirebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser().getEmail().contains("+library")) {
            user = new User();
            user.setName("library");
            user.setType("library");

            ((TextView) findViewById(R.id.headerText)).setText("You can find the list of books below with students who have borrowed them");

            firestore.collection("books")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                findViewById(R.id.progressBar).setVisibility(View.GONE);
                                if (task.getResult() != null) {
                                    ArrayList<Book> books = new ArrayList<>();
                                    for (DocumentSnapshot documentSnapshot : task.getResult()){
                                        books.add(documentSnapshot.toObject(Book.class));
                                    }
                                    showUser(books);
                                }
                            }else {
                                Toast.makeText(StudentActivity.this, "Some error occurred: "
                                        + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            firestore.collection("users")
                    .document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists() && task.getResult() != null) {
                                    user = task.getResult().toObject(User.class);
                                    showUser(user.getStudentExtras().getBooksIssued());
                                } else {
                                    generateNewUser();
                                }
                            } else {
                                Toast.makeText(StudentActivity.this, "Some error occurred:" +
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void showUser(ArrayList<Book> books) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        BooksAdapter adapter = new BooksAdapter(user, books);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.GONE);
    }

    private void generateNewUser(){
        ProgressBar progressBar = findViewById(R.id.progressBar);
        user = new User(mAuth.getCurrentUser()
        .getEmail().split("@")[0], "student", null, new StudentExtras(
                mAuth.getCurrentUser()
                .getUid(),
                new ArrayList<Book>(),
                new ArrayList<Book>()
        ));
        firestore.collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(StudentActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}
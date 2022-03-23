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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.libraryapp.Models.Book;
import com.libraryapp.Models.IssueClass;
import com.libraryapp.Models.User;

import java.util.ArrayList;

public class BorrowBook extends AppCompatActivity {

    private Button uploadBtn;
    private EditText nameEt;
    private EditText returnEt;
    private EditText issueEt;
    private EditText userID;
    private EditText remarkEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_book);
        findViews();

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
            }
        });
    }

    private void verify() {
        if (nameEt.getText().toString().isEmpty()){
            nameEt.setError("Required");
            return;
        }

        if(userID.getText().toString().isEmpty()){
            userID.setError(null);
            return;
        }

        nameEt.setError(null);
        userID.setError(null);

        FirebaseFirestore.getInstance().collection("books")
                .document(getIntent().getStringExtra("bookId"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            Book book = task.getResult().toObject(Book.class);
                            upload(book);
                        } else {
                            Toast.makeText(BorrowBook.this, "Some error occurred: "
                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void upload(Book book) {
        FirebaseFirestore.getInstance().collection("users")
                .document(userID.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            if (task.getResult().exists()){
                                User user = task.getResult().toObject(User.class);
                                IssueClass issueClass = new IssueClass(user.getName(), issueEt.getText().toString(),
                                        returnEt.getText().toString(), remarkEt.getText().toString(), nameEt.getText().toString());
                                book.getIssueClasses().add(issueClass);
                                uploadBook(book, user, issueClass);
                            } else {
                                process(true);
                                Toast.makeText(BorrowBook.this, "Email Id does not exist", Toast.LENGTH_SHORT).show();
                                userID.setError("Check email address");
                            }
                        } else {
                            process(true);
                            Toast.makeText(BorrowBook.this, "Some error occurred: "
                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void uploadBook(Book book, User user, IssueClass issueClass) {
        FirebaseFirestore.getInstance().collection("books")
                .document(book.getId())
                .set(book)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            ArrayList<IssueClass> issueClasses = new ArrayList<IssueClass>();
                            issueClasses.add(issueClass);
                            book.setIssueClasses(issueClasses);
                            user.getStudentExtras().getBooksIssued().add(book);
                            FirebaseFirestore.getInstance().collection("users")
                                    .document(user.getName())
                                    .set(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(BorrowBook.this, "Successfull", Toast.LENGTH_SHORT).show();
                                            }
                                            process(true);
                                        }
                                    });
                        } else {
                            process(true);
                            Toast.makeText(BorrowBook.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void findViews() {
        uploadBtn = findViewById(R.id.uploadBtn);
        nameEt = findViewById(R.id.nameEt);
        remarkEt = findViewById(R.id.remarksEt);
        returnEt = findViewById(R.id.returnEt);
        issueEt = findViewById(R.id.issueDate);
        userID = findViewById(R.id.userIdEt);
    }

    private void process(boolean enable){
        uploadBtn.setEnabled(enable);
        nameEt.setEnabled(enable);
        returnEt.setEnabled(enable);
        issueEt.setEnabled(enable);
        userID.setEnabled(enable);
        remarkEt.setEnabled(enable);
    }
}
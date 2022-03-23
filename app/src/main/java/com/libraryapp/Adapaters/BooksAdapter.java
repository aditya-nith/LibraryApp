package com.libraryapp.Adapaters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.libraryapp.BorrowBook;
import com.libraryapp.Models.Book;
import com.libraryapp.Models.IssueClass;
import com.libraryapp.Models.User;
import com.libraryapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {

    private User user;
    private ArrayList<Book> books;
    private Activity context;

    public BooksAdapter(User user, ArrayList<Book> books, Activity context) {
        this.user = user;
        this.books = books;
        this.context = context;
    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_books, parent, false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  BooksAdapter.MyViewHolder holder, int position) {
        if (books.get(position).getImageLink() != null
                && !books.get(position).getImageLink().isEmpty()){
            Picasso.get()
                    .load(books.get(position).getImageLink())
                    .into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
        holder.headerTxt.setText(books.get(position).getName());

        if (user.getType().equals("student")) {

            String desTxt = "";
            for (IssueClass issueClass : books.get(position).getIssueClasses()) {
                desTxt += "Issue Date - " + issueClass.getIssuedAt()
                        + "\n" +
                        "Return Date - " +issueClass.getReturnBy();
            }

            holder.descriptionTxt.setText(desTxt);
            holder.add.setVisibility(View.GONE);

        } else {
            String des = "";
            for (IssueClass issueClass : books.get(position).getIssueClasses()) {
                des += issueClass.getStudentName() +"\n" +

                        "Issue Date - " + issueClass.getIssuedAt()
                        + "\n" +
                        "Return Date - " +issueClass.getReturnBy()
                        +"\n\n";
            }

            holder.descriptionTxt.setText(des);
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    launchBorrowActivity(position);
                }
            });
        }

    }

    private void launchBorrowActivity(int position) {
        Intent intent = new Intent(context, BorrowBook.class);
        intent.putExtra("bookId", books.get(position).getId());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView headerTxt;
        TextView descriptionTxt;
        ImageView imageView;
        Button add;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTxt = itemView.findViewById(R.id.header_title);
            imageView = itemView.findViewById(R.id.imageView);
            descriptionTxt = itemView.findViewById(R.id.description);
            add = itemView.findViewById(R.id.addBorrower);
        }
    }
}

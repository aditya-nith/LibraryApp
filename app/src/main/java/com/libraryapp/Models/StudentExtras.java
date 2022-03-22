package com.libraryapp.Models;

import java.util.ArrayList;

public class StudentExtras {
    String rollNumber;
    ArrayList<Book> booksIssued;
    ArrayList<Book> booksReturned;

    public StudentExtras(){

    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public ArrayList<Book> getBooksIssued() {
        return booksIssued;
    }

    public void setBooksIssued(ArrayList<Book> booksIssued) {
        this.booksIssued = booksIssued;
    }

    public ArrayList<Book> getBooksReturned() {
        return booksReturned;
    }

    public void setBooksReturned(ArrayList<Book> booksReturned) {
        this.booksReturned = booksReturned;
    }

    public StudentExtras(String rollNumber, ArrayList<Book> booksIssued, ArrayList<Book> booksReturned) {
        this.rollNumber = rollNumber;
        this.booksIssued = booksIssued;
        this.booksReturned = booksReturned;
        if (booksIssued == null){
            this.booksIssued = new ArrayList<Book>();
        }

        if (booksReturned == null){
            this.booksReturned = new ArrayList<Book>();
        }
    }
}

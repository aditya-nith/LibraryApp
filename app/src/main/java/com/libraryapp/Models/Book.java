package com.libraryapp.Models;

import java.util.ArrayList;

public class Book {
    String id;
    String name;
    String author;

    ArrayList<IssueClass> issueClasses;

    public ArrayList<IssueClass> getIssueClasses() {
        return issueClasses;
    }

    public void setIssueClasses(ArrayList<IssueClass> issueClasses) {
        this.issueClasses = issueClasses;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    String imageLink;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public Book(String id, String name, String author, ArrayList<IssueClass> issueClasses, String imageLink) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.imageLink = imageLink;
        this.issueClasses = issueClasses;
    }

    public Book(){}

}

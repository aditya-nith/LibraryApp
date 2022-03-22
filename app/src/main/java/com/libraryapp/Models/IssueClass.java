package com.libraryapp.Models;

public class IssueClass{
    String uid;
    String issuedAt;
    String returnBy;
    String remarks;
    String studentName;

    public IssueClass(){

    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getReturnBy() {
        return returnBy;
    }

    public void setReturnBy(String returnBy) {
        this.returnBy = returnBy;
    }

    public IssueClass(String u, String i, String r, String rm, String st){
        uid = u;
        issuedAt = i;
        returnBy = r;
        remarks = rm;
        studentName = st;
    }
}

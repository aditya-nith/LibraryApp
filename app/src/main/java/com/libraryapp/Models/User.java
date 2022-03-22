package com.libraryapp.Models;

public class User {

    String name;
    String type;
    TeacherExtras teacherExtras;
    StudentExtras studentExtras;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TeacherExtras getTeacherExtras() {
        return teacherExtras;
    }

    public void setTeacherExtras(TeacherExtras teacherExtras) {
        this.teacherExtras = teacherExtras;
    }

    public StudentExtras getStudentExtras() {
        return studentExtras;
    }

    public void setStudentExtras(StudentExtras studentExtras) {
        this.studentExtras = studentExtras;
    }

    public User(String name, String type, TeacherExtras teacherExtras, StudentExtras studentExtras) {
        this.name = name;
        this.type = type;
        this.teacherExtras = teacherExtras;
        this.studentExtras = studentExtras;
    }
}

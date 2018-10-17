package com.example.android.quizzy.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Student {

    private String firstName;
    private String lastName;
    private String academicYear;
    private String city;
    private String teacherTelephoneNumber;


    public Student(@NonNull String firstName, @NonNull String lastName, @Nullable String academicYear, @Nullable String city, @NonNull String teacherTelephoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.academicYear = academicYear;
        this.city = city;
        this.teacherTelephoneNumber = teacherTelephoneNumber;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTeacherTelephoneNumber() {
        return teacherTelephoneNumber;
    }

    public void setTeacherTelephoneNumber(String teacherTelephoneNumber) {
        this.teacherTelephoneNumber = teacherTelephoneNumber;
    }

}

package com.example.android.quizzy.model;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class Teacher {

    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String subject;
    private String city;


    public Teacher(@NonNull String firstName, @NonNull String lastName, @NonNull String telephoneNumber, @NonNull String subject, @Nullable String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.subject = subject;
        this.city = city;
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

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}

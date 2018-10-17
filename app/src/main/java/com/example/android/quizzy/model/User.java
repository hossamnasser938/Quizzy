package com.example.android.quizzy.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class User {

    private String firstName;
    private String lastName;
    private String city;

    public User(@NonNull String firstName, @NonNull String lastName, @Nullable String city) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

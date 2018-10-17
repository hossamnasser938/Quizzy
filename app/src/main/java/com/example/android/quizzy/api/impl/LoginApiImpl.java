package com.example.android.quizzy.api.impl;

import com.example.android.quizzy.api.LoginApi;
import com.example.android.quizzy.model.Student;
import com.example.android.quizzy.model.Teacher;
import com.example.android.quizzy.model.User;
import com.example.android.quizzy.util.Constants;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Completable;
import io.reactivex.Maybe;

public class LoginApiImpl implements LoginApi {

    @Override
    public Maybe<AuthResult> registerInFirebaseAuth(String email, String password) {

        return RxFirebaseAuth.createUserWithEmailAndPassword(FirebaseAuth.getInstance(), email, password);

    }

    @Override
    public Completable registerInFirebaseDatabase(User user) {

        if(user instanceof Teacher){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS_KEY).child(Constants.TEACHERS_KEY).child(((Teacher) user).getTelephoneNumber());
            return RxFirebaseDatabase.setValue(reference, user);
        }
        else if(user instanceof Student){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS_KEY).child(Constants.TEACHERS_KEY).child(((Student) user).getTeacherTelephoneNumber()).child(Constants.STUDENTS_KEY).child(user.getId());
            return RxFirebaseDatabase.setValue(reference, user);
        }
        return Completable.error(new Throwable());

    }

    @Override
    public Maybe<AuthResult> login(String email, String password) {

        return RxFirebaseAuth.signInWithEmailAndPassword(FirebaseAuth.getInstance(), email, password);

    }

}

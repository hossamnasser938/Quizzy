package com.example.android.quizzy.api.impl;

import com.example.android.quizzy.api.LoginApi;
import com.example.android.quizzy.model.Student;
import com.example.android.quizzy.model.Teacher;
import com.example.android.quizzy.model.User;
import com.example.android.quizzy.util.Constants;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

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

    /**
     * checks if the teacher with the given telephone number exists in fire-base database or not
     * @param teacherTelephoneNumber the given telephone number
     * @return Single that indicate whether the teacher exists or not or an error occurred while accessing database
     */
    @Override
    public Single<Boolean> teacherExists(final String teacherTelephoneNumber) {

        final DatabaseReference teachersReference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS_KEY).child(Constants.TEACHERS_KEY);

        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final SingleEmitter<Boolean> emitter) throws Exception {
                teachersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(teacherTelephoneNumber)){
                            emitter.onSuccess(true);
                        }
                        else {
                            emitter.onSuccess(false);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(new Throwable(databaseError.getMessage()));
                    }
                });
            }
        });

    }

    /**
     * given a user's id, it return from database either Teacher object or Student object
     * @param id the id of the teacher or student
     * @return
     */
    @Override
    public Single<User> getUser(final String id){

        final DatabaseReference teachersReference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS_KEY).child(Constants.TEACHERS_KEY);

        return Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(final SingleEmitter<User> emitter) {
                teachersReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Teacher teacher = snapshot.getValue(Teacher.class);
                            if(teacher.getId().contentEquals(id)) {
                                emitter.onSuccess(teacher);
                            }
                            else {
                                DatabaseReference studentsReference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS_KEY).child(Constants.TEACHERS_KEY).child(teacher.getTelephoneNumber()).child(Constants.STUDENTS_KEY);
                                studentsReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild(id)) {
                                            Student student = dataSnapshot.child(id).getValue(Student.class);
                                            emitter.onSuccess(student);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        emitter.onError(new Throwable(databaseError.getMessage()));
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(new Throwable(databaseError.getMessage()));
                    }
                });
            }
        });
    }
}

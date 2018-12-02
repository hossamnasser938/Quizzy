package com.example.android.quizzy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.quizzy.R;
import com.example.android.quizzy.util.Constants;

public class TeacherActivity extends AppCompatActivity {

    private String teacherTelephoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        //Get teacher telephone
        Intent intent = getIntent();
        teacherTelephoneNumber = intent.getExtras().getString(Constants.TELEPHONE_NUMBER_KEY);

        if(teacherTelephoneNumber == null){
            Toast.makeText(this, "Error getting teacher info", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Got teacher number = " + teacherTelephoneNumber, Toast.LENGTH_LONG).show();
        }
    }
}

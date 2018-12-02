package com.example.android.quizzy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.quizzy.R;
import com.example.android.quizzy.util.Constants;

public class StudentActivity extends AppCompatActivity {

    private String teacherTelephoneNumber;
    private String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        //Get teacher telephone and student name
        Intent intent = getIntent();
        teacherTelephoneNumber = intent.getExtras().getString(Constants.TEACHER_TELEPHONE_NUMBER_KEY);
        studentName = intent.getExtras().getString(Constants.STUDENT_NAME_KEY);

        if(teacherTelephoneNumber == null || studentName == null){
            Toast.makeText(this, "Error getting student info", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Got teacher number = " + teacherTelephoneNumber + ", student name = " + studentName, Toast.LENGTH_LONG).show();
        }
    }
}

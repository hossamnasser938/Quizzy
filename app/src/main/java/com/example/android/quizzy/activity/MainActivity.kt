package com.example.android.quizzy.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android.quizzy.R
import com.example.android.quizzy.util.Constants
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check if user logged or still needs
        if(FirebaseAuth.getInstance().currentUser == null){
            //check sdk
            if(android.os.Build.VERSION.SDK_INT < 19){
                val intent = Intent(applicationContext, IntroActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(applicationContext, WalkThroughActivty::class.java)
                startActivity(intent)
            }
        }
        else {
            //Check getting number from login/register
            var isTeacher: Boolean? = null
            var gotTeacherNumber: String? = null
            var gotStudentName: String? = null

            if (intent.extras.containsKey(Constants.TELEPHONE_NUMBER_KEY)) { //Teacher logged
                isTeacher = true
                gotTeacherNumber = intent.extras[Constants.TELEPHONE_NUMBER_KEY] as String
            }

            if (intent.extras.containsKey(Constants.TEACHER_TELEPHONE_NUMBER_KEY)) { //Student logged
                isTeacher = false
                gotTeacherNumber = intent.extras[Constants.TEACHER_TELEPHONE_NUMBER_KEY] as String
                gotStudentName = intent.extras[Constants.STUDENT_NAME_KEY] as String
            }

            if(isTeacher != null){
                Toast.makeText(this, "Got teacher number = " + gotTeacherNumber + " and isTeacher : " + isTeacher, Toast.LENGTH_LONG).show()
                if(!isTeacher)
                    Toast.makeText(this, "Got student name = " + gotStudentName, Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "Error getting number", Toast.LENGTH_LONG).show()
            }
        }
    }
}

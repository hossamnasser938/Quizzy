package com.example.android.quizzy.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.android.quizzy.R
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
    }
}

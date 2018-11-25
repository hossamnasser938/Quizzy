package com.example.android.quizzy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.android.quizzy.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    AppIntroFragment page1 = AppIntroFragment.newInstance("No paper", "Quizzes can be done with no paper", R.drawable.teacher, getResources().getColor(R.color.colorPrimary));
    AppIntroFragment page2 = AppIntroFragment.newInstance("Digitize your work", "Quizzes can be digitalized through mobile application", R.drawable.teacher, getResources().getColor(R.color.colorPrimary));
    AppIntroFragment page3 = AppIntroFragment.newInstance("Keep in touch", "Keep connected with your students anywhere, and anytime", R.drawable.teacher, getResources().getColor(R.color.colorPrimary));
    AppIntroFragment page4 = AppIntroFragment.newInstance("Promote the process", "Generate reports on students' attitudes to promote the learning process", R.drawable.teacher, getResources().getColor(R.color.colorPrimary));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add pages
        addSlide(page1);
        addSlide(page2);
        addSlide(page3);
        addSlide(page4);

        //Show Skip & Done button
        showSkipButton(true);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        navigateToLoginActivity();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        navigateToLoginActivity();
    }

    private void navigateToLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

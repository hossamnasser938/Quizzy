package com.example.android.quizzy.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.quizzy.R;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughActivity;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughCard;

import java.util.ArrayList;
import java.util.List;

public class WalkThroughActivty extends FancyWalkthroughActivity {

    FancyWalkthroughCard page1 = new FancyWalkthroughCard("No paper", "Quizzes can be done with no paper");
    FancyWalkthroughCard page2 = new FancyWalkthroughCard("Digitize your work", "Quizzes can be digitalized through mobile application");
    FancyWalkthroughCard page3 = new FancyWalkthroughCard("Keep in touch", "Keep connected with your students anywhere, and anytime");
    FancyWalkthroughCard page4 = new FancyWalkthroughCard("Promote the process", "Generate reports on students' attitudes to promote the learning process");

    List<FancyWalkthroughCard> pages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add cards to the list
        pages.add(page1);
        pages.add(page2);
        pages.add(page3);
        pages.add(page4);

        setOnboardPages(pages);

        setImageBackground(R.drawable.teacher);
    }

    @Override
    public void onFinishButtonPressed() {
        //Navigate to Login Activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

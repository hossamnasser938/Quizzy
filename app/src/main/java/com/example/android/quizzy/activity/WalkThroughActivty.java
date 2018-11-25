package com.example.android.quizzy.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.quizzy.R;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughActivity;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughCard;

import java.util.ArrayList;
import java.util.List;

public class WalkThroughActivty extends FancyWalkthroughActivity {

    FancyWalkthroughCard card1 = new FancyWalkthroughCard("No paper", "Quizzes can be done with no paper");
    FancyWalkthroughCard card2 = new FancyWalkthroughCard("Digitize your work", "Quizzes can be digitalized through mobile application");
    FancyWalkthroughCard card3 = new FancyWalkthroughCard("Keep in touch", "Keep connected with your students anywhere, and anytime");
    FancyWalkthroughCard card4 = new FancyWalkthroughCard("Promote the process", "Generate reports on students' attitudes to promote the learning process");

    List<FancyWalkthroughCard> cardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add cards to the list
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);

        setImageBackground(R.drawable.digital_teacher);
    }

    @Override
    public void onFinishButtonPressed() {
        //Navigate to Login Activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

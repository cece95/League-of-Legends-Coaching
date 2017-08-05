package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.SwipeListener;
import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.R;

public class CoachArea extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_area);

        LoggedUser loggedUser = LoggedUser.getIstance(null, null, false);

        final TextView welcome = (TextView) findViewById(R.id.welcomeMessage);
        welcome.setText("Welcome " + loggedUser.getIgn());

        RelativeLayout finestra = (RelativeLayout) findViewById(R.id.relative_coachArea);
        if (loggedUser.isCoach()) {
            finestra.setOnTouchListener(new SwipeListener(CoachArea.this) {
                @Override
                public void onSwipeRight() {
                    Intent coachArea = new Intent(CoachArea.this, StudentArea.class);
                    startActivity(coachArea);
                    finish();
                }
            });
        }
    }
}

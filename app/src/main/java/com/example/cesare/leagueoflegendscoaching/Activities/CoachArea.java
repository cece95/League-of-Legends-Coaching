package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.ShakeDetector;
import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.SwipeListener;
import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.R;

public class CoachArea extends Activity {

    private ShakeDetector shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_area);
        shaker = new ShakeDetector(this);

        LoggedUser loggedUser = LoggedUser.getIstance(null, null, false, this);

        final TextView welcome = (TextView) findViewById(R.id.welcomeMessage);
        welcome.setText("Welcome " + loggedUser.getIgn());

        RelativeLayout finestra = (RelativeLayout) findViewById(R.id.relative_coachArea);
        finestra.setOnTouchListener(new SwipeListener(CoachArea.this) {
                @Override
                public void onSwipeRight() {
                    Intent coachArea = new Intent(CoachArea.this, StudentArea.class);
                    startActivity(coachArea);
                    finish();
                }
            });

        Button appointmentsButton = (Button) findViewById(R.id.apppointments_button);
        appointmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoachArea.this, Appointments.class);
                startActivity(intent);
            }
        });

        Button modifyButton = (Button) findViewById(R.id.modify_button);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoachArea.this, UpdateInfo.class);
                startActivity(intent);
            }
        });

        Button logout = (Button) findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoachArea.this, Home.class);
                LoggedUser.logout(v.getContext());
                startActivity(intent);
                finish();
            }
        });
    }
}

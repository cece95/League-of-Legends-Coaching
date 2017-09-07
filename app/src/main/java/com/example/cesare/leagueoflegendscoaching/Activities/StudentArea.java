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

public class StudentArea extends Activity {

    private ShakeDetector shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_area);
        shaker = new ShakeDetector(this);

        final LoggedUser loggedUser = LoggedUser.getIstance(null, null, false, this);

        final TextView welcome = (TextView) findViewById(R.id.welcomeMessage);
        welcome.setText("Welcome "+loggedUser.getIgn());

        RelativeLayout finestra = (RelativeLayout) findViewById(R.id.relative_studentArea);

            finestra.setOnTouchListener(new SwipeListener(StudentArea.this){
                @Override
                public void onSwipeLeft() {
                    if (loggedUser.isCoach()) {
                        Intent coachArea = new Intent(StudentArea.this, CoachArea.class);
                        startActivity(coachArea);
                        finish();
                    }
                    else{
                        Intent upgrade = new Intent(StudentArea.this, CoachUpgrade.class);
                        startActivity(upgrade);
                        finish();
                    }
                }
            });


        Button findCoach = (Button) findViewById(R.id.findCoach_button);
        findCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentArea.this, SearchCoach.class);
                startActivity(intent);
            }
        });

        Button booking = (Button) findViewById(R.id.booking_button);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentArea.this, Booking.class);
                startActivity(intent);
            }
        });

        Button logout = (Button) findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentArea.this, Home.class);
                LoggedUser.logout(v.getContext());
                startActivity(intent);
                finish();
            }
        });
    }
}

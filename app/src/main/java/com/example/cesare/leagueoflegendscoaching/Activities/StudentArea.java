package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.SwipeListener;
import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.R;

public class StudentArea extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_area);

        LoggedUser loggedUser = LoggedUser.getIstance(null, null, false);

        final TextView welcome = (TextView) findViewById(R.id.welcomeMessage);
        welcome.setText("Welcome "+loggedUser.getIgn());

        RelativeLayout finestra = (RelativeLayout) findViewById(R.id.relative_studentArea);
        if (loggedUser.isCoach()){
            finestra.setOnTouchListener(new SwipeListener(StudentArea.this){
                @Override
                public void onSwipeLeft() {
                    Intent coachArea = new Intent(StudentArea.this, CoachArea.class);
                    startActivity(coachArea);
                    finish();
                }
            });
        }

        Button findCoach = (Button) findViewById(R.id.findCoach_button);
        findCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentArea.this, SearchCoach.class);
                startActivity(intent);
            }
        });
    }
}

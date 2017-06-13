package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cesare.leagueoflegendscoaching.R;
import com.example.cesare.leagueoflegendscoaching.SwipeListener;

public class StudentArea extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_area);

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        boolean isCoach = intent.getBooleanExtra("isCoach", false);

        final TextView welcome = (TextView) findViewById(R.id.welcomeMessage);
        welcome.setText("Welcome "+user);

        RelativeLayout finestra = (RelativeLayout) findViewById(R.id.relative_studentArea);
        if (isCoach){
            finestra.setOnTouchListener(new SwipeListener(StudentArea.this, user, isCoach){
                @Override
                public void onSwipeLeft() {
                    Intent coachArea = new Intent(StudentArea.this, CoachArea.class);
                    coachArea.putExtra("user", user);
                    coachArea.putExtra("isCoach", isCoach);
                    startActivity(coachArea);
                }
            });
        }

        Button findCoach = (Button) findViewById(R.id.findCoach_button);
        findCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentArea.this, SearchCoach.class);
            }
        });
    }
}

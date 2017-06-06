package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cesare.leagueoflegendscoaching.R;

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

    }
}

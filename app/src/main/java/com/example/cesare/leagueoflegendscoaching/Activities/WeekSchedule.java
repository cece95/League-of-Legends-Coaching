package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.ShakeDetector;
import com.example.cesare.leagueoflegendscoaching.R;

public class WeekSchedule extends Activity {

    private ShakeDetector shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_schedule);
        shaker = new ShakeDetector(this);

        final Button mondayButton = (Button) findViewById(R.id.changeScheduleMonday);
        mondayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeekSchedule.this, DaySchedule.class);
                intent.putExtra("dayId", 0);
                startActivity(intent);
            }
        });

        final Button tuesdayButton = (Button) findViewById(R.id.changeScheduleTuesday);
        tuesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeekSchedule.this, DaySchedule.class);
                intent.putExtra("dayId", 1);
                startActivity(intent);
            }
        });

        final Button wednesdayButton = (Button) findViewById(R.id.changeScheduleWednesday);
        wednesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeekSchedule.this, DaySchedule.class);
                intent.putExtra("dayId", 2);
                startActivity(intent);
            }
        });

        final Button thursdayButton = (Button) findViewById(R.id.changeScheduleThursday);
        thursdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeekSchedule.this, DaySchedule.class);
                intent.putExtra("dayId", 3);
                startActivity(intent);
            }
        });

        final Button fridayButton = (Button) findViewById(R.id.changeScheduleFriday);
        fridayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeekSchedule.this, DaySchedule.class);
                intent.putExtra("dayId", 4);
                startActivity(intent);
            }
        });

        final Button saturdayButton = (Button) findViewById(R.id.changeScheduleSaturday);
        saturdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeekSchedule.this, DaySchedule.class);
                intent.putExtra("dayId", 5);
                startActivity(intent);
            }
        });

        final Button sundayButton = (Button) findViewById(R.id.changeScheduleSunday);
        sundayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeekSchedule.this, DaySchedule.class);
                intent.putExtra("dayId", 6);
                startActivity(intent);
                finish();
            }
        });

        final Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}

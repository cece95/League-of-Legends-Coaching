package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cesare.leagueoflegendscoaching.R;

public class WeekSchedule extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_schedule);
        // test
        final Button mondayButton = (Button) findViewById(R.id.changeScheduleMonday);
        mondayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeekSchedule.this, DaySchedule.class);
                intent.putExtra("dayId", 0);
                startActivity(intent);
            }
        });
    }


}

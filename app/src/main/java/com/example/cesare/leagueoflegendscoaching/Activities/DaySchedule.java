package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.ShakeDetector;
import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.Schedule;
import com.example.cesare.leagueoflegendscoaching.R;

public class DaySchedule extends Activity {

    private ShakeDetector shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_schedule);
        shaker = new ShakeDetector(this);

        Intent intent = getIntent();
        final int dayId = intent.getIntExtra("dayId", 0);

        loadDay(Schedule.getIstance().getDay(dayId));

        Button save_button = (Button) findViewById(R.id.saveDay);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Schedule s = Schedule.getIstance();
                s.setDay(dayId, getHours());
                onBackPressed();
                finish();
            }
        });
    }

    private boolean[] getHours(){
        LinearLayout parent = (LinearLayout) findViewById(R.id.parentLayout);
        boolean[] hours = new boolean[24];

        for (int i = 0; i < parent.getChildCount(); i++) {
                LinearLayout layoutChild = (LinearLayout) parent.getChildAt(i);
                View v = layoutChild.getChildAt(0);
                if (v instanceof ToggleButton){
                    hours[i] = ((ToggleButton) v).isChecked();
                }
            }
        return  hours;
    }

    private void loadDay(boolean[] day){
        LinearLayout parent = (LinearLayout) findViewById(R.id.parentLayout);

        for (int i = 0; i < parent.getChildCount(); i++) {
            LinearLayout layoutChild = (LinearLayout) parent.getChildAt(i);
            View v = layoutChild.getChildAt(0);
            if (v instanceof ToggleButton) {
                ((ToggleButton) v).setChecked(day[i]);
            }
        }
    }
}

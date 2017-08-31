package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.SwipeListener;
import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.R;

public class CoachUpgrade extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_upgrade);

        RelativeLayout finestra = (RelativeLayout) findViewById(R.id.relative_coachUpgrade);
        finestra.setOnTouchListener(new SwipeListener(CoachUpgrade.this) {
            @Override
            public void onSwipeRight() {
                Intent coachArea = new Intent(CoachUpgrade.this, StudentArea.class);
                startActivity(coachArea);
                finish();
            }
        });

        Button upgrade = (Button) findViewById(R.id.upgrade_button);
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoggedUser user = LoggedUser.getIstance(null, null, false);
                Intent intent = new Intent(CoachUpgrade.this, CoachRegistration.class);
                intent.putExtra("ign", user.getIgn());
                intent.putExtra("password", user.getPassword());
                intent.putExtra("upgrade", true);
                startActivity(intent);
            }
        });
    }
}

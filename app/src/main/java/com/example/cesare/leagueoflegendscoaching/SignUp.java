package com.example.cesare.leagueoflegendscoaching;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SignUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final CheckBox coachCheckBox = (CheckBox) findViewById(R.id.coach_checkBox);


        //add listener for signup button
        final Button signupButton = (Button) findViewById(R.id.signupSubmit_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = coachIntent(SignUp.this, coachCheckBox);
                startActivity(signup);
            }
        });

    }

    Intent coachIntent(Context context, CheckBox coach) {
        Intent risIntent;
        if (coach.isChecked()) {
            risIntent = new Intent(context, CoachRegistration.class);
        }
        else {
            risIntent = new Intent(context, StudentArea.class);
        }
        return risIntent;
    }


}

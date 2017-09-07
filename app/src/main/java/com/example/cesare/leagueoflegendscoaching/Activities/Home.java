package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.ShakeDetector;
import com.example.cesare.leagueoflegendscoaching.Classes.Security;
import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.R;

public class Home extends Activity{

    private ShakeDetector shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("CREATE", "HOME");

        LoggedUser loggedUser = LoggedUser.getIstance(null, null, false, this);
        if (loggedUser != null) {
            Intent intent;
            if (loggedUser.isCoach()) {
                intent = new Intent(Home.this, CoachArea.class);
            } else {
                intent = new Intent(Home.this, StudentArea.class);
            }
            startActivity(intent);
            finish();
        }

        Security.PACKAGE_NAME = getApplicationContext().getPackageName();

        //add listener for login button
        final Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(Home.this, Login.class);
                startActivity(login);
            }
        });

        //add listener for signUp button
        final Button signUpButton = (Button) findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(Home.this, SignUp.class);
                startActivity(signUp);
            }
        });

        shaker = new ShakeDetector(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LoggedUser loggedUser = LoggedUser.getIstance(null, null, false, this);
        if (loggedUser != null) {
            Intent intent;
            if (loggedUser.isCoach()) {
                intent = new Intent(Home.this, CoachArea.class);
            } else {
                intent = new Intent(Home.this, StudentArea.class);
            }
            startActivity(intent);
            finish();
        }
    }
}

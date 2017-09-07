package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.ShakeDetector;
import com.example.cesare.leagueoflegendscoaching.Classes.Security;
import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.UserParams;
import com.example.cesare.leagueoflegendscoaching.Operations.UserOperation;
import com.example.cesare.leagueoflegendscoaching.R;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class Login extends Activity {

    private ShakeDetector shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        shaker = new ShakeDetector(this);

        final Button signupButton = (Button) findViewById(R.id.loginSubmit_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = createIntent(Login.this);
                if (login != null) {
                    startActivity(login);
                    finish();
                }
            }
        });
    }

    Intent createIntent(Context context){
        Intent risIntent = null;

        EditText user_input = (EditText) findViewById(R.id.user_input);
        EditText passwordLogin_input = (EditText) findViewById(R.id.passwordLogin_input);

        String ign = user_input.getText().toString();
        String password = passwordLogin_input.getText().toString();

        if (ign.isEmpty() || password.isEmpty()){
            Security.createToast("Fill In All Fields", this);
            return null;
        }

        int login = 0;
        UserParams params;
        try{
            params = new UserParams(ign, password, context, "login", null);
            login = new UserOperation().execute(params).get();
        } catch (InterruptedException | ExecutionException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            Security.createToast("Login Error", this);
            return null;
        }

        switch (login){
            case 20: {
                risIntent = new Intent(context, StudentArea.class);
                LoggedUser l = LoggedUser.getIstance(params.getIgn(), params.getPassword(), false, context);
            }
            break;

            case 21:{
                Security.createToast("Incorrect Username/Password", this);
                risIntent = null;
            }
            break;

            case 22:{
                risIntent = new Intent(context, CoachArea.class);
                LoggedUser l = LoggedUser.getIstance(params.getIgn(), params.getPassword(), true, context);
            }
            break;

            case 404:{
                Security.createToast("No Internet Connection", this);
                risIntent = null;
            }
        }
        return risIntent;
    }

}



package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.UserParams;
import com.example.cesare.leagueoflegendscoaching.Operations.UserOperation;
import com.example.cesare.leagueoflegendscoaching.R;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
            Toast toast = Toast.makeText(context, "Compila tutti i campi", Toast.LENGTH_SHORT);
            toast.show();
            return null;
        }

        int login = 0;
        UserParams params;
        try{
            params = new UserParams(ign, password, context, "login");
            login = new UserOperation().execute(params).get();
        } catch (InterruptedException | ExecutionException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            Toast toast = Toast.makeText(context, "Login Error", Toast.LENGTH_SHORT);
            toast.show();
            return null;
        }

        switch (login){
            case 20: {
                risIntent = new Intent(context, StudentArea.class);
                LoggedUser l = LoggedUser.getIstance(params.getIgn(), params.getPassword(), false);
            }
            break;

            case 21:{
                Toast toast = Toast.makeText(context, "Incorrect username/password", Toast.LENGTH_SHORT);
                toast.show();
                risIntent = null;
            }
            break;

            case 22:{
                risIntent = new Intent(context, StudentArea.class);
                LoggedUser l = LoggedUser.getIstance(params.getIgn(), params.getPassword(), true);
            }
            break;

            case 404:{
                Toast toast = Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
                risIntent = null;
            }
        }
        return risIntent;
    }
}

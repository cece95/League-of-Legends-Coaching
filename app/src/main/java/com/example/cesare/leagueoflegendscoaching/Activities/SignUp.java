package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.UserParams;
import com.example.cesare.leagueoflegendscoaching.Operations.UserOperation;
import com.example.cesare.leagueoflegendscoaching.R;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

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
                Intent signup = createIntent(SignUp.this, coachCheckBox);
                if (signup != null) {
                    startActivity(signup);
                    finish();
                }
            }
        });
    }

    Intent createIntent(Context context, CheckBox coach) {
        Intent risIntent = null;

        //ottengo i campi
        EditText inGame_input = (EditText) findViewById(R.id.inGame_input);
        EditText password_input = (EditText) findViewById(R.id.password_input);
        EditText repeatPassword_input = (EditText) findViewById(R.id.repeatPassword_input);

        //ottengo i parametri
        String ign = inGame_input.getText().toString();
        String password = password_input.getText().toString();
        String r_password = repeatPassword_input.getText().toString();

        //controllo che non ci siano parametri vuoti
        if (ign.isEmpty() || password.isEmpty() || r_password.isEmpty()){
            Toast toast = Toast.makeText(context, "Compila tutti i campi", Toast.LENGTH_SHORT);
            toast.show();
            return null;
        }

        //controllo che le password coincidano
        if (password.equals(r_password)){
            //se "i am a coach" è selezionato procedo alla schermata del coach
            if (coach.isChecked()) {
                risIntent = new Intent(context, CoachRegistration.class);
                risIntent.putExtra("ign", ign);
                risIntent.putExtra("password", password);
                return risIntent;
            }

            //altrimenti completo la registrazione
            int registration;
            UserParams params;
            try {
                params = new UserParams(ign, password, context, "register");
                registration = new UserOperation().execute(params).get();
            } catch (InterruptedException | ExecutionException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
                Toast toast = Toast.makeText(context, "Registration Error", Toast.LENGTH_SHORT);
                toast.show();
                return null;
            }

            switch (registration){
                case 10:{
                    risIntent = new Intent(context, StudentArea.class);
                    LoggedUser l = LoggedUser.getIstance(params.getIgn(), params.getPassword(), false);
                }
                break;

                case 11:{
                    Toast toast = Toast.makeText(context, "User already in database", Toast.LENGTH_SHORT);
                    toast.show();
                    risIntent = null;
                }
                break;

                case 12:{
                    Toast toast = Toast.makeText(context, "Database Error", Toast.LENGTH_SHORT);
                    toast.show();
                    risIntent = null;
                }
                break;

                case 13: {
                    Toast toast = Toast.makeText(context, "Username not valid", Toast.LENGTH_SHORT);
                    toast.show();
                    risIntent = null;
                }
                break;

                case 404:{
                    Toast toast = Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT);
                    toast.show();
                    risIntent = null;
                }
            }
        }
        else{
            Toast toast = Toast.makeText(context, "Le password non coincidono", Toast.LENGTH_SHORT);
            toast.show();
            risIntent = null;
        }
    return risIntent;
    }
}
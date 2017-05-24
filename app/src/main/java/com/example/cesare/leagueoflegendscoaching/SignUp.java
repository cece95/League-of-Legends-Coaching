package com.example.cesare.leagueoflegendscoaching;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
                Intent signup = coachIntent(SignUp.this, coachCheckBox);
                if (signup != null) {
                    startActivity(signup);
                }
            }
        });

    }

    Intent coachIntent(Context context, CheckBox coach) {
        Intent risIntent = null;
        if (coach.isChecked()) {
            risIntent = new Intent(context, CoachRegistration.class);
        }
        else {
            EditText inGame_input = (EditText) findViewById(R.id.inGame_input);
            EditText password_input = (EditText) findViewById(R.id.password_input);
            EditText repeatPassword_input = (EditText) findViewById(R.id.repeatPassword_input);

            String ign = inGame_input.getText().toString();
            String password = password_input.getText().toString();
            String r_password = repeatPassword_input.getText().toString();

            if (ign.isEmpty() || password.isEmpty() || r_password.isEmpty()){
                Toast toast = Toast.makeText(context, "Compila tutti i campi", Toast.LENGTH_SHORT);
                toast.show();
                return null;
            }


            if (password.equals(r_password)){
                RegistrationParams params = new RegistrationParams(ign, password, context);
                int registration = 0;
                try {
                    registration = new UserSignup().execute(params).get();
                } catch (InterruptedException | ExecutionException e) {
                    Toast toast = Toast.makeText(context, "Registration Error", Toast.LENGTH_SHORT);
                    toast.show();
                    return null;
                }

                Log.d("REGISTRATION", Integer.toString(registration));

                switch (registration){
                    case 10:{
                        risIntent = new Intent(context, StudentArea.class);
                        risIntent.putExtra("user", ign);
                    }
                    break;

                    case 20:{
                        Toast toast = Toast.makeText(context, "User already in database", Toast.LENGTH_SHORT);
                        toast.show();
                        risIntent = null;
                    }
                    break;

                    case 30:{
                        Toast toast = Toast.makeText(context, "Database Error", Toast.LENGTH_SHORT);
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
        }
        return risIntent;
    }


}

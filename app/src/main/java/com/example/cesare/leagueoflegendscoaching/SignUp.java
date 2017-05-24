package com.example.cesare.leagueoflegendscoaching;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
        Intent risIntent = null;
        if (coach.isChecked()) {
            risIntent = new Intent(context, CoachRegistration.class);
        }
        else {
            EditText name_input = findViewById(R.id.name_input);
            EditText inGame_input = findViewById(R.id.inGame_input);
            EditText password_input = findViewById(R.id.password_input);
            EditText repeatPassword_input = findViewById(R.id.repeatPassword_input);

            String name = name_input.getText().toString();
            String ign = inGame_input.getText().toString();
            String password = password_input.getText().toString();
            String r_password = repeatPassword_input.getText().toString();

            if (password.equals(r_password)){
                int registration = ServerConnection.userRegistration(name, ign, password);
                if (registration == 200){
                    risIntent = new Intent(context, StudentArea.class);
                    risIntent.putExtra("user", name);
                }
                else {
                    //errore registrazione fallita
                }
            }
            else{
                //errore password non coincidono
            }
        }
        return risIntent;
    }


}

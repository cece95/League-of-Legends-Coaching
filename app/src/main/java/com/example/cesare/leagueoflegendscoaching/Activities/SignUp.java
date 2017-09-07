package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.ShakeDetector;
import com.example.cesare.leagueoflegendscoaching.Classes.Security;
import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.UserParams;
import com.example.cesare.leagueoflegendscoaching.Operations.UserOperation;
import com.example.cesare.leagueoflegendscoaching.R;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class SignUp extends Activity {

    private ShakeDetector shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        shaker = new ShakeDetector(this);

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
            Security.createToast("Fill In All Fields", this);
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

            String token = FirebaseInstanceId.getInstance().getToken();
            //altrimenti completo la registrazione
            int registration;
            UserParams params;
            try {
                params = new UserParams(ign, password, context, "register", token);
                registration = new UserOperation().execute(params).get();
                if (registration == 10 && token != null){
                    params = new UserParams(ign, password, context, "token", token);
                    registration = registration + new UserOperation().execute(params).get();
                }
            } catch (InterruptedException | ExecutionException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
                Security.createToast("Registration Error", this);
                return null;
            }

            switch (registration){
                case 10:
                case 20:{
                    risIntent = new Intent(context, StudentArea.class);
                    LoggedUser.getIstance(params.getIgn(), params.getPassword(), false, context);
                }
                break;

                case 11:{
                    Security.createToast("User already in database", this);
                    risIntent = null;
                }
                break;

                case 12:{
                    Security.createToast("Database Error", this);
                    risIntent = null;
                }
                break;

                case 13: {
                    Security.createToast("Username not valid", this);
                    risIntent = null;
                }
                break;

                case 21:{
                    Security.createToast("Error saving device's token", this);
                    risIntent = null;
                }
                break;

                case 404:{
                    Security.createToast("No Internet Connection", this);
                    risIntent = null;
                }
            }
        }
        else{
            Security.createToast("Le password non coincidono", this);
            risIntent = null;
        }
    return risIntent;
    }
}
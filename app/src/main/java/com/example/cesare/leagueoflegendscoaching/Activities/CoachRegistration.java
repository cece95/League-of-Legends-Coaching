package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cesare.leagueoflegendscoaching.Miscellaneous;
import com.example.cesare.leagueoflegendscoaching.Operations.CoachOperation;
import com.example.cesare.leagueoflegendscoaching.Params.CoachParams;
import com.example.cesare.leagueoflegendscoaching.R;
import com.example.cesare.leagueoflegendscoaching.ToggleImageButton;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class CoachRegistration extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_registration);

        // Create elo spinner
        Spinner eloSpinner = (Spinner) findViewById(R.id.elo_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.elo_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eloSpinner.setAdapter(adapter);

        //create roles spinner
        Spinner role1Spinner = (Spinner) findViewById(R.id.role1_spinner);
        ArrayAdapter<CharSequence> r1Adapter = ArrayAdapter.createFromResource(this, R.array.role1_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role1Spinner.setAdapter(r1Adapter);

        //create 2nd role spinner
        Spinner role2Spinner = (Spinner) findViewById(R.id.role2_spinner);
        ArrayAdapter<CharSequence> r2Adapter = ArrayAdapter.createFromResource(this, R.array.role2_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role2Spinner.setAdapter(r2Adapter);

        //flags
        ToggleImageButton it_flag = (ToggleImageButton) findViewById(R.id.flag_Italy_ImageButton);
        ToggleImageButton eng_flag = (ToggleImageButton) findViewById(R.id.flag_UnitedKingdom_ImageButton);
        ToggleImageButton fr_flag = (ToggleImageButton) findViewById(R.id.flag_France_ImageButton);
        ToggleImageButton sp_flag = (ToggleImageButton) findViewById(R.id.flag_Spain_ImageButton);
        ToggleImageButton ger_flag = (ToggleImageButton) findViewById(R.id.flag_Germany_ImageButton);

        //Set signupListener
        Button signupCoach = (Button) findViewById(R.id.signupCoachSubmit_button);
        signupCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent coachSignup = createIntent(CoachRegistration.this, getIntent());
            }
        });

    }

    private Intent createIntent(Context context, Intent intent){
        Intent risIntent = null;

        //Ottengo i campi
        Spinner eloSpinner = (Spinner) findViewById(R.id.elo_spinner);
        Spinner role1Spinner = (Spinner) findViewById(R.id.role1_spinner);
        Spinner role2Spinner = (Spinner) findViewById(R.id.role2_spinner);
        EditText cost_input = (EditText) findViewById(R.id.cost_input);

        //schedule?

        //ottengo i parametri
        String ign = intent.getStringExtra("ign");
        String password = intent.getStringExtra("password");
        String elo = (String) eloSpinner.getSelectedItem();
        int elo_int = Miscellaneous.eloMap.get(elo);
        boolean languages[] = checkLanguages();
        String role1 = (String) role1Spinner.getSelectedItem();
        String role2 = (String) role2Spinner.getSelectedItem();
        int cost = Integer.getInteger(cost_input.getText().toString());

        //procedo alla registrazione
        int coachRegistration;
        try {
            CoachParams coachParams = new CoachParams(ign, password, context, "register", elo_int, languages, role1, role2, cost);
            coachRegistration = new CoachOperation().execute(coachParams).get();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InterruptedException | ExecutionException e) {
            Toast toast = Toast.makeText(context, "Registration Error", Toast.LENGTH_SHORT);
            toast.show();
            return null;
        }

        //modifica da qui in poi(+aggiungere parte server)
        switch (coachRegistration) {
            case 10: {
                risIntent = new Intent(context, StudentArea.class);
                risIntent.putExtra("user", ign);
            }
            break;

            case 11: {
                Toast toast = Toast.makeText(context, "User already in database", Toast.LENGTH_SHORT);
                toast.show();
                risIntent = null;
            }
            break;

            case 12: {
                Toast toast = Toast.makeText(context, "Database Error", Toast.LENGTH_SHORT);
                toast.show();
                risIntent = null;
            }
            break;

            case 404: {
                Toast toast = Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
                risIntent = null;
            }
        }

        return risIntent;
    }

    private boolean[] checkLanguages(){
        boolean[] languages = new boolean[5];

        ToggleImageButton eng_flag = (ToggleImageButton) findViewById(R.id.flag_UnitedKingdom_ImageButton);
        ToggleImageButton it_flag = (ToggleImageButton) findViewById(R.id.flag_Italy_ImageButton);
        ToggleImageButton ger_flag = (ToggleImageButton) findViewById(R.id.flag_Germany_ImageButton);
        ToggleImageButton fr_flag = (ToggleImageButton) findViewById(R.id.flag_France_ImageButton);
        ToggleImageButton sp_flag = (ToggleImageButton) findViewById(R.id.flag_Spain_ImageButton);

        if (eng_flag.isChecked())
            languages[0] = true;
        else
            languages[0] = false;

        if (it_flag.isChecked())
            languages[1] = true;
        else
            languages[1] = false;

        if (ger_flag.isChecked())
            languages[2] = true;
        else
            languages[2] = false;

        if (fr_flag.isChecked())
            languages[3] = true;
        else
            languages[3] = false;

        if (sp_flag.isChecked())
            languages[4] = true;
        else
            languages[4] = false;

        return languages;
    }
}

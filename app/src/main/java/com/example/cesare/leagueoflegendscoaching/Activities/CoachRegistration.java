package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cesare.leagueoflegendscoaching.Operations.CoachOperation;
import com.example.cesare.leagueoflegendscoaching.Params.CoachParams;
import com.example.cesare.leagueoflegendscoaching.R;
import com.example.cesare.leagueoflegendscoaching.ToggleImageButton;
import com.example.cesare.leagueoflegendscoaching.Types.Elo;
import com.example.cesare.leagueoflegendscoaching.Types.Language;
import com.example.cesare.leagueoflegendscoaching.Types.Role;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

public class CoachRegistration extends Activity {

    PopupWindow popup;

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

        // Date Popup window
        ImageButton calendarButton = (ImageButton) findViewById(R.id.schedule_ImageButton);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast myToast = Toast.makeText(CoachRegistration.this, "prova", Toast.LENGTH_SHORT);
                myToast.show();
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popup_view = inflater.inflate(R.layout.test, null);

                popup = new PopupWindow(popup_view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                RelativeLayout popup_position = (RelativeLayout) findViewById(R.id.schedule_layout);

                popup.showAtLocation(popup_position, Gravity.CENTER, 0,0);

            }
        });

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
        boolean upgrade = intent.getBooleanExtra("upgrade", false);
        Elo elo = Elo.valueOf((String) eloSpinner.getSelectedItem());
        HashSet<Language> languages = checkLanguages();
        Role role1 = Role.valueOf((String) role1Spinner.getSelectedItem());
        Role role2 = Role.valueOf((String) role2Spinner.getSelectedItem());
        int cost = Integer.parseInt(cost_input.getText().toString());

        if (languages.isEmpty()){
            Toast toast = Toast.makeText(context, "Select at least one language", Toast.LENGTH_SHORT);
            toast.show();
            return null;
        }

        //procedo alla registrazione
        int coachRegistration;
        try {
            CoachParams coachParams = new CoachParams(ign, password, context, "register", elo, languages, role1, role2, cost, upgrade);
            coachRegistration = new CoachOperation().execute(coachParams).get();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InterruptedException | ExecutionException e) {
            Toast toast = Toast.makeText(context, "Registration Error", Toast.LENGTH_SHORT);
            toast.show();
            return null;
        }

        //modifica da qui in poi(+aggiungere parte server)
        switch (coachRegistration) {
            case 10: {
                risIntent = new Intent(context, CoachArea.class);
                risIntent.putExtra("user", ign);
                risIntent.putExtra("isCoach", true);
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

    private HashSet<Language> checkLanguages(){
        HashSet<Language> languages = new HashSet<>();

        ToggleImageButton eng_flag = (ToggleImageButton) findViewById(R.id.flag_UnitedKingdom_ImageButton);
        ToggleImageButton it_flag = (ToggleImageButton) findViewById(R.id.flag_Italy_ImageButton);
        ToggleImageButton ger_flag = (ToggleImageButton) findViewById(R.id.flag_Germany_ImageButton);
        ToggleImageButton fr_flag = (ToggleImageButton) findViewById(R.id.flag_France_ImageButton);
        ToggleImageButton sp_flag = (ToggleImageButton) findViewById(R.id.flag_Spain_ImageButton);

        if (eng_flag.isChecked())
            languages.add(Language.English);

        if (it_flag.isChecked())
            languages.add(Language.Italian);

        if (ger_flag.isChecked())
            languages.add(Language.German);

        if (fr_flag.isChecked())
            languages.add(Language.French);

        if (sp_flag.isChecked())
            languages.add(Language.Spanish);

        return languages;
    }
}

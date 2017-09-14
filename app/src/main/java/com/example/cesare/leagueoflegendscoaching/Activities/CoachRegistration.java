package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.ToggleImageButton;
import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.ShakeDetector;
import com.example.cesare.leagueoflegendscoaching.Classes.Security;
import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.Operations.CoachOperation;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.CoachParams;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.UserParams;
import com.example.cesare.leagueoflegendscoaching.Operations.UserOperation;
import com.example.cesare.leagueoflegendscoaching.R;
import com.example.cesare.leagueoflegendscoaching.Types.Elo;
import com.example.cesare.leagueoflegendscoaching.Types.Language;
import com.example.cesare.leagueoflegendscoaching.Types.Role;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class CoachRegistration extends Activity {

    private ShakeDetector shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_registration);
        shaker = new ShakeDetector(this);

        ToggleImageButton selected_flag = getLocalLanguage();
        selected_flag.setChecked(true);

        // Create elo spinner
        Spinner eloSpinner = (Spinner) findViewById(R.id.elo_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.elo_list, android.R.layout.simple_spinner_item);
        eloSpinner.setAdapter(adapter);

        //create roles spinner
        Spinner role1Spinner = (Spinner) findViewById(R.id.role1_spinner);
        ArrayAdapter<CharSequence> r1Adapter = ArrayAdapter.createFromResource(this, R.array.role1_list, android.R.layout.simple_spinner_item);
        role1Spinner.setAdapter(r1Adapter);

        //create 2nd role spinner
        Spinner role2Spinner = (Spinner) findViewById(R.id.role2_spinner);
        ArrayAdapter<CharSequence> r2Adapter = ArrayAdapter.createFromResource(this, R.array.role2_list, android.R.layout.simple_spinner_item);
        role2Spinner.setAdapter(r2Adapter);

        // Date Popup window
        ImageButton calendarButton = (ImageButton) findViewById(R.id.schedule_ImageButton);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent weekSchedule = new Intent(CoachRegistration.this, WeekSchedule.class);
                startActivity(weekSchedule);
            }
        });

        //Set signupListener
        Button signupCoach = (Button) findViewById(R.id.signupCoachSubmit_button);
        signupCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coachSignup = createIntent(CoachRegistration.this, getIntent());
                if (coachSignup != null){
                    startActivity(coachSignup);
                    finish();
                }
            }
        });

    }

    private Intent createIntent(Context context, Intent intent){
        Intent risIntent = null;

        CoachParams coachParams = null;
        int coachRegistration;
        try {
            coachParams = getParams();
            if (coachParams.languages.isEmpty()){
                Security.createToast("Select at least one language", this);
                return null;
            }

            coachRegistration = new CoachOperation().execute(coachParams).get();

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InterruptedException |  ExecutionException e) {
            e.printStackTrace();
            Security.createToast("Registration Error", this);
            return null;
        }


        //procedo alla registrazione
        switch (coachRegistration) {
            case 10: {
                try {
                    UserParams params = new UserParams(coachParams.getIgn(), coachParams.getPassword(), context, "token", coachParams.getToken());
                    new UserOperation().execute(params).get();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                risIntent = new Intent(context, CoachArea.class);
                LoggedUser l = LoggedUser.getIstance(coachParams.getIgn(), coachParams.getPassword(), true, context);
            }
            break;

            case 11: {
                Security.createToast("User already in database", this);
                risIntent = null;
            }
            break;

            case 12: {
                Security.createToast("Database Error", this);
                risIntent = null;
            }
            break;

            case 13: {
                Security.createToast("Username not valid", this);
                risIntent = null;
            }
            break;

            case 404: {
                Security.createToast("No Internet Connection", this);
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

    private CoachParams getParams() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Intent intent = getIntent();
        Context context = CoachRegistration.this;
        String token = FirebaseInstanceId.getInstance().getToken();

        //Ottengo i campi
        Spinner eloSpinner = (Spinner) findViewById(R.id.elo_spinner);
        Spinner role1Spinner = (Spinner) findViewById(R.id.role1_spinner);
        Spinner role2Spinner = (Spinner) findViewById(R.id.role2_spinner);
        EditText cost_input = (EditText) findViewById(R.id.cost_input);

        //ottengo i parametri
        String ign = intent.getStringExtra("ign");
        String password = intent.getStringExtra("password");
        boolean upgrade = intent.getBooleanExtra("upgrade", false);
        int elo = (Elo.valueOf((String) eloSpinner.getSelectedItem())).EloToInt();
        HashSet<Language> languages = checkLanguages();
        Role role1 = Role.valueOf((String) role1Spinner.getSelectedItem());
        Role role2 = Role.valueOf((String) role2Spinner.getSelectedItem());
        int cost = Integer.parseInt(cost_input.getText().toString());

        CoachParams coachParams = new CoachParams(ign, password, context, "register",token, elo, languages, role1, role2, cost, upgrade);

        return coachParams;
    }

    private ToggleImageButton getLocalLanguage(){
        String localLanguage = Locale.getDefault().getLanguage();
        ToggleImageButton res;
        switch (localLanguage){
            case "it":  res = (ToggleImageButton) findViewById(R.id.flag_Italy_ImageButton);
                break;
            case "fr": res = (ToggleImageButton) findViewById(R.id.flag_France_ImageButton);
                break;
            case "de": res = (ToggleImageButton) findViewById(R.id.flag_Germany_ImageButton);
                break;
            case "es": res = (ToggleImageButton) findViewById(R.id.flag_Spain_ImageButton);
                break;
            default: res = (ToggleImageButton) findViewById(R.id.flag_UnitedKingdom_ImageButton);
        }
        return res;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Spinner eloSpinner = (Spinner) findViewById(R.id.elo_spinner);
        Spinner role1Spinner = (Spinner) findViewById(R.id.role1_spinner);
        Spinner role2Spinner = (Spinner) findViewById(R.id.role2_spinner);
        EditText cost_input = (EditText) findViewById(R.id.cost_input);

        int eloPos = eloSpinner.getSelectedItemPosition();
        int role1Pos = role1Spinner.getSelectedItemPosition();
        int role2Pos = role2Spinner.getSelectedItemPosition();
        String cost = String.valueOf(cost_input.getText());
        HashSet<Language> languages = checkLanguages();

        savedInstanceState.putInt("eloPos", eloPos);
        savedInstanceState.putInt("role1pos", role1Pos);
        savedInstanceState.putInt("role2Pos", role2Pos);
        savedInstanceState.putString("cost", cost);
        savedInstanceState.putSerializable("languages", languages);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);

        int eloPos = savedInstanceState.getInt("eloPos");
        int role1Pos = savedInstanceState.getInt("role1Pos");
        int role2Pos = savedInstanceState.getInt("role2Pos");
        String cost = savedInstanceState.getString("cost");
        HashSet<Language> languages = (HashSet<Language>) savedInstanceState.getSerializable("languages");

        Spinner eloSpinner = (Spinner) findViewById(R.id.elo_spinner);
        Spinner role1Spinner = (Spinner) findViewById(R.id.role1_spinner);
        Spinner role2Spinner = (Spinner) findViewById(R.id.role2_spinner);
        EditText cost_input = (EditText) findViewById(R.id.cost_input);

        eloSpinner.setSelection(eloPos);
        role1Spinner.setSelection(role1Pos);
        role2Spinner.setSelection(role2Pos);
        cost_input.setText(cost);
        setLanguages(languages);

    }

    private void setLanguages(HashSet<Language> languages){

        ToggleImageButton eng_flag = (ToggleImageButton) findViewById(R.id.flag_UnitedKingdom_ImageButton);
        ToggleImageButton it_flag = (ToggleImageButton) findViewById(R.id.flag_Italy_ImageButton);
        ToggleImageButton ger_flag = (ToggleImageButton) findViewById(R.id.flag_Germany_ImageButton);
        ToggleImageButton fr_flag = (ToggleImageButton) findViewById(R.id.flag_France_ImageButton);
        ToggleImageButton sp_flag = (ToggleImageButton) findViewById(R.id.flag_Spain_ImageButton);

        if (languages.contains(Language.English))
            eng_flag.setChecked(true);

        if (languages.contains(Language.Italian))
            it_flag.setChecked(true);

        if (languages.contains(Language.German))
            ger_flag.setChecked(true);

        if (languages.contains(Language.French))
            fr_flag.setChecked(true);

        if (languages.contains(Language.Spanish))
            sp_flag.setChecked(true);
    }
}

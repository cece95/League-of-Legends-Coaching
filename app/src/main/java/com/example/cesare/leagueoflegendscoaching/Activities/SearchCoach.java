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

import com.example.cesare.leagueoflegendscoaching.Classes.ChampionsMap;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.ToggleImageButton;
import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.ShakeDetector;
import com.example.cesare.leagueoflegendscoaching.R;
import com.example.cesare.leagueoflegendscoaching.Types.Elo;
import com.example.cesare.leagueoflegendscoaching.Types.Language;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static com.example.cesare.leagueoflegendscoaching.Classes.ChampionsMap.spinnerMap;

public class SearchCoach extends Activity {

    private ShakeDetector shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_coach);
        shaker = new ShakeDetector(this);

        //eng flag preChecked
        ToggleImageButton eng_flag = (ToggleImageButton) findViewById(R.id.flag_UnitedKingdom_ImageButton);
        eng_flag.setChecked(true);


        // Create elo spinner
        Spinner eloSpinner = (Spinner) findViewById(R.id.elo_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.elo_list, R.layout.simple_spinner_item);
        eloSpinner.setAdapter(adapter1);

        //create roles spinner
        Spinner role1Spinner = (Spinner) findViewById(R.id.role_spinner);
        ArrayAdapter<CharSequence> r1Adapter = ArrayAdapter.createFromResource(this, R.array.role1_list, R.layout.simple_spinner_item);
        role1Spinner.setAdapter(r1Adapter);

        // create a spinner for champions
        InputStream championsList = getResources().openRawResource(R.raw.champions);
        JSONObject json = ChampionsMap.readJson(championsList);


        try {
            spinnerMap = ChampionsMap.getChampionsMap(json);
            ChampionsMap.idToChamp = ChampionsMap.getIdToChampMap(spinnerMap);

            ArrayList<String> spinnerList = new ArrayList<String>(spinnerMap.keySet());
            Collections.sort(spinnerList);

            Spinner champion1Spinner = (Spinner) findViewById(R.id.champions1_spinner);
            ArrayAdapter<String> c1Adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, spinnerList);
            champion1Spinner.setAdapter(c1Adapter);

            Spinner champion2Spinner = (Spinner) findViewById(R.id.champions2_spinner);
            ArrayAdapter<String> c2Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
            champion2Spinner.setAdapter(c2Adapter);

            Spinner champion3Spinner = (Spinner) findViewById(R.id.champions3_spinner);
            ArrayAdapter<String> c3Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
            champion3Spinner.setAdapter(c3Adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Button Find Coach
        final Button findCoachButton = (Button) findViewById(R.id.findCoach_button);
        findCoachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findCoach = createIntent(SearchCoach.this);
                startActivity(findCoach);
            }
        });
    }

    private Intent createIntent(Context context) {
        Intent risIntent = null;
        EditText nameCoachInput = (EditText) findViewById(R.id.name_input);
        Spinner eloSpinner = (Spinner) findViewById(R.id.elo_spinner);
        Spinner roleSpinner = (Spinner) findViewById(R.id.role_spinner);
        Spinner firstChampionSpinner = (Spinner) findViewById(R.id.champions1_spinner);
        Spinner secondChampionSpinner = (Spinner) findViewById(R.id.champions2_spinner);
        Spinner thirdChampionSpinner = (Spinner) findViewById(R.id.champions3_spinner);
        EditText costInput = (EditText) findViewById(R.id.cost_input);

        // ottengo i parametri
        String nameCoach = nameCoachInput.getText().toString();
        Elo elo = Elo.valueOf((String) eloSpinner.getSelectedItem());
        String role = (String) roleSpinner.getSelectedItem();
        String champion1 = firstChampionSpinner.getSelectedItem().toString();
        String champion2 = secondChampionSpinner.getSelectedItem().toString();
        String champion3 = thirdChampionSpinner.getSelectedItem().toString();
        int idChampion1 = spinnerMap.get(champion1);
        int idChampion2 = spinnerMap.get(champion2);
        int idChampion3 = spinnerMap.get(champion3);
        int cost = Integer.parseInt(costInput.getText().toString());
        HashSet<Language> languages = checkLanguages();

        risIntent = new Intent(context, CoachesList.class);

        risIntent.putExtra("nameCoach", nameCoach);
        risIntent.putExtra("elo", elo);
        risIntent.putExtra("role", role);
        risIntent.putExtra("idChampion1", idChampion1);
        risIntent.putExtra("idChampion2", idChampion2);
        risIntent.putExtra("idChampion3", idChampion3);
        risIntent.putExtra("cost", cost);
        risIntent.putExtra("languages", languages);

        return risIntent;
    }

    // Per gestire le lingue
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



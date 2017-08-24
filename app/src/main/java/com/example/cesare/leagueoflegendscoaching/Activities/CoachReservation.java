package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.CoachFrame;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.ScheduleParams;
import com.example.cesare.leagueoflegendscoaching.Operations.ScheduleOperation;
import com.example.cesare.leagueoflegendscoaching.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

public class CoachReservation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_reservation);

        String jsonString = getIntent().getStringExtra("json");
        JSONObject json = null;
        String coach = null;
        try {
            json = new JSONObject(jsonString);
            //coachFrame
            final CoachFrame coachFrame = new CoachFrame(json);
            coach = coachFrame.getName();
            ViewStub stub = (ViewStub) findViewById(R.id.stub);
            View frame = stub.inflate();
            coachFrame.createFrame(frame, false);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ScheduleParams params = new ScheduleParams(coach, CoachReservation.this);
        JSONObject reservations = null;
        try {
            reservations = new ScheduleOperation().execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        final JSONObject finalJson = json;
        final JSONObject finalReservations = reservations;

        //datepicker
        DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                //get day
                GregorianCalendar date = new GregorianCalendar(year, month, dayOfMonth-1);
                int dayOfWeek = date.get(Calendar.DAY_OF_WEEK) - 1;
                String dateId = date.toString();

                Boolean[] staticDay = new Boolean[24];
                Boolean[] dinamicDay = new Boolean[24];

                try {
                    JSONArray staticDayJson = finalJson.getJSONArray("schedule");
                    staticDay = getBooleanArray((JSONArray) staticDayJson.get(dayOfWeek));

                    JSONArray dinamicDayJson = finalReservations.getJSONArray(dateId);
                    dinamicDay = getBooleanArray(dinamicDayJson);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int[] resultTable = new int[24];
                for (int i = 0; i<24; i++){
                    int sInt = (staticDay[i]) ? 1 : 0;
                    int dInt = (dinamicDay[i]) ? 1 : 0;
                    resultTable[i] = sInt + dInt;
                }

                LinearLayout table = generateTable(resultTable);
                LinearLayout parent = (LinearLayout) findViewById(R.id.coach_reservation);
                parent.addView(table, 2);
            }
        });

    }

    private Boolean[] getBooleanArray(JSONArray jsonArray) throws JSONException {
        if (jsonArray != null) {
            Boolean[] booleanArray = new Boolean[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                booleanArray[i] = jsonArray.getBoolean(i);
            }
            return booleanArray;
        } else
            return null;
    }

    private LinearLayout generateTable(int[] table){
        LinearLayout res = new LinearLayout(CoachReservation.this);
        boolean check = false;

        for (int i = 0; i<table.length; i++){
            if (i > 0){
                check = true;
            }
        }

        if (check){
            for (int i = 0; i<table.length; i++){
                if (table[i] > 0){
                    LinearLayout ll = new LinearLayout(CoachReservation.this);
                    ll.setOrientation(LinearLayout.HORIZONTAL);

                    ToggleButton tb = new ToggleButton(CoachReservation.this);
                    tb.setTextSize(R.dimen.label);
                    if (i<9){
                        tb.setTextOn("0"+i+" - 0"+(i+1));
                        tb.setTextOff("0"+i+" - 0"+(i+1));
                    }
                    if (i == 9){
                        tb.setTextOn("09 - 10");
                        tb.setTextOff("09 - 10");
                    }
                    if (i > 9){
                        tb.setTextOn(i+" - "+(i+1));
                        tb.setTextOff(i+" - "+(i+1));
                    }

                    if (table[i] == 1){
                        tb.setBackgroundResource(R.drawable.book_toggle_button);
                    }
                    else{
                        tb.setBackgroundColor(Color.RED);
                        tb.setClickable(false);
                    }

                    ll.addView(tb);
                    res.addView(ll);
                }
            }
        }
        else{
            TextView tw = new TextView(CoachReservation.this);
            tw.setText("Coach not available today");
            res.addView(tw);
        }

        return res;
    }
}





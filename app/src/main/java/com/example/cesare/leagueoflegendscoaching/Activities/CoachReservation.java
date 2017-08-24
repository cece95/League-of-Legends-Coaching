package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.DatePicker;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.CoachFrame;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.HourToggleButton;
import com.example.cesare.leagueoflegendscoaching.Classes.Security;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.ScheduleParams;
import com.example.cesare.leagueoflegendscoaching.Operations.ScheduleOperation;
import com.example.cesare.leagueoflegendscoaching.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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
                GregorianCalendar date = new GregorianCalendar(year, month, dayOfMonth);
                int dayOfWeek = date.get(Calendar.DAY_OF_WEEK) - 1;
                String dateId = Security.format(date);

                Boolean[] staticDay = new Boolean[24];
                Boolean[] dinamicDay = new Boolean[24];

                try {
                    JSONArray staticDayJson = finalJson.getJSONArray("schedule");
                    staticDay = getBooleanArray((JSONArray) staticDayJson.get(dayOfWeek));

                    if (!finalReservations.has("status")) {
                        Log.d("Empty", "Empty");
                        JSONArray dinamicDayJson = finalReservations.getJSONArray(dateId);
                        dinamicDay = getBooleanArray(dinamicDayJson);
                    }
                    else{
                        for (int i = 0; i<24; i++){
                            dinamicDay[i] = false;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int[] resultTable = new int[24];
                for (int i = 0; i<24; i++){
                    int sInt = (staticDay[i]) ? 1 : 0;
                    int dInt = (dinamicDay[i]) ? 1 : 0;
                    resultTable[i] = sInt + dInt;
                }

                List<HourToggleButton> hourList = new ArrayList<>();
                for (int i = 0; i<24; i++){
                    if (resultTable[i] > 0){
                        Log.d("table "+i, Integer.toString(resultTable[i]));
                        HourToggleButton tmp = new HourToggleButton(i, resultTable[i]);
                        hourList.add(tmp);
                    }
                }
                Intent intent = new Intent(CoachReservation.this, ReservationList.class);
                intent.putExtra("lista", (Serializable) hourList);
                intent.putExtra("date", dateId);
                startActivity(intent);

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
}





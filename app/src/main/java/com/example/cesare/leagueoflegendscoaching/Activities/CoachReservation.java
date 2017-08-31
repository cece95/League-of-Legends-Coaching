package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.DatePicker;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.CoachFrame;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.HourToggleButton;
import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.ShakeDetector;
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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CoachReservation extends Activity {

    private ShakeDetector shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_reservation);
        shaker = new ShakeDetector(this);


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

        ScheduleParams params = new ScheduleParams(coach, CoachReservation.this, "coachS");
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
        final String finalCoach = jsonString;

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

                boolean[] staticDay = new boolean[24];
                boolean[] dinamicDay = new boolean[24];

                try {
                    JSONArray staticDayJson = finalJson.getJSONArray("schedule");
                    staticDay = getBooleanArray((JSONArray) staticDayJson.get(dayOfWeek));

                    int status = finalReservations.getInt("status");

                    if (status == 1) {
                        Log.d("STATUS", "1");
                        JSONObject dinamicDayJson = finalReservations.getJSONObject(dateId).getJSONObject("array");
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
                    Log.d("table "+i, Integer.toString(resultTable[i]));
                    HourToggleButton tmp = new HourToggleButton(i, resultTable[i]);
                    hourList.add(tmp);
                }
                Intent intent = new Intent(CoachReservation.this, ReservationList.class);
                intent.putExtra("lista", (Serializable) hourList);
                intent.putExtra("dinamic", dinamicDay);
                intent.putExtra("date", dateId);
                intent.putExtra("coach", finalCoach);
                startActivity(intent);

            }
        });

    }

    private boolean[] getBooleanArray(JSONObject jsonObject) throws JSONException {
        if (jsonObject != null){
            Iterator x = jsonObject.keys();
            JSONArray jsonArray = new JSONArray();

            while (x.hasNext()){
                String key = (String) x.next();
                jsonArray.put(jsonObject.get(key));
            }

            if (jsonArray != null) {
                boolean[] booleanArray = new boolean[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    booleanArray[i] = jsonArray.getBoolean(i);
                }
                return booleanArray;
            }
        }
            return null;
    }

    private boolean[] getBooleanArray(JSONArray jsonArray) throws JSONException {
        if (jsonArray != null) {
            boolean[] booleanArray = new boolean[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                booleanArray[i] = jsonArray.getBoolean(i);
            }
            return booleanArray;
        }
    else
        return null;
    }
}





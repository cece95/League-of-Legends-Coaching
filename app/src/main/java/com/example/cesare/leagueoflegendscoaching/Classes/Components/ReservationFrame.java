package com.example.cesare.leagueoflegendscoaching.Classes.Components;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cesare.leagueoflegendscoaching.Operations.DeleteOperation;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.DeleteParams;
import com.example.cesare.leagueoflegendscoaching.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class ReservationFrame implements Serializable{
    private Date date;
    String dateString;
    String coach;
    int end;
    int start;
    Activity activity;
    String key;

    public ReservationFrame(String date, String key, JSONObject json, Activity a) throws JSONException, ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        this.date = formatter.parse(date);
        this.dateString = date;
        this.coach = json.getString("user");
        this.start = json.getInt("start");
        this.end = json.getInt("end");
        this.activity = a;
        this.key = key;
    }

    public View createFrame(final View frame){
        TextView nameTextView = (TextView) frame.findViewById(R.id.nameRes);
        nameTextView.setText(coach);

        TextView dayTextView = (TextView) frame.findViewById(R.id.dayRes);
        dayTextView.setText(dateString);

        TextView hourTextView = (TextView) frame.findViewById(R.id.hourRes);
        hourTextView.setText("Hours: " + this.start + " - " + this.end);

        Button delete = (Button) frame.findViewById(R.id.deleteRes);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteParams params = new DeleteParams(frame.getContext(), start, end, coach, dateString, key);
                try {
                    int result = new DeleteOperation().execute(params).get();
                    activity.finish();
                    activity.startActivity(activity.getIntent());

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        return frame;
    }

    public Date getDate() {
        return date;
    }

    public String getCoach() {
        return coach;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }
}

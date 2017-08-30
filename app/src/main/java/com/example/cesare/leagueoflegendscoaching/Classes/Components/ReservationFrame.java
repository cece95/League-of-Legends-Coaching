package com.example.cesare.leagueoflegendscoaching.Classes.Components;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cesare.leagueoflegendscoaching.R;
import com.example.cesare.leagueoflegendscoaching.Types.Role;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cesare on 29/08/2017.
 */

public class ReservationFrame implements Serializable{
    Date date;
    String dateString;
    String coach;
    int end;
    int start;
    String roles;
    int cost;

    public ReservationFrame(String date, JSONObject json) throws JSONException, ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        this.date = formatter.parse(date);
        this.dateString = date;
        this.coach = json.getString("coach");
        this.start = json.getInt("start");
        this.end = json.getInt("end");
        Role role1 = Role.valueOf(json.getString("role1"));
        Role role2 = Role.valueOf( json.getString("role2"));
        if (role2 == Role.None) {
            this.roles = "Role: " + String.valueOf(role1);
        }
        else {
            this.roles = "Roles: " + String.valueOf(role1) + "/" + String.valueOf(role2);
        }

        this.cost = json.getInt("cost");

    }

    public View createFrame(View frame){
        TextView nameTextView = (TextView) frame.findViewById(R.id.nameRes);
        nameTextView.setText(coach);

        TextView roleTextView = (TextView) frame.findViewById(R.id.roleRes);
        roleTextView.setText(roles);

        TextView costTextView = (TextView) frame.findViewById(R.id.costRes);
        costTextView.setText("Cost: " + this.cost+"€");

        TextView dayTextView = (TextView) frame.findViewById(R.id.dayRes);
        dayTextView.setText(dateString);

        TextView hourTextView = (TextView) frame.findViewById(R.id.hourRes);
        hourTextView.setText("Hours: " + this.start + " - " + this.end);

        Button delete = (Button) frame.findViewById(R.id.deleteRes);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TO DO DELETE RESERVATION
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
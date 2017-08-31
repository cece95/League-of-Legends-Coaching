package com.example.cesare.leagueoflegendscoaching.Classes.Components;

import android.app.Activity;

import com.example.cesare.leagueoflegendscoaching.Types.Role;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by cesare on 31/08/2017.
 */

public class ReservationFrameUser extends ReservationFrame {

    String roles;
    int cost;

    public ReservationFrameUser(String date, String key, JSONObject json, Activity a) throws JSONException, ParseException {
        super(date, key, json, a);
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
}

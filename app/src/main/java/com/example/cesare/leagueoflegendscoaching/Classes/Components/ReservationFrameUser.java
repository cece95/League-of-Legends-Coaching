package com.example.cesare.leagueoflegendscoaching.Classes.Components;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.RateListener;
import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.Operations.DeleteOperation;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.DeleteParams;
import com.example.cesare.leagueoflegendscoaching.R;
import com.example.cesare.leagueoflegendscoaching.Types.Role;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

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

    public View createFrame(final View frame, final boolean past) {
        TextView nameTextView = (TextView) frame.findViewById(R.id.nameRes);
        nameTextView.setText(coach);

        TextView roleTextView = (TextView) frame.findViewById(R.id.roleRes);
        TextView costTextView = (TextView) frame.findViewById(R.id.costRes);

        roleTextView.setText(this.roles);
        costTextView.setText("Cost: " + this.cost+"€");


        TextView dayTextView = (TextView) frame.findViewById(R.id.dayRes);
        dayTextView.setText(dateString);

        TextView hourTextView = (TextView) frame.findViewById(R.id.hourRes);
        hourTextView.setText("Hours: " + this.start + " - " + this.end);

        Button delete = (Button) frame.findViewById(R.id.deleteRes);
        Button rate = (Button) frame.findViewById(R.id.rateButton);
        if (past){
            delete.setVisibility(View.GONE);
            rate.setVisibility(View.VISIBLE);

            rate.setOnClickListener(new RateListener(new LoggedUser(null, null, false).getIgn(), coach, frame));
        }
        else{
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteParams params = new DeleteParams(frame.getContext(), start, end, coach, dateString, key);
                    try {
                        int result = new DeleteOperation().execute(params).get();
                        activity.finish();
                        activity.startActivity(activity.getIntent());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        return frame;
    }
}

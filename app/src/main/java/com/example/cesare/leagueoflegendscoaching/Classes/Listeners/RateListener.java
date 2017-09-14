package com.example.cesare.leagueoflegendscoaching.Classes.Listeners;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;

import com.example.cesare.leagueoflegendscoaching.Operations.Params.RatingParams;
import com.example.cesare.leagueoflegendscoaching.Operations.RatingOperation;
import com.example.cesare.leagueoflegendscoaching.R;

import java.util.concurrent.ExecutionException;

/**
 * Created by cesare on 14/09/2017.
 */

public class RateListener implements View.OnClickListener {
    String student;
    String coach;
    View frame;

    public RateListener(String student, String coach, View frame){
        this.student = student;
        this.coach = coach;
        this.frame = frame;
    }

    @Override
    public void onClick(View v) {
        final Context context = v.getContext();
        //getRating
        RatingParams params = new RatingParams(student, coach, 0, "GET", context);
        try {
            int rate = new RatingOperation().execute(params).get();
            if (rate < 6){
                LayoutInflater inflater = (LayoutInflater) frame.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popup = inflater.inflate(R.layout.rate_popup, null);
                final RatingBar rb = (RatingBar) popup.findViewById(R.id.ratingBar);
                rb.setNumStars(rate);

                PopupWindow popupWindow = new PopupWindow(popup, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAtLocation(frame, Gravity.CENTER,0,0);

                Button rateButton = (Button) frame.findViewById(R.id.confirmRating);
                rateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int rate = rb.getNumStars();
                        RatingParams params = new RatingParams(student, coach, rate, "SET", context);
                        try {
                            new RatingOperation().execute(params).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //create popup



    }
}

package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.Adapters.CoachFrameAdapter;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.Adapters.HTBAdapter;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.HourToggleButton;
import com.example.cesare.leagueoflegendscoaching.R;
import com.example.cesare.leagueoflegendscoaching.Types.Reservation;

import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ReservationList extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        Set<Integer> selected = new HashSet<Integer>();


        Intent intent = getIntent();
        final ArrayList<HourToggleButton> myArrayList = (ArrayList<HourToggleButton>) intent.getSerializableExtra("lista");
        setListAdapter(new HTBAdapter(this, R.layout.hour_toggle_button, myArrayList));


        String date = intent.getStringExtra("date");
        TextView tv = (TextView) findViewById(R.id.reservationList_title);

        String text = (String) tv.getText();
        tv.setText(text + " " + date);

        final ListView lsv = getListView();

        Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> buttons = new ArrayList<>();
                for (int i = lsv.getChildCount() - 1 ; i>=0; i--) {
                    LinearLayout ll = (LinearLayout) lsv.getChildAt(i);
                    ToggleButton tb = (ToggleButton) ll.getChildAt(0);
                    if (tb.isChecked()) {
                        buttons.add(i);
                    }
                }

                Integer[] array = (Integer[]) buttons.toArray();
                Arrays.sort(array);

                boolean consecutive = true;

                for (int i = 0; i < array.length; i++) {
                    if (array[i]+1 != array[i+1]) {
                        consecutive = false;
                    }
                }

                if (consecutive){

                }
                else{
                    Toast toast = Toast.makeText(ReservationList.this, "Select consecutive hours", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });



    }
}

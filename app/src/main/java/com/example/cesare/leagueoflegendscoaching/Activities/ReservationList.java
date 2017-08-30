package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.Adapters.HTBAdapter;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.HourToggleButton;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.ReserveParams;
import com.example.cesare.leagueoflegendscoaching.Operations.ReserveOperation;
import com.example.cesare.leagueoflegendscoaching.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ReservationList extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);


        Intent intent = getIntent();
        final ArrayList<HourToggleButton> myArrayList = (ArrayList<HourToggleButton>) intent.getSerializableExtra("lista");
        final boolean[] arraybool = (boolean[]) intent.getSerializableExtra("dinamic");
        final String coach = intent.getStringExtra("coach");

        setListAdapter(new HTBAdapter(this, R.layout.hour_toggle_button, myArrayList));


        final String date = intent.getStringExtra("date");
        TextView tv = (TextView) findViewById(R.id.reservationList_title);

        String text = (String) tv.getText();
        tv.setText(text + " " + date);

        final ListView lsv = getListView();

        Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> buttons = new ArrayList<Integer>();
                for (int i = 0 ; i<lsv.getChildCount(); i++) {
                    LinearLayout ll = (LinearLayout) lsv.getChildAt(i);
                    ToggleButton tb = (ToggleButton) ll.getChildAt(0);
                    if (tb.isChecked()) {
                        buttons.add(i);
                    }
                }

                System.out.println(buttons);

                boolean consecutive = true;

                for (int i = 0; i < buttons.size() - 1; i++) {
                    if (buttons.get(i) +1 != buttons.get(i + 1)) {
                        consecutive = false;
                    }
                }

                if (consecutive){
                    // Salva prenotazione sul server
                    for (int i = 0; i < buttons.size(); i++) {
                        arraybool[buttons.get(i)] = true;
                    }

                    ReserveParams params = new ReserveParams(arraybool, ReservationList.this, buttons.get(0), buttons.get(buttons.size() - 1), coach, date);
                    int reservation;

                    try {
                        reservation = new ReserveOperation().execute(params).get();
                        if (reservation == 10){
                            Intent intent = new Intent(ReservationList.this, StudentArea.class);
                            startActivity(intent);
                        }

                        if (reservation == 11 || reservation == 12){
                            Toast toast = Toast.makeText(ReservationList.this, "Database error", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast toast = Toast.makeText(ReservationList.this, "Select consecutive hours", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        Button back = (Button) findViewById(R.id.button_exit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
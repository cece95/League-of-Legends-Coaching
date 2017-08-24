package com.example.cesare.leagueoflegendscoaching.Classes.Components;

import android.graphics.Color;
import android.view.View;
import android.widget.ToggleButton;

import com.example.cesare.leagueoflegendscoaching.R;

/**
 * Created by cesare on 24/08/2017.
 */

public class HourToggleButton {
    int ora;
    int status;

    public HourToggleButton(int o, int s){
        this.ora = o;
        this.status = s;
    }

    public int getOra() {
        return ora;
    }

    public int getStatus() {
        return status;
    }

    public void create(View frame){
        System.out.println("creating frame "+ora+" "+status);
        ToggleButton tb = (ToggleButton) frame.findViewById(R.id.tb);
        String text = null;
        if (this.ora < 9){
            text = "0"+ora+" - 0"+(ora+1);
        }
        else if (this.ora == 9){
            text = "09 - 10";
        }
        else if (this.ora > 9){
            text = ora+" - "+(ora+1);
        }

        tb.setTextOn(text);
        tb.setTextOff(text);

        if (this.status == 1){
            tb.setBackgroundResource(R.drawable.book_toggle_button);
        }
        else{
            tb.setBackgroundColor(Color.RED);
            tb.setClickable(false);
        }
    }
}

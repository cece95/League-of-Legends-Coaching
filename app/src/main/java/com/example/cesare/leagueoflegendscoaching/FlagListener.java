package com.example.cesare.leagueoflegendscoaching;

import android.content.Context;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;

/**
 * Created by cesare on 28/05/2017.
 */

public class FlagListener implements View.OnClickListener {

    int background_color;
    Context baseContext;

    public FlagListener(Context context){
        this.baseContext = context;
    }


    @Override
    public void onClick(View v) {
        ToggleImageButton ti = (ToggleImageButton) v;
        if(ti.isChecked()){
            ContextThemeWrapper newContext = new ContextThemeWrapper(this.baseContext, R.style.Widget_AppCompat_Button_Colored);
            ToggleImageButton t1 = new ToggleImageButton(newContext);
            v.setBackground(t1.getBackground());

        }
        if(!ti.isChecked()){
            ContextThemeWrapper newContext = new ContextThemeWrapper(this.baseContext, R.style.Widget_AppCompat_Button);
            ToggleImageButton t1 = new ToggleImageButton(newContext);
            v.setBackground(t1.getBackground());
        }
    }
}

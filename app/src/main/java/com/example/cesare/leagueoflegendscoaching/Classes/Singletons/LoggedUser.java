package com.example.cesare.leagueoflegendscoaching.Classes.Singletons;

import android.content.Context;
import android.util.Log;

import com.example.cesare.leagueoflegendscoaching.Operations.Params.UserParams;
import com.example.cesare.leagueoflegendscoaching.Operations.UserOperation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by cesare on 05/08/2017.
 */

public class LoggedUser {
    private static LoggedUser mInstance = null;

    private String ign;
    private String password;
    private boolean isCoach;
    private String language;


    public LoggedUser(String ign, String password, boolean isCoach) {
        this.ign = ign;
        this.password = password;
        this.isCoach = isCoach;
        this.language = Locale.getDefault().getLanguage();
    }

    public LoggedUser(JSONObject obj) throws JSONException {
        this.ign = obj.getString("ign");
        this.password = obj.getString("password");
        this.isCoach = obj.getBoolean("isCoach");
        this.language = obj.getString("language");
    }

    public static LoggedUser getIstance(String ign, String password, boolean isCoach, Context c) {
        if (mInstance == null) {
            if (ign == null) {
                LoggedUser remember = rememberUser(c);
                if (remember != null) {
                    mInstance = remember;
                } else {
                    return null;
                }
            } else {
                mInstance = new LoggedUser(ign, password, isCoach);
                saveUser(mInstance, c);
            }
        }
        return mInstance;
    }

    public static void logout(Context c) {
        mInstance = null;
        c.deleteFile("user.json");
    }

    public String getIgn() {
        return ign;
    }

    public void setIgn(String ign) {
        this.ign = ign;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCoach() {
        return isCoach;
    }

    public void setCoach(boolean coach) {
        isCoach = coach;
    }

    public static void saveUser(LoggedUser user, Context c) {
        String filename = "user.json";
        FileOutputStream outputStream;

        try {
            JSONObject json = new JSONObject();
            json.put("ign", user.getIgn());
            json.put("password", user.getPassword());
            json.put("isCoach", user.isCoach());
            json.put("language", user.getLanguage());

            String jsonString = json.toString();
            Log.d("SAVING USER", jsonString);

            outputStream = c.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonString.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static LoggedUser rememberUser(Context c){
        LoggedUser res = null;
        FileInputStream inputStream;
        String filename = "user.json";

        try {
            inputStream = c.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            JSONObject json;
            json = new JSONObject(sb.toString());
            Log.d("REMEMBER USER", sb.toString());
            res = new LoggedUser(json);

            refreshChampions(res, c);

        } catch (IOException | JSONException | InterruptedException | ExecutionException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return res;
    }

    private static void refreshChampions(LoggedUser user, Context c) throws UnsupportedEncodingException, NoSuchAlgorithmException, ExecutionException, InterruptedException {
        if (user.isCoach()){
            UserParams params = new UserParams(user.getIgn(), user.getPassword(), c, "refresh", "");
            new UserOperation().execute(params).get();
        }
    }

    public String getLanguage() {
        return language;
    }
}

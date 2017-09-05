package com.example.cesare.leagueoflegendscoaching.Services;

import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.UserParams;
import com.example.cesare.leagueoflegendscoaching.Operations.UserOperation;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

/**
 * Created by cesare on 05/09/2017.
 */

public class InstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh(){
        String token = FirebaseInstanceId.getInstance().getToken();
        try {
            registerToken(token);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void registerToken(String token) throws UnsupportedEncodingException, NoSuchAlgorithmException, ExecutionException, InterruptedException {
        LoggedUser user = LoggedUser.getIstance(null, null, false);
        if (user != null){
            UserParams params = new UserParams(user.getIgn(), user.getPassword(), getApplicationContext(), "token", token);
            new UserOperation().execute(params).get();
        }

    }
}


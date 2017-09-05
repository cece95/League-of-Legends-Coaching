package com.example.cesare.leagueoflegendscoaching.Services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by cesare on 05/09/2017.
 */

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {
            Log.d("NOTIFICATION", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
}

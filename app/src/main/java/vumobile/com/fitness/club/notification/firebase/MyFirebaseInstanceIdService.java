package vumobile.com.fitness.club.notification.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import vumobile.com.fitness.club.utils.TokenPreference;

/**
 * Created by toukirul on 8/2/2018.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String REG_TOKEN = "REG_TOKEN";

    @Override
    public void onTokenRefresh() {

        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase","Firebase token "+recent_token);

        storeRegToken(recent_token);
    }

    private void storeRegToken(String recent_token) {
        TokenPreference.storeToken(this, recent_token);
    }
}

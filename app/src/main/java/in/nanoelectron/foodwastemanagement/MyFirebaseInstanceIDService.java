package in.nanoelectron.foodwastemanagement;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by root on 4/3/18.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        editor.putString("token",refreshedToken);
        editor.apply();
        editor.commit();


        Log.d("mytoken",refreshedToken);

    }
}

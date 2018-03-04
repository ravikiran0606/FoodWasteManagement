package in.nanoelectron.foodwastemanagement;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by root on 4/3/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private NotificationManager notificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.cast_ic_expanded_controller_skip_previous);
        mBuilder.setContentTitle(remoteMessage.getNotification().getTitle());
        mBuilder.setContentText(remoteMessage.getNotification().getBody());
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(79, mBuilder.build());

    }
}

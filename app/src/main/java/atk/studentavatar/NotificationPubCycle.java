package atk.studentavatar;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationPubCycle extends BroadcastReceiver{

    public static final String NOTE_ID = "note_id_1";
    public static final String NOTE_INTENT_KEY = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTE_INTENT_KEY);

        int id = intent.getIntExtra(NOTE_ID, 0);

        if (notificationManager != null) {
            notificationManager.notify(id, notification);
        }
    }
}

package atk.studentavatar;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

public class MakeNotificationJob extends Job {

    public static final String NOTIFICATION_JOB_CREATOR_TAG1 = "note_tag_1";
    public static final String NOTIFICATION_JOB_CREATOR_TAG2 = "note_tag_2";

    public static final String NOTE_ID = "note_id_1";
    public static final String NOTE_INTENT_KEY = "notification";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {

        Log.d("job","on run");

        if(params.getTag().equals(NOTIFICATION_JOB_CREATOR_TAG1))
        {
            ManageNoteExe("hi");
        }
        else
        {
            ManageNoteExe("h222222");
        }
        return Result.SUCCESS;
    }

    public static void scheduleJob(String tag, long time)
    {
        Log.d("job","job request builder");
        JobRequest.Builder builder = new JobRequest.Builder(tag);
        builder.setExact(time);
        //builder.startNow();
        builder.build().schedule();
        //scheduleJob(NOTIFICATION_JOB_CREATOR_TAG1, );
    }

    private Notification getNotification(String title)
    {
        Log.d("job","get note");
        Notification.Builder builder = new Notification.Builder(getContext());
        builder.setContentTitle(title);
        builder.setContentText("test notifications");
        builder.setSmallIcon(R.drawable.ic_calendar_text_black_18dp);

        //Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //builder.setSound(alarmSound);

        //builder.build();
        return builder.build();
    }

    private void ManageNoteExe(String title)
    {
        Log.d("job","mang note");
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = getNotification(title);

        if (notificationManager != null) {
            Log.d("job","notify");
            notificationManager.notify(1, notification);
        }
    }
}

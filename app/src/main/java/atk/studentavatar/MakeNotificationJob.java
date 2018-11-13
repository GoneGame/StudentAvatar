package atk.studentavatar;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MakeNotificationJob extends Job {

    public static final String NOTIFICATION_JOB_CREATOR_TAG1 = "note_tag_1";
    public static final String NOTIFICATION_JOB_CREATOR_TAG2 = "note_tag_2";

    public static final String NOTE_ID = "note_id_1";
    public static final String NOTE_INTENT_KEY = "notification";

    private String miliToday;

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

        /*
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String today = year + "-" + (month + 1) + "-" + day;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");

        try {
            Date date = format.parse(today);
            miliToday = Long.toString(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("event").orderByChild("date/" + miliToday).equalTo(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        return Result.SUCCESS;
    }

    public static void scheduleJob(String tag, long time)
    {
        Log.d("job","job request builder");
        JobRequest.Builder builder = new JobRequest.Builder(tag);
        builder.setExact(time);
        //builder.startNow();
        builder.build().schedule();
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

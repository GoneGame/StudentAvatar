package atk.studentavatar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

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

import atk.studentavatar.models.Event;

public class NotificationRecycler {

    private String _title;
    private long _exactTimeMili;

    private Context _context;

    //public boolean endToday = false;

    public NotificationRecycler(Context context) {
        this._context = context;
    }

    private String getDateMiliToday()
    {
        String miliToday = "";

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

        Log.d("timeToday", miliToday);

        return miliToday;
    }

    private void scheduleNotification(boolean btnclk) {

        if(btnclk)
        {
            Intent intent = new Intent(_context, NotificationPubCycle.class);
            intent.putExtra(NotificationPubCycle.NOTE_ID, 1);
            intent.putExtra(NotificationPubCycle.NOTE_INTENT_KEY, getNotification(_title));

            int ticks = (int) System.currentTimeMillis();
            //get unique number with system time in milliseconds
            PendingIntent pendingIntent = PendingIntent.getBroadcast(_context, ticks, intent, PendingIntent.FLAG_ONE_SHOT);

            AlarmManager alarmManager = (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                //set exact time alarm will trigger pending Intent
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, _exactTimeMili, pendingIntent);
            }
        }
        else
        {
            Log.d("end", "no more notifications today");
        }
    }

    private Notification getNotification(String title)
    {
        Notification.Builder builder = new Notification.Builder(_context);
        builder.setContentTitle(title);
        builder.setContentText("test notifications");
        builder.setSmallIcon(R.drawable.ic_calendar_text_black_18dp);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        return builder.build();
    }

    //first log the next event time
    public void getNextTimeToTrigger(boolean b)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query eventsQuery = reference.child("event").orderByChild("date/" + getDateMiliToday()).equalTo(true);

        eventsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(b)
                {
                    Log.d("all", dataSnapshot.toString());

                    for(DataSnapshot ss : dataSnapshot.getChildren())
                    {
                        Event event = ss.getValue(Event.class);

                        Log.d("key", ss.getKey());

                        Log.d("title", event.title);
                        //Log.d("first date", entry.getKey());

                        Log.d("timeStart", event.timestart);

                        String startTime = event.timestart;

                        String[] half = startTime.split(":");

                        int hr = Integer.parseInt(half[0]);
                        int min = Integer.parseInt(half[1]);

                        long miliTodayTimePrecise = (hr * 60 + min) * 60000 + Long.parseLong(getDateMiliToday());

                        if(event.note)
                        {
                            if(miliTodayTimePrecise > System.currentTimeMillis())
                            {
                                _title = event.title;
                                _exactTimeMili = miliTodayTimePrecise;

                                Log.d("timecomp", Long.toString(System.currentTimeMillis()));
                                Log.d("timesche", Long.toString(_exactTimeMili));

                                scheduleNotification(b);
                                break;
                            }
                            else
                            {
                                Log.d("end", "no more notifications today");
                            }
                        }
                        else
                        {
                            Log.d("end", "notification is false");
                        }
                    }
                }
                else
                {
                    eventsQuery.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
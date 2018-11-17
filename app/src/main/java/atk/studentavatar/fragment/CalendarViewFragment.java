package atk.studentavatar.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.Random;

import atk.studentavatar.CalendarActivity;
import atk.studentavatar.NotificationPubCycle;
import atk.studentavatar.NotificationRecycler;
import atk.studentavatar.R;


public abstract class CalendarViewFragment extends Fragment {

    public static final String EVENT_INTENT_KEY = "event_date";

    private CalendarView calendarView;
    private Button noteBtn, filterBtn;

    private boolean onNotifications;

    private int y, m, d;
    private String date;

    private static final String SHAREDKEY = "pref";
    private static final String NOTIFICATION_STAT = "notification_status";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    private NotificationRecycler notificationRecycler;

    private PackageManager packageManager;
    private ComponentName componentName;

    public CalendarViewFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notificationRecycler = new NotificationRecycler(context);

        packageManager = context.getPackageManager();
        componentName = new ComponentName(context, NotificationPubCycle.class);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_calendar_view, container, false);

        TextView hello;

        calendarView = rootView.findViewById(R.id.calendarView);
        hello = rootView.findViewById(R.id.helloTextView);
        noteBtn = rootView.findViewById(R.id.notificationButton);
        filterBtn = rootView.findViewById(R.id.filtersButton);

        Random random = new Random();

        //number 0 - 3
        int g = random.nextInt(4);
        String[] strings = {getString(R.string.helloText1),
                getString(R.string.helloText2),
                getString(R.string.helloText3),
                getString(R.string.helloText4)};

        hello.setText(strings[g]);

        return rootView;
    }

    private void goToCalendarActivity()
    {
        //https://stackoverflow.com/questions/17453297/passing-arraylist-of-string-arrays-from-one-activity-to-another-in-android
        Intent intent = new Intent(getActivity(), CalendarActivity.class);
        intent.putExtra(EVENT_INTENT_KEY, date);
        //intent.putStringArrayListExtra(EVENT_INTENT_KEY, EventToCard);
        Log.d("calenFrag", "3 bundle put in");
        startActivity(intent);
    }

    //code for username, here if needed
    //query = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid());

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //i2 = day, i1 = month - 1, i = year
                //Toast.makeText(getContext(), i2 + "/" + Integer.toString(i1 + 1) + "/" + i, Toast.LENGTH_SHORT).show();
                y = i;
                m = i1 + 1;
                d = i2;

                final String t = Integer.toString(y) + "-" + Integer.toString(m) + "-" + Integer.toString(d);

                date = t;

                Toast.makeText(getContext(), t, Toast.LENGTH_SHORT).show();
                goToCalendarActivity();
            }
        });

        preferences = this.getActivity().getSharedPreferences(SHAREDKEY, Context.MODE_PRIVATE);

        if(preferences.contains(NOTIFICATION_STAT))
        {
            Log.d("pref", "into contains");

            onNotifications = preferences.getBoolean(NOTIFICATION_STAT, false);

            if(onNotifications)
            {
                Log.d("pref", "into contains true");
                noteBtn.setText(getString(R.string.note_off_btn));
                //put the trigger here

                packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            }
            else
            {
                Log.d("pref", "into contains false");
                noteBtn.setText(getString(R.string.note_on_btn));
                packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            }

            notificationRecycler.getNextTimeToTrigger(onNotifications);
        }
        else
        {
            Log.d("pref", "out contains");
            noteBtn.setText(getString(R.string.note_on_btn));
        }

        noteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chgBtnLgc();
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open fragment, use calendar activity
                //keep an array of strings as words or maybe ids
            }
        });
    }

    private void chgBtnLgc()
    {
        //switch, so if true turn off, if false turn on
        if(onNotifications)
        {
            onNotifications = false;
            noteBtn.setText(getString(R.string.note_on_btn));
            //turn off notifications

            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
        else
        {
            onNotifications = true;
            noteBtn.setText(getString(R.string.note_off_btn));
            //do not get confused, noteBtn is set to turn off
            //because the notification in on

            //getNextTimeToTrigger();
            //turn on notifications

            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        }

        notificationRecycler.getNextTimeToTrigger(onNotifications);

        editor = preferences.edit();
        editor.putBoolean(NOTIFICATION_STAT, onNotifications);
        editor.apply();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);
}

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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


import atk.studentavatar.CalendarActivity;
import atk.studentavatar.NotificationPubCycle;
import atk.studentavatar.NotificationRecycler;
import atk.studentavatar.R;


public abstract class CalendarViewFragment extends Fragment {

    public static final String EVENT_INTENT_KEY = "event_date";
    public static final String EVENT_INTENT_FILTER_KEY = "event_filter";

    private CalendarView calendarView;
    private Button filterBtn;

    private Switch noteSwitch;

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

        calendarView = rootView.findViewById(R.id.calendarView);
        filterBtn = rootView.findViewById(R.id.filtersButton);

        noteSwitch = rootView.findViewById(R.id.notiSwitch);

        return rootView;
    }

    private void goToCalendarActivity()
    {
        Intent intent = new Intent(getActivity(), CalendarActivity.class);
        intent.putExtra(EVENT_INTENT_KEY, date);
        startActivity(intent);
    }

    //code for username, here if needed
    //query = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid());

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        preferences = this.getActivity().getSharedPreferences(SHAREDKEY, Context.MODE_PRIVATE);

        if(preferences.contains(NOTIFICATION_STAT))
        {
            Log.d("pref", "into contains");

            onNotifications = preferences.getBoolean(NOTIFICATION_STAT, false);

            if(onNotifications)
            {
                Log.d("pref", "into contains true");
                //put the trigger here
                packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            }
            else
            {
                Log.d("pref", "into contains false");
                packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            }

            noteSwitch.setChecked(onNotifications);
            notificationRecycler.getNextTimeToTrigger(onNotifications);
        }
        else
        {
            Log.d("pref", "out contains");
        }

        noteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                chgBtnLgc();
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open fragment, use calendar activity
                //keep an array of strings as words or maybe ids
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                intent.putExtra(EVENT_INTENT_FILTER_KEY, "1");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
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
    }

    private void chgBtnLgc()
    {
        //switch, so if true turn off, if false turn on
        if(onNotifications)
        {
            onNotifications = false;
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
        else
        {
            onNotifications = true;
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        }

        notificationRecycler.getNextTimeToTrigger(onNotifications);

        editor = preferences.edit();
        editor.putBoolean(NOTIFICATION_STAT, onNotifications);
        editor.apply();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);
}

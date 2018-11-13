package atk.studentavatar.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import atk.studentavatar.CalendarActivity;
import atk.studentavatar.CalendarCardViewActivity;
import atk.studentavatar.MakeNotificationJob;
import atk.studentavatar.R;
import atk.studentavatar.models.Event;

public abstract class CalendarViewFragment extends Fragment {

    // [START define_database_reference]
    private DatabaseReference reference;
    // [END define_database_reference]

    private static final String EVENT_INTENT_KEY = "EVENT_LIST";

    private TextView hello;
    private CalendarView calendarView;
    private Button button;

    private boolean onNotifications;

    private int y, m, d;

    private String date;

    private static final String SHAREDKEY = "pref";
    private static final String NOTIFICATION_STAT = "notification_status";


    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    //private ArrayList<String> EventToCard = new ArrayList<>();

    //some copy
    private Event ll = new Event();

    public CalendarViewFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        reference = FirebaseDatabase.getInstance().getReference();

        View rootView = inflater.inflate(R.layout.fragment_calendar_view, container, false);

        calendarView = rootView.findViewById(R.id.calendarView);
        hello = rootView.findViewById(R.id.helloTextView);
        button = rootView.findViewById(R.id.notificationButton);

        Random random = new Random();

        //number 0 - 3
        int g = random.nextInt(4);
        String[] strings = {getString(R.string.helloText1),
                getString(R.string.helloText2),
                getString(R.string.helloText3),
                getString(R.string.helloText4)};

        hello.setText(strings[g]);

        //button interaction

        button = rootView.findViewById(R.id.notificationButton);

        preferences = this.getActivity().getSharedPreferences(SHAREDKEY, Context.MODE_PRIVATE);

        if(preferences.contains(NOTIFICATION_STAT))
        {
            Log.d("pref", "into contains");
            onNotifications = preferences.getBoolean(NOTIFICATION_STAT, false);
            if(onNotifications)
            {
                Log.d("pref", "into contains true");
                button.setText(getString(R.string.note_off_btn));
            }
            else
            {
                Log.d("pref", "into contains false");
                button.setText(getString(R.string.note_on_btn));
            }
        }
        else
        {
            Log.d("pref", "out contains");
            button.setText(getString(R.string.note_on_btn));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chgBtnLgc();
            }
        });

        return rootView;
    }

    private void chgBtnLgc()
    {
        //switch, so if true turn off, if false turn on
        if(onNotifications)
        {
            onNotifications = false;
            button.setText(getString(R.string.note_on_btn));
            //turn off notifications
        }
        else
        {
            onNotifications = true;
            button.setText(getString(R.string.note_off_btn));
            //turn on notifications
            MakeNotificationJob.scheduleJob(MakeNotificationJob.NOTIFICATION_JOB_CREATOR_TAG1, 3000);
            MakeNotificationJob.scheduleJob(MakeNotificationJob.NOTIFICATION_JOB_CREATOR_TAG2, 6000);
        }

        editor = preferences.edit();
        editor.putBoolean(NOTIFICATION_STAT, onNotifications);
        editor.apply();

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
                /*
                Query eventsQuery = getQuery(reference);
                eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Log.d("all", dataSnapshot.toString());


                        for(DataSnapshot cc : dataSnapshot.getChildren())
                        {
                            Event event = cc.getValue(Event.class);

                            Log.d("key", cc.getKey());

                            Log.d("title", event.title);
                            //Log.d("first date", entry.getKey());

                            for(String i : event.date)
                            {
                                if(i.matches(t))
                                {
                                    Log.d("date", cc.getKey());
                                    Log.d("date", i);
                                    EventToCard.add(cc.getKey());
                                }
                            }
                            Log.d("date", "------");
                        }


                        //this function must be here to transfer the keys properly
                        //probably due to a delay in the addition to Array List


                        EventToCard.add(t);
                        goToCalendarActivity();
                        EventToCard.clear();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });*/

                Toast.makeText(getContext(), t, Toast.LENGTH_SHORT).show();
                goToCalendarActivity();

                /*
                Log.d("inList", "here");

                for(String s : EventToCard)
                {
                    Log.d("inList", s);
                }*/


            }
        });
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

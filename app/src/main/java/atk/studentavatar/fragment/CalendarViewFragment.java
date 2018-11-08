package atk.studentavatar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;
import java.util.Random;

import atk.studentavatar.CalendarCardViewActivity;
import atk.studentavatar.R;
import atk.studentavatar.models.Event;

public abstract class CalendarViewFragment extends Fragment {

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private static final String EVENT_INTENT_KEY = "EVENT_LIST";

    private TextView hello;
    private CalendarView calendarView;
    //private Query query;

    private int y, m, d;

    private ArrayList<Event> eventList = new ArrayList<>();

    private ArrayList<String> EventToCard = new ArrayList<>();

    //some copy
    private Event ll = new Event();

    public CalendarViewFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        View rootView = inflater.inflate(R.layout.fragment_calendar_view, container, false);

        calendarView = rootView.findViewById(R.id.calendarView);
        hello = rootView.findViewById(R.id.helloTextView);

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

    //pass values and open new fragment to view card view of events

    /*
    private Bundle packBundle()
    {
        //https://stackoverflow.com/questions/13601883/how-to-pass-arraylist-of-objects-from-one-to-another-activity-using-intent-in-an/24630640
        //Bundle bundle = new Bundle();
        //bundle.putInt("Year", year);
        //bundle.putInt("Month", month + 1);
        //bundle.putInt("Day", day);

        //bundle.putSerializable("EventList", eventList);

        //bundle.putString("Username", usernameE);

        //Log.d("calenFrag", "2 from keep data Username: " + usernameE);
        //return bundle;
    }*/

    private void goToCardView()
    {
        //https://stackoverflow.com/questions/17453297/passing-arraylist-of-string-arrays-from-one-activity-to-another-in-android
        Intent intent = new Intent(getActivity(), CalendarCardViewActivity.class);
        intent.putStringArrayListExtra(EVENT_INTENT_KEY, EventToCard);
        Log.d("calenFrag", "3 bundle put in");
        startActivity(intent);
    }

    private void getUserName()
    {
        //Query eventsQuery = getQuery(mDatabase);

        //query = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid());
        /*
        eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event eventt = dataSnapshot.getValue(Event.class);
                //String s = user.username;

                String title = eventt.title;

                //usernameE = s;
                Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
                //Log.d("calenFrag", "1 get Username: " + usernameE);
                //goToCardView(keepData(y, m ,d));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        //Log.d("calenFrag", "2 return Username: " + usernameE);
    }

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

                Query eventsQuery = getQuery(mDatabase);

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

                        /*
                        * this function must be here to transfer the keys properly
                        * probably due to a delay in the addition to Array List
                        * */

                        goToCardView();
                        EventToCard.clear();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(getContext(), t, Toast.LENGTH_SHORT).show();


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

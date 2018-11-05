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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import atk.studentavatar.CalendarCardViewActivity;
import atk.studentavatar.R;
import atk.studentavatar.models.Event;

public abstract class CalendarViewFragment extends Fragment {

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private TextView hello;
    private CalendarView calendarView;
    //private Query query;

    private int y, m, d;

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

    private Bundle keepData(int year, int month, int day)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("Year", year);
        bundle.putInt("Month", month + 1);
        bundle.putInt("Day", day);
        //bundle.putString("Username", usernameE);

        //Log.d("calenFrag", "2 from keep data Username: " + usernameE);
        return bundle;
    }

    private void goToCardView(Bundle bundle)
    {
        Intent intent = new Intent(getActivity(), CalendarCardViewActivity.class);
        intent.putExtras(bundle);
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

        Query eventsQuery = getQuery(mDatabase);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //i2 = day, i1 = month - 1, i = year
                //Toast.makeText(getContext(), i2 + "/" + Integer.toString(i1 + 1) + "/" + i, Toast.LENGTH_SHORT).show();
                y = i;
                m = i1 + 1;
                d = i2;

                String t = Integer.toString(y) + "-" + Integer.toString(m) + "-" + Integer.toString(d);

                eventsQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot cc : dataSnapshot.getChildren())
                        {
                            Event event = cc.getValue(Event.class);

                            Log.d("key", cc.getKey());
                            //Log.d("key", cc.getRef().toString());
                            Log.d("title", event.title);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //Toast.makeText(getContext(), ss[0], Toast.LENGTH_SHORT).show();

                //goToCardView(keepData(i, i1, i2, getUserName()));
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

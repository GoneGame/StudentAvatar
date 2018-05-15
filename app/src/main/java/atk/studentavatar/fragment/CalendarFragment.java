package atk.studentavatar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import atk.studentavatar.CalendarCardViewActivity;
import atk.studentavatar.GeneralDetailActivity;
import atk.studentavatar.R;
import atk.studentavatar.models.User;

public class CalendarFragment extends Fragment{
    private CalendarView calendarView;
    private Query query;

    private String usernameE;
    private int y, m, d;

    public CalendarFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_calendar_view, container, false);

        calendarView = rootView.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //i2 = day, i1 = month - 1, i = year
                //Toast.makeText(getContext(), i2 + "/" + Integer.toString(i1 + 1) + "/" + i, Toast.LENGTH_SHORT).show();
                y = i;
                m = i1;
                d = i2;
                getUserName();
                //goToCardView(keepData(i, i1, i2, getUserName()));
            }
        });
        return rootView;
    }

    //pass values and open new fragment to view card view of events

    private Bundle keepData(int year, int month, int day)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("Year", year);
        bundle.putInt("Month", month + 1);
        bundle.putInt("Day", day);
        bundle.putString("Username", usernameE);

        Log.d("calenFrag", "2 from keep data Username: " + usernameE);
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
        query = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String s = user.username;

                usernameE = s;
                Toast.makeText(getContext(), usernameE, Toast.LENGTH_SHORT).show();
                Log.d("calenFrag", "1 get Username: " + usernameE);
                goToCardView(keepData(y, m ,d));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.d("calenFrag", "2 return Username: " + usernameE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}

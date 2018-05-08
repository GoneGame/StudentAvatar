package atk.studentavatar.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import atk.studentavatar.R;

public class CalendarFragment extends Fragment {
    private LinearLayoutManager mManager;
    private CalendarView calendarView;

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


                Bundle bundle = new Bundle();
                bundle.putInt("Year", i);
                bundle.putInt("Month", i1 + 1);
                bundle.putInt("Day", i2);

                //find way to get current user from firebase
                //continue to watch video to open new fragment
            }
        });
        return rootView;
    }

    //pass values and open new fragment to view card view of events
    private void goToListFragment()
    {
        CalendarListFragment calendarListFragment = new CalendarListFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.calenLinear, calendarListFragment).commit();
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

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

import atk.studentavatar.R;

public class CalendarListFragment extends Fragment {
    private LinearLayoutManager mManager;
    private CalendarView calendarView;
    private int year, month, date;

    public CalendarListFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_calendar_list, container, false);

        //get date and username information from calendarfragment
        Bundle bundle = getArguments();
        year = bundle.getInt("Year");
        month = bundle.getInt("Month");
        date = bundle.getInt("Day");
        //and finally the username

        bundle.clear();

        //recycler view stuff

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void queryToMySQLserver()
    {
        //volley script here
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

package atk.studentavatar.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import atk.studentavatar.ConstantURL;
import atk.studentavatar.MySingletonVolley;
import atk.studentavatar.R;
import atk.studentavatar.models.Event;
import atk.studentavatar.viewholder.CalendarHolderAdapter;

public class CalendarListFragment extends Fragment {
    private RecyclerView recyclerView;
    private CalendarHolderAdapter calendarHolderAdapter;
    private List<Event> events;

    private int year, month, day;
    private String username;

    public CalendarListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        year = bundle.getInt("Year");
        month = bundle.getInt("Month");
        day = bundle.getInt("Day");
        username = bundle.getString("Username");

        bundle.clear();
    }

    private void testevent()
    {
        events.add(new Event("hi"));
        events.add(new Event("bye"));
        events.add(new Event("die"));
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_calendar_list, container, false);

        recyclerView = view.findViewById(R.id.calenListRec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //test the events, replace it with events later
        calendarHolderAdapter = new CalendarHolderAdapter(getActivity(), events);

        recyclerView.setAdapter(calendarHolderAdapter);
        
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void queryToMySQLserver()
    {
        final String date = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantURL.URL_GET_EVENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("date", date);
                return map;
            }
        };

        //hopefully getContext() works..
        MySingletonVolley.getInstance(getContext()).addToRequestQueue(stringRequest);

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

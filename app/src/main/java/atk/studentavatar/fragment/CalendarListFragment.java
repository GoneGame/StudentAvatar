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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import atk.studentavatar.ConstantURL;
import atk.studentavatar.R;

public class CalendarListFragment extends Fragment {
    private LinearLayoutManager mManager;
    private CalendarView calendarView;
    private int year, month, day;

    public CalendarListFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_calendar_list, container, false);

        //get date and username information from calendarfragment
        Bundle bundle = getArguments();
        year = bundle.getInt("Year");
        month = bundle.getInt("Month");
        day = bundle.getInt("Day");
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
        //write the php script
        final String username = "put from current user";
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
            }
        };

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

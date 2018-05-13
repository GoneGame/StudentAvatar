package atk.studentavatar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import atk.studentavatar.models.Event;
import atk.studentavatar.viewholder.CalendarHolderAdapter;


public class CalendarCardViewActivity extends BaseActivity{

    private static final String TAG = "CalendarCardViewActivity";
    public static final String EXTRA_CALENDAR_KEY = "calendar_key";

    private RecyclerView recyclerView;
    private CalendarHolderAdapter calendarHolderAdapter;
    private List<Event> events;

    private int year, month, day;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_card_view_holder);

        Log.d("lolo", "IN new activity");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(!bundle.isEmpty())
        {
            Log.d("lolo", "IN bundle");
            year = bundle.getInt("Year");
            month = bundle.getInt("Month");
            day = bundle.getInt("Day");
            username = bundle.getString("Username");

            //bundle.clear();
        }

        Log.d("lolo", "before test event");
        testevent();

        Log.d("lolo11111111111", events.get(0).getTitle());
        Log.d("lolo11111111111", events.get(1).getTitle());
        Log.d("lolo111111111111", events.get(2).getTitle());

        recyclerView = findViewById(R.id.calenListRec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d("lolo", "before adapter");
        calendarHolderAdapter = new CalendarHolderAdapter(this, events);
        Log.d("lolo", "before rec set");
        recyclerView.setAdapter(calendarHolderAdapter);
    }

    private void testevent()
    {
        Log.d("lolo", "in test event");
        events = new ArrayList<Event>();

        events.add(new Event("hi"));
        events.add(new Event("bye"));
        events.add(new Event("die"));
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

        MySingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

}

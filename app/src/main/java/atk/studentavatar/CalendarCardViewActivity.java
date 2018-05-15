package atk.studentavatar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import atk.studentavatar.models.Event;
import atk.studentavatar.models.EventClub;
import atk.studentavatar.models.EventGeneral;
import atk.studentavatar.models.EventNotifi;
import atk.studentavatar.models.EventUnit;
import atk.studentavatar.viewholder.CalendarHolderAdapter;


public class CalendarCardViewActivity extends BaseActivity{

    private static final String TAG = "CalendarCardViewActivity";
    public static final String EXTRA_CALENDAR_KEY = "calendar_key";

    private RecyclerView recyclerView;
    private CalendarHolderAdapter calendarHolderAdapter;
    private List<Event> events;

    private TextView date_on_view, status;

    private int year, month, day;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_card_view_holder);

        checkBundle();

        Log.d("lolo", "before test event");
        //testevent();

        queryToMySQLserver();

    }

    private void checkBundle()
    {
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

            Log.d("lolo", "year: " + Integer.toString(year));
            Log.d("lolo", "month: " + Integer.toString(month));
            Log.d("lolo", "day: " + Integer.toString(day));
            //bundle.clear();
        }
    }

    private void setAdapter()
    {
        Log.d("lolo", "2 set adapter");
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

        events.add(new EventGeneral("title 1 test", "2018-5-15", "15:00", "Hello, put more text here", "GG33"));
        events.add(new EventClub());
        events.add(new EventGeneral("title 2 test", "2018-5-15", "16:00", "Hello, put more text here", "GG33"));
        events.add(new EventUnit("testing title", "2018-5-15", "15:00", "Hello, put more text here", "GG33", "the linked unit"));
        events.add(new EventClub("title 1 test"));
    }


    private void dateSet()
    {
        Log.d("lolo", "3 set date");
        String date = getString(R.string.dateText, Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day));
        date_on_view = findViewById(R.id.TextView_date);
        date_on_view.setText(date);


        if(events.isEmpty())
        {
            status = findViewById(R.id.TextView_eeeeS);
            status.setVisibility(View.VISIBLE);

        }
    }

    private void queryToMySQLserver()
    {
        final String date = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantURL.URL_GET_EVENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Log.d("lolo", response);
                    formatEvents(jsonObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CalendarCardViewActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                Log.d("lolo", "Username: " + username);
                Log.d("lolo", "Date: " + date);
                map.put("user_name", username);
                map.put("date", date);
                return map;
            }
        };

        MySingletonVolley.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void formatEvents(JSONObject jsonObject)
    {

        try {
            Log.d("lolo", "1 in format events try");
            events = new ArrayList<Event>();
            Event tempEvent;

            String[] eventType = {"event", "notifi", "club", "unit"};

            JSONArray jsonArray1 = jsonObject.getJSONArray("event");

            //for general event...title, date, location, time, description

            for(int i = 0; i < jsonArray1.length(); i++)
            {
                tempEvent = new EventGeneral();
                JSONObject object = jsonArray1.getJSONObject(i);
                tempEvent.setTitle(object.getString("title"));
                tempEvent.setDate(object.getString("date"));
                tempEvent.setLocation(object.getString("location"));
                tempEvent.setTime(object.getString("time"));
                tempEvent.setDescription(object.getString("description"));
                events.add(tempEvent);
            }

            JSONArray jsonArray2 = jsonObject.getJSONArray(eventType[1]);
            //for notifications...title, date, time, description

            for(int j = 0; j < jsonArray2.length(); j++)
            {
                tempEvent = new EventNotifi();
                JSONObject object = jsonArray2.getJSONObject(j);
                tempEvent.setTitle(object.getString("title"));
                tempEvent.setDate(object.getString("date"));
                tempEvent.setTime(object.getString("time"));
                tempEvent.setDescription(object.getString("description"));
                events.add(tempEvent);
                tempEvent.clearValues();
            }

            JSONArray jsonArray3 = jsonObject.getJSONArray(eventType[2]);
            //for club and unit...name, title, date, location, time, description

            for(int j = 0; j < jsonArray3.length(); j++)
            {
                tempEvent = new EventClub();
                JSONObject object = jsonArray3.getJSONObject(j);
                tempEvent.setTitle(object.getString("title"));
                tempEvent.setDate(object.getString("date"));
                tempEvent.setTime(object.getString("time"));
                tempEvent.setDescription(object.getString("description"));
                tempEvent.setLocation(object.getString("location"));
                tempEvent.setName(object.getString("name"));
                events.add(tempEvent);
            }

            JSONArray jsonArray4 = jsonObject.getJSONArray(eventType[3]);

            for(int j = 0; j < jsonArray4.length(); j++)
            {
                tempEvent = new EventClub();
                JSONObject object = jsonArray4.getJSONObject(j);
                tempEvent.setTitle(object.getString("title"));
                tempEvent.setDate(object.getString("date"));
                tempEvent.setTime(object.getString("time"));
                tempEvent.setDescription(object.getString("description"));
                tempEvent.setLocation(object.getString("location"));
                tempEvent.setName(object.getString("name"));
                events.add(tempEvent);
            }

            Log.d("lolo", "1.5 in format events try");
            setAdapter();
            dateSet();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

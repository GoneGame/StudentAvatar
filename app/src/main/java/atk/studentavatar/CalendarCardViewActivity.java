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
        testevent();

        Log.d("lolo11111111111", events.get(0).getTitle());
        Log.d("lolo11111111111", events.get(1).getTitle());
        Log.d("lolo111111111111", events.get(2).getTitle());

        setAdapter();

        dateSet();

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

            //bundle.clear();
        }
    }

    private void setAdapter()
    {
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

        events.add(new EventGeneral("hi"));
        events.add(new EventGeneral("bye"));
        events.add(new EventGeneral("die"));
    }


    private void dateSet()
    {
        String date = getString(R.string.dateText, Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day));
        date_on_view = findViewById(R.id.TextView_date);
        date_on_view.setText(date);


        if(events.isEmpty())
        {
            status = findViewById(R.id.TextView_eventStatus);
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
                map.put("username", username);
                map.put("date", date);
                return map;
            }
        };

        MySingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void formatEvents(JSONObject jsonObject)
    {

        try {
            events = new ArrayList<Event>();
            Event tempEvent;

            String[] eventType = {"event", "notifi", "club", "unit"};

            JSONArray jsonArray = jsonObject.getJSONArray(eventType[0]);

            //for general event...title, date, location, time, description
            tempEvent = new EventGeneral();
            for(int j = 0; j < jsonArray.length(); j++)
            {
                JSONObject object = jsonArray.getJSONObject(j);
                tempEvent.setTitle(object.getString("title"));
                tempEvent.setDate(object.getString("date"));
                tempEvent.setLocation(object.getString("location"));
                tempEvent.setTime(object.getString("time"));
                tempEvent.setDescription(object.getString("description"));
                events.add(tempEvent);
                tempEvent.clearValues();
            }

            //for notifications...title, date, time, description
            tempEvent = new EventNotifi();
            for(int j = 0; j < jsonArray.length(); j++)
            {
                JSONObject object = jsonArray.getJSONObject(j);
                tempEvent.setTitle(object.getString("title"));
                tempEvent.setDate(object.getString("date"));
                tempEvent.setTime(object.getString("time"));
                tempEvent.setDescription(object.getString("description"));
                events.add(tempEvent);
                tempEvent.clearValues();
            }

            //for club and unit...name, title, date, location, time, description
            tempEvent = new EventClub();
            for(int j = 0; j < jsonArray.length(); j++)
            {
                JSONObject object = jsonArray.getJSONObject(j);
                tempEvent.setTitle(object.getString("title"));
                tempEvent.setDate(object.getString("date"));
                tempEvent.setTime(object.getString("time"));
                tempEvent.setDescription(object.getString("description"));
                tempEvent.setLocation(object.getString("location"));
                tempEvent.setName(object.getString("name"));
                events.add(tempEvent);
                tempEvent.clearValues();
            }

            tempEvent = new EventClub();
            for(int j = 0; j < jsonArray.length(); j++)
            {
                JSONObject object = jsonArray.getJSONObject(j);
                tempEvent.setTitle(object.getString("title"));
                tempEvent.setDate(object.getString("date"));
                tempEvent.setTime(object.getString("time"));
                tempEvent.setDescription(object.getString("description"));
                tempEvent.setLocation(object.getString("location"));
                tempEvent.setName(object.getString("name"));
                events.add(tempEvent);
                tempEvent.clearValues();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

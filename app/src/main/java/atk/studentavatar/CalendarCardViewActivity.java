package atk.studentavatar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;


public class CalendarCardViewActivity extends BaseActivity {

    private static final String TAG = "CalendarCardViewActivity";
    public static final String EXTRA_CALENDAR_KEY = "calendar_key";

    private String selDate;
    private static final String EVENT_INTENT_KEY = "EVENT_LIST";

    private RecyclerView recyclerView;
    //private CalendarHolderAdapter calendarHolderAdapter;


    //probably going to be useless
    private ArrayList<String> selEventListAndSelDate = new ArrayList<>();

    private TextView date_on_view, status;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        selEventListAndSelDate.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_card_view_holder);


        Log.d("lolo", "before test event");


        Intent i = getIntent();
        selEventListAndSelDate = i.getStringArrayListExtra(EVENT_INTENT_KEY);

        for(String selEve : selEventListAndSelDate)
        {
            Log.d("lolo", selEve);
        }

        selDate = selEventListAndSelDate.get(selEventListAndSelDate.size() - 1);

        Log.d("lolo", selDate);
    }

    /*
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
    }*/
}

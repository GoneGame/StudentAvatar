package atk.studentavatar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import atk.studentavatar.models.Event;
import atk.studentavatar.viewholder.CalendarViewHolder;


public class CalendarCardViewActivity extends BaseActivity {

    private static final String TAG = "CalendarCardViewActivity";
    public static final String EXTRA_CALENDAR_KEY = "calendar_key";

    private String selDate;
    private static final String EVENT_INTENT_KEY = "EVENT_LIST";

    private DatabaseReference reference;

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Event, CalendarViewHolder> firebaseRecyclerAdapter;

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
        setContentView(R.layout.fragment_all_calendar);

        Log.d("lolo", "before test event");

        reference = FirebaseDatabase.getInstance().getReference();

        Intent i = getIntent();
        selEventListAndSelDate = i.getStringArrayListExtra(EVENT_INTENT_KEY);

        for(String selEve : selEventListAndSelDate)
        {
            Log.d("lolo", selEve);
        }

        selDate = selEventListAndSelDate.get(selEventListAndSelDate.size() - 1);

        Log.d("lolo", selDate);

        date_on_view = findViewById(R.id.TextView_date);
        date_on_view.setText(selDate);
        setAdapter();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setAdapter()
    {
        Log.d("lolo", "2 set adapter");
        recyclerView = findViewById(R.id.calendarListRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query queryEvent = reference.child("event");

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Event>().setQuery(queryEvent, Event.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, CalendarViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CalendarViewHolder holder, int position, @NonNull Event model) {

                /*
                * the important part is holder.bindToCalendar(model)
                * so if the eventkey received at that point is not included is the
                * keys received from previous calendarViewFragment
                * there will be no binding, thus
                * the unrelated events will not be displayed
                * */
                /*
                final DatabaseReference EventRef = getRef(position);

                boolean binding = false;

                String eventKey = EventRef.getKey();

                for(String s : selEventListAndSelDate)
                {
                    if(s.matches(eventKey))
                    {
                        binding = true;
                    }
                }

                if(binding)
                {

                }*/

                holder.bindToCalendar(model);
            }

            @NonNull
            @Override
            public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar, parent, false);
                return new CalendarViewHolder(view);
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void getEvents()
    {

    }

}

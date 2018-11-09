package atk.studentavatar;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        setContentView(R.layout.calendar_card_view_holder);

        Log.d("lolo", "before test event");

        reference = FirebaseDatabase.getInstance().getReference();

        //setAdapter();


        Intent i = getIntent();
        selEventListAndSelDate = i.getStringArrayListExtra(EVENT_INTENT_KEY);

        for(String selEve : selEventListAndSelDate)
        {
            Log.d("lolo", selEve);
        }

        selDate = selEventListAndSelDate.get(selEventListAndSelDate.size() - 1);

        Log.d("lolo", selDate);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setAdapter()
    {
        Log.d("lolo", "2 set adapter");
        recyclerView = findViewById(R.id.calenListRec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = reference.child("event");

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Event>().setQuery(query, Event.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, CalendarViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CalendarViewHolder holder, int position, @NonNull Event model) {

            }

            @NonNull
            @Override
            public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
        /*
        Log.d("lolo", "before adapter");
        calendarHolderAdapter = new CalendarHolderAdapter(this, events);
        Log.d("lolo", "before rec set");
        recyclerView.setAdapter(calendarHolderAdapter);
        */

    }



}

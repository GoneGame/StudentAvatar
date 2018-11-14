package atk.studentavatar.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import atk.studentavatar.R;
import atk.studentavatar.models.Event;
import atk.studentavatar.viewholder.CalendarViewHolder;

public class CalendarCardViewFragment extends Fragment {

    private static final String EVENT_INTENT_KEY2 = "EVENT_ARRAY";

    private OnFragmentInteractionListener mListener;

    private DatabaseReference reference;

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Event, CalendarViewHolder> firebaseRecyclerAdapter;

    private String selDate;
    private String miliDate;

    private TextView date_on_view;

    public CalendarCardViewFragment() {}

    public static CalendarCardViewFragment newInstance(String date) {
        CalendarCardViewFragment fragment = new CalendarCardViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EVENT_INTENT_KEY2, date);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selDate = getArguments().getString(EVENT_INTENT_KEY2);
        }
        reference = FirebaseDatabase.getInstance().getReference();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");

        try {
            Date date = dateFormat.parse(selDate);
            Log.d("newDate", Long.toString(date.getTime()));
            miliDate = Long.toString(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_calendar, container, false);

        Log.d("lolo", "before test event");

        date_on_view = view.findViewById(R.id.TextView_date);
        date_on_view.setText(selDate);

        recyclerView = view.findViewById(R.id.calendarListRecycler);

        setAdapter();
        return view;
    }

    private void setAdapter()
    {
        Log.d("lolo", "2 set adapter");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query queryEvent = reference.child("event").orderByChild("date/" + miliDate).equalTo(true);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Event>().setQuery(queryEvent, Event.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, CalendarViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CalendarViewHolder holder, int position, @NonNull Event model) {

                final DatabaseReference EventRef = getRef(position);
                holder.bindToCalendar(model);

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(holder.txt_desc.getVisibility() == View.GONE)
                        {
                            holder.txt_desc.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            holder.txt_desc.setVisibility(View.GONE);
                        }
                    }
                });
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

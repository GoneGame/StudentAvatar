package atk.studentavatar.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import atk.studentavatar.CalendarFilter;
import atk.studentavatar.GuideActivity;
import atk.studentavatar.R;
import atk.studentavatar.models.Event;
import atk.studentavatar.viewholder.CalendarViewHolder;

public class CalendarCardViewFragment extends Fragment {

    private SharedPreferences preferences;
    private CalendarFilter calendarFilter;

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
        bundle.putString(CalendarViewFragment.EVENT_INTENT_KEY, date);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selDate = getArguments().getString(CalendarViewFragment.EVENT_INTENT_KEY);
        }
        reference = FirebaseDatabase.getInstance().getReference();
        Log.d("tag", selDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");

        try {
            Date date = dateFormat.parse(selDate);
            Log.d("newDate", Long.toString(date.getTime()));
            miliDate = Long.toString(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        preferences = this.getActivity().getSharedPreferences(CalendarFilterFragment.SHAREDFILTERKEY, Context.MODE_PRIVATE);

        if(preferences.contains(CalendarFilterFragment.FILTERSTAT))
        {
            Gson gson = new Gson();
            String json = preferences.getString(CalendarFilterFragment.FILTERSTAT, "");
            if(!json.isEmpty())
            {
                calendarFilter = gson.fromJson(json, CalendarFilter.class);
            }
            else
            {
                calendarFilter = new CalendarFilter();
            }
        }
        else
        {
            calendarFilter = new CalendarFilter();
        }

        if(calendarFilter.general)
            Log.d("fil", "general");

        if(calendarFilter.unit)
            Log.d("fil", "unit");

        if(calendarFilter.club)
            Log.d("fil", "club");

        for(String i : calendarFilter.idList)
        {
            Log.d("fil", i);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_calendar, container, false);

        Log.d("lolo", "before test general");

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

        Log.d("tag", miliDate);
        Query queryEvent = reference.child("event").orderByChild("date/" + miliDate).equalTo(true);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Event>().setQuery(queryEvent, Event.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, CalendarViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull CalendarViewHolder holder, int position, @NonNull Event model) {

                final DatabaseReference eventRef = getRef(position);
                holder.bindToCalendar(model);

                Log.d("try", model.title);

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(holder.txt_desc.getVisibility() == View.GONE)
                        {
                            holder.txt_desc.setVisibility(View.VISIBLE);

                            if(!model.guiderel.equals("none"))
                            {
                                holder.btn_guiderel.setVisibility(View.VISIBLE);
                            }

                        }
                        else
                        {
                            holder.txt_desc.setVisibility(View.GONE);
                            holder.btn_guiderel.setVisibility(View.GONE);
                        }
                    }
                });

                holder.btn_guiderel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("holder", model.guiderel);
                        Intent intent = new Intent(getActivity(), GuideActivity.class);
                        intent.putExtra(GuideActivity.EXTRA_GUIDE_KEY, model.guiderel);
                        startActivity(intent);
                    }
                });

                //get gene or unit or club
                //returns true if match
                //we hide if does not match
                if(!calendarFilter.eventFilter(model.relateTo.substring(0, 4)))
                {
                    holder.cardView.setVisibility(View.GONE);
                    holder.cardView.setLayoutParams(new CardView.LayoutParams(0, 0));
                }

                String unitClubKey;
                boolean b = false;

                if(model.relateTo.length() > 15)
                {
                    unitClubKey = model.relateTo.substring(5);

                    Log.d("more5", unitClubKey);

                    for(String t : calendarFilter.idList)
                    {
                        if(t.equals(unitClubKey))
                        {
                            b = true;
                            break;
                        }
                    }
                }

                if(b)
                {
                    CardView.LayoutParams params = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
                    params.setMargins(3, 3, 3, 3);
                    holder.cardView.setLayoutParams(params);
                    holder.cardView.setVisibility(View.VISIBLE);
                }

            }

            @NonNull
            @Override
            public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar, parent, false);
                return new CalendarViewHolder(view);
            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
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
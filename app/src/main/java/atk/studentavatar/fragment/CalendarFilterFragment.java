package atk.studentavatar.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import atk.studentavatar.CalendarFilter;
import atk.studentavatar.R;
import atk.studentavatar.models.Club;
import atk.studentavatar.models.Unit;


public class CalendarFilterFragment extends Fragment {

    public static final String SHAREDFILTERKEY = "filter";
    public static final String FILTERSTAT = "filter status";

    private Context con;

    private DatabaseReference reference;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private OnFragmentInteractionListener mListener;

    private String tempp;
    private Switch event, unit, club;

    private MultiAutoCompleteTextView multiUnit;
    private String selectUnit;

    private HashMap<String, String> filterMap;
    private ArrayList<String> filterName;


    private CalendarFilter calendarFilter;

    public CalendarFilterFragment() {}

    public static CalendarFilterFragment newInstance(String temp) {
        CalendarFilterFragment fragment = new CalendarFilterFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CalendarViewFragment.EVENT_INTENT_FILTER_KEY, temp);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            con = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tempp = getArguments().getString(CalendarViewFragment.EVENT_INTENT_FILTER_KEY);
        }

        Log.d("tag", tempp);

        preferences = this.getActivity().getSharedPreferences(SHAREDFILTERKEY, Context.MODE_PRIVATE);

        if(preferences.contains(FILTERSTAT))
        {
            Gson gson = new Gson();
            String json = preferences.getString(CalendarFilterFragment.FILTERSTAT, "");

            Log.d("start", json);

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

        reference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_calendar, container, false);

        event = view.findViewById(R.id.switchGeneral);
        unit = view.findViewById(R.id.switchUnit);
        club = view.findViewById(R.id.switchClub);

        multiUnit = view.findViewById(R.id.multiAutoCompleteTextView_unit);


        multiUnit.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiUnit.setThreshold(0);

        multiUnit.setText(calendarFilter.lel);

        event.setChecked(calendarFilter.general);
        unit.setChecked(calendarFilter.unit);
        club.setChecked(calendarFilter.club);

        OnOff();

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        filterMap = new HashMap<String, String>();
        filterName = new ArrayList<String>();

        genUnit();
        genClub();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(con, R.layout.item_array_adapt, filterName);

        multiUnit.setAdapter(adapter);

        multiUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiUnit.showDropDown();
            }
        });
        
    }

    private void OnOff()
    {
        event.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                calendarFilter.general = event.isChecked();
            }
        });

        unit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                calendarFilter.unit = unit.isChecked();
            }
        });

        club.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                calendarFilter.club = club.isChecked();
            }
        });
    }

    private void genUnit()
    {
        Query query = reference.child("unit");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Unit unit = snapshot.getValue(Unit.class);

                    if (unit != null) {
                        filterMap.put(snapshot.getKey(), unit.code + " " + unit.name);
                        filterName.add(unit.code + " " + unit.name);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("unit", "error loading unit");
            }
        });
    }

    private void genClub() {
        Query query = reference.child("club");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Club club = snapshot.getValue(Club.class);

                    if (club != null) {
                        filterMap.put(snapshot.getKey(), club.name);
                        filterName.add(club.name);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("club", "error loading club");
            }
        });
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


        selectUnit = multiUnit.getText().toString();

        Log.d("stop", selectUnit.trim());
        Log.d("stop", calendarFilter.lel.trim());

        if(!selectUnit.trim().equals(calendarFilter.lel.trim()))
        {
            calendarFilter.idList.clear();
            calendarFilter.lel = "";

            String temp[] = selectUnit.trim().split("\\s*,\\s*");

            for(String d : temp)
            {
                for(Map.Entry<String, String> entry : filterMap.entrySet())
                {
                    if(d.equals(entry.getValue()))
                    {
                        calendarFilter.idList.add(entry.getKey());
                        calendarFilter.lel = calendarFilter.lel + entry.getValue() + ", ";
                    }
                }
            }
        }

        for(String g : calendarFilter.idList)
        {

            Log.d("lop", g);
        }

        editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(calendarFilter);
        Log.d("stop", json);
        editor.putString(FILTERSTAT, json);
        editor.apply();

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
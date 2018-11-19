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
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.gson.Gson;

import atk.studentavatar.CalendarFilter;
import atk.studentavatar.R;


public class CalendarFilterFragment extends Fragment {

    public static final String SHAREDFILTERKEY = "filter";
    public static final String FILTERSTAT = "filter status";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private OnFragmentInteractionListener mListener;

    private String tempp;
    private Switch event, unit, club;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tempp = getArguments().getString(CalendarViewFragment.EVENT_INTENT_FILTER_KEY);
        }

        Log.d("tag", tempp);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_calendar, container, false);

        preferences = this.getActivity().getSharedPreferences(SHAREDFILTERKEY, Context.MODE_PRIVATE);

        event = view.findViewById(R.id.switchGeneral);
        unit = view.findViewById(R.id.switchUnit);
        club = view.findViewById(R.id.switchClub);

        if(preferences.contains(FILTERSTAT))
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

        event.setChecked(calendarFilter.event);
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
        Log.d("start", "see test");
    }

    private void OnOff()
    {
        event.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                calendarFilter.event = event.isChecked();
            }
        });

        unit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                calendarFilter.unit = event.isChecked();
            }
        });

        club.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                calendarFilter.club = event.isChecked();
            }
        });
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
        Log.d("stop", "see test");

        editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(calendarFilter);
        editor.putString(FILTERSTAT, json);
        editor.apply();

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
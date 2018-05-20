package atk.studentavatar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import atk.studentavatar.R;
import atk.studentavatar.models.Faq;

public class FaqViewFragment extends Fragment {

    private static final String TAG = "faqFragment";

    private TextView mQuestionView;
    private TextView mAnswerView;
    private DatabaseReference mGuideReference;
    private ValueEventListener mGuideListener;

    String mGuideKey = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.include_faq_text, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mGuideKey = bundle.getString("key");
        }

        mGuideReference = FirebaseDatabase.getInstance().getReference("tguides").child(mGuideKey);

        mQuestionView = view.findViewById(R.id.faq_question);
        mAnswerView = view.findViewById(R.id.faq_answer);

        return view;
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        // Initialize Views
//        mImageView = getActivity().findViewById(R.id.general_header);
//        mTitleView = getActivity().findViewById(R.id.general_title);
//        mLocationView = getActivity().findViewById(R.id.general_location);
//        mDescriptionView = getActivity().findViewById(R.id.general_description);
//    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the general view
        // [START general_value_event_listener]
        ValueEventListener guideListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get General object and use the values to update the UI
                Faq faq = dataSnapshot.child("faq").getValue(Faq.class);
                // [START_EXCLUDE]
                mQuestionView.setText(faq.question);
                mAnswerView.setText(faq.answer);
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
//                Toast.makeText(GuideActivity.this, "Failed to load details.",
//                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        if(mGuideReference != null) {
            mGuideReference.addValueEventListener(guideListener);
            // [END general_value_event_listener]
        }

        // Keep copy of guide listener so we can remove it when app stops
        mGuideListener = guideListener;
    }

    @Override
    public void onResume() {
        super.onResume();

//        BusStation.getBus().register(this);
//        getActivity().getSupportActionBar().setTitle(R.string.station_info_access_mobility_title);
    }

    @Override
    public void onPause() {
        super.onPause();

//        BusStation.getBus().unregister(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove general value event listener
        if (mGuideListener != null) {
            mGuideReference.removeEventListener(mGuideListener);
        }
    }
}

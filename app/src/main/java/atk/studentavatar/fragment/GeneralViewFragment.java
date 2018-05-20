package atk.studentavatar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import atk.studentavatar.BusStation;
import atk.studentavatar.Message;
import atk.studentavatar.R;
import atk.studentavatar.models.General;

public class GeneralViewFragment extends Fragment {

    private static final String TAG = "GVFragment";

    private ImageView mImageView;
    private TextView mTitleView;
    private TextView mLocationView;
    private TextView mDescriptionView;
    private DatabaseReference mGuideReference;
    private ValueEventListener mGuideListener;

    String mGuideKey = "";


//    public GeneralViewFragment() {}

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // Initialize Database
////        if(mGuideKey != null) {
////            Bundle mBundle = this.getArguments();
////            mGuideKey = mBundle.getString("mGuideKey");
////            mGuideKey = getArguments().getString("mGuideKey");
////            mGuideKey = String.valueOf((Message)getArguments().getParcelable("key"));
////            mGuideKey = String.valueOf((Message)getIntent().getExtras().getParcelable("key"));
////            mGuideReference = FirebaseDatabase.getInstance().getReference("testguides").child(mGuideKey);
////        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.include_general_text, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mGuideKey = bundle.getString("key");
        }

        mGuideReference = FirebaseDatabase.getInstance().getReference("tguides").child(mGuideKey);

        mImageView = view.findViewById(R.id.general_header);
        mTitleView = view.findViewById(R.id.general_title);
        mLocationView = view.findViewById(R.id.general_location);
        mDescriptionView = view.findViewById(R.id.general_description);

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
                General general = dataSnapshot.child("general").getValue(General.class);
                // [START_EXCLUDE]
                Picasso.get().load(general.header).into(mImageView);
                mTitleView.setText(general.title);
                mLocationView.setText(general.location);
                mDescriptionView.setText(general.description);
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

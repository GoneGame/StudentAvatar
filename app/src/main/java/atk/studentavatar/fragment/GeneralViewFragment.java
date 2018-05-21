package atk.studentavatar.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

import atk.studentavatar.R;
import atk.studentavatar.models.General;

public class GeneralViewFragment extends Fragment {

    private static final String TAG = "GVFragment";

    private OnFragmentInteractionListener mListener;

    private ImageView mImageView;
    private TextView mTitleView;
    private TextView mLocationView;
    private TextView mDescriptionView;
    private DatabaseReference mGuideReference;
    private ValueEventListener mGuideListener;

    private String mGuideKey;

    public GeneralViewFragment() {}

    public static GeneralViewFragment newInstance(String mGuideKey) {
        GeneralViewFragment fragment = new GeneralViewFragment();
        Bundle args = new Bundle();
        args.putString("key", mGuideKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGuideKey = getArguments().getString("key");
        }
        mGuideReference = FirebaseDatabase.getInstance().getReference("guides").child(mGuideKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.include_general_text, container, false);

        mImageView = view.findViewById(R.id.general_header);
        mTitleView = view.findViewById(R.id.general_title);
        mLocationView = view.findViewById(R.id.general_location);
        mDescriptionView = view.findViewById(R.id.general_description);

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
        // Add value event listener to the general view
        ValueEventListener guideListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get General object and use the values to update the UI
                General general = dataSnapshot.child("general").getValue(General.class);
                Picasso.get().load(general.header).into(mImageView);
                mTitleView.setText(general.title);
                mLocationView.setText(general.location);
                mDescriptionView.setText(general.description);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting General failed, log a message
                Log.w(TAG, "loadGeneral:onCancelled", databaseError.toException());
            }
        };
        if(mGuideReference != null) {
            mGuideReference.addValueEventListener(guideListener);
        }
        // Keep copy of guide listener so we can remove it when app stops
        mGuideListener = guideListener;
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

        // Remove general value event listener
        if (mGuideListener != null) {
            mGuideReference.removeEventListener(mGuideListener);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

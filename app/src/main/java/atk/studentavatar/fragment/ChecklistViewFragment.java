package atk.studentavatar.fragment;

import android.content.Context;
import android.net.Uri;
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
import atk.studentavatar.models.Checklist;

public class ChecklistViewFragment extends Fragment {

    private static final String TAG = "clFragment";

    private OnFragmentInteractionListener mListener;

    private TextView mTaskView;
    private TextView mDescriptionView;
    private TextView mBodyView;
    private TextView mLinkView;
    private DatabaseReference mGuideReference;
    private ValueEventListener mGuideListener;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private String mGuideKey;
    private String mCheckListKey;

    public ChecklistViewFragment() {}

    public static ChecklistViewFragment newInstance(String mGuideKey, String mChecklistKey) {
        ChecklistViewFragment fragment = new ChecklistViewFragment();
        Bundle args = new Bundle();
        args.putString("gkey", mGuideKey);
        args.putString("ckey", mChecklistKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGuideKey = getArguments().getString("gkey");
            mCheckListKey = getArguments().getString("ckey");
        }
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference("guides").child(mGuideKey).child("checklist");
        // [END initialize_database_ref]
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.include_checklist_text, container, false);

        mTaskView = view.findViewById(R.id.checklist_task);
        mDescriptionView = view.findViewById(R.id.checklist_description);
        mBodyView = view.findViewById(R.id.checklist_body);
        mLinkView = view.findViewById(R.id.checklist_link);

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
        // Add value event listener to the faq view
        mDatabase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get Faq object and use the values to update the UI
                        Checklist checklist = dataSnapshot.child(mCheckListKey).getValue(Checklist.class);

                        if (checklist == null) {
                            Log.e(TAG, "Checklist is null");
                        } else {
                            mTaskView.setText(checklist.task);
                            mDescriptionView.setText(checklist.description);
                            mBodyView.setText(checklist.body);
                            mLinkView.setText(checklist.link);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Faq failed, log a message
                        Log.w(TAG, "loadChecklist:onCancelled", databaseError.toException());
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GeneralViewFragment.OnFragmentInteractionListener) {
            mListener = (ChecklistViewFragment.OnFragmentInteractionListener) context;
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

        // Remove guide value event listener
        if (mGuideListener != null) {
            mGuideReference.removeEventListener(mGuideListener);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

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
import atk.studentavatar.models.Faq;

public class FaqViewFragment extends Fragment {

    private static final String TAG = "faqFragment";

    private OnFragmentInteractionListener mListener;

    private TextView mQuestionView;
    private TextView mAnswerView;
    private DatabaseReference mGuideReference;
    private ValueEventListener mGuideListener;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private String mGuideKey;
    private String mFaqKey;

    public FaqViewFragment() {}

    public static FaqViewFragment newInstance(String mGuideKey, String mFaqKey) {
        FaqViewFragment fragment = new FaqViewFragment();
        Bundle args = new Bundle();
        args.putString("gkey", mGuideKey);
        args.putString("fkey", mFaqKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGuideKey = getArguments().getString("gkey");
            mFaqKey = getArguments().getString("fkey");
        }
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference("guides").child(mGuideKey).child("faq");
        // [END initialize_database_ref]
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.include_faq_text, container, false);

        mQuestionView = view.findViewById(R.id.faq_question);
        mAnswerView = view.findViewById(R.id.faq_answer);

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
                        Faq faq = dataSnapshot.child(mFaqKey).getValue(Faq.class);

                        if (faq == null) {
                            Log.e(TAG, "Faq is null");
                        } else {
                            mQuestionView.setText(faq.question);
                            mAnswerView.setText(faq.answer);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Faq failed, log a message
                        Log.w(TAG, "loadFaq:onCancelled", databaseError.toException());
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GeneralViewFragment.OnFragmentInteractionListener) {
            mListener = (FaqViewFragment.OnFragmentInteractionListener) context;
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

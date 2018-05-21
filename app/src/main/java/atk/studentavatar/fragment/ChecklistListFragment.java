package atk.studentavatar.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import atk.studentavatar.GuideActivity;
import atk.studentavatar.R;
import atk.studentavatar.models.Checklist;
import atk.studentavatar.viewholder.ChecklistViewHolder;

public abstract class ChecklistListFragment extends Fragment {

    private static final String TAG = "clListFragment";

    private OnFragmentInteractionListener mListener;

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Checklist, ChecklistViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    private String mGuideKey;

    public ChecklistListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGuideKey = getArguments().getString("key");
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_checklists, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = rootView.findViewById(R.id.checklists_list);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query ChecklistsQuery = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Checklist>()
                .setQuery(ChecklistsQuery, Checklist.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<Checklist, ChecklistViewHolder>(options) {

            @Override
            public ChecklistViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ChecklistViewHolder(inflater.inflate(R.layout.item_checklist, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(ChecklistViewHolder viewHolder, int position, final Checklist model) {
                final DatabaseReference checklistRef = getRef(position);

                // Set click listener for the whole faq view
                final String checklistKey = checklistRef.getKey();
                viewHolder.itemView.setOnClickListener(v -> {
                    // Launch GuideActivity
                    Intent intent = new Intent(getActivity(), GuideActivity.class);
                    intent.putExtra(GuideActivity.EXTRA_CLL_KEY, mGuideKey);
                    intent.putExtra(GuideActivity.EXTRA_CL_KEY, checklistKey);
                    startActivity(intent);
                });

                // Bind Faq to ViewHolder
                viewHolder.bindToChecklist(model);
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GeneralViewFragment.OnFragmentInteractionListener) {
            mListener = (ChecklistListFragment.OnFragmentInteractionListener) context;
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
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

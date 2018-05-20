package atk.studentavatar.fragment;

import android.content.Intent;
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
import atk.studentavatar.models.Faq;
import atk.studentavatar.viewholder.FaqViewHolder;

public abstract class FaqListFragment extends Fragment {

    private static final String TAG = "FaqListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Faq, FaqViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    protected String mGuideKey;

    public FaqListFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_faqs, container, false);
        mGuideKey = getActivity().getIntent().getExtras().getString("mGuideKey");

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = rootView.findViewById(R.id.faqs_list);
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
        Query FaqsQuery = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Faq>()
                .setQuery(FaqsQuery, Faq.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<Faq, FaqViewHolder>(options) {

            @Override
            public FaqViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new FaqViewHolder(inflater.inflate(R.layout.item_faq, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(FaqViewHolder viewHolder, int position, final Faq model) {
                final DatabaseReference faqRef = getRef(position);

                // Set click listener for the whole faq view
                final String faqKey = faqRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch GuideActivity
                        Intent intent = new Intent(getActivity(), GuideActivity.class);
                        intent.putExtra(GuideActivity.EXTRA_FAQ_KEY, faqKey);
                        startActivity(intent);
                    }
                });

                // Bind Faq to ViewHolder
                viewHolder.bindToFaq(model);
            }
        };
        mRecycler.setAdapter(mAdapter);
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

}

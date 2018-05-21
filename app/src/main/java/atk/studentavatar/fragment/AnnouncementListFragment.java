package atk.studentavatar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

import atk.studentavatar.AnnouncementDetailActivity;
import atk.studentavatar.R;
import atk.studentavatar.models.Announcement;
import atk.studentavatar.viewholder.AnnouncementViewHolder;

public abstract class AnnouncementListFragment extends Fragment {

    private static final String TAG = "AnnounceListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Announcement, AnnouncementViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public AnnouncementListFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_announcements, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = rootView.findViewById(R.id.messages_list);
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
        Query announcementsQuery = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Announcement>()
                .setQuery(announcementsQuery, Announcement.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<Announcement, AnnouncementViewHolder>(options) {

            @Override
            public AnnouncementViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new AnnouncementViewHolder(inflater.inflate(R.layout.item_announcement, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(AnnouncementViewHolder viewHolder, int position, final Announcement model) {
                final DatabaseReference announcementRef = getRef(position);

                // Set click listener for the whole announcement view
                final String announcementKey = announcementRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch AnnouncementDetailActivity
                        Intent intent = new Intent(getActivity(), AnnouncementDetailActivity.class);
                        intent.putExtra(AnnouncementDetailActivity.EXTRA_ANNOUNCEMENT_KEY, announcementKey);
                        startActivity(intent);
                    }
                });

                // Determine if the current user has liked this announcement and set UI accordingly
                if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }

                // Bind Announcement to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToAnnouncement(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the announcement is stored
                        DatabaseReference globalAnnouncementRef = mDatabase.child("announcements").child(announcementRef.getKey());
                        DatabaseReference userAnnouncementRef = mDatabase.child("user-announcements").child(model.uid).child(announcementRef.getKey());

                        // Run two transactions
                        onStarClicked(globalAnnouncementRef);
                        onStarClicked(userAnnouncementRef);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    // [START announcement_stars_transaction]
    private void onStarClicked(DatabaseReference announcementRef) {
        announcementRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Announcement p = mutableData.getValue(Announcement.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(getUid())) {
                    // Unstar the announcement and remove self from stars
                    p.starCount = p.starCount - 1;
                    p.stars.remove(getUid());
                } else {
                    // Star the announcement and add self to stars
                    p.starCount = p.starCount + 1;
                    p.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "announcementTransaction:onComplete:" + databaseError);
            }
        });
    }
    // [END announcement_stars_transaction]


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


    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}

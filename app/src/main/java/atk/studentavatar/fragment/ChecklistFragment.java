package atk.studentavatar.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class ChecklistFragment extends ChecklistListFragment {

    private String mGuideKey;

    public ChecklistFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        if (getArguments() != null) {
            mGuideKey = getArguments().getString("key");
        }
        // [START faqs_query]
        Query ChecklistsQuery = databaseReference.child("guides").child(mGuideKey).child("checklist");
        // [END faqs_query]

        return ChecklistsQuery;
    }
}

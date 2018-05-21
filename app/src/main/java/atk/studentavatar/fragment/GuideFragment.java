package atk.studentavatar.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class GuideFragment extends GuideListFragment {

    public GuideFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentGuidesQuery = databaseReference.child("guides")
                .limitToFirst(100);

        return recentGuidesQuery;
    }
}

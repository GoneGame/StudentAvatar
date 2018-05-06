package atk.studentavatar.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class GeneralFragment extends GuideListFragment {

    public GeneralFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentGuidesQuery = databaseReference.child("guides").child("general")
                .limitToFirst(100);
        // [END recent_guides_query]

        return recentGuidesQuery;
    }
}

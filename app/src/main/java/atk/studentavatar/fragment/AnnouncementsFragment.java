package atk.studentavatar.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class AnnouncementsFragment extends AnnouncementListFragment {

    public AnnouncementsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_announcements_query]
        // Last 100 announcements, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentAnnouncementsQuery = databaseReference.child("announcements")
                .limitToFirst(100);
        // [END recent_announcements_query]

        return recentAnnouncementsQuery;
    }
}

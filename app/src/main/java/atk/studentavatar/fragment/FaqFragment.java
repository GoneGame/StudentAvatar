package atk.studentavatar.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class FaqFragment extends FaqListFragment {

    private String mGuideKey;

    public FaqFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        if (getArguments() != null) {
            mGuideKey = getArguments().getString("key");
        }
        // [START faqs_query]
        Query FaqsQuery = databaseReference.child("guides").child(mGuideKey).child("faq");
        // [END faqs_query]

        return FaqsQuery;
    }
}

package atk.studentavatar.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class FaqFragment extends FaqListFragment {

    public FaqFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
//        mGuideKey = getActivity().getIntent().getExtras().getString("mGuideKey");
//        mGuideKey = getArguments().getString("mGuideKey"); //String text
        // [START faqs_query]
        Query FaqsQuery = databaseReference.child("testguides").child("mGuideKey");
        // [END faqs_query]

        return FaqsQuery;
    }
}

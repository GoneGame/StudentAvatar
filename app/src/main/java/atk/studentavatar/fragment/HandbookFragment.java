package atk.studentavatar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import atk.studentavatar.R;
import atk.studentavatar.models.User;

public class HandbookFragment extends Fragment{
    private LinearLayoutManager mManager;
    private WebView webView;
    private DatabaseReference databaseReference;
    private Query query;

    public HandbookFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_handbook_view, container, false);

        webView = rootView.findViewById(R.id.webView);

        String url = "file:///android_asset/Handbook/index.html";
        WebView view = (WebView)rootView.findViewById(R.id.webView);
        //view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        return rootView;
    }

    //pass values and open new fragment to view card view of events

    private Bundle keepData(int year, int month, int day)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("Year", year);
        bundle.putInt("Month", month + 1);
        bundle.putInt("Day", day);

        //get current user parse to string, put in bundle



        return bundle;
    }

    private void goToListFragment(Bundle bundle)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CalendarListFragment calendarListFragment = new CalendarListFragment();
        calendarListFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.calenLinear, calendarListFragment);
        fragmentTransaction.commit();
    }

    private void getUserName()
    {
        query = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String s = user.username;

                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}

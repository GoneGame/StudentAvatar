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

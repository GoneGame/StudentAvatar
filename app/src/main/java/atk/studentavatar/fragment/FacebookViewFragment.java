package atk.studentavatar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import atk.studentavatar.R;

public class FacebookViewFragment extends Fragment {

    private static final String TAG = "TVFragment";

    public FacebookViewFragment() {}

    public static FacebookViewFragment newInstance(String mGuideKey) {
        FacebookViewFragment fragment = new FacebookViewFragment();
        Bundle args = new Bundle();
        args.putString("key", mGuideKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_facebook_view, container, false);

        WebView wv = (WebView)rootView.findViewById(R.id.FacebookWebView);

        wv.getSettings().setJavaScriptEnabled(true);
        String url = "https://www.facebook.com/swinburnesarawak/";
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        return rootView;
    }
}

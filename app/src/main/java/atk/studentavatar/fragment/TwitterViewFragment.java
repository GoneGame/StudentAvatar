package atk.studentavatar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import atk.studentavatar.R;

public class TwitterViewFragment extends Fragment {

    private static final String TAG = "TVFragment";

    public TwitterViewFragment() {}

    public static TwitterViewFragment newInstance(String mGuideKey) {
        TwitterViewFragment fragment = new TwitterViewFragment();
        Bundle args = new Bundle();
        args.putString("key", mGuideKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_twitter_view, container, false);

        WebView wv = (WebView)rootView.findViewById(R.id.TwitterWebView);

        wv.getSettings().setJavaScriptEnabled(true);
        String url = "https://twitter.com/Swinburne_Swk";
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

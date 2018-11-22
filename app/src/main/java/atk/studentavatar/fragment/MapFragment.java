package atk.studentavatar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import atk.studentavatar.R;
public class MapFragment extends Fragment{

    private WebView view;

    public MapFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_map_view, container, false);

        String url = "https://my-awesome-project-6dd85.firebaseapp.com/CampusMap2/";
        view = rootView.findViewById(R.id.webView);
        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setDisplayZoomControls(false);
        view.loadUrl(url);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        view.clearCache(true);
    }
}

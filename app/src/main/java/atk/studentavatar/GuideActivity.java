package atk.studentavatar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.design.widget.NavigationView;

import com.google.firebase.auth.FirebaseAuth;

import atk.studentavatar.fragment.FaqFragment;
import atk.studentavatar.fragment.FaqListFragment;
import atk.studentavatar.fragment.FaqViewFragment;
import atk.studentavatar.fragment.GeneralViewFragment;

public class GuideActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        GeneralViewFragment.OnFragmentInteractionListener,
        FaqListFragment.OnFragmentInteractionListener,
        FaqViewFragment.OnFragmentInteractionListener {

    private static final String TAG = "GuideActivity";

    public static final String EXTRA_GUIDE_KEY = "guide_key";
    public static final String EXTRA_FAQL_KEY = "faql_key";
    public static final String EXTRA_FAQ_KEY = "faq_key";

    private String mGuideKey;
    private String mFaqKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This activity was started with special instructions from an
        // Intent, so we pass the Intent's extras to the fragment as arguments
        if (savedInstanceState == null) {
            Fragment fragment = null;
            if (getIntent().getStringExtra(EXTRA_GUIDE_KEY) != null) {
                mGuideKey = getIntent().getStringExtra(EXTRA_GUIDE_KEY);
                fragment = new GeneralViewFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString("key", mGuideKey);
                fragment.setArguments(mBundle);
            } else if (getIntent().getStringExtra(EXTRA_FAQ_KEY) != null) {
                mGuideKey = getIntent().getStringExtra(EXTRA_FAQL_KEY);
                mFaqKey = getIntent().getStringExtra(EXTRA_FAQ_KEY);
                fragment = new FaqViewFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString("gkey", mGuideKey);
                mBundle.putString("fkey", mFaqKey);
                fragment.setArguments(mBundle);
            }
            // Add the fragment to the 'content_frame' FrameLayout
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            } else {
                Log.e("GuideActivity", "Error creating fragment");
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_first_fragment) {
            fragment = new GeneralViewFragment();
            Bundle mBundle = new Bundle();
            mBundle.putString("key", mGuideKey);
            fragment.setArguments(mBundle);
        } else if (id == R.id.nav_second_fragment) {
            fragment = new FaqFragment();
            Bundle mBundle = new Bundle();
            mBundle.putString("key", mGuideKey);
            fragment.setArguments(mBundle);
        } else if (id == R.id.nav_third_fragment) {
//            fragment = new Fragment();
        }

        if(fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        else {
            Log.e("GuideActivity", "Error creating fragment");
        }
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

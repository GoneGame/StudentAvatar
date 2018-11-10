package atk.studentavatar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import atk.studentavatar.fragment.CalendarCardViewFragment;
import atk.studentavatar.fragment.ChecklistListFragment;
import atk.studentavatar.fragment.ChecklistViewFragment;
import atk.studentavatar.fragment.FaqListFragment;
import atk.studentavatar.fragment.FaqViewFragment;
import atk.studentavatar.fragment.GeneralViewFragment;

public class CalendarActivity extends BaseActivity implements
        CalendarCardViewFragment.OnFragmentInteractionListener {


    private static final String EVENT_INTENT_KEY = "EVENT_LIST";
    private static final String EVENT_INTENT_KEY2 = "EVENT_ARRAY";

    private ArrayList<String> selEventListAndSelDate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This activity was started with special instructions from an
        // Intent, so we pass the Intent's extras to the fragment as arguments

        Intent i = getIntent();

        if(savedInstanceState == null)
        {
            Fragment fragment = null;

            if(i.getStringArrayListExtra(EVENT_INTENT_KEY) != null)
            {
                selEventListAndSelDate = i.getStringArrayListExtra(EVENT_INTENT_KEY);
                String s[] = new String[selEventListAndSelDate.size()];
                for(int j = 0; j < selEventListAndSelDate.size(); j++)
                {
                    s[j] = selEventListAndSelDate.get(j);
                }

                fragment = new CalendarCardViewFragment();
                Bundle bundle = new Bundle();
                bundle.putStringArray(EVENT_INTENT_KEY2, s);
                fragment.setArguments(bundle);
            }
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame2, fragment).commit();
            } else {
                Log.e("GuideActivity", "Error creating fragment");
            }
        }
        /*
        if (savedInstanceState == null) {
            Fragment fragment = null;
            if (getIntent().getStringExtra(EXTRA_GUIDE_KEY) != null) {
                mGuideKey = getIntent().getStringExtra(EXTRA_GUIDE_KEY);
                fragment = new GeneralViewFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString("key", mGuideKey);
                fragment.setArguments(mBundle);
            }
            else if (getIntent().getStringExtra(EXTRA_FAQ_KEY) != null) {
                mGuideKey = getIntent().getStringExtra(EXTRA_FAQL_KEY);
                mFaqKey = getIntent().getStringExtra(EXTRA_FAQ_KEY);
                fragment = new FaqViewFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString("gkey", mGuideKey);
                mBundle.putString("fkey", mFaqKey);
                fragment.setArguments(mBundle);
            }
            else if (getIntent().getStringExtra(EXTRA_CL_KEY) != null) {
            mGuideKey = getIntent().getStringExtra(EXTRA_CLL_KEY);
            mChecklistKey = getIntent().getStringExtra(EXTRA_CL_KEY);
            fragment = new ChecklistViewFragment();
            Bundle mBundle = new Bundle();
            mBundle.putString("gkey", mGuideKey);
            mBundle.putString("ckey", mChecklistKey);
            fragment.setArguments(mBundle);
        }
            // Add the fragment to the 'content_frame' FrameLayout

        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

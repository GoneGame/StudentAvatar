package atk.studentavatar;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import atk.studentavatar.fragment.GeneralFragment;
import atk.studentavatar.fragment.GuideListFragment;
import atk.studentavatar.models.General;


public class GeneralDetailActivity extends BaseActivity {

    private static final String TAG = "GeneralDetailActivity";

    public static final String EXTRA_GENERAL_KEY = "general_key";

    private DatabaseReference mGeneralReference;
    private ValueEventListener mGeneralListener;
    private String mGeneralKey;

    private TextView mTitleView;
    private TextView mLocationView;
    private TextView mDescriptionView;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        // Get general key from intent
        mGeneralKey = getIntent().getStringExtra(EXTRA_GENERAL_KEY);
        if (mGeneralKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_GENERAL_KEY");
        }

        // Initialize Database
        mGeneralReference = FirebaseDatabase.getInstance().getReference()
                .child("guides").child("general").child(mGeneralKey);

        // Initialize Views
        mTitleView = findViewById(R.id.general_title);
        mLocationView = findViewById(R.id.general_location);
        mDescriptionView = findViewById(R.id.general_description);

//        // Executes Toolbar code from BaseActivity.java
//        activateToolbar();

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.nav_actionbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        nvDrawer = findViewById(R.id.nav_view);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

//        // Lookup navigation view
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_draw);
//// Inflate the header view at runtime
//        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
//// We can now look up items within the header if needed
//        ImageView ivHeaderPhoto = headerLayout.findViewById(R.id.imageView);


        // Getting reference to header
        // There is usually only 1 header view.
// Multiple header views can technically be added at runtime.
// We can use navigationView.getHeaderCount() to determine the total number.
//        View headerLayout = navigationView.getHeaderView(0);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = GeneralFragment.class;
                Intent intent2 = new Intent(getApplicationContext(), GeneralDetailActivity.class);
                intent2.putExtra(GeneralDetailActivity.EXTRA_GENERAL_KEY, mGeneralKey);
                startActivity(intent2);
                break;
            case R.id.nav_second_fragment:
                fragmentClass = GeneralFragment.class;  // placeholder
                break;
            case R.id.nav_third_fragment:
                fragmentClass = GeneralFragment.class;  // placeholder
                break;
            default:
                fragmentClass = GeneralFragment.class;
                Intent intent = new Intent(getApplicationContext(), GeneralDetailActivity.class);
                intent.putExtra(GeneralDetailActivity.EXTRA_GENERAL_KEY, mGeneralKey);
                startActivity(intent);
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the general view
        // [START general_value_event_listener]
        ValueEventListener generalListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                General general = dataSnapshot.getValue(General.class);
                // [START_EXCLUDE]
                mTitleView.setText(general.title);
                mLocationView.setText(general.location);
                mDescriptionView.setText(general.description);
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(GeneralDetailActivity.this, "Failed to load details.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mGeneralReference.addValueEventListener(generalListener);
        // [END general_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mGeneralListener = generalListener;
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove general value event listener
        if (mGeneralListener != null) {
            mGeneralReference.removeEventListener(mGeneralListener);
        }

    }
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        int id = item.getItemId();

//        if (id == R.id.nav_welcome) {
                    // Set the fragment initially
//            Intent intent = new Intent(getApplicationContext(), GeneralDetailActivity.class);
//            intent.putExtra(GeneralDetailActivity.EXTRA_GENERAL_KEY, mGeneralKey);
//            startActivity(intent);

//        } else if (id == R.id.nav_checklist) {
//            Intent intent = new Intent(getApplicationContext(), GeneralDetailActivity.class);
//            intent.putExtra(GeneralDetailActivity.EXTRA_GENERAL_KEY, mGeneralKey);
//            startActivity(intent);
//
//        } else if (id == R.id.nav_faq) {
//
//        }

//                switch (item.getItemId()) {
//                    case (R.id.nav_welcome):
//                        Intent intent = new Intent(getApplicationContext(), GeneralDetailActivity.class);
//                        startActivity(intent);
//                        break;
//                    case (R.id.nav_checklist):
//                        Intent intent2 = new Intent(getApplicationContext(), GeneralDetailActivity.class);
//                        startActivity(intent2);
//                }

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}

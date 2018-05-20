package atk.studentavatar;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import atk.studentavatar.fragment.FaqFragment;
import atk.studentavatar.fragment.GeneralViewFragment;

public class GuideActivity extends BaseActivity {

    private static final String TAG = "GuideActivity";

    public static final String EXTRA_GUIDE_KEY = "guide_key";
    public static final String EXTRA_FAQ_KEY = "faq_key";

    private String mGuideKey;
    private String mFaqKey;

    private FragmentManager fragmentManager;
    private ImageView mImageView;
    private TextView mTitleView;
    private TextView mLocationView;
    private TextView mDescriptionView;

    private TextView mQuestionView;
    private TextView mAnswerView;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail);

        // Get guide key from GuideListFragment intent
        mGuideKey = getIntent().getStringExtra(EXTRA_GUIDE_KEY);
        if (mGuideKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_GUIDE_KEY");
        }

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.flContent) != null) {


            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            GeneralViewFragment firstFragment = new GeneralViewFragment();

            // This activity was started with special instructions from an
            // Intent, so we pass the Intent's extras to the fragment as arguments
            Bundle mBundle = new Bundle();
            mBundle.putString("key", mGuideKey);
            firstFragment.setArguments(mBundle);

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flContent, firstFragment, "firstFragment_tag").commit();

        }

        // Initialize Views
        mImageView = findViewById(R.id.general_header);
        mTitleView = findViewById(R.id.general_title);
        mLocationView = findViewById(R.id.general_location);
        mDescriptionView = findViewById(R.id.general_description);

        mQuestionView = findViewById(R.id.faq_question);
        mAnswerView = findViewById(R.id.faq_answer);


//        if(savedInstanceState == null) {
//            GeneralViewFragment obj = new GeneralViewFragment();
//            Bundle mBundle = new Bundle();
//            mBundle.putString("key", mGuideKey);
//            obj.setArguments(mBundle);
//            fragmentManager.beginTransaction()
//                    .add(R.id.flContent, obj)
//                    .commit();
//        }

//        if (getIntent().getStringExtra(EXTRA_FAQ_KEY) != null) {
//            // Get faq key from intent
//            mFaqKey = getIntent().getStringExtra(EXTRA_FAQ_KEY);
//            // Initialize Database faq
//            mFaqReference = FirebaseDatabase.getInstance().getReference("testguides").child("faq").child(mFaqKey);
//            if (mFaqKey == null) {
//                throw new IllegalArgumentException("Must pass EXTRA_FAQ_KEY");
//            }
//        }

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

//        nvDrawer.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        selectDrawerItem(menuItem);
//                        // Highlight the selected item has been done by NavigationView
//                        menuItem.setChecked(true);
//                        // Set action bar title
//                        setTitle(menuItem.getTitle());
//                        // Close the navigation drawer
//                        mDrawer.closeDrawers();
//
//                        // Add code here to update the UI based on the item selected
//                        // For example, swap UI fragments here
//
//                        return true;
//                    }
//                });
    }

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
//    }

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
                    fragmentClass = GeneralViewFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = FaqFragment.class;
                break;
//            case R.id.nav_third_fragment:
//                fragmentClass = .class;
//                break;
            default:
                fragmentClass = GeneralViewFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.flContent, fragment).addToBackStack(null).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

//    public void selectDrawerItem(MenuItem menuItem, int position) {
//        FaqFragment secondFragment = new FaqFragment();
//
//        Fragment[] fragments = new Fragment[]{firstFragment,secondFragment};
////        String[] fragmentTAGS = new String[]{"firstFragment_tag","secondFragment_tag"};
////
////        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
////        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
////
////        if (getSupportFragmentManager().findFragmentByTag(fragmentTAGS[position]) == null) {
////            fragmentTransaction.add(R.id.container, fragments[position], fragmentTAGS[position]);
////        }
////        for (int i = 0; i < fragments.length; i++) {
////            if (i == position) {
////                fragmentTransaction.show(fragments[i]);
////            } else {
////                if (getSupportFragmentManager().findFragmentByTag(fragmentTAGS[position]) != null) {
////                    fragmentTransaction.hide(fragments[i]);
////                }
////            }
////        }
////        fragmentTransaction.commit();
//
//
//        // Highlight the selected item has been done by NavigationView
//        menuItem.setChecked(true);
//        // Set action bar title
//        setTitle(menuItem.getTitle());
//        // Close the navigation drawer
//        mDrawer.closeDrawers();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // Add value event listener to the general view
//        // [START general_value_event_listener]
//        ValueEventListener guideListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                    // Get General object and use the values to update the UI
//                    General general = dataSnapshot.child("general").getValue(General.class);
//                    // [START_EXCLUDE]
//                    Picasso.get().load(general.header).into(mImageView);
//                    mTitleView.setText(general.title);
//                    mLocationView.setText(general.location);
//                    mDescriptionView.setText(general.description);
//                    // [END_EXCLUDE]
//
//                if (mFaqReference != null) {
//
//                    // Get Faq object and use the values to update the UI
//                    Faq faq = dataSnapshot.child("faq").getValue(Faq.class);
//                    // [START_EXCLUDE]
//                    mQuestionView.setText(faq.question);
//                    mAnswerView.setText(faq.answer);
//                    //[END_EXCLUDE]
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                // [START_EXCLUDE]
//                Toast.makeText(GuideActivity.this, "Failed to load details.",
//                        Toast.LENGTH_SHORT).show();
//                // [END_EXCLUDE]
//            }
//        };
//        if(mGuideReference != null) {
//            mGuideReference.addValueEventListener(guideListener);
//            // [END general_value_event_listener]
//        }
//        if (mFaqReference != null) {
//            mFaqReference.addValueEventListener(guideListener);
//            // [END faq_value_event_listener]
//        }
//
//        // Keep copy of guide listener so we can remove it when app stops
//        mGuideListener = guideListener;
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // Remove general value event listener
//        if (mGuideListener != null) {
//            mGuideReference.removeEventListener(mGuideListener);
//            mFaqReference.removeEventListener(mGuideListener);
//        }
//    }
}

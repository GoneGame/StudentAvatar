package atk.studentavatar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import atk.studentavatar.fragment.GeneralFragment;

public class  GuideActivity extends BaseActivity {

    private static final String TAG = "GuideActivity";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

//        // Executes Toolbar code from BaseActivity.java
//        activateToolbar();

        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new GeneralFragment(),
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.heading_discover)
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        // Button launches NewPostActivity
//        findViewById(R.id.fab_new_post).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, NewPostActivity.class));
//            }
//        });

        //        You need to edit your activity's xml file.
//                - go to activity.xml and search for NavigationView tag (check how it looks like in my code shown below)
//        - inside the tag add this line: (android:id="@+id/nv1") and it should work afterwards (check how my code looks like below)
//
//                <android.support.design.widget.NavigationView
//        android:id="@+id/nv1"
//        android:layout_width="wrap_content"
//        android:layout_height="match_parent"
//        app:menu="@menu/navigation_menu"
//        android:layout_gravity="start">
//
//</android.support.design.widget.NavigationView>
//
//                Of course make sure you don't forget to edit the java code in the above reply to match the id
// of the menu item "case(R.id.ITEM_ID)" as well as the activity name you're trying to call "ACTIVITY_NAME.class"
// (check my code below) and don't forget to add "break;" after every case (if you have more than one case) (check my code below)
//
//        switch (menuItem.getItemId()) {
//            case(R.id.ITEM_ID):
//                in = new Intent(getApplicationContext(),ACTIVITY_NAME.class);
//                startActivity(in);
//                break;
//            case(R.id.ITEM2_ID):
//                in = new Intent(getApplicationContext(),ACTIVITY2_NAME.class);
//                startActivity(in);
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
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}

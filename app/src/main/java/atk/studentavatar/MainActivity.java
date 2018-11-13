package atk.studentavatar;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import atk.studentavatar.fragment.CalendarFragment;
import atk.studentavatar.fragment.GuideFragment;
import atk.studentavatar.fragment.MapFragment;
import atk.studentavatar.fragment.OtherServicesFragment;
import atk.studentavatar.fragment.RecentPostsFragment;

public class  MainActivity extends BaseActivity {
    // "New" android studio
    private static final String TAG = "MainActivity";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    // Configure icons
    private int[] imageResId = {
            R.drawable.book_open_page_variant,
            R.drawable.forum,
            R.drawable.calendar_text,
            R.drawable.map_search_outline,
            R.drawable.apps
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Execute Toolbar from BaseActivity
        activateMainToolbar();

        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[]{
                    new GuideFragment(),
                    new RecentPostsFragment(),
                    new CalendarFragment(),
                    new MapFragment(),
                    new OtherServicesFragment()
            };

            /*private final String[] mFragmentNames = new String[] {
                    getString(R.string.heading_discover),
                    getString(R.string.heading_recent),
                    getString(R.string.heading_calendar),
                    getString(R.string.heading_map),
                    getString(R.string.otherServicesText)

            };*/
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
                return null;
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        // Set up icons for Tablayout
        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }
        // Set tint for active fragment
        ColorStateList colors;
        if (Build.VERSION.SDK_INT >= 23) {
            colors = getResources().getColorStateList(R.color.tab_selector, getTheme());
        } else {
            colors = getResources().getColorStateList(R.color.tab_selector);
        }

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            Drawable icon = tab.getIcon();

            if (icon != null) {
                icon = DrawableCompat.wrap(icon);
                DrawableCompat.setTintList(icon, colors);
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            FloatingActionButton fab = findViewById(R.id.fab_new_post);

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        fab.hide();
                        break;
                    case 1:
                        fab.show();
                        break;
                    case 2:
                        fab.hide();
                        break;
                    case 3:
                        fab.hide();
                        break;
                    case 4:
                        fab.hide();
                        break;
                    default:
                        fab.hide();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Floating button launches NewPostActivity
        findViewById(R.id.fab_new_post).setOnClickListener(
                v -> startActivity(new Intent(MainActivity.this, NewPostActivity.class)));
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





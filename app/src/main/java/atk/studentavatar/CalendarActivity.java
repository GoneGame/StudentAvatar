package atk.studentavatar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;


import atk.studentavatar.fragment.CalendarCardViewFragment;
import atk.studentavatar.fragment.CalendarViewFragment;


public class CalendarActivity extends BaseActivity implements CalendarCardViewFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This activity was started with special instructions from an
        // Intent, so we pass the Intent's extras to the fragment as arguments

        String date;

        Intent i = getIntent();

        if(savedInstanceState == null)
        {
            Fragment fragment = null;

            if(i.getStringExtra(CalendarViewFragment.EVENT_INTENT_KEY) != null)
            {
                date = i.getStringExtra(CalendarViewFragment.EVENT_INTENT_KEY);
                fragment = new CalendarCardViewFragment();
                Bundle bundle = new Bundle();
                bundle.putString(CalendarViewFragment.EVENT_INTENT_KEY, date);
                fragment.setArguments(bundle);
            }
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame2, fragment).commit();
            } else {
                Log.e("GuideActivity", "Error creating fragment");
            }
        }
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

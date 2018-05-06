package atk.studentavatar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.google.firebase.auth.FirebaseAuth;

public class BaseActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

//    protected Toolbar activateToolbar() {
//        if(mToolbar == null) {
//            mToolbar = findViewById(R.id.nav_actionbar);
//            setSupportActionBar(mToolbar);
//
//            mDrawerLayout = findViewById(R.id.drawer_layout);
//            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_nav, R.string.close_nav);
//
//            mDrawerLayout.addDrawerListener(mToggle);
//            mToggle.syncState();
//
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        }
//        return mToolbar;
//    }
//
//    protected Toolbar activateMainToolbar() {
//        if(mToolbar == null) {
//            mToolbar = findViewById(R.id.nav_actionbar);
//            setSupportActionBar(mToolbar);
//        }
//        return mToolbar;
//    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



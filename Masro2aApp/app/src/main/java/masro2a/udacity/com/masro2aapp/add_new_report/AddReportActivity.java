package masro2a.udacity.com.masro2aapp.add_new_report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import masro2a.udacity.com.masro2aapp.R;
import masro2a.udacity.com.masro2aapp.common.base.BaseActivity;

/**
 * Created by peter on 23/04/18.
 */

public class AddReportActivity extends BaseActivity implements AddPostFragment.AddReportInteraction {

    private AddPostFragment addPostFragment;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddReportActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        initializeViews();
        if (savedInstanceState != null)
            addPostFragment = (AddPostFragment) getSupportFragmentManager().getFragment(savedInstanceState, AddPostFragment.class.getName());
        else
            addPostFragment = AddPostFragment.newInstance();
        loadFragment();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        Toolbar addReportToolbar = findViewById(R.id.toolbar);
        setToolbar(addReportToolbar, getString(R.string.add_new_report), true, true);
    }

    @Override
    protected void loadFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, addPostFragment).commit();
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void onPostAddSuccess() {
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (addPostFragment != null)
            getSupportFragmentManager().putFragment(outState, AddPostFragment.class.getName(), addPostFragment);
    }
}

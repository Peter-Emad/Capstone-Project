package masro2a.udacity.com.masro2aapp.posts_listing;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import masro2a.udacity.com.masro2aapp.R;
import masro2a.udacity.com.masro2aapp.add_new_report.AddReportActivity;
import masro2a.udacity.com.masro2aapp.common.base.BaseActivity;

public class ListingActivity extends BaseActivity {

    private FloatingActionButton floatingActionButton;
    private ListingFragment listingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        initializeViews();
        if (savedInstanceState != null)
            listingFragment = (ListingFragment) getSupportFragmentManager().getFragment(savedInstanceState, ListingFragment.class.getName());
        else
            listingFragment = ListingFragment.newInstance();
        loadFragment();
        setListeners();
    }


    @Override
    protected void initializeViews() {
        Toolbar listingActivityToolbar = findViewById(R.id.toolbar);
        setToolbar(listingActivityToolbar, getString(R.string.app_name), false, true);
        floatingActionButton = findViewById(R.id.fab);
    }

    @Override
    protected void loadFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, listingFragment).commit();
    }

    @Override
    protected void setListeners() {
        floatingActionButton.setOnClickListener(floatingActionButtonOnClickListener);
    }

    private View.OnClickListener floatingActionButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AddReportActivity.startActivity(ListingActivity.this);
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (listingFragment != null)
            getSupportFragmentManager().putFragment(outState, ListingFragment.class.getName(), listingFragment);
    }
}

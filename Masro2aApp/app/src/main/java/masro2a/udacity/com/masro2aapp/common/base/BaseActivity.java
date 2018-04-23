package masro2a.udacity.com.masro2aapp.common.base;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import masro2a.udacity.com.masro2aapp.R;

/**
 * Created by peter on 22/04/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar myToolbar;

    protected void setToolbar(Toolbar toolbar, String title, boolean showUpButton, boolean withElevation) {
        myToolbar = toolbar;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && withElevation) {
            toolbar.setElevation(getResources().getDimension(R.dimen.padding_small));
        }
        if (myToolbar != null) {
            myToolbar.setTitle(title);
            setSupportActionBar(myToolbar);
        }
        if (showUpButton) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    protected void setToolbarColor(Toolbar toolbar, int color) {
        toolbar.setBackgroundColor(color);
    }

    public void setToolbarTitle(String title) {
        if (myToolbar != null)
            myToolbar.setTitle(title);
    }

    public void setToolbarSubTitle(String subTitle) {
        if (myToolbar != null) {
            myToolbar.setSubtitle(subTitle);
        }

    }


    protected abstract void initializeViews();


    protected abstract void setListeners();

    protected abstract void loadFragment();

}



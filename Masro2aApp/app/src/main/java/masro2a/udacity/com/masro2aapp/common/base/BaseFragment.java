package masro2a.udacity.com.masro2aapp.common.base;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by peter on 22/04/18.
 */

public abstract class BaseFragment extends Fragment {

    protected abstract void initializeViews(View v);

    protected abstract void setListeners();
}

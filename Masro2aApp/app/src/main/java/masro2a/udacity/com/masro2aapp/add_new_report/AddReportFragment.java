package masro2a.udacity.com.masro2aapp.add_new_report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import masro2a.udacity.com.masro2aapp.R;
import masro2a.udacity.com.masro2aapp.common.base.BaseFragment;

/**
 * Created by peter on 23/04/18.
 */

public class AddReportFragment extends BaseFragment {

    public static AddReportFragment newInstance() {
        return new AddReportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_report, container, false);
    }

    @Override
    protected void initializeViews(View v) {

    }

    @Override
    protected void setListeners() {

    }
}

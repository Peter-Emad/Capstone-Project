package masro2a.udacity.com.masro2aapp.add_new_report;

import masro2a.udacity.com.masro2aapp.common.base.BaseView;

/**
 * Created by peter on 23/04/18.
 */

public interface AddReportView extends BaseView {

    void showValidationErrors(boolean isCarMakeSelected, boolean isValidCarModel, boolean isValidCarLocation);

    void onAddSuccess();
}

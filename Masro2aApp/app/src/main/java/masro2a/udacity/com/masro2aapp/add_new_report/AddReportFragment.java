package masro2a.udacity.com.masro2aapp.add_new_report;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.zhihu.matisse.Matisse;

import java.util.List;

import masro2a.udacity.com.masro2aapp.R;
import masro2a.udacity.com.masro2aapp.common.helpers.Constants;
import masro2a.udacity.com.masro2aapp.common.helpers.MediaPickerHelper;
import masro2a.udacity.com.masro2aapp.common.helpers.PermissionHandlerFragment;
import masro2a.udacity.com.masro2aapp.common.helpers.PermissionsListener;
import masro2a.udacity.com.masro2aapp.common.helpers.UiUtils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by peter on 23/04/18.
 */

public class AddReportFragment extends PermissionHandlerFragment implements PermissionsListener, AddReportView {

    private LinearLayout lnrAddCarImage;
    private ImageView imgCar;
    private Spinner spCarMake;
    private TextView txtCarLocation;
    private TextInputLayout tilCarModel, tilNotes, tilContactInfo;
    private AppCompatEditText etCarModel, etNotes, etContactInfo;
    private Context context;
    private LatLng carLatLng = null;
    private Uri carImageUri;
    private AddReportPresenter addReportPresenter;
    private ProgressBar carPostProgressBar;
    private RelativeLayout rlCarPostContainer;
    private AddReportInteraction addReportInteraction;


    public static AddReportFragment newInstance() {
        return new AddReportFragment();
    }

    interface AddReportInteraction {
        void onPostAddSuccess();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        addReportPresenter = new AddReportPresenter(context, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);
        initializeViews(view);
        setListeners();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            addReportInteraction = (AddReportInteraction) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        addReportInteraction = null;
    }

    @Override
    protected void initializeViews(View v) {
        lnrAddCarImage = v.findViewById(R.id.lnrAddCarImage);
        imgCar = v.findViewById(R.id.imgCar);
        spCarMake = v.findViewById(R.id.spCarMake);
        txtCarLocation = v.findViewById(R.id.txtCarLocation);
        tilCarModel = v.findViewById(R.id.tilCarModel);
        tilNotes = v.findViewById(R.id.tilNotes);
        tilContactInfo = v.findViewById(R.id.tilContactInfo);
        etCarModel = v.findViewById(R.id.etCarModel);
        etNotes = v.findViewById(R.id.etNotes);
        etContactInfo = v.findViewById(R.id.etContactInfo);
        rlCarPostContainer = v.findViewById(R.id.rlCarPostContainer);
        carPostProgressBar = v.findViewById(R.id.carPostProgressBar);

    }

    @Override
    protected void setListeners() {
        lnrAddCarImage.setOnClickListener(lnrAddCarImageClickListener);
        txtCarLocation.setOnClickListener(txtCarLocationClickListener);
    }

    @Override
    public void onPermissionGranted(int requestCode) {
        if (requestCode == Constants.PermissionCodes.CAMERA_GALLERY_REQUEST_PERMISSION)
            MediaPickerHelper.initCameraAndImagePicker(this, Constants.PermissionCodes.CAMERA_GALLERY_REQUEST_PERMISSION);

        else if (requestCode == Constants.PermissionCodes.LOCATION_REQUEST_PERMISSION)
            pickCarLocation();

    }

    @Override
    public void onPermissionDenied(int requestCode) {
        if (requestCode == Constants.PermissionCodes.CAMERA_GALLERY_REQUEST_PERMISSION)
            UiUtils.showToast(context, context.getString(R.string.camera_permission));

        else if (requestCode == Constants.PermissionCodes.LOCATION_REQUEST_PERMISSION)
            UiUtils.showToast(context, context.getString(R.string.location_permission));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_done, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                clearErrors();
                UiUtils.hideSoftKeyboard((Activity) context);
                if (addReportPresenter.validatePost(spCarMake.getSelectedItemPosition(), etCarModel.getText().toString(), carLatLng))
                    addReportPresenter.addPost(carImageUri, context.getResources().getStringArray(R.array.car_makes)[spCarMake.getSelectedItemPosition()], etCarModel.getText().toString(),
                            carLatLng, etNotes.getText().toString(), etContactInfo.getText().toString(), txtCarLocation.getText().toString());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearErrors() {
        tilCarModel.setError(null);
        if (carLatLng == null) {
            txtCarLocation.setText(context.getString(R.string.car_location));
            txtCarLocation.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private View.OnClickListener lnrAddCarImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkPermissions(context, Constants.PermissionCodes.CAMERA_GALLERY_REQUEST_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        }
    };

    private View.OnClickListener txtCarLocationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkPermissions(context, Constants.PermissionCodes.LOCATION_REQUEST_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION);

        }
    };

    private void pickCarLocation() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build((Activity) context), Constants.PermissionCodes.LOCATION_REQUEST_PERMISSION);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.PermissionCodes.CAMERA_GALLERY_REQUEST_PERMISSION) {
                List<String> filePath = Matisse.obtainPathResult(data);
                List<Uri> fileUri = Matisse.obtainResult(data);
                if (filePath != null && filePath.size() > 0 && filePath.get(0) != null
                        && fileUri != null && fileUri.size() > 0 && fileUri.get(0) != null) {
                    Bitmap carBitmap = MediaPickerHelper.getImageBitmap(filePath.get(0));
                    if (carBitmap != null) {
                        imgCar.setImageBitmap(carBitmap);
                        carImageUri = fileUri.get(0);
                    }
                }
            }
            if (requestCode == Constants.PermissionCodes.LOCATION_REQUEST_PERMISSION) {
                Place place = PlacePicker.getPlace(data, context);
                carLatLng = place.getLatLng();
                txtCarLocation.setText(place.getAddress());
            }
        }
    }

    @Override
    public void showProgress(boolean show) {
        rlCarPostContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        carPostProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showValidationErrors(boolean isCarMakeSelected, boolean isValidCarModel, boolean isValidCarLocation) {
        if (!isCarMakeSelected)
            UiUtils.showToast(context, context.getString(R.string.select_car_make));

        if (!isValidCarModel)
            tilCarModel.setError(context.getString(R.string.select_car_model));

        if (!isValidCarLocation) {
            txtCarLocation.setTextColor(context.getResources().getColor(R.color.colorRed));
            txtCarLocation.setText(context.getString(R.string.select_car_location));
        }
    }

    @Override
    public void onAddSuccess() {
        UiUtils.showToast(context, context.getString(R.string.post_added));
        addReportInteraction.onPostAddSuccess();
    }
}

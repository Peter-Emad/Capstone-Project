package masro2a.udacity.com.masro2aapp.common.helpers;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

import masro2a.udacity.com.masro2aapp.common.base.BaseFragment;

/**
 * Created by peter on 28/03/18.
 */

public abstract class PermissionHandlerFragment extends BaseFragment {

    private PermissionsListener permissionsListener = (PermissionsListener) this;

    public void checkPermissions(Context context, int requestCode, String... permissions) {
        ArrayList<String> notGrantedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                notGrantedPermissions.add(permission);
            }
        }
        if (notGrantedPermissions.size() == 0) {
            permissionsListener.onPermissionGranted(requestCode);
        } else {
            requestPermissions(notGrantedPermissions.toArray(new String[notGrantedPermissions.size()]), requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PermissionCodes.CAMERA_GALLERY_REQUEST_PERMISSION:
            case Constants.PermissionCodes.LOCATION_REQUEST_PERMISSION: {
                boolean permissionsGranted = true;
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            permissionsGranted = false;
                            break;
                        }
                    }
                }
                if (permissionsGranted)
                    permissionsListener.onPermissionGranted(requestCode);
                else
                    permissionsListener.onPermissionDenied(requestCode);
            }
            break;
        }
    }
}

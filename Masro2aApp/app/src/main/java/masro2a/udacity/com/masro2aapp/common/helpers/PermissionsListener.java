package masro2a.udacity.com.masro2aapp.common.helpers;

/**
 * Created by peter on 28/03/18.
 */

public interface PermissionsListener {

    void onPermissionGranted(int requestCode);

    void onPermissionDenied(int requestCode);
}

package masro2a.udacity.com.masro2aapp.common.helpers;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import masro2a.udacity.com.masro2aapp.R;

/**
 * Created by peter on 25/04/18.
 */

public class MediaPickerHelper {

    public static void initCameraAndImagePicker(Fragment fragment, int requestCode) {
        Matisse.from(fragment)
                .choose(MimeType.ofImage())
                .showSingleMediaType(true)
                .countable(false)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "masro2a.udacity.com.masro2aapp.provider"))
                .maxSelectable(fragment.getActivity().getResources().getInteger(R.integer.max_media_selection))
                .gridExpectedSize(fragment.getResources().getDimensionPixelSize(R.dimen.media_picker_grid_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .imageEngine(new PicassoEngine())
                .theme(com.zhihu.matisse.R.style.Matisse_Zhihu)
                .forResult(requestCode);
    }


    public static Bitmap getImageBitmap(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmap = BitmapFactory.decodeFile(filePath, options);
        return rotateBitmap(filePath, bitmap);
    }

    private static Bitmap rotateBitmap(String filePath, Bitmap bitmap) {

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = exifToDegrees(rotation);
            Matrix matrix = new Matrix();
            if (rotation != 0f) {
                matrix.preRotate(rotationInDegrees);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
        return bitmap;
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }
}

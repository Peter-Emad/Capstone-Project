package masro2a.udacity.com.masro2aapp.common.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Peter on 06/11/2017.
 */


public class AppPreferences {

    public static String getString(String key, Context ctx, String defaultValue) {
        SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return appPreferences.getString(key, defaultValue);
    }

    public static void setString(String key, String value, Context ctx) {
        SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        appPreferences.edit().putString(key, value).apply();

    }

    public static void clearKey(String key, Context ctx) {
        SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        appPreferences.edit().remove(key).apply();
    }
}

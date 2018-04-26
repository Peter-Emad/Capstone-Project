package masro2a.udacity.com.masro2aapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import masro2a.udacity.com.masro2aapp.common.helpers.AppPreferences;
import masro2a.udacity.com.masro2aapp.common.helpers.Constants;
import masro2a.udacity.com.masro2aapp.posts_listing.ListingActivity;

/**
 * Implementation of App Widget functionality.
 */
public class Masro2aWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.masro2a_widget);
        views.setTextViewText(R.id.txtCarMake, context.getString(R.string.details_car_make,
                AppPreferences.getString(Constants.GeneralKeys.LAST_CAR_MAKE_VIEWED, context, "")));

        views.setTextViewText(R.id.txtCarModel, context.getString(R.string.details_car_model,
                AppPreferences.getString(Constants.GeneralKeys.LAST_CAR_MODEL_VIEWED, context, "")));

        views.setTextViewText(R.id.txtCarLocation, context.getString(R.string.details_car_location,
                AppPreferences.getString(Constants.GeneralKeys.LAST_CAR_ADDRESS_VIEWED, context, "")));
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, ListingActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.masro2a_widget);
            views.setOnClickPendingIntent(R.id.rlWidgetContainer, pendingIntent);
            updateAppWidget(context, appWidgetManager, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


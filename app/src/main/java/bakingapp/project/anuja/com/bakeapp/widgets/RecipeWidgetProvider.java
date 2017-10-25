package bakingapp.project.anuja.com.bakeapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import bakingapp.project.anuja.com.bakeapp.MainActivity;
import bakingapp.project.anuja.com.bakeapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews Rviews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

            Intent title_Intent = new Intent(context, MainActivity.class);
            PendingIntent titlePendingIntent = PendingIntent.getActivity(context, 0, title_Intent, 0);
            Rviews.setOnClickPendingIntent(R.id.title, titlePendingIntent);

            Intent intent = new Intent(context, WidgetRemoteViewService.class);
            Rviews.setRemoteAdapter(R.id.listview_recipe, intent);

            appWidgetManager.updateAppWidget(appWidgetId, Rviews);
        }
    }


    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager appMgr = AppWidgetManager.getInstance(context);
            ComponentName name = new ComponentName(context, RecipeWidgetProvider.class);
            appMgr.notifyAppWidgetViewDataChanged(appMgr.getAppWidgetIds(name), R.id.listview_recipe);
        }
        super.onReceive(context, intent);
    }

}


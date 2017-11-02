package bakingapp.project.anuja.com.bakeapp.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import bakingapp.project.anuja.com.bakeapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for(int i=0; i < appWidgetIds.length; ++i){
            updateWidget(context, appWidgetIds[i]);
        }
    }

    public static void updateWidget(Context context, int Id){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Intent intent = new Intent(context, WidgetRemoteViewService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, Id);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        // set remoteAdapter with a listView for ConfigurationActivity to use within its recyclerView implementation
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        rv.setRemoteAdapter(R.id.ingredient_listview, intent);

        /* retrieve widgetId and sharedPref for
           Recipe title concurrently when configurationActivity has finished
           Execute gson to json conversion within try catch block and set textView */
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(WidgetConfiguration.WIDGET_KEY_RECIPE_TITLE, Context.MODE_PRIVATE);

        String recipeTitleReceived = sharedPreferences.getString("Title for: "+appWidgetId, "");

        //set TextView for Recipe Title
        rv.setTextViewText(R.id.ingredient_title, recipeTitleReceived);

        // Update the remoteView at this end
        appWidgetManager.updateAppWidget(Id, rv);
    }


//    public static void updatePlantWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
//    }
//
//    // setImageViewResource to update the widget’s image
//    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
//        Intent intent;
//
//            Log.d(RecipeWidgetProvider.class.getSimpleName(), "inside updateAppWidget");
//            intent = new Intent(context, MainActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
//        views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);
//
//        Intent wateringIntent = new Intent(context, RecipeWidgetService.class);
//
//        PendingIntent wateringPendingIntent = PendingIntent.getService(context, 0, wateringIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//
//
//    }
//
//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        //Start the intent service update widget action, the service takes care of updating the widgets UI
//        RecipeWidgetService.startActionUpdatePlantWidgets(context);
//    }


//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        for (int appWidgetId : appWidgetIds) {
//            RemoteViews Rviews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
//
//            Intent title_Intent = new Intent(context, MainActivity.class);
//            PendingIntent titlePendingIntent = PendingIntent.getActivity(context, 0, title_Intent, 0);
//            Rviews.setOnClickPendingIntent(R.id.title, titlePendingIntent);
//
//            Intent intent = new Intent(context, WidgetRemoteViewService.class);
//            Rviews.setRemoteAdapter(R.id.listview_recipe, intent);
//
//            appWidgetManager.updateAppWidget(appWidgetId, Rviews);
//        }
//    }
//
//
//    @Override
//    public void onReceive(final Context context, Intent intent) {
//        final String action = intent.getAction();
//        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
//            AppWidgetManager appMgr = AppWidgetManager.getInstance(context);
//            ComponentName name = new ComponentName(context, RecipeWidgetProvider.class);
//            appMgr.notifyAppWidgetViewDataChanged(appMgr.getAppWidgetIds(name), R.id.listview_recipe);
//        }
//        super.onReceive(context, intent);
//    }

}


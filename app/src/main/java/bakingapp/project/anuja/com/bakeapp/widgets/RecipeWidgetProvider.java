package bakingapp.project.anuja.com.bakeapp.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import bakingapp.project.anuja.com.bakeapp.R;


public class RecipeWidgetProvider extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appIds) {
        super.onUpdate(context, appWidgetManager, appIds);

        for (int i = 0; i < appIds.length; ++i) {
            updateWidget(context, appIds[i]);
        }
    }

    public static void updateWidget(Context context, int Id) {

        AppWidgetManager Widget_Manager = AppWidgetManager.getInstance(context);
        Intent intent = new Intent(context, WidgetRemoteViewService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, Id);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        rv.setRemoteAdapter(R.id.ingredient_listview, intent);

        SharedPreferences sharedPreferences =
                context.getSharedPreferences(WidgetConfiguration.WIDGET_KEY_RECIPE_TITLE, Context.MODE_PRIVATE);

        int appId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        String recipeTitleReceived = sharedPreferences.getString("RecipeTitle" + appId, "");
        //set Recipe Title
        rv.setTextViewText(R.id.ingredient_title, recipeTitleReceived);
        // Update the remoteView
        Widget_Manager.updateAppWidget(Id, rv);
    }

}
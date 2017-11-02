package bakingapp.project.anuja.com.bakeapp.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import bakingapp.project.anuja.com.bakeapp.R;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Ingredient;

/**
 * Created by USER on 02-10-2017.
 */

public class WidgetRemoteViewFactory  implements RemoteViewsService.RemoteViewsFactory {


    private List<Ingredient> mIngredientList;
    private Intent mIntent;

    public WidgetRemoteViewFactory(Context context, Intent intent){
        mContext = context;
        mIntent = intent;
    }
    private Context mContext;

    public WidgetRemoteViewFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

        int appWidgetId = mIntent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        SharedPreferences preferences =
                mContext.getSharedPreferences(WidgetConfiguration.WIDGET_KEY, Context.MODE_PRIVATE);

        if(appWidgetId != 0){
            // Gson
            Gson gson = new Gson();
            String json = preferences.getString("Id: "+appWidgetId, "");

            Type ingredient = new TypeToken<List<Ingredient>>() {} .getType();
            //put it into gson reflect type
            List<Ingredient> recipe_Ingredient = gson.fromJson(json, ingredient);

            if(recipe_Ingredient == null){

                return;
            }

            mIngredientList = recipe_Ingredient;


            for (int i = 0; i < mIngredientList.size(); i++)
            {
                System.out.print("----------------------------------Ingredient :::"+mIngredientList.get(i).getIngredient());
                System.out.print("----------------------------------Measure :::"+mIngredientList.get(i).getMeasure());
                System.out.print("----------------------------------Quantity :::"+mIngredientList.get(i).getQuantity());
            }
        }


    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews =
                new RemoteViews(mContext.getPackageName(), R.layout.widget_recipe_ing_list);
        remoteViews.setTextViewText(R.id.text_ingredient, mIngredientList.get(position).getIngredient());
        remoteViews.setTextViewText(R.id.text_measure, String.valueOf(mIngredientList.get(position).getMeasure()));
        remoteViews.setTextViewText(R.id.text_quantity, String.valueOf(mIngredientList.get(position).getQuantity()));

        return remoteViews;
    }


    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredientList.size();
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

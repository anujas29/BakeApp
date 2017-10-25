package bakingapp.project.anuja.com.bakeapp.widgets;

import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import bakingapp.project.anuja.com.bakeapp.R;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Recipe;
import bakingapp.project.anuja.com.bakeapp.rest.BakingClient;
import bakingapp.project.anuja.com.bakeapp.rest.BakingInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bakingapp.project.anuja.com.bakeapp.MainActivity.recipeList;

/**
 * Created by USER on 02-10-2017.
 */

public class WidgetRemoteViewFactory  implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;

    public WidgetRemoteViewFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    public void getData()
    {
        BakingInterface apiService = BakingClient.getClient().create(BakingInterface.class);
        Call<List<Recipe>> call = apiService.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> RecipeData = response.body();
                recipeList = RecipeData;

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDataSetChanged() {
        getData();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public RemoteViews getViewAt(int position) {

       Log.v(mContext.getClass().getSimpleName(), "position = "+position);

        RemoteViews rview = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_item);
        rview.setTextViewText(R.id.recipe_textview, recipeList.get(position).getName());
        return rview;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
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

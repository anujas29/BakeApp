package bakingapp.project.anuja.com.bakeapp.widgets;

/**
 * Created by USER on 31-10-2017.
 */

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import bakingapp.project.anuja.com.bakeapp.R;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Ingredient;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Recipe;
import bakingapp.project.anuja.com.bakeapp.rest.BakingClient;
import bakingapp.project.anuja.com.bakeapp.rest.BakingInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WidgetConfiguration extends Activity {

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    // KEY constants for all widget stuff
    public static final String WIDGET_KEY = "Recipe_Ingredient_Widget";
    public static String WIDGET_KEY_RECIPE_TITLE = "Title_key";
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_widget);

        getData();

        //retrieve intent from WidgetProvider - assign to variable
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            /*   If the activity was opened with the widget,
                 then set result to canceled to ensure
                 that if user exits the activity, the widget is created and
                 we are not recreating any activity */
            Intent intent = new Intent();
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(Activity.RESULT_CANCELED, intent);
        }

        /* RecyclerView to setLayoutManager as LinearLayout
         * Make Retrofit calls and pull data and set adapter
         * */
        mRecyclerView = (RecyclerView) findViewById(R.id.widgets_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void getData()
    {
        BakingInterface apiService = BakingClient.getClient().create(BakingInterface.class);
        Call<List<Recipe>> call = apiService.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> RecipeData = response.body();
                updateData(RecipeData);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }

    private void updateData(List<Recipe> mRecipeList){

        mRecyclerView.setAdapter(new MyRecycler(mRecipeList));
    }





    class MyRecycler extends RecyclerView.Adapter<MyRecycler.RecipeItemHolder> {

        public List<Recipe> mRecipe;

        private MyRecycler(List<Recipe> Recipelist) {
            mRecipe = Recipelist;
        }

        @Override
        public RecipeItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            int layoutNeeded = R.layout.recipe_widget_item;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;
            View view = inflater.inflate(layoutNeeded, parent, shouldAttachToParentImmediately);
            return new RecipeItemHolder(view);
        }

        @Override
        public void onBindViewHolder(RecipeItemHolder holder, int position) {
            holder.bindItem(mRecipe.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mRecipe.size();
        }

        class RecipeItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            @BindView(R.id.recipe_name)
            TextView mRecipeName;

            List<Ingredient> mRecipeIngredient;
            String mRecipeTitle;

            private RecipeItemHolder(View v) {
                super(v);
                ButterKnife.bind(this, v);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {

                    SharedPreferences sharedPreferences = getSharedPreferences(WIDGET_KEY, MODE_PRIVATE);
                    SharedPreferences sharedPreferences_title = getSharedPreferences(WIDGET_KEY_RECIPE_TITLE, MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    SharedPreferences.Editor prefsEditorForTitle = sharedPreferences_title.edit();

                    Recipe recipe_name = mRecipe.get(getAdapterPosition());
                    String recipename = recipe_name.getName();

                    // gson to json for ingredient List
                    Gson gson = new Gson();
                    String json = gson.toJson(mRecipeIngredient);
                    prefsEditor.putString("Id: " + appWidgetId, json);


                   // System.out.println("---------------------- inside widget configuration ------------"+json);

                    prefsEditorForTitle.putString("Title " + appWidgetId, recipename);

                    //apply all prefs
                    prefsEditor.apply();
                    prefsEditorForTitle.apply();

                    // use updateWidget method to reassure the installments of remote layouts
                    RecipeWidgetProvider.updateWidget(WidgetConfiguration.this, appWidgetId);

                    Intent intent = new Intent();
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                    // if operation succeeded, therefore finish this call
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }

            private void bindItem(Recipe recipe, int position) {
                mRecipeTitle = recipe.getName();
                mRecipeName.setText(recipe.getName());
                mRecipeIngredient = mRecipe.get(position).getIngredients();
            }

            }

    }
}

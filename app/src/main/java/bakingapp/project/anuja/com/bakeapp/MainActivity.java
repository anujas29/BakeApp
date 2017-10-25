package bakingapp.project.anuja.com.bakeapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import bakingapp.project.anuja.com.bakeapp.adapter.RecipeAdapter;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Recipe;
import bakingapp.project.anuja.com.bakeapp.rest.BakingClient;
import bakingapp.project.anuja.com.bakeapp.rest.BakingInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static List<Recipe> recipeList = new ArrayList<>();
    private GridLayoutManager mLayoutManager;
    private String List_State = "list_state";
    private Parcelable State;
    private RecipeAdapter mRecipeAdapter;
    public boolean Tablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Tablet = getResources().getBoolean(R.bool.tablet);
        int columns;
        ButterKnife.bind(this);

        if (Tablet) {
            columns = 3;
        } else {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                columns = 1;
            } else {
                columns = 2;
            }
        }
         mLayoutManager = new GridLayoutManager(this, columns, LinearLayoutManager.VERTICAL, false);


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecipeAdapter = new RecipeAdapter(recipeList, MainActivity.this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouch(this, mRecyclerView, new RecyclerTouch.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Recipe recipe = recipeList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("object", recipe);

                Intent recipeIntent = new Intent(MainActivity.this, RecipeActivity.class);
                recipeIntent.putExtras(bundle);
                startActivity(recipeIntent);

            }
            @Override
            public void onLongClick(View view, int position) {}
        }));

        getRecipe();

    }
    private void updateData(List<Recipe> recipe) {

        recipeList = recipe;
        mRecipeAdapter = new RecipeAdapter(recipeList, MainActivity.this);
        mRecyclerView.setAdapter(mRecipeAdapter);

    }


    private void getRecipe()
    {


        BakingInterface apiService = BakingClient.getClient().create(BakingInterface.class);
        Call<List<Recipe>> call = apiService.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> RecipeData = response.body();
                updateData(RecipeData);

                mLayoutManager.onRestoreInstanceState(State);

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                //Toast.makeText(getContext(),"No Intenet Access ",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            State = savedInstanceState.getParcelable(List_State);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        State = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(List_State, State);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (State != null) {
            mLayoutManager.onRestoreInstanceState(State);
        }
    }
}

package bakingapp.project.anuja.com.bakeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bakingapp.project.anuja.com.bakeapp.adapter.StepIngredientAdapter;
import bakingapp.project.anuja.com.bakeapp.detail.IngredientDetail;
import bakingapp.project.anuja.com.bakeapp.detail.IngredientDetailFragment;
import bakingapp.project.anuja.com.bakeapp.detail.StepDetail;
import bakingapp.project.anuja.com.bakeapp.detail.StepDetailViewFragment;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Ingredient;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Recipe;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by USER on 19-06-2017.
 */

public class RecipeActivity extends AppCompatActivity {

    private Recipe recipeList;
    private List<Ingredient> IngredientsList;
    private List<Step> stepsList;
    private LinearLayoutManager mLayoutManager;

    private boolean TwoPane;

    private StepIngredientAdapter stepIngredientAdapter;

    @BindView(R.id.textview_recipe)
    TextView recipe_name;

    @BindView(R.id.activity_item_list)
    RecyclerView step_ingredient_RecyclerView;

    private String LIST_STATE = "list_state";
    private Parcelable ListState;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipe_list_content);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            recipeList = bundle.getParcelable("object");
        }

        IngredientsList = recipeList.getIngredients();
        stepsList = recipeList.getSteps();

        Log.v(RecipeActivity.class.getSimpleName(), "ingredients List  = " + IngredientsList.toString());
        Log.v(RecipeActivity.class.getSimpleName(), "steps List  = " + stepsList.toString());

        mLayoutManager = new LinearLayoutManager(this);
        step_ingredient_RecyclerView.setLayoutManager(mLayoutManager);
        stepIngredientAdapter = new StepIngredientAdapter(IngredientsList,stepsList,TwoPane, this);
        step_ingredient_RecyclerView.setAdapter(stepIngredientAdapter);
        recipe_name.setText(recipeList.getName());

        mLayoutManager.onRestoreInstanceState(ListState);

        if (findViewById(R.id.detail_item_container) != null) {
            TwoPane = true;
        }

        step_ingredient_RecyclerView.addOnItemTouchListener(new RecyclerTouch(this,step_ingredient_RecyclerView,new RecyclerTouch.ClickListener()
        {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();

                if(position == 0){

                    bundle.putParcelableArrayList("ingredient", (ArrayList<? extends Parcelable>) IngredientsList);

                    if(TwoPane)
                    {
                        IngredientDetailFragment ingDetailFrag = new IngredientDetailFragment();
                        ingDetailFrag.setIngredientList(IngredientsList);
                        getSupportFragmentManager().beginTransaction().add(R.id.detail_item_container,ingDetailFrag).commit();
                    }
                    else
                    {
                        Intent intent = new Intent(getBaseContext(), IngredientDetail.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }

                if(position >1)
                {
                    bundle.putParcelableArrayList("step", (ArrayList<? extends Parcelable>) stepsList);
                    bundle.putInt("position", position -2);

                    if(TwoPane)
                    {
                        StepDetailViewFragment stepDetailView = new StepDetailViewFragment();
                        stepDetailView.setPosition(position-2);
                        stepDetailView.setStep(stepsList);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.detail_item_container, stepDetailView)
                                .commit();
                    }
                    else
                    {
                        Intent intent = new Intent(getBaseContext(), StepDetail.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ListState != null) {
            mLayoutManager.onRestoreInstanceState(ListState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            ListState = savedInstanceState.getParcelable(LIST_STATE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE, ListState);
    }
}

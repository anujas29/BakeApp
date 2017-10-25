package bakingapp.project.anuja.com.bakeapp.detail;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bakingapp.project.anuja.com.bakeapp.R;
import bakingapp.project.anuja.com.bakeapp.adapter.IngredientAdapter;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by USER on 18-07-2017.
 */

public class IngredientDetailFragment extends Fragment{

    private IngredientAdapter ingredientAdapter;

    public static final String INGREDIENT = "ingredient";
    private List<Ingredient> ingredient;

    @BindView(R.id.ingredient_recyclerView)
    RecyclerView IngredientRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState!= null){
            ingredient = savedInstanceState.getParcelableArrayList(INGREDIENT);
        }
        View rootView = inflater.inflate(R.layout.activity_ingredient, container, false);
        ButterKnife.bind(this, rootView);

        if(ingredient!=null){
            IngredientRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, GridLayoutManager.VERTICAL));

            ingredientAdapter = new IngredientAdapter(ingredient);
            IngredientRecyclerView.setAdapter(ingredientAdapter);
        }
        else{
            Toast.makeText(getContext(),"No Ingredients ",Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Ingredient");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(INGREDIENT, (ArrayList<? extends Parcelable>) ingredient);
    }

    public void setIngredientList(List<Ingredient> ingredient){
        this.ingredient = ingredient;
    }
}

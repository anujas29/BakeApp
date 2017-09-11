package bakingapp.project.anuja.com.bakeapp.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import bakingapp.project.anuja.com.bakeapp.R;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Ingredient;
import butterknife.ButterKnife;

/**
 * Created by USER on 07-07-2017.
 */

public class IngredientDetail extends AppCompatActivity {


    private List<Ingredient> ingredient;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_detail);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {

            ingredient = getIntent().getParcelableArrayListExtra("ingredient");

            System.out.println("---------- inside IngredientDetail --------------------");
            if (ingredient != null) {

                IngredientDetailFragment IngredientDetail = new IngredientDetailFragment();
                IngredientDetail.setIngredientList(ingredient);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ingredient_detail_container, IngredientDetail)
                        .commit();
            }


        }
    }
}

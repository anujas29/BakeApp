package bakingapp.project.anuja.com.bakeapp.rest;

import java.util.List;

import bakingapp.project.anuja.com.bakeapp.pojoclass.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by USER on 11-05-2017.
 */

public interface BakingInterface {

    @GET("5907926b_baking/baking.json")
    Call<List<Recipe>> getRecipes();




}

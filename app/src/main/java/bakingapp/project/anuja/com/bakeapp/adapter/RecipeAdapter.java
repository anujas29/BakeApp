package bakingapp.project.anuja.com.bakeapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bakingapp.project.anuja.com.bakeapp.R;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by USER on 20-05-2017.
 */


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private List<Recipe> mRecipe;
    private Context context;

    public RecipeAdapter(List<Recipe> recipe, Context context) {
        this.mRecipe = recipe;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list, parent, false);
        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        System.out.println("-------------------inside onBindViewHolder -----------"+position);
        holder.recipeName.setText(mRecipe.get(position).getName());




    }

    public void add(List<Recipe> movie) {
        mRecipe.clear();
        mRecipe.addAll(movie);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mRecipe.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name)
        TextView recipeName;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


//public class RecipeAdapter  extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
//
//    private  List<Recipe> recipe;
//    private Context context;
//    //private final Callbacks mCallbacks;
//
////    public interface Callbacks {
////        void open(Recipe recipe, int position);
////    }
//
//    public RecipeAdapter(List<Recipe> recipeList, Context context) {
//        System.out.println("------------ RecipeAdapter -----------------"+recipeList.size());
//        this.recipe = recipeList;
//        this.context = context;
//    }
//
//    @Override
//    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_content, parent, false);
//        return new RecipeViewHolder(itemView);
//    }
//
//    @Override
//    public int getItemCount() {
//        System.out.println("-------------------inside getItemCount -----------"+recipe.size());
//        return recipe.size();
//    }
//
//    @Override
//    public void onBindViewHolder(RecipeViewHolder holder, int position) {
//        System.out.println("-------------------inside onBindViewHolder -----------"+recipe.get(position).getName());
//        holder.recipeName.setText(recipe.get(position).getName());
//
//    }
//
//
//
//    public class RecipeViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.recipe_name)
//        TextView recipeName;
//
//        public RecipeViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//}
//

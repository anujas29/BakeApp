package bakingapp.project.anuja.com.bakeapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        holder.recipeName.setText(mRecipe.get(position).getName());
        String path = mRecipe.get(position).getImage();
        if (path.isEmpty())
            Picasso.with(context).load(R.drawable.baking_blunders).into(holder.thumbnail);
        else {
            Picasso.with(context).load(path).into(holder.thumbnail);
        }
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
        @BindView(R.id.image_view)
        ImageView thumbnail;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package bakingapp.project.anuja.com.bakeapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bakingapp.project.anuja.com.bakeapp.R;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by USER on 19-07-2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyIngredientViewHolder> {

    private List<Ingredient> ingredient;

    public IngredientAdapter(List<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }


    @Override
    public MyIngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new MyIngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyIngredientViewHolder holder, int position) {

        Ingredient IngredientList = ingredient.get(position);
        holder.ingeName.setText(IngredientList.getIngredient());
        holder.ingeQuantity.setText(IngredientList.getMeasure());
        holder.ingeMeasure.setText(""+IngredientList.getQuantity());

    }

    @Override
    public int getItemCount() {
        return ingredient.size();
    }

    public class MyIngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.inge_name)
        TextView ingeName;
        @BindView(R.id.inge_quantity)
        TextView ingeQuantity;
        @BindView(R.id.inge_measure)
        TextView ingeMeasure;

        public MyIngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

package bakingapp.project.anuja.com.bakeapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bakingapp.project.anuja.com.bakeapp.R;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Ingredient;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by USER on 20-06-2017.
 */

public class StepIngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Ingredient> ingredient;
    private List<Step> step;
    private Context context;
    private final int VIEW_TYPE_INGREDIENT = 0;
    private final int VIEW_TYPE_STEP = 2;
    private final int VIEW_STEP_LABLE = 1;
    private Boolean mTwoPane;


    public StepIngredientAdapter(List<Ingredient> ingredient,List<Step> step, Boolean mTwoPane, Context context) {
        this.step = step;
        this.ingredient = ingredient;
        this.mTwoPane = mTwoPane;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        if(viewType == VIEW_ING_LABLE)
//        {
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.label_ingredient, parent, false);
//            return new ViewHolderLableIngredient(itemView);
//        }

         if (viewType == VIEW_TYPE_INGREDIENT) {

            System.out.println("------------------- VIEW_TYPE_INGREDIENT ----------------------");
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list, parent, false);
            return new ViewHolderIngredient(itemView);
       }
        else if(viewType == VIEW_STEP_LABLE)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.label_step, parent, false);
            return new ViewHolderLableStep(itemView);
        }
        else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list, parent, false);
            System.out.println("------------------- T3 ----------------------");
            return new ViewHolderSteps(itemView);

        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            System.out.println("------------------------- position VIEW_ING_LABLE -------------");
//            return VIEW_ING_LABLE;
//        }
        if (position == 0) {
            System.out.println("------------------------- position VIEW_TYPE_INGREDIENT -------------");
            return VIEW_TYPE_INGREDIENT;
        }
        if (position == 1) {
            System.out.println("------------------------- position VIEW_STEP_LABLE -------------");
            return VIEW_STEP_LABLE;
        }
         else {
            System.out.println("------------------------- position VIEW_TYPE_STEP -------------");
            return VIEW_TYPE_STEP;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_INGREDIENT:
                ViewHolderIngredient IngredientViewHolder = (ViewHolderIngredient) holder;
                IngredientViewHolder.bindViews();
                break;
            case VIEW_TYPE_STEP:
                ViewHolderSteps StepViewHolder = (ViewHolderSteps) holder;
                StepViewHolder.bindViews(position - 2);
                break;
        }

    }

    @Override
    public int getItemCount() {

        System.out.println("------------------- T1 ingredient ----------------------"+ingredient.size());
        System.out.println("------------------- T1 step ----------------------"+step.size());
        //return  ingredient.size();
        return 2 + step.size();
    }

    public class ViewHolderLableIngredient extends RecyclerView.ViewHolder {

        public ViewHolderLableIngredient(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolderIngredient extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_size)
        TextView ingredient_size;

        public ViewHolderIngredient(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews() {
            System.out.println("------------------- T2 ----------------------");
            ingredient_size.setText("Total Ingredients = "+ingredient.size());
        }

    }

    public class ViewHolderLableStep extends RecyclerView.ViewHolder {

        public ViewHolderLableStep(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolderSteps extends RecyclerView.ViewHolder
    {
        @BindView(R.id.step_desc)
        TextView StepDesc;

        public ViewHolderSteps(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(int position) {
            //StepDesc.setText(stepList.get(position).getShortDescription());
            System.out.println("------------------- ViewHolderSteps ----------------------"+step.size());
            StepDesc.setText(step.get(position).getShortDescription());
        }

    }

}

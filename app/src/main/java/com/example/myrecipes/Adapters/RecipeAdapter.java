package com.example.myrecipes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myRecipes.R;
import com.example.myrecipes.Interfaces.RecipeCallback;
import com.example.myrecipes.Models.Recipe;
import com.example.myrecipes.Utilities.ImageLoader;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private Context context;
    private ArrayList<Recipe> recipes;
    private RecipeCallback recipeCallback;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes){
        this.context = context;
        this.recipes = recipes;
    }

    public RecipeAdapter setRecipeCallback(RecipeCallback recipeCallback) {
        this.recipeCallback = recipeCallback;
        return this;
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
        Recipe recipe = getItem(position);

        //ImageLoader.getInstance().load(holder.recipe_IMG_image.setImageURI(recipe.getPhoto()));

        holder.recipe_LBL_name.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    private Recipe getItem(int position){
        return recipes.get(position);
    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        private ShapeableImageView recipe_IMG_image;
        private MaterialTextView recipe_LBL_name;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_IMG_image = itemView.findViewById(R.id.recipe_IMG_image);
            recipe_LBL_name = itemView.findViewById(R.id.recipe_LBL_name);
        }
    }
}

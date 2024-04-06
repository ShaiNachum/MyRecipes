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

//view holder is the class that hold single object and creates the connection between
// the data and the view of a single object.
//The adapter connect between all the object and all the views
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>{
    private Context context;
    private ArrayList<Recipe> favorites;
    private RecipeCallback recipeCallback;

    public FavoriteAdapter(Context context, ArrayList<Recipe> favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    public FavoriteAdapter setRecipeCallback(RecipeCallback recipeCallback) {
        this.recipeCallback = recipeCallback;
        return this;
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return  new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder holder, int position) {
    //this is the method that brings specific recipe and connects the data with the view holder
        Recipe recipe = getItem(position);
        ImageLoader.getInstance().load(recipe.getPhoto(), holder.favorite_IMG_image);
        holder.favorite_LBL_name.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return favorites == null ? 0 : favorites.size();
    }

    private Recipe getItem(int position){
        return favorites.get(position);
    }


    public class FavoriteViewHolder extends RecyclerView.ViewHolder{
        private ShapeableImageView favorite_IMG_image;
        private MaterialTextView favorite_LBL_name;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            favorite_IMG_image = itemView.findViewById(R.id.favorite_IMG_image);
            favorite_LBL_name = itemView.findViewById(R.id.favorite_LBL_name);
        }
    }
}

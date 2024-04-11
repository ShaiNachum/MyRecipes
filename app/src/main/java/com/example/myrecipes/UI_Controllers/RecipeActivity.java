package com.example.myrecipes.UI_Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.myRecipes.R;
import com.example.myrecipes.Models.Recipe;
import com.example.myrecipes.Utilities.DataManager;
import com.example.myrecipes.Utilities.ImageLoader;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;


public class RecipeActivity extends AppCompatActivity {
    private ShapeableImageView recipe_IMG_background;
    private MaterialTextView recipe_TXT_recipeName;
    private ShapeableImageView recipe_IMG_dishPhoto;
    private MaterialTextView recipe_TXT_dishDescription;
    private ShapeableImageView recipe_IMG_back;
    private ShapeableImageView recipe_IMG_addFavorite;
    private ShapeableImageView recipe_IMG_removeFavorite;
    private ShapeableImageView recipe_IMG_deleteRecipe;
    private int rid;
    private boolean cameFromAllRecipes;
    private boolean cameFromFavorites;
    private DataManager manager;
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = DataManager.getInstance();

        Intent intent = getIntent();
        this.rid = intent.getIntExtra("rid", -1);

        this.recipe = manager.getRecipeById(rid);

        this.cameFromAllRecipes = intent.getBooleanExtra("cameFromAllRecipes", false);
        this.cameFromFavorites = intent.getBooleanExtra("cameFromFavorites", false);

        findViews();

        ImageLoader.getInstance().load(this.recipe.getPhoto(), this.recipe_IMG_dishPhoto);

        Glide
                .with(this)
                .load(R.drawable.old_paper_background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(recipe_IMG_background);

        initViews();
    }


    private void backClicked() {
        if (this.cameFromAllRecipes) {
            Intent intent = new Intent(RecipeActivity.this, AllRecipesActivity.class);
            this.cameFromAllRecipes = false;
            startActivity(intent);
            this.finish();
        } else if (this.cameFromFavorites) {
            Intent intent = new Intent(RecipeActivity.this, AllFavoritesActivity.class);
            this.cameFromFavorites = false;
            startActivity(intent);
            this.finish();
        }
    }


    private void addFavoriteClicked() {
        recipe_IMG_addFavorite.setVisibility(View.INVISIBLE);
        recipe_IMG_removeFavorite.setVisibility(View.VISIBLE);

        manager.addFavorite(this.recipe, this.rid);
    }


    private void removeFavoriteClicked() {
        recipe_IMG_addFavorite.setVisibility(View.VISIBLE);
        recipe_IMG_removeFavorite.setVisibility(View.INVISIBLE);

        manager.removeFavorite(this.rid);
    }


    private void deleteRecipeClicked() {

        manager.deleteRecipe(this.rid);

        Intent intent = new Intent(RecipeActivity.this, AllRecipesActivity.class);
        this.cameFromAllRecipes = false;
        startActivity(intent);
        this.finish();
    }


    private void initViews() {
        recipe_TXT_recipeName.setText(this.recipe.getName());

        recipe_TXT_dishDescription.setText(this.recipe.getDescription());

        if (this.recipe.isFavorite()) {
            recipe_IMG_addFavorite.setVisibility(View.INVISIBLE);
            recipe_IMG_removeFavorite.setVisibility(View.VISIBLE);
        } else {
            recipe_IMG_addFavorite.setVisibility(View.VISIBLE);
            recipe_IMG_removeFavorite.setVisibility(View.INVISIBLE);
        }

        if (this.cameFromFavorites)
            recipe_IMG_deleteRecipe.setVisibility(View.INVISIBLE);

        recipe_IMG_removeFavorite.setOnClickListener(v -> removeFavoriteClicked());
        recipe_IMG_addFavorite.setOnClickListener(v -> addFavoriteClicked());
        recipe_IMG_back.setOnClickListener(v -> backClicked());
        recipe_IMG_deleteRecipe.setOnClickListener(v -> deleteRecipeClicked());
    }


    private void findViews() {
        recipe_IMG_background = findViewById(R.id.recipe_IMG_background);
        recipe_TXT_recipeName = findViewById(R.id.recipe_TXT_recipeName);
        recipe_IMG_dishPhoto = findViewById(R.id.recipe_IMG_dishPhoto);
        recipe_TXT_dishDescription = findViewById(R.id.recipe_TXT_dishDescription);
        recipe_IMG_back = findViewById(R.id.recipe_IMG_back);
        recipe_IMG_addFavorite = findViewById(R.id.recipe_IMG_addFavorite);
        recipe_IMG_removeFavorite = findViewById(R.id.recipe_IMG_removeFavorite);
        recipe_IMG_deleteRecipe = findViewById(R.id.recipe_IMG_deleteRecipe);
    }
}
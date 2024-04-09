package com.example.myrecipes.UI_Controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.myRecipes.R;
import com.example.myrecipes.Models.Recipe;
import com.example.myrecipes.Models.User;
import com.example.myrecipes.Utilities.DataManager;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

public class RecipeActivity extends AppCompatActivity {
    private ShapeableImageView recipe_IMG_background;
    private MaterialTextView recipe_TXT_recipeName;
    private ShapeableImageView recipe_IMG_dishPhoto;
    private MaterialTextView recipe_TXT_dishDescription;
    private ShapeableImageView recipe_IMG_back;
    private ShapeableImageView recipe_IMG_addFavorite;
    private ShapeableImageView recipe_IMG_removeFavorite;
    private ShapeableImageView recipe_IMG_deleteRecipe;
    private String rid = "";
    private String name = "";
    private String description = "";
    private Uri image;
    private boolean isFavorite = false;
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

        findViews();

        Intent intent = getIntent();
        this.rid = intent.getStringExtra("rid");

        getRecipeFromManager(rid);
        getRecipeDetails(rid);

        this.cameFromAllRecipes = intent.getBooleanExtra("cameFromAllRecipes", false);
        this.cameFromFavorites = intent.getBooleanExtra("cameFromFavorites", false);

        Glide
                .with(this)
                .load(R.drawable.old_paper_background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(recipe_IMG_background);

        initViews();
    }

    private void getRecipeFromManager(String rid) {
        for (int i = 0; i < this.manager.getMyUser().getRecipes().size(); i++) {
            if(Objects.equals(this.manager.getMyUser().getRecipes().get(i).getRid(), rid))
                this.recipe = this.manager.getMyUser().getRecipes().get(i);
        }
    }

    private void getRecipeDetails(String rid) {
        this.name = this.recipe.getName();
        this.description = this.recipe.getDescription();
        this.image = this.recipe.getPhoto();
        this.isFavorite = this.recipe.isFavorite();
    }


    private void backClicked() {
        if(this.cameFromAllRecipes){
            Intent intent = new Intent(RecipeActivity.this, AllRecipesActivity.class);
            this.cameFromAllRecipes = false;
            startActivity(intent);
            this.finish();
        }
        else if(this.cameFromFavorites){
            Intent intent = new Intent(RecipeActivity.this, AllFavoritesActivity.class);
            this.cameFromFavorites = false;
            startActivity(intent);
            this.finish();
        }
    }


    private void addFavoriteClicked() {
        recipe_IMG_addFavorite.setVisibility(View.INVISIBLE);
        recipe_IMG_removeFavorite.setVisibility(View.VISIBLE);

        for (int i = 0; i < this.manager.getMyUser().getRecipes().size(); i++) {
            if (Objects.equals(this.manager.getMyUser().getRecipes().get(i).getRid(), rid)) {
                this.manager.getMyUser().getRecipes().get(i).setFavorite(true);
                this.manager.getMyUser().getFavorites().add(this.recipe);
            }
        }
    }


    private void removeFavoriteClicked() {
        recipe_IMG_addFavorite.setVisibility(View.VISIBLE);
        recipe_IMG_removeFavorite.setVisibility(View.INVISIBLE);

        for (int i = 0; i < this.manager.getMyUser().getFavorites().size(); i++) {
            if (Objects.equals(this.manager.getMyUser().getFavorites().get(i).getRid(), rid)) {
                this.manager.getMyUser().getFavorites().remove(i);
            }
        }

        for (int i = 0; i < this.manager.getMyUser().getRecipes().size(); i++) {
            if (Objects.equals(this.manager.getMyUser().getRecipes().get(i).getRid(), rid)) {
                this.manager.getMyUser().getRecipes().get(i).setFavorite(false);
            }
        }
    }


    private void deleteRecipeClicked() {

        deleteRecipeFromDataManager();

        Intent intent = new Intent(RecipeActivity.this, AllRecipesActivity.class);
        this.cameFromAllRecipes = false;
        startActivity(intent);
        this.finish();
    }

    private void deleteRecipeFromDataManager() {
        for (int i = 0; i < this.manager.getMyUser().getFavorites().size(); i++) {
            if (Objects.equals(this.manager.getMyUser().getFavorites().get(i).getRid(), rid)) {
                this.manager.getMyUser().getFavorites().remove(i);
            }
        }

        for (int i = 0; i < this.manager.getMyUser().getRecipes().size(); i++) {
            if (Objects.equals(this.manager.getMyUser().getRecipes().get(i).getRid(), rid)) {
                this.manager.getMyUser().getRecipes().remove(i);
            }
        }
    }


    private void initViews() {
        recipe_TXT_recipeName.setText(this.name);

        recipe_IMG_dishPhoto.setImageURI(this.image);

        recipe_TXT_dishDescription.setText(this.description);

        if(this.isFavorite){
            recipe_IMG_addFavorite.setVisibility(View.INVISIBLE);
            recipe_IMG_removeFavorite.setVisibility(View.VISIBLE);
        }
        else{
            recipe_IMG_addFavorite.setVisibility(View.VISIBLE);
            recipe_IMG_removeFavorite.setVisibility(View.INVISIBLE);
        }

        if(this.cameFromFavorites)
            recipe_IMG_deleteRecipe.setVisibility(View.INVISIBLE);

        recipe_IMG_removeFavorite.setOnClickListener(v -> removeFavoriteClicked());
        recipe_IMG_addFavorite.setOnClickListener(v -> addFavoriteClicked());
        recipe_IMG_back.setOnClickListener(v-> backClicked());
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
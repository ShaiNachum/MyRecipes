package com.example.myrecipes.UI_Controllers;

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
    private String name = "";
    private String description = "";
    private Uri photo;
    private boolean isFavorite = false;


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

        findViews();

        Glide
                .with(this)
                .load(R.drawable.old_paper_background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(recipe_IMG_background);

        initViews();
    }

    private void initViews() {
        recipe_TXT_recipeName.setText(this.name);

        recipe_IMG_dishPhoto.setImageURI(this.photo);

        recipe_TXT_dishDescription.setText(this.description);

        if(this.isFavorite){
            recipe_IMG_addFavorite.setVisibility(View.INVISIBLE);
            recipe_IMG_removeFavorite.setVisibility(View.VISIBLE);

            recipe_IMG_removeFavorite.setOnClickListener(v -> removeFavoriteClicked());
        }
        else{
            recipe_IMG_addFavorite.setVisibility(View.VISIBLE);
            recipe_IMG_removeFavorite.setVisibility(View.INVISIBLE);

            recipe_IMG_addFavorite.setOnClickListener(v -> addFavoriteClicked());
        }

        recipe_IMG_back.setOnClickListener(v-> backClicked());
    }

    private void backClicked() {
    }


    private void addFavoriteClicked() {
    }


    private void removeFavoriteClicked() {
    }



    private void findViews() {
        recipe_IMG_background = findViewById(R.id.recipe_IMG_background);
        recipe_TXT_recipeName = findViewById(R.id.recipe_TXT_recipeName);
        recipe_IMG_dishPhoto = findViewById(R.id.recipe_IMG_dishPhoto);
        recipe_TXT_dishDescription = findViewById(R.id.recipe_TXT_dishDescription);
        recipe_IMG_back = findViewById(R.id.recipe_IMG_back);
        recipe_IMG_addFavorite = findViewById(R.id.recipe_IMG_addFavorite);
        recipe_IMG_removeFavorite = findViewById(R.id.recipe_IMG_removeFavorite);
    }
}
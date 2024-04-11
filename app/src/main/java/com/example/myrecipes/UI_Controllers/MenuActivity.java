package com.example.myrecipes.UI_Controllers;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.myRecipes.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class MenuActivity extends AppCompatActivity {
    private ShapeableImageView menu_IMG_background;
    private MaterialTextView menu_LBL_addRecipe;
    private ShapeableImageView menu_IMG_addRecipe;
    private MaterialTextView menu_LBL_allRecipes;
    private ShapeableImageView menu_IMG_allRecipes;
    private MaterialTextView menu_LBL_favorites;
    private ShapeableImageView menu_IMG_favorites;
    private ShapeableImageView menu_IMG_logOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
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
                .into(menu_IMG_background);

        initViews();
    }


    private void signOutClicked() {
        logOut();
    }


    private void logOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MenuActivity.this, LogInActivity.class));
                        finish();
                    }
                });
    }


    private void addRecipeClicked() {
        Intent intent = new Intent(MenuActivity.this, AddRecipeActivity.class);
        startActivity(intent);
    }


    private void allRecipesClicked() {
        Intent intent = new Intent(MenuActivity.this, AllRecipesActivity.class);
        startActivity(intent);
        finish();
    }


    private void favoritesClicked() {
        Intent intent = new Intent(MenuActivity.this, AllFavoritesActivity.class);
        startActivity(intent);
        finish();
    }


    private void initViews() {
        menu_IMG_addRecipe.setOnClickListener(v -> addRecipeClicked());
        menu_IMG_allRecipes.setOnClickListener(v -> allRecipesClicked());
        menu_IMG_favorites.setOnClickListener(v -> favoritesClicked());
        menu_IMG_logOut.setOnClickListener(v -> signOutClicked());
    }


    private void findViews() {
        menu_IMG_background = findViewById(R.id.menu_IMG_background);

        menu_LBL_addRecipe = findViewById(R.id.menu_LBL_addRecipe);
        menu_IMG_addRecipe = findViewById(R.id.menu_IMG_addRecipe);

        menu_LBL_allRecipes = findViewById(R.id.menu_LBL_allRecipes);
        menu_IMG_allRecipes = findViewById(R.id.menu_IMG_allRecipes);

        menu_LBL_favorites = findViewById(R.id.menu_LBL_favorites);
        menu_IMG_favorites = findViewById(R.id.menu_IMG_favorites);

        menu_IMG_logOut = findViewById(R.id.menu_IMG_logOut);
    }
}
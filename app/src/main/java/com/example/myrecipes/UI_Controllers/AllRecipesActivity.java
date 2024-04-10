package com.example.myrecipes.UI_Controllers;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myRecipes.R;
import com.example.myrecipes.Adapters.RecipeAdapter;
import com.example.myrecipes.Utilities.DataManager;
import com.google.android.material.imageview.ShapeableImageView;

public class AllRecipesActivity extends AppCompatActivity {
    private ShapeableImageView allRecipes_IMG_background;
    private RecyclerView allRecipes_LST_recipes;
    private ShapeableImageView allRecipes_IMG_back;
    private DataManager manager;
    private RecipeAdapter recipeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_recipes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = DataManager.getInstance();

        findViews();

        Glide
                .with(this)
                .load(R.drawable.old_paper_background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(allRecipes_IMG_background);

        initViews();
    }


    private void backClicked() {
        Intent intent = new Intent(AllRecipesActivity.this, MenuActivity.class);
        startActivity(intent);
        this.finish();
    }


    private void initViews() {
        allRecipes_IMG_back.setOnClickListener(v -> backClicked());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        allRecipes_LST_recipes.setLayoutManager(linearLayoutManager);

        this.recipeAdapter = new RecipeAdapter(this, this.manager.getMyUser().getRecipes());

        allRecipes_LST_recipes.setAdapter(recipeAdapter);

        recipeAdapter.setRecipeCallback((recipe, position) -> {

            Intent intent = new Intent(AllRecipesActivity.this, RecipeActivity.class);
            intent.putExtra("rid", recipe.getRid());
            intent.putExtra("cameFromAllRecipes", true);
            startActivity(intent);
        });
    }


    private void findViews() {
        allRecipes_IMG_background = findViewById(R.id.allRecipes_IMG_background);
        allRecipes_LST_recipes = findViewById(R.id.allRecipes_LST_recipes);
        allRecipes_IMG_back = findViewById(R.id.allRecipes_IMG_back);
    }
}
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

import com.example.myRecipes.R;
import com.example.myrecipes.Adapters.RecipeAdapter;
import com.example.myrecipes.Interfaces.RecipeCallback;
import com.example.myrecipes.Models.Recipe;
import com.example.myrecipes.Models.User;
import com.google.android.material.imageview.ShapeableImageView;

public class AllRecipesActivity extends AppCompatActivity {
    private RecyclerView allRecipes_LST_recipes;
    private ShapeableImageView allRecipes_IMG_back;
    private User user = new User();


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

        findViews();

        initViews();
    }

    private void findViews() {
        allRecipes_LST_recipes = findViewById(R.id.allRecipes_LST_recipes);
        allRecipes_IMG_back = findViewById(R.id.allRecipes_IMG_back);
    }

    private void initViews() {
        allRecipes_IMG_back.setOnClickListener(v -> backClicked());

        RecipeAdapter recipeAdapter = new RecipeAdapter(getApplicationContext(), user.getRecipesArrayList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        allRecipes_LST_recipes.setLayoutManager(linearLayoutManager);
        allRecipes_LST_recipes.setAdapter(recipeAdapter);

        recipeAdapter.setRecipeCallback(new RecipeCallback() {
            @Override
            public void recipeClicked(Recipe recipe, int position) {
                //TODO: move to recipe intent
            }
        });

    }

    private void backClicked() {
        Intent intent = new Intent(AllRecipesActivity.this, LogInActivity.class);

        startActivity(intent);

        this.finish();
    }
}
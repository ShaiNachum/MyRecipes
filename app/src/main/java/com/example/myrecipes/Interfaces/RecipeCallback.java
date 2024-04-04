package com.example.myrecipes.Interfaces;

import com.example.myrecipes.Models.Recipe;

public interface RecipeCallback {
    void recipeClicked(Recipe recipe, int position);
}

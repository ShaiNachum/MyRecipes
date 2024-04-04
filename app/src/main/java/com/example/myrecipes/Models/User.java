package com.example.myrecipes.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class User {
    private String name;
    private String uid;
    private ArrayList<Recipe> recipesArrayList = new ArrayList<>();
    private ArrayList<Recipe> favoriteRecipesArrayList = new ArrayList<>();
    private HashMap<String, Recipe> allRecipes = new HashMap<>();
    private HashMap<String, Recipe> allFavorites = new HashMap<>();


    public User() {
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public ArrayList<Recipe> getRecipesArrayList() {
        return recipesArrayList;
    }

    public User setRecipesArrayList(ArrayList<Recipe> recipesArrayList) {
        this.recipesArrayList = recipesArrayList;
        return this;
    }

    public ArrayList<Recipe> getFavoriteRecipesArrayList() {
        return favoriteRecipesArrayList;
    }

    public User setFavoriteRecipesArrayList(ArrayList<Recipe> favoriteRecipesArrayList) {
        this.favoriteRecipesArrayList = favoriteRecipesArrayList;
        return this;
    }

    public HashMap<String, Recipe> getAllRecipes() {
        return allRecipes;
    }

    public User setAllRecipes(HashMap<String, Recipe> allRecipes) {
        this.allRecipes = allRecipes;
        return this;
    }

    public HashMap<String, Recipe> getAllFavorites() {
        return allFavorites;
    }

    public User setAllFavorites(HashMap<String, Recipe> allFavorites) {
        this.allFavorites = allFavorites;
        return this;
    }
}

package com.example.myrecipes.Models;

import java.util.ArrayList;
import java.util.Map;

public class User {
    private String uid;
    private Map<String , Recipe> recipesMap;
    private ArrayList<String> favoritesRecipesUidList;


    public User(){
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public Map<String, Recipe> getRecipesMap() {
        return recipesMap;
    }

    public User setRecipesMap(Map<String, Recipe> recipesMap) {
        this.recipesMap = recipesMap;
        return this;
    }

    public ArrayList<String> getFavoritesRecipesUidList() {
        return favoritesRecipesUidList;
    }

    public User setFavoritesRecipesUidList(ArrayList<String> favoritesRecipesUidList) {
        this.favoritesRecipesUidList = favoritesRecipesUidList;
        return this;
    }

    public ArrayList<Recipe> getAllRecipes(){
        return new ArrayList<Recipe>(this.recipesMap.values());
    }

    public ArrayList<Recipe> getFavoriteRecipes(){

        ArrayList<Recipe> favoriteRecipes = new ArrayList<Recipe>();

        for (int i = 0; i < this.favoritesRecipesUidList.size() ; i++) {
            favoriteRecipes.add(this.recipesMap.get(this.favoritesRecipesUidList.get(i)));
        }

        return favoriteRecipes;
    }
}

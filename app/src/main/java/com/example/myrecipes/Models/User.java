package com.example.myrecipes.Models;

import java.util.ArrayList;

public class User {
    private String uid;
    private ArrayList<Recipe> recipesArrayList = new ArrayList<>();
    private ArrayList<Recipe> favoriteRecipesArrayList = new ArrayList<>();

    public User(){
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

}

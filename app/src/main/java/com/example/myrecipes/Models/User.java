package com.example.myrecipes.Models;

import java.util.ArrayList;

public class User {
    private String uid;
    private ArrayList<Integer> myRecipesRids;
    private ArrayList <Recipe> recipes;
    private ArrayList <Recipe> favorites;


    public User(){
        this.recipes = new ArrayList<>();
        this.favorites = new ArrayList<>();
        this.myRecipesRids = new ArrayList<>();
    }


    public User(String uid){
        this.uid = uid;
        this.recipes = new ArrayList<>();
        this.favorites = new ArrayList<>();
        this.myRecipesRids = new ArrayList<>();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<Recipe> getFavorites() {
        return favorites;
    }


    public void setFavorites(ArrayList<Recipe> favorites) {
        this.favorites = favorites;
    }


    public void initFavorites(){
        for (int i = 0; i < this.recipes.size(); i++) {
            if(recipes.get(i).isFavorite())
                this.favorites.add(recipes.get(i));
        }
    }


    public ArrayList<Integer> getMyRecipesRids() {
        return myRecipesRids;
    }


    public void setMyRecipesRids(ArrayList<Integer> myRecipesRids) {
        this.myRecipesRids = myRecipesRids;
    }

    public void initMyRecipesRids() {
        for (int i = 0; i < this.recipes.size(); i++)
                this.myRecipesRids.add(recipes.get(i).getRid());
    }
}

package com.example.myrecipes.Models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String uid;
    private ArrayList <Recipe> recipes;
    private ArrayList <Recipe> favorites;


    public User(){
        this.recipes = new ArrayList<Recipe>();
        this.favorites = new ArrayList<Recipe>();
    }


    public User(String uid){
        this.uid = uid;
        this.recipes = new ArrayList<Recipe>();
        this.favorites = new ArrayList<Recipe>();

        //getUserDataFromDatabase(uid);
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







//    public void addFavorite(String rid) {
//        //this.recipesMap.get(rid).setFavorite(true);
//        this.favoritesRecipesRidList.add(rid);
//
//        //add to all_favorites
//        addToAllFavorites(rid);
//
//        //update the isFavorite value of the recipe in all_recipe;
//        updateIsFavoriteValueOfRecipeInAllRecipes(rid, true);
//    }
//
//
//
//    public void removeFavorite(String rid) {
//       // this.recipesMap.get(rid).setFavorite(false);
//
//        for (int i = 0; i < this.favoritesRecipesUidList.size(); i++) {
//            if(this.favoritesRecipesUidList.get(i) == rid)
//                this.favoritesRecipesUidList.remove(i);
//        }
//
//        //remove from all_favorites
//        removeFromAllFavorites(rid);
//
//        //update the isFavorite value of the recipe in all_recipe;
//        updateIsFavoriteValueOfRecipeInAllRecipes(rid, false);
//    }
//
//
//    private void updateIsFavoriteValueOfRecipeInAllRecipes(String rid, boolean b) {
//    }
//
//
//    private void addToAllFavorites(String rid) {
//    }
//
//
//    private void removeFromAllFavorites(String rid) {
//    }

}

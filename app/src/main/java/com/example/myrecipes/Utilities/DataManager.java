package com.example.myrecipes.Utilities;

import android.net.Uri;

import com.example.myrecipes.Models.Recipe;
import com.example.myrecipes.Models.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class DataManager {
    private static DataManager instance;
    private FirebaseUser user;
    private User myUser;

    private DataManager() {
    }

    public static DataManager getInstance() {
        if(instance == null)
            instance = new DataManager();

        return instance;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
        this.myUser = new User(this.user.getUid());
    }

    public User getMyUser() {
        return myUser;
    }

    public void loadBasicInformation(){
        this.myUser = new User();

        ArrayList <Recipe> recipes = new ArrayList<>();
        ArrayList <String> favorites = new ArrayList<>();

        Recipe recipe1 = new Recipe("1111", "pizza" , "advadvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5"));
        Recipe recipe2 = new Recipe("2222", "hamburger" , "advadvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5"));
        Recipe recipe3 = new Recipe("3333", "sushi" , "advadvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5"));
        Recipe recipe4 = new Recipe("4444", "pasta" , "advadvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5"));
        recipe4.setFavorite(true);
        Recipe recipe5 = new Recipe("5555", "shawarma" , "advadvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5"));
        recipe5.setFavorite(true);

        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);
        recipes.add(recipe4);
        recipes.add(recipe5);

        myUser.setRecipes(recipes);
        myUser.initFavorites();
    }


}

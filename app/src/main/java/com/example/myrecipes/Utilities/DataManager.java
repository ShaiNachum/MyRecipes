package com.example.myrecipes.Utilities;

import android.net.Uri;

import com.example.myrecipes.Models.Recipe;
import com.example.myrecipes.Models.User;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.checker.units.qual.A;

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

        Recipe recipe1 = new Recipe("1234", "pizza" , "advadvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5") , false);
        Recipe recipe2 = new Recipe("1234", "pizza" , "advadvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5") , false);
        Recipe recipe3 = new Recipe("1234", "pizza" , "advadvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5") , false);
        Recipe recipe4 = new Recipe("1234", "pizza" , "advadvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5") , true);
        Recipe recipe5 = new Recipe("1234", "pizza" , "advadvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5") , true);

        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);
        recipes.add(recipe4);
        recipes.add(recipe5);

        myUser.setRecipes(recipes);
        myUser.initFavorites();
    }


}

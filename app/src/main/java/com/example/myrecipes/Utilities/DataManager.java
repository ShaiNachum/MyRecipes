package com.example.myrecipes.Utilities;

import android.net.Uri;
import com.example.myrecipes.Models.Recipe;
import com.example.myrecipes.Models.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

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


    public void addFavorite(Recipe recipe, String rid) {
        for (int i = 0; i < this.myUser.getRecipes().size(); i++) {
            if (Objects.equals(this.myUser.getRecipes().get(i).getRid(), rid)) {
                this.myUser.getRecipes().get(i).setFavorite(true);
                this.myUser.getFavorites().add(recipe);
            }
        }
    }


    public void removeFavorite(String rid) {

        for (int i = 0; i < this.myUser.getFavorites().size(); i++) {
            if (Objects.equals(this.myUser.getFavorites().get(i).getRid(), rid)) {
                this.myUser.getFavorites().remove(i);
            }
        }

        for (int i = 0; i < this.myUser.getRecipes().size(); i++) {
            if (Objects.equals(this.myUser.getRecipes().get(i).getRid(), rid)) {
                this.myUser.getRecipes().get(i).setFavorite(false);
            }
        }
    }


    public void deleteRecipe(String rid) {
        for (int i = 0; i < this.myUser.getFavorites().size(); i++) {
            if (Objects.equals(this.myUser.getFavorites().get(i).getRid(), rid)) {
                this.myUser.getFavorites().remove(i);
            }
        }

        for (int i = 0; i < this.myUser.getRecipes().size(); i++) {
            if (Objects.equals(this.myUser.getRecipes().get(i).getRid(), rid)) {
                this.myUser.getRecipes().remove(i);
            }
        }
    }


    // init user's data from firebase when when finish the authentication.
    // if the user exist, get his data.
    // If not create new user in firebase.
    public void initUserDataFromFirebase(){
    }


    // update the the user's all recipes list with the new recipe added.
    public void updateAllRecipesOnFirebase(){
    }


    // update the user's all recipes and all favorite lists when deleting a recipe.
    public void deleteRecipeFromFirebase(){
    }


    // update the user's data: 1. The favorite status of specific recipe in all recipes list.
    //                         2. Add the recipe to all Favorites list.
    public void addRecipeToAllFavoritesOnFirebase(){
    }


    // update the user's data: 1. The favorite status of specific recipe in all recipes list.
    //                         2. Remove the recipe from all Favorites list.
    public void removeRecipeToAllFavoritesOnFirebase(){
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

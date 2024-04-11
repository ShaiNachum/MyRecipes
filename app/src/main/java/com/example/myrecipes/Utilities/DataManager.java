package com.example.myrecipes.Utilities;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myrecipes.Models.Recipe;
import com.example.myrecipes.Models.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class DataManager {
    private static DataManager instance;
    private FirebaseUser user;
    private User myUser;
    private DatabaseReference ref;
    private FirebaseDatabase firebaseRTDatabase;


    private DataManager() {
    }


    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();

        return instance;
    }


    public FirebaseUser getUser() {
        return user;
    }


    public void setUser(FirebaseUser user) {
        this.user = user;
        this.myUser = new User(this.user.getUid());
        this.firebaseRTDatabase = FirebaseDatabase.getInstance();
        this.ref = firebaseRTDatabase.getReference(this.myUser.getUid() + "");
    }


    public User getMyUser() {
        return myUser;
    }


    public void addFavorite(Recipe recipe, int rid) {
        for (int i = 0; i < this.myUser.getRecipes().size(); i++) {
            if (this.myUser.getRecipes().get(i).getRid() == rid) {
                this.myUser.getRecipes().get(i).setFavorite(true);
                this.myUser.getFavorites().add(recipe);
            }
        }

        addRecipeToAllFavoritesOnFirebase(recipe);
    }


    public void removeFavorite(int rid) {
        for (int i = 0; i < this.myUser.getFavorites().size(); i++) {
            if (this.myUser.getFavorites().get(i).getRid() == rid) {
                this.myUser.getFavorites().remove(i);
            }
        }

        for (int i = 0; i < this.myUser.getRecipes().size(); i++) {
            if (this.myUser.getRecipes().get(i).getRid() == rid) {
                this.myUser.getRecipes().get(i).setFavorite(false);
            }
        }

        removeRecipeFromAllFavoritesOnFirebase(getRecipeById(rid), false);
    }


    public void deleteRecipe(int rid) {
        deleteRecipeFromFirebase(getRecipeById(rid));

        for (int i = 0; i < this.myUser.getFavorites().size(); i++) {
            if (this.myUser.getFavorites().get(i).getRid() == rid) {
                this.myUser.getFavorites().remove(i);
            }
        }

        for (int i = 0; i < this.myUser.getRecipes().size(); i++) {
            if (this.myUser.getRecipes().get(i).getRid() == rid) {
                this.myUser.getRecipes().remove(i);
            }
        }

        for (int i = 0; i < this.myUser.getMyRecipesRids().size(); i++) {
            if (this.myUser.getMyRecipesRids().get(i) == rid)
                this.myUser.getMyRecipesRids().remove(i);
        }
    }


    public void addRecipe(Recipe recipe) {
        this.myUser.getRecipes().add(recipe);

        addRecipeToAllRecipesOnFirebase(recipe);
    }

    public DatabaseReference getRef() {
        return ref;
    }


    // init user's data from firebase when when finish the authentication.
    // if the user exist, get his data.
    // If not create new user in firebase.
    public void initUserDataFromFirebase() {
        DatabaseReference myRef = firebaseRTDatabase.getReference(getUser().getUid()).child("all_recipes");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterate through each recipe ID
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {

                    DataSnapshot recipeSnapshot = iterator.next();
                    int recipeId = Integer.parseInt(recipeSnapshot.getKey());
                    // Retrieve the data for that recipe
                    String name = recipeSnapshot.child("name").getValue(String.class);
                    String description = recipeSnapshot.child("description").getValue(String.class);
                    String uri = recipeSnapshot.child("uri").getValue(String.class);
                    boolean isFavorite = recipeSnapshot.child("isFavorite").getValue(Boolean.class);
                    DatabaseReference recipeRef = myRef.child(recipeId + "");

                    Recipe recipe = new Recipe();
                    recipe.setName(name).setDescription(description).setFavorite(isFavorite).setRid(recipeId);
                    if(uri != null)
                        recipe.setPhoto(Uri.parse(uri));
                    else
                        recipe.setPhoto(null);

                    myUser.getRecipes().add(recipe);
                }
                myUser.initFavorites();
                myUser.initMyRecipesRids();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Log.w("RecipeData", "Failed to read value.", databaseError.toException());
            }
        });
    }


    //Add recipe to all Recipes list.
    private void addRecipeToAllRecipesOnFirebase(Recipe recipe) {
        this.ref.child("all_recipes").child("" + recipe.getRid()).child("id").setValue(recipe.getRid());
        if (recipe.getPhoto() != null)
            this.ref.child("all_recipes").child("" + recipe.getRid()).child("uri").setValue(recipe.getPhoto().toString());
        this.ref.child("all_recipes").child("" + recipe.getRid()).child("isFavorite").setValue(recipe.isFavorite());
        this.ref.child("all_recipes").child("" + recipe.getRid()).child("name").setValue(recipe.getName());
        this.ref.child("all_recipes").child("" + recipe.getRid()).child("description").setValue(recipe.getDescription());
    }


    // update the user's data: 1. The favorite status of specific recipe in all recipes list.
    //                         2. Add the recipe to all Favorites list.
    public void addRecipeToAllFavoritesOnFirebase(Recipe recipe) {
        this.ref.child("all_recipes").child("" + recipe.getRid()).child("isFavorite").setValue(recipe.isFavorite());

        this.ref.child("all_favorites").child("" + recipe.getRid()).child("id").setValue(recipe.getRid());
        if (recipe.getPhoto() != null)
            this.ref.child("all_favorites").child("" + recipe.getRid()).child("uri").setValue(recipe.getPhoto().toString());
        this.ref.child("all_favorites").child("" + recipe.getRid()).child("isFavorite").setValue(recipe.isFavorite());
        this.ref.child("all_favorites").child("" + recipe.getRid()).child("name").setValue(recipe.getName());
        this.ref.child("all_favorites").child("" + recipe.getRid()).child("description").setValue(recipe.getDescription());
    }


    // update the user's all recipes and all favorite lists when deleting a recipe.
    public void deleteRecipeFromFirebase(Recipe recipe) {
        this.ref.child("all_recipes").child("" + recipe.getRid()).removeValue();

        removeRecipeFromAllFavoritesOnFirebase(recipe, true);
    }


    // update the user's data: 1. The favorite status of specific recipe in all recipes list.
    //                         2. Remove the recipe from all Favorites list.
    public void removeRecipeFromAllFavoritesOnFirebase(Recipe recipe, Boolean recipeDeleted) {
        this.ref.child("all_favorites").child("" + recipe.getRid()).removeValue();

        if(!recipeDeleted)
            this.ref.child("all_recipes").child("" + recipe.getRid()).child("isFavorite").setValue(recipe.isFavorite());
    }


    public Recipe getRecipeById(int rid) {
        for (int i = 0; i < this.getMyUser().getRecipes().size(); i++) {
            if (this.getMyUser().getRecipes().get(i).getRid() == rid)
                return this.getMyUser().getRecipes().get(i);
        }
        return null;
    }


    public int generateID() {
        int id = 555;

        while (myUser.getMyRecipesRids().contains(id))
            id++;

        myUser.getMyRecipesRids().add(id);

        return id;
    }


//    public void loadBasicInformation() {
//        myUser.getRecipes();
//
//        ArrayList<Recipe> recipes = new ArrayList<>();
//
//        Recipe recipe1 = new Recipe("pizza", "advadvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5"));
//        Recipe recipe2 = new Recipe("hamburger", "advaavrsvsrsvrsrdvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5"));
//        Recipe recipe3 = new Recipe("sushi", "advadvacdzcdzcd", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5"));
//        Recipe recipe4 = new Recipe("pasta", "advadzczdvfzvzfvzfvzfvzfvdvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5"));
//        recipe4.setFavorite(true);
//        Recipe recipe5 = new Recipe("shawarma", "advadzfvzfvzfvzfbzbzfvzdvdvzdvzdvzdvzdvzdvzdvzdvzdvzdvvad", Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/eliya.jpg?alt=media&token=9625f48e-5a77-47da-84f1-6130fe6658d5"));
//        recipe5.setFavorite(true);
//
//        recipes.add(recipe1);
//        addRecipeToAllRecipesOnFirebase(recipe1);
//        recipes.add(recipe2);
//        addRecipeToAllRecipesOnFirebase(recipe2);
//        recipes.add(recipe3);
//        addRecipeToAllRecipesOnFirebase(recipe3);
//        recipes.add(recipe4);
//        addRecipeToAllRecipesOnFirebase(recipe4);
//        recipes.add(recipe5);
//        addRecipeToAllRecipesOnFirebase(recipe5);
//
//        myUser.setRecipes(recipes);
//        myUser.initFavorites();
//    }
}

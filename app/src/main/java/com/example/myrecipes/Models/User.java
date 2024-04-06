package com.example.myrecipes.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class User implements Parcelable {
    private String name;
    private String uid;
    private ArrayList<Recipe> recipesArrayList = new ArrayList<>();
    private ArrayList<Recipe> favoriteRecipesArrayList = new ArrayList<>();

    public User(){

    }

    protected User(Parcel in) {
        name = in.readString();
        uid = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(uid);
    }
}

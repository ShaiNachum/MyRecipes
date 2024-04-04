package com.example.myrecipes.Models;

import android.net.Uri;

public class Recipe {
    private String name = "";
    private String description = "";
    private Uri photo;
    private boolean isFavorite = false;

    public Recipe(){
    }

    public String getName() {
        return name;
    }

    public Recipe setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Recipe setDescription(String description) {
        this.description = description;
        return this;
    }

    public Uri getPhoto() {
        return photo;
    }

    public Recipe setPhoto(Uri photo) {
        this.photo = photo;
        return this;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public Recipe setFavorite(boolean favorite) {
        isFavorite = favorite;
        return this;
    }
}

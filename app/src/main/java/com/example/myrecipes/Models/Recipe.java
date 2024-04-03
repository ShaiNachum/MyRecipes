package com.example.myrecipes.Models;

import android.net.Uri;

public class Recipe {
    private String name;
    private String description;
    private Uri photo;

    public Recipe(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }
}

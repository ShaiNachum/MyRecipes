package com.example.myrecipes.Models;

import android.net.Uri;

import com.example.myrecipes.Utilities.DataManager;


public class Recipe {
    private int rid;
    private String name = "";
    private String description = "";
    private Uri photo;
    private boolean isFavorite = false;
    private DataManager manager = DataManager.getInstance();


    public Recipe() {
    }


    public Recipe(String name, String description, Uri photo) {
        this.rid = manager.generateID();
        this.name = name;
        this.description = description;
        this.photo = photo;
    }


    public Recipe(int rid, String name, String description, Uri photo, Boolean isFavorite) {
        this.rid = rid;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.isFavorite = isFavorite;
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


    public int getRid() {
        return rid;
    }


    public Recipe setRid(int rid) {
        this.rid = rid;
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

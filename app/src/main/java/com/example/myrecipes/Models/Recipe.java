package com.example.myrecipes.Models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Recipe implements Parcelable {
    private String name = "";
    private String description = "";
    private Uri photo;
    private boolean isFavorite = false;

    public Recipe(){
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        description = in.readString();
        photo = in.readParcelable(Uri.class.getClassLoader());
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeParcelable(photo, flags);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}

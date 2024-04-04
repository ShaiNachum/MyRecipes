package com.example.myrecipes.Utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myRecipes.R;

public class ImageLoader {
    private static ImageLoader instance;
    private static Context appContext;

    private ImageLoader(Context context) {
        appContext = context;
    }

    public static ImageLoader getInstance() {
        return instance;
    }

    public static ImageLoader initImageLoader(Context context) {
        if (instance == null)
            instance = new ImageLoader(context);
        return instance;
    }

    public void load (String link, ImageView imageView){
        Glide.
                with(appContext)
                .load(link)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
    }

    public void load (Drawable drawable, ImageView imageView){
        Glide.
                with(appContext)
                .load(drawable)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
    }
}

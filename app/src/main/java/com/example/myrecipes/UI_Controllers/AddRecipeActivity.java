package com.example.myrecipes.UI_Controllers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.myRecipes.R;
import com.example.myrecipes.Models.Recipe;
import com.example.myrecipes.Utilities.DataManager;
import com.google.android.material.imageview.ShapeableImageView;


public class AddRecipeActivity extends AppCompatActivity {
    private static final int SMALL_VIBRATE = 50;
    private ShapeableImageView addRecipe_IMG_background;
    private ShapeableImageView addRecipe_IMG_dishPhoto;
    private ShapeableImageView addRecipe_IMG_addPhoto;
    private ShapeableImageView addRecipe_IMG_save;
    private ShapeableImageView addRecipe_IMG_cancel;
    private EditText addRecipe_TXT_dishName;
    private EditText addRecipe_TXT_dishDescription;
    private String dishName;
    private String dishDescription;
    private Uri imageUri;
    private DataManager manager;
    //private ActivityResultLauncher<Intent> resultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_recipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = DataManager.getInstance();

        findViews();

        Glide
                .with(this)
                .load(R.drawable.old_paper_background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(addRecipe_IMG_background);

        initViews();
    }



    private void addPhotoClicked() {

    }


    private void cancelClicked() {
        Intent intent = new Intent(AddRecipeActivity.this, MenuActivity.class);
        startActivity(intent);
        this.finish();
    }


    private void saveClicked() {
        this.dishName = addRecipe_TXT_dishName.getText().toString();
        this.dishDescription = addRecipe_TXT_dishDescription.getText().toString();

        Recipe recipe = new Recipe(this.dishName, this.dishDescription, this.imageUri);

        this.manager.addRecipe(recipe);

        Intent intent = new Intent(AddRecipeActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }


    private void initViews() {
        addRecipe_IMG_addPhoto.setOnClickListener(v -> addPhotoClicked());

        addRecipe_IMG_save.setOnClickListener(v -> saveClicked());
        addRecipe_IMG_cancel.setOnClickListener((v -> cancelClicked()));
    }


    private void findViews() {
        addRecipe_IMG_background = findViewById(R.id.addRecipe_IMG_background);

        addRecipe_IMG_dishPhoto = findViewById(R.id.addRecipe_IMG_dishPhoto);
        addRecipe_IMG_addPhoto = findViewById(R.id.addRecipe_IMG_addPhoto);

        addRecipe_TXT_dishName = findViewById(R.id.addRecipe_TXT_dishName);
        addRecipe_TXT_dishDescription = findViewById(R.id.addRecipe_TXT_dishDescription);

        addRecipe_IMG_save = findViewById(R.id.addRecipe_IMG_save);
        addRecipe_IMG_cancel = findViewById(R.id.addRecipe_IMG_cancel);
    }
}
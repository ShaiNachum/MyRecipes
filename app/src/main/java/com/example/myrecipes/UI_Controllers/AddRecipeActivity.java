package com.example.myrecipes.UI_Controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresExtension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myRecipes.R;
import com.example.myrecipes.Models.Recipe;
import com.example.myrecipes.Utilities.SignalManager;
import com.google.android.material.imageview.ShapeableImageView;

public class AddRecipeActivity extends AppCompatActivity {
    private static final int SMALL_VIBRATE = 50;
    private ShapeableImageView addRecipe_IMG_dishPhoto;
    private ShapeableImageView addRecipe_IMG_addPhoto;
    private ShapeableImageView addRecipe_IMG_save;
    private EditText addRecipe_IMG_dishName;
    private EditText addRecipe_IMG_dishDescription;
    private String dishName;
    private String dishDescription;
    private Uri imageUri;
    private Recipe recipe;


    ActivityResultLauncher<Intent> resultLauncher;

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

        findViews();

        initViews();

        registerResult();

    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try{
                            imageUri = result.getData().getData();
                            addRecipe_IMG_dishPhoto.setImageURI(imageUri);
                        }catch (Exception e){
                            SignalManager.getInstance().toast("No Image Selected");
                            SignalManager.getInstance().vibrate(SMALL_VIBRATE);
                        }
                    }
                });
    }


    private void initViews() {
        addRecipe_IMG_addPhoto.setOnClickListener(v -> addPhotoClicked());
        addRecipe_IMG_save.setOnClickListener(v -> saveClicked());
    }

    private void findViews() {
        addRecipe_IMG_dishPhoto = findViewById(R.id.addRecipe_IMG_dishPhoto);
        addRecipe_IMG_addPhoto = findViewById(R.id.addRecipe_IMG_addPhoto);

        addRecipe_IMG_dishName = findViewById(R.id.addRecipe_IMG_dishName);
        addRecipe_IMG_dishDescription = findViewById(R.id.addRecipe_IMG_dishDescription);

        addRecipe_IMG_save = findViewById(R.id.addRecipe_IMG_save);
    }


    private void addPhotoClicked() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void saveClicked(){
        this.dishName = addRecipe_IMG_dishName.getText().toString();
        this.dishDescription = addRecipe_IMG_dishDescription.getText().toString();

        recipe.setName(this.dishName);
        recipe.setDescription(this.dishDescription);
        recipe.setPhoto(this.imageUri);

        //TODO: add to allRecipes list
        //TODO: go back to login
    }
}
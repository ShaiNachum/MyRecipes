package com.example.myrecipes.UI_Controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresExtension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.myRecipes.R;
import com.example.myrecipes.Models.Recipe;
import com.example.myrecipes.Utilities.SignalManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AddRecipeActivity extends AppCompatActivity {
    private static final int SMALL_VIBRATE = 50;
    private ShapeableImageView addRecipe_IMG_background;
    private ShapeableImageView addRecipe_IMG_dishPhoto;
    private ShapeableImageView addRecipe_IMG_addPhoto;
    private ShapeableImageView addRecipe_IMG_save;
    private EditText addRecipe_TXT_dishName;
    private EditText addRecipe_TXT_dishDescription;
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

        Glide
                .with(this)
                .load(R.drawable.old_paper_background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(addRecipe_IMG_background);

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
        addRecipe_IMG_background = findViewById(R.id.addRecipe_IMG_background);

        addRecipe_IMG_dishPhoto = findViewById(R.id.addRecipe_IMG_dishPhoto);
        addRecipe_IMG_addPhoto = findViewById(R.id.addRecipe_IMG_addPhoto);

        addRecipe_TXT_dishName = findViewById(R.id.addRecipe_TXT_dishName);
        addRecipe_TXT_dishDescription = findViewById(R.id.addRecipe_TXT_dishDescription);

        addRecipe_IMG_save = findViewById(R.id.addRecipe_IMG_save);
    }


    private void addPhotoClicked() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void saveClicked(){
        this.dishName = addRecipe_TXT_dishName.getText().toString();
        this.dishDescription = addRecipe_TXT_dishDescription.getText().toString();

        this.recipe.setName(this.dishName)
                .setDescription(this.dishDescription)
                .setPhoto(this.imageUri);

        recipe.setName(this.dishName);
        recipe.setDescription(this.dishDescription);
        recipe.setPhoto(this.imageUri);




        //TODO: add to allRecipes list

        Intent intent = new Intent(AddRecipeActivity.this, LogInActivity.class);
        startActivity(intent);
        this.finish();
    }
}
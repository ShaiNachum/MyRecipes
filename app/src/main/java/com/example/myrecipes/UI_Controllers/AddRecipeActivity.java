package com.example.myrecipes.UI_Controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.myRecipes.R;
import com.example.myrecipes.Models.Recipe;
import com.example.myrecipes.Utilities.DataManager;
import com.example.myrecipes.Utilities.SignalManager;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;


public class AddRecipeActivity extends AppCompatActivity {
    private ShapeableImageView addRecipe_IMG_background;
    private ShapeableImageView addRecipe_IMG_dishPhoto;
    private LinearProgressIndicator addRecipe_PI_uploadIndicator;
    private ShapeableImageView addRecipe_IMG_addPhoto;
    private ShapeableImageView addRecipe_IMG_save;
    private ShapeableImageView addRecipe_IMG_cancel;
    private EditText addRecipe_TXT_dishName;
    private EditText addRecipe_TXT_dishDescription;
    private String dishName;
    private String dishDescription;
    private Uri imageUri;
    private DataManager manager;
    private StorageReference storageReference;
    private Uri firebaseImage;
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            imageUri = result.getData().getData();
                            Glide.with(getApplicationContext())
                                    .load(imageUri)
                                    .into(addRecipe_IMG_dishPhoto);
                            SignalManager.getInstance().toast("Image selected successfully");
                            uploadImage(imageUri);
                        } else {
                            SignalManager.getInstance().toast("No image selected");
                        }
                    }
                }
            });


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

        FirebaseApp.initializeApp(AddRecipeActivity.this);
        storageReference = FirebaseStorage.getInstance().getReference();

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
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }


    private void uploadImage(Uri image) {
        StorageReference reference = storageReference.child(UUID.randomUUID().toString());
        reference.putFile(image)
                .addOnSuccessListener(taskSnapshot -> {
                    reference.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                firebaseImage = uri;
                                addRecipe_IMG_save.setVisibility(View.VISIBLE);
                                SignalManager.getInstance().toast("Image uploaded successfully");
                            })
                            .addOnFailureListener(e -> {
                                SignalManager.getInstance().toast("Fail to get the download url");
                            });
                })
                .addOnFailureListener(e -> {
                    SignalManager.getInstance().toast("Fail to upload image");
                })
                .addOnProgressListener(snapshot -> {
                    addRecipe_IMG_save.setVisibility(View.INVISIBLE);
                    addRecipe_PI_uploadIndicator.setMax(Math.toIntExact(snapshot.getTotalByteCount()));
                    addRecipe_PI_uploadIndicator.setProgress(Math.toIntExact(snapshot.getBytesTransferred()));
                });
    }


    private void cancelClicked() {
        Intent intent = new Intent(AddRecipeActivity.this, MenuActivity.class);
        startActivity(intent);
        this.finish();
    }


    private void saveClicked() {
        this.dishName = addRecipe_TXT_dishName.getText().toString();
        this.dishDescription = addRecipe_TXT_dishDescription.getText().toString();

        Recipe recipe;

        if (this.firebaseImage == null)
            recipe = new Recipe(this.dishName, this.dishDescription, this.imageUri);
        else
            recipe = new Recipe(this.dishName, this.dishDescription, this.firebaseImage);

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
        addRecipe_PI_uploadIndicator = findViewById(R.id.addRecipe_PI_uploadIndicator);
        addRecipe_IMG_addPhoto = findViewById(R.id.addRecipe_IMG_addPhoto);

        addRecipe_TXT_dishName = findViewById(R.id.addRecipe_TXT_dishName);
        addRecipe_TXT_dishDescription = findViewById(R.id.addRecipe_TXT_dishDescription);

        addRecipe_IMG_save = findViewById(R.id.addRecipe_IMG_save);
        addRecipe_IMG_cancel = findViewById(R.id.addRecipe_IMG_cancel);
    }
}

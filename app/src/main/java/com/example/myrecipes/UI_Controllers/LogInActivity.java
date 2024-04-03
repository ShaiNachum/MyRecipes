package com.example.myrecipes.UI_Controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.myRecipes.R;
import com.example.myrecipes.Utilities.SignalManager;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LogInActivity extends AppCompatActivity {
    private MaterialTextView login_LBL_addRecipe;
    private ShapeableImageView login_IMG_addRecipe;
    private MaterialTextView login_LBL_allRecipes;
    private ShapeableImageView login_IMG_allRecipes;
    private MaterialTextView login_LBL_favorites;
    private ShapeableImageView login_IMG_favorites;
    private ShapeableImageView login_IMG_logOut;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();

        initViews();

        checkLogIn();

    }


    private void checkLogIn(){
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if(user == null)
            login();
        else{
            String uid = user.getUid();
            String phone = user.getPhoneNumber();
            String name = user.getDisplayName();
            String email = user.getEmail();
            String pid = user.getProviderId();
            String tid = user.getTenantId();
            Uri photo  = user.getPhotoUrl();
            int x = 0;
        }
    }


    private void signOutClicked(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        login();
    }


    private void findViews() {
        login_LBL_addRecipe = findViewById(R.id.login_LBL_addRecipe);
        login_IMG_addRecipe = findViewById(R.id.login_IMG_addRecipe);

        login_LBL_allRecipes = findViewById(R.id.login_LBL_allRecipes);
        login_IMG_allRecipes = findViewById(R.id.login_IMG_allRecipes);

        login_LBL_favorites = findViewById(R.id.login_LBL_favorites);
        login_IMG_favorites = findViewById(R.id.login_IMG_favorites);

        login_IMG_logOut = findViewById(R.id.login_IMG_logOut);
    }


    private void initViews() {
        login_IMG_addRecipe.setOnClickListener(v -> addRecipeClicked());
        login_IMG_allRecipes.setOnClickListener(v -> allRecipesClicked());
        login_IMG_favorites.setOnClickListener(v -> favoritesClicked());
        login_IMG_logOut.setOnClickListener(v -> signOutClicked());
    }


    private void favoritesClicked() {

    }


    private void allRecipesClicked() {


    }


    private void addRecipeClicked() {
        Intent intent = new Intent(LogInActivity.this, AddRecipeActivity.class);
        startActivity(intent);
    }

    
    private void login() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
                );

// Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_logo)
                .build();
        signInLauncher.launch(signInIntent);
    }


    // See: https://developer.android.com/training/basics/intents/result
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );


    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }


}
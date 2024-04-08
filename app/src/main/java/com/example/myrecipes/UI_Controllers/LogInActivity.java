package com.example.myrecipes.UI_Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.myRecipes.R;
import com.example.myrecipes.Models.Recipe;
import com.example.myrecipes.Models.User;
import com.example.myrecipes.Utilities.DataManager;
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
    private ShapeableImageView login_IMG_background;
    private FirebaseAuth auth;
    private String uid;

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

        login_IMG_background = findViewById(R.id.login_IMG_background);

        Glide
                .with(this)
                .load(R.drawable.old_paper_background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(login_IMG_background);

        login();
    }


    private void login() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_logo)
                .setIsSmartLockEnabled(false)
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
            DataManager manager = DataManager.getInstance();
            manager.loadBasicInformation();
            //manager.setUser(user);




//            manager.setUid(user);
//            manager.InitGeneralData();
//            manager.getFbmanager().LoadUserFromFBRTDB(user); //FIREBASE REALTIME DATABASE
//            fbManager.LoadUserFromFBRTDB(user); //FIREBASE REALTIME DATABASE
            int x = 10;
            Intent intent = new Intent(LogInActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.d("LogInError", "Sign in failed");
        }
    }

}


/*
TODO:
לשלוח את המתכון כאובייקט ולא את השדות שלו

לשאול לגבי החזרה מכל אינטנט, מה לכבות ומה לא

בעת לחיצה על מתכון בריסייקלר ויו נפתח אקטיביטי מתכון וכשאני לוחץ חזור אני חוזר לאקטיביטי של הרשימת מתכונים או לאקטיביטי של רשימת המועדפים, בהתאם למה שהייתי קודם

כשאני לוחץ על הוספה למועדפים של מתכון אני מוסיף אותו לרשימת המועדפים וכשאני לוחץ על הסרה הוא מוסר מהרשימה

כשאני מסיים ליצור מתכון להוסיף אותו לרשימת המתכונים של אותו משתמש

*/
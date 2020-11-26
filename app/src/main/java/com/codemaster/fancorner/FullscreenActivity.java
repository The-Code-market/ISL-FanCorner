package com.codemaster.fancorner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class FullscreenActivity extends AppCompatActivity {

    ImageView logoSplashScreen;
    TextView welcomeText;
    Animation zoomAnimation, fadeAnimation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        logoSplashScreen = findViewById(R.id.logo);
        welcomeText = findViewById(R.id.welcome_text);

        zoomAnimation = AnimationUtils.loadAnimation(FullscreenActivity.this, R.anim.zoom);
        logoSplashScreen.setAnimation(zoomAnimation);

        fadeAnimation = AnimationUtils.loadAnimation(FullscreenActivity.this, R.anim.fade);
        welcomeText.setAnimation(fadeAnimation);

        new Handler().postDelayed(() -> {
            Intent verifyIntent = new Intent(FullscreenActivity.this, SignInScreen.class);
            startActivity(verifyIntent);
            finish();
        }, 1500);
    }
}
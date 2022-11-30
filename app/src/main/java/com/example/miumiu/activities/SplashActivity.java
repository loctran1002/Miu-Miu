package com.example.miumiu.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.example.miumiu.R;
import com.example.miumiu.activities.MainActivity;
import com.example.miumiu.utils.Constants;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo);
        AlphaAnimation fade = new AlphaAnimation(1.0f, 0.75f);
        fade.setDuration(4000);
        logo.startAnimation(fade);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn())
                {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
//                    CurrentSession.Init();
                    finish();
                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, Constants.SPLASH_TIME_OUT);
    }

    private boolean isLoggedIn()
    {
//        FirebaseUser user = null;
//        try
//        {
//            user = FirebaseAuth.getInstance().getCurrentUser();
//        }
//        catch (Exception e)
//        {
//        }
//        if (user != null)
//        {
//            return true;
//        }
        return false;
//        return true;
    }
}
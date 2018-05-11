package com.plexosysconsult.homemart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

                goToMainActivity();


            }
        }, 3000);


    }

    private void goToMainActivity() {

        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        i.putExtra("beginning", 1);
        startActivity(i);
        finish();
    }

}

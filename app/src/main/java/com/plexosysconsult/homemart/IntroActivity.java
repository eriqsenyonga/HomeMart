package com.plexosysconsult.homemart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by senyer on 9/11/2016.
 */
public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("WELCOME", "HELLO FRESH UGANDA", R.drawable.fruit_veg, Color.BLUE));
        addSlide(AppIntroFragment.newInstance("WELCOME", "HELLO FRESH", R.drawable.bg_two, Color.GREEN));
        addSlide(AppIntroFragment.newInstance("WELCOME", "HELLO FRESH", R.drawable.texture_bg2, Color.RED));
        addSlide(AppIntroFragment.newInstance("WELCOME", "HELLO FRESH", R.drawable.apple, Color.YELLOW));




    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.

       goToMainActivity();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.

        goToMainActivity();
    }

    private void goToMainActivity() {

        Intent i = new Intent(IntroActivity.this, MainActivity.class);
        i.putExtra("beginning", 1);
        startActivity(i);
        finish();
    }

}

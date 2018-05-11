package com.plexosysconsult.homemart;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.w3c.dom.Text;

public class RecipeDetails extends AppCompatActivity {


    CoordinatorLayout mCoordinatorLayout;
    AppBarLayout mAppBarLayout;
    int heightPx;
    Intent receivedIntent;
    Bundle recipeBundle;
    TextView tvRecipeDetails;
    ImageView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvRecipeDetails = (TextView) findViewById(R.id.tv_recipe_body);
        header = (ImageView) findViewById(R.id.header);

        receivedIntent = getIntent();
        recipeBundle = receivedIntent.getBundleExtra("recipe_details");
        getSupportActionBar().setTitle(recipeBundle.getString("title"));

        setupCollapsingToolbar();

        tvRecipeDetails.setText(Html.fromHtml(recipeBundle.getString("body")));

        Glide
                .with(this)
                .load(recipeBundle.getString("imageUrl"))
                .centerCrop()
                //      .placeholder(R.drawable.placeholder_veggie)
                .crossFade()

                .into(header);

    }

    private void setupCollapsingToolbar() {

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);


        mAppBarLayout.post(new Runnable() {
            @Override
            public void run() {
                heightPx = findViewById(R.id.app_bar_layout).getHeight();



                setAppBarOffset((heightPx * 3) / 5);


            }
        });


        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);

        collapsingToolbar.setTitleEnabled(false);
    }

    private void setAppBarOffset(int offsetPx) {

      //  Log.d("offsetPx", "" + offsetPx);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedPreScroll(mCoordinatorLayout, mAppBarLayout, null, 0, heightPx - offsetPx, new int[]{0, 0});

    }
}

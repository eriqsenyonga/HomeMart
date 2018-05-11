package com.plexosysconsult.homemart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    Toolbar toolbar;
    FragmentManager fm;
    public FloatingActionButton fab;
    View navigationHeader;
    CircleImageView ivClientProfilePic;
    TextView tvClientName;
    TextView tvClientEmail;
    DrawerLayout drawer;
    ImageView redDot;
    MyApplicationClass myApplicationClass = MyApplicationClass.getInstance();
    SharedPreferences mPositionSavedPrefs;
    SharedPreferences.Editor posSavedEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationHeader = navigationView.getHeaderView(0);
        ivClientProfilePic = (CircleImageView) navigationHeader.findViewById(R.id.iv_client_profile_pic);
        tvClientName = (TextView) navigationHeader.findViewById(R.id.tv_display_name);
        tvClientEmail = (TextView) navigationHeader.findViewById(R.id.tv_email);


        fm = getSupportFragmentManager();

        mPositionSavedPrefs = getSharedPreferences("mPositionSaved",
                Context.MODE_PRIVATE);
        posSavedEditor = mPositionSavedPrefs.edit();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (getIntent().hasExtra("beginning")) {

            fm.beginTransaction().replace(R.id.contentMain, new ShopFragment()).commit();
            drawer.openDrawer(GravityCompat.START);
            posSavedEditor.putInt("last_main_position", R.id.nav_shop).apply();
            getSupportActionBar().setTitle("Shop");

        } else {

            Fragment fragment = null;
            CharSequence title = null;

            int id = mPositionSavedPrefs.getInt(
                    "last_main_position", 1);



            if (id == R.id.nav_shop) {
                fragment = new ShopFragment();
                title = "Shop";
            }
/*
            if (id == R.id.nav_my_account) {
                fragment = new MyAccountFragment();
                title = "My Account";

            }

            if (id == R.id.nav_orders) {
                fragment = new OrdersFragment();
                title = "Orders";
            }

            if (id == R.id.nav_recipes) {
                fragment = new RecipesFragment();
                title = "Recipes";
            }
*/
            if (fragment != null) {

                fm.beginTransaction().replace(R.id.contentMain, fragment).commit();
                getSupportActionBar().setTitle(title);

                drawer.closeDrawer(GravityCompat.START);

            }
        }

        //checkCartForItems();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);


        final View notifications = menu.findItem(R.id.action_cart).getActionView();

        redDot = (ImageView) notifications.findViewById(R.id.iv_red_notification);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {

            //     Toast.makeText(this, "open cart", Toast.LENGTH_LONG).show();


            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment fragment = null;



        if (id == R.id.nav_shop) {
            fragment = new ShopFragment();
        }
/*
        if (id == R.id.nav_my_account) {
            fragment = new MyAccountFragment();
        }

        if (id == R.id.nav_orders) {
            fragment = new OrdersFragment();
        }

        if (id == R.id.nav_recipes) {
            fragment = new RecipesFragment();
        }
*/
        if (id == R.id.nav_about) {

            fragment = new AboutUsFragment();
        }

        if (fragment != null) {

            fm.beginTransaction().replace(R.id.contentMain, fragment).commit();
            getSupportActionBar().setTitle(item.getTitle());
            posSavedEditor.putInt("last_main_position", id).apply();
            drawer.closeDrawer(GravityCompat.START);
            item.setChecked(true);
        }


        return true;
    }

    public void checkCartForItems() {

        if (!myApplicationClass.getCart().getCurrentCartItems().isEmpty()) {
            redDot.setVisibility(View.VISIBLE);
        } else {
            redDot.setVisibility(View.GONE);
        }


    }

    public void OpenCart(View v) {

        //  Toast.makeText(this, "Open cart on click", Toast.LENGTH_LONG).show();

        if (myApplicationClass.getCart().getCurrentCartItems().isEmpty()) {

            Toast.makeText(this, "Cart is empty", Toast.LENGTH_LONG).show();


        } else {

            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        }

    }
}

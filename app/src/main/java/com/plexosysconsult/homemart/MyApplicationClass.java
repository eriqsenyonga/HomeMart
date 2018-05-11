package com.plexosysconsult.homemart;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by senyer on 6/21/2016.
 */
public class MyApplicationClass extends Application {

    private RequestQueue mRequestQueue;
    private static MyApplicationClass mInstance;
    public static final String TAG = MyApplicationClass.class.getName();
    private String generalUrl = "http://hellofreshuganda.com/";

    Cart cart;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        cart = new Cart(getApplicationContext());
    }


    public static synchronized MyApplicationClass getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public <T> void add(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancel() {

        mRequestQueue.cancelAll(TAG);
    }

    public Cart getCart() {
        return cart;
    }

    public void updateCart(Cart updatedCart) {
        cart = updatedCart;
    }


}

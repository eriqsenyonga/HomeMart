package com.plexosysconsult.homemart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CartActivity extends AppCompatActivity {

    RecyclerView rvCart;
    LinearLayoutManager linearLayoutManager;
    Toolbar toolbar;
    Button bCheckOut;
    TextView tvGrandTotal;
    String testVCS;
    RecyclerViewAdapterCart adapter;
    MyApplicationClass myApplicationClass = MyApplicationClass.getInstance();
    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        testVCS = "testVCS";

        tvGrandTotal = (TextView) findViewById(R.id.tv_grand_total);
        cart = myApplicationClass.getCart();

        bCheckOut = (Button) findViewById(R.id.b_checkout);

        rvCart = (RecyclerView) findViewById(R.id.recycler_view);
        rvCart.hasFixedSize();

        linearLayoutManager = new LinearLayoutManager(this);

        rvCart.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapterCart(this);

        adapter.setCartDataChangedListener(new CartDataChanged() {
            @Override
            public void onCartDataChanged() {
                tvGrandTotal.setText(cart.getCartGrandTotal());
            }
        });

        rvCart.setAdapter(adapter);

        tvGrandTotal.setText(cart.getCartGrandTotal());

        bCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(CartActivity.this, BillingDetails.class);
                startActivity(i);

            }
        });




    }


}

package com.plexosysconsult.homemart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderSuccessActivity extends AppCompatActivity {

    Button bContinueShopping;
    MyApplicationClass myApplicationClass = MyApplicationClass.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        bContinueShopping = (Button) findViewById(R.id.b_continue_shopping);

        bContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OrderSuccessActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        myApplicationClass.getCart().emptyCart();


    }
}

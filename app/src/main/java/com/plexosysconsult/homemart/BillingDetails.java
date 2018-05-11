package com.plexosysconsult.homemart;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingDetails extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout tilFirstName, tilSurName, tilEmail, tilPhoneNumber,
            tilPassword, tilReenterPassword, tilDeliveryAddress, tilTownCity;
    Button bPlaceOrder;
    CheckBox cbCreateAccount;
    MyApplicationClass myApplicationClass = MyApplicationClass.getInstance();
    Cart cart;
    String URL_PLACE_ORDER = "http://www.hellofreshuganda.com/example/placeOrder.php";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tilFirstName = (TextInputLayout) findViewById(R.id.til_firstname);
        tilSurName = (TextInputLayout) findViewById(R.id.til_surname);
        tilEmail = (TextInputLayout) findViewById(R.id.til_email);
        tilPhoneNumber = (TextInputLayout) findViewById(R.id.til_phone_number);
        tilDeliveryAddress = (TextInputLayout) findViewById(R.id.til_address_line_1);
        tilPassword = (TextInputLayout) findViewById(R.id.til_password);
        tilTownCity = (TextInputLayout) findViewById(R.id.til_city_town);
        tilReenterPassword = (TextInputLayout) findViewById(R.id.til_reenter_password);
        bPlaceOrder = (Button) findViewById(R.id.b_place_order);
        cbCreateAccount = (CheckBox) findViewById(R.id.cb_create_account);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Placing order");
        progressDialog.setIndeterminate(true);

        cbCreateAccount.setVisibility(View.GONE);


        bPlaceOrder.setOnClickListener(this);
        cbCreateAccount.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view == bPlaceOrder) {

            //on clicking Place Order, organise the order details to JSON ie the cart and the billing details plus the mode of payment

            if (checkBillingDetailsComplete() == false) {

                //if billing details are not complete

            } else {

             //   Log.d("billing", "0");

                progressDialog.show();

                bPlaceOrder.setEnabled(false);

                try {
                    JSONObject orderObject = new JSONObject();

                    orderObject.put("payment_method", "COD");
                    orderObject.put("payment_method_title", "Cash on Delivery");
                    orderObject.put("set_paid", true);
                    //   orderObject.put("status", "processing");
                    orderObject.put("shipping_total", 5000);

                    //add billing jsonArray

                    JSONArray billingJsonArray = new JSONArray();

                    JSONObject billingJson = new JSONObject();
                    billingJson.put("first_name", tilFirstName.getEditText().getText().toString());
                    billingJson.put("last_name", tilSurName.getEditText().getText().toString());
                    billingJson.put("address_1", tilDeliveryAddress.getEditText().getText().toString());
                    billingJson.put("address_2", "");
                    billingJson.put("email", tilEmail.getEditText().getText().toString());
                    billingJson.put("phone", tilPhoneNumber.getEditText().getText().toString());
                    billingJson.put("city", tilTownCity.getEditText().getText().toString());
                    billingJson.put("country", "UG");
                    billingJson.put("state", "Uganda");
                    billingJson.put("postcode", "256");

                    billingJsonArray.put(billingJson);

                    orderObject.put("billing_address", billingJson);

                    //add shipping jsonArray

                    JSONArray shippingJsonArray = new JSONArray();

                    JSONObject shippingJson = new JSONObject();
                    shippingJson.put("first_name", tilFirstName.getEditText().getText().toString());
                    shippingJson.put("last_name", tilSurName.getEditText().getText().toString());
                    shippingJson.put("address_1", tilDeliveryAddress.getEditText().getText().toString());
                    shippingJson.put("address_2", "");
                    shippingJson.put("city", tilTownCity.getEditText().getText().toString());
                    shippingJson.put("country", "UG");
                    shippingJson.put("state", "Uganda");
                    shippingJson.put("postcode", "256");

                    shippingJsonArray.put(shippingJson);

                    orderObject.put("shipping_address", shippingJson);

                    //add line_items json array
                    JSONArray lineItemsJsonArray = new JSONArray();

                    cart = myApplicationClass.getCart();

                    List<CartItem> cartItems = cart.getCurrentCartItems();

                    for (CartItem cartItem : cartItems) {

                        JSONObject lineItem = new JSONObject();

                        if (cartItem.isVariation()) {
                            lineItem.put("product_id", cartItem.getItemVariationId());
                        } else {

                            lineItem.put("product_id", cartItem.getItemId());
                        }

                        lineItem.put("quantity", cartItem.getQuantity());

                        lineItemsJsonArray.put(lineItem);
                    }

                    orderObject.put("line_items", lineItemsJsonArray);


                    //add shipping_lines object

                    JSONArray shippingLinesJsonArray = new JSONArray();

                    JSONObject shippingLinesObject = new JSONObject();

                    shippingLinesObject.put("method_id", "Flat Rate");
                    shippingLinesObject.put("method_title", "Delivery Fee");
                    shippingLinesObject.put("total", 5000);

                    shippingLinesJsonArray.put(shippingLinesObject);


                    orderObject.put("shipping_lines", shippingLinesJsonArray);


                  //  Log.d("order", orderObject.toString());

                    placeOrderOnline(orderObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

        if (view == cbCreateAccount) {


            if (cbCreateAccount.isChecked()) {

                tilPassword.setVisibility(View.VISIBLE);
                tilReenterPassword.setVisibility(View.VISIBLE);

            } else {

                tilPassword.setVisibility(View.GONE);
                tilReenterPassword.setVisibility(View.GONE);

            }


        }
    }

    private boolean checkBillingDetailsComplete() {

//double check the entries...if they are all complete then return true
        if (tilFirstName.getEditText().getText().toString().isEmpty()) {

            tilFirstName.getEditText().setError("Enter First Name");

            return false;

        }

        if (tilSurName.getEditText().getText().toString().isEmpty()) {

            tilSurName.getEditText().setError("Enter surname");

            return false;

        }

        if (tilPhoneNumber.getEditText().getText().toString().isEmpty()) {

            tilPhoneNumber.getEditText().setError("Enter Phone Number");

            return false;

        }
        if (tilEmail.getEditText().getText().toString().isEmpty()) {

            tilEmail.getEditText().setError("Enter E-mail");

            return false;

        }
        if (tilDeliveryAddress.getEditText().getText().toString().isEmpty()) {

            tilDeliveryAddress.getEditText().setError("Enter Delivery Address");

            return false;

        }
        if (tilTownCity.getEditText().getText().toString().isEmpty()) {

            tilTownCity.getEditText().setError("Enter Town or City");

            return false;

        }


        return true;
    }

    private void placeOrderOnline(final JSONObject orderObject) {

        StringRequest placeOrderOnlineRequest = new StringRequest(Request.Method.POST, URL_PLACE_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       // Log.d("billing", "1");

                        try {

                          //  Log.d("billing", "2");

                            JSONObject jsonResponse = new JSONObject(response);

                          //  Log.d("billing", "3");

                            Intent i = new Intent(BillingDetails.this, OrderSuccessActivity.class);
                            progressDialog.cancel();
                            startActivity(i);
                            finish();
                            bPlaceOrder.setEnabled(true);
                        } catch (JSONException e) {

                         //   Log.d("billing", "4");

                           // Log.d("place_order_error", e.toString());
                            e.printStackTrace();

                         //   Log.d("billing", "5");
                            bPlaceOrder.setEnabled(true);
                            progressDialog.cancel();
                            AlertDialog.Builder builder = new AlertDialog.Builder(BillingDetails.this);
                            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.setMessage("JSON error response");

                            Dialog dialog = builder.create();
                            dialog.show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //      Toast.makeText(BillingDetails.this, error.toString(), Toast.LENGTH_LONG).show();

                        progressDialog.cancel();

                        bPlaceOrder.setEnabled(true);

                        AlertDialog.Builder builder = new AlertDialog.Builder(BillingDetails.this);
                        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });


                        //  pbLoading.setVisibility(View.GONE);

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                            builder.setMessage("Order could not be placed \n \nConnection timed out!");


                        }

                        else if(error instanceof  NoConnectionError){

                            builder.setMessage("Order could not be placed \n \nCheck internet connection!");

                        }

                        else if (error instanceof ParseError) {

                            builder.setMessage("Oops! Something went wrong. Data unreadable");

                        }
                        //       errorLayout.setVisibility(View.VISIBLE);

                        Dialog dialog = builder.create();
                        dialog.show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("order_details_json_string", orderObject.toString());

                return map;
            }
        };

     //   placeOrderOnlineRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        placeOrderOnlineRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 10000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

    //    Log.d("billing", "0.5");

   //   Log.d("billing timeout","" + placeOrderOnlineRequest.getTimeoutMs());

        myApplicationClass.add(placeOrderOnlineRequest);


    }
}

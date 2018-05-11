package com.plexosysconsult.homemart;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopSeaFoodFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    View v;
    String URL_GET_FRUITS = "http://www.hellofreshuganda.com/example/getAllSeaFood.php";
    MyApplicationClass myApplicationClass = MyApplicationClass.getInstance();
    List<Item> fruitsToShow;
    SwipeRefreshLayout swipeRefreshLayout;
    UsefulFunctions usefulFunctions;
    ProgressBar pbLoading;
    LinearLayout errorLayout;
    Button bReload;
    TextView tvErrorMsg;
    String jsonFileName = "seafood.json";

    public ShopSeaFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_shop_fruits, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        pbLoading = (ProgressBar) v.findViewById(R.id.pb_loading);
        errorLayout = (LinearLayout) v.findViewById(R.id.error_layout);
        bReload = (Button) v.findViewById(R.id.b_reload);
        tvErrorMsg = (TextView) v.findViewById(R.id.tv_error_message);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        usefulFunctions = new UsefulFunctions(getActivity());

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));


        fruitsToShow = new ArrayList();

        if (usefulFunctions.checkForJsonFile(jsonFileName)) {

            //if file is available

            //  Log.d("JSON file available", "true");

            try {

                JSONObject jsonResponse = new JSONObject(usefulFunctions.mReadJsonData(jsonFileName));
                putJsonIntoList(jsonResponse);
                pbLoading.setVisibility(View.GONE);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            //if file is not available
            //   Log.d("JSON file available", "true");
            fetchFruitsJson();
        }

        bReload.setOnClickListener(this);
    }



    private void fetchFruitsJson() {

        StringRequest vegetableRequest = new StringRequest(Request.Method.POST, URL_GET_FRUITS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        usefulFunctions.mCreateAndSaveFile(jsonFileName, response);

                        try {

                            JSONObject jsonResponse = new JSONObject(usefulFunctions.mReadJsonData(jsonFileName));
                            putJsonIntoList(jsonResponse);
                            pbLoading.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//
                        pbLoading.setVisibility(View.GONE);

                        if ( error instanceof NoConnectionError) {

                            tvErrorMsg.setText("Connection could not be established");


                        }

                        else if(error instanceof TimeoutError){

                            tvErrorMsg.setText("Request timed out");

                        }


                        else if (error instanceof ParseError) {

                            tvErrorMsg.setText("Oops! Something went wrong. Data unreadable");

                        }
                        errorLayout.setVisibility(View.VISIBLE);

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("category", "Sea Food");

                return map;
            }
        };

        vegetableRequest.setRetryPolicy(new RetryPolicy() {
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


        myApplicationClass.add(vegetableRequest);

    }

    private void putJsonIntoList(JSONObject jsonResponse) {


        try {

            JSONArray fruits = jsonResponse.getJSONArray("products");

            for (int i = 0; i < fruits.length(); i++) {

                JSONObject fruitJSON = fruits.getJSONObject(i);

                Item fruit = new Item();

                fruit.setItemName(fruitJSON.getString("title"));
                fruit.setImageUrl(fruitJSON.getJSONArray("images").getJSONObject(0).getString("src"));
                fruit.setItemId(fruitJSON.getInt("id"));
                fruit.setItemPrice(fruitJSON.getString("price"));

                fruit.setItemShortDescription(usefulFunctions.stripHtml(fruitJSON.getString("short_description")));

                JSONArray variationArray = fruitJSON.getJSONArray("variations");

                if (variationArray.length() > 0) {

                    fruit.setHasVariations(true);

                    List<Item> variations = new ArrayList<>();

                    for (int j = 0; j < variationArray.length(); j++) {

                        JSONObject variationJSONObject = variationArray.getJSONObject(j);

                        Item variationFruit = new Item();
                        variationFruit.setItemId(variationJSONObject.getInt("id"));
                        variationFruit.setItemPrice(variationJSONObject.getString("price"));
                        variationFruit.setOptionUnit(variationJSONObject.getJSONArray("attributes").getJSONObject(0).getString("option"));

                        variations.add(variationFruit);

                    }

                    fruit.setItemVariations(variations);


                } else {

                    fruit.setHasVariations(false);
                }

                fruitsToShow.add(fruit);

            }


        } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();

        }


        recyclerView.setAdapter(new RecyclerViewAdapterVegetable(getActivity(), fruitsToShow));


    }


    @Override
    public void onClick(View view) {

        if (view == bReload) {

            errorLayout.setVisibility(View.GONE);
            pbLoading.setVisibility(View.VISIBLE);
            fetchFruitsJson();


        }


    }


}

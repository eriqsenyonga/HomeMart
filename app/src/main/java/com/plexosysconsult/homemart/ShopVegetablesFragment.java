package com.plexosysconsult.homemart;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopVegetablesFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    View v;
    String URL_GET_VEGETABLES = "http://www.hellofreshuganda.com/example/getAllVegetables.php";
    MyApplicationClass myApplicationClass = MyApplicationClass.getInstance();
    List<Item> veggiesToShow;
    ProgressBar pbLoading;
    UsefulFunctions usefulFunctions;
    LinearLayout errorLayout;
    Button bReload;
    TextView tvErrorMsg;
  //  SwipeRefreshLayout swipeRefreshLayout;
    String jsonFileName = "vegetables.json";


    public ShopVegetablesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_shop_vegetables, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        pbLoading = (ProgressBar) v.findViewById(R.id.pb_loading);
        errorLayout = (LinearLayout) v.findViewById(R.id.error_layout);
        bReload = (Button) v.findViewById(R.id.b_reload);
        tvErrorMsg = (TextView) v.findViewById(R.id.tv_error_message);
//        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        usefulFunctions = new UsefulFunctions(getActivity());

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));


        veggiesToShow = new ArrayList();

        if (usefulFunctions.checkForJsonFile(jsonFileName)) {

            //if file is available

       //     Log.d("JSON file available", "true");

            try {

                JSONObject jsonResponse = new JSONObject(usefulFunctions.mReadJsonData(jsonFileName));
                putJsonIntoList(jsonResponse);
                pbLoading.setVisibility(View.GONE);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            //if file is not available
          //  Log.d("JSON file available", "true");
            fetchVegetablesJson();
        }

        bReload.setOnClickListener(this);

  /*      swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


            }
        });

        */
    }

    private void fetchVegetablesJson() {

        StringRequest vegetableRequest = new StringRequest(Request.Method.POST, URL_GET_VEGETABLES,
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

                        pbLoading.setVisibility(View.GONE);

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                            tvErrorMsg.setText("Connection could not be established");


                        } else if (error instanceof ParseError) {

                            tvErrorMsg.setText("Oops! Something went wrong. Data unreadable");

                        }
                        errorLayout.setVisibility(View.VISIBLE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("category", "Vegetables");

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

            JSONArray vegetables = jsonResponse.getJSONArray("products");

            for (int i = 0; i < vegetables.length(); i++) {

                JSONObject vegetableJSON = vegetables.getJSONObject(i);

                Item vegetable = new Item();

                vegetable.setItemName(vegetableJSON.getString("title"));
                vegetable.setImageUrl(vegetableJSON.getJSONArray("images").getJSONObject(0).getString("src"));
                vegetable.setItemId(vegetableJSON.getInt("id"));
                vegetable.setItemPrice(vegetableJSON.getString("price"));

                vegetable.setItemShortDescription(usefulFunctions.stripHtml(vegetableJSON.getString("short_description")));


                JSONArray variationArray = vegetableJSON.getJSONArray("variations");

                if (variationArray.length() > 0) {

                    vegetable.setHasVariations(true);

                    List<Item> variations = new ArrayList<>();

                    for (int j = 0; j < variationArray.length(); j++) {

                        JSONObject variationJSONObject = variationArray.getJSONObject(j);

                        Item variationVegetable = new Item();
                        variationVegetable.setItemId(variationJSONObject.getInt("id"));
                        variationVegetable.setItemPrice(variationJSONObject.getString("price"));
                        variationVegetable.setOptionUnit(variationJSONObject.getJSONArray("attributes").getJSONObject(0).getString("option"));

                        variations.add(variationVegetable);

                    }

                    vegetable.setItemVariations(variations);


                } else {

                    vegetable.setHasVariations(false);
                }


                veggiesToShow.add(vegetable);

            }


        } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();
        }

        recyclerView.setAdapter(new RecyclerViewAdapterVegetable(getActivity(), veggiesToShow));


    }

    @Override
    public void onClick(View view) {

        if (view == bReload) {

            errorLayout.setVisibility(View.GONE);
            pbLoading.setVisibility(View.VISIBLE);
            fetchVegetablesJson();


        }

    }
}

package com.plexosysconsult.homemart;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
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
public class RecipesFragment extends Fragment implements View.OnClickListener {


    RecyclerView rvRecipes;
    String URL_GET_RECIPES = "http://www.hellofreshuganda.com/api/get_posts/?post_type=recipe";
    MyApplicationClass myApplicationClass = MyApplicationClass.getInstance();
    List<Recipe> recipeList;
    UsefulFunctions usefulFunctions;
    ProgressBar pbLoading;
    LinearLayout errorLayout;
    Button bReload;
    TextView tvErrorMsg;
    View v;
    String jsonFileName = "recipes.json";


    public RecipesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_recipes, container, false);

        rvRecipes = (RecyclerView) v.findViewById(R.id.recycler_view);
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

        rvRecipes.hasFixedSize();
        rvRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));


        recipeList = new ArrayList();


        if (usefulFunctions.checkForJsonFile(jsonFileName)) {

            //if file is available

           // Log.d("JSON file available", "true");

            try {

                JSONObject jsonResponse = new JSONObject(usefulFunctions.mReadJsonData(jsonFileName));
                putJsonIntoList(jsonResponse);
                pbLoading.setVisibility(View.GONE);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            //if file is not available
           // Log.d("JSON file available", "true");
            fetchRecipesJson();
        }

        bReload.setOnClickListener(this);
    }

    private void fetchRecipesJson() {

        StringRequest recipeRequest = new StringRequest(Request.Method.POST, URL_GET_RECIPES,
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
                map.put("category", "recipe");

                return map;
            }
        };


        myApplicationClass.add(recipeRequest);

    }

    private void putJsonIntoList(JSONObject jsonResponse) {


        try {

            JSONArray recipesJArray = jsonResponse.getJSONArray("posts");

            for (int i = 0; i < recipesJArray.length(); i++) {

                JSONObject recipeJson = recipesJArray.getJSONObject(i);

                Recipe recipe = new Recipe();

                recipe.setTitle(usefulFunctions.stripHtml(recipeJson.getString("title")));
                recipe.setImageUrl(recipeJson.getJSONObject("thumbnail_images").getJSONObject("full").getString("url"));
                recipe.setBody(recipeJson.getString("content"));


                //     fruit.setItemShortDescription(usefulFunctions.stripHtml(recipeJson.getString("short_description")));


                recipeList.add(recipe);


            }


        } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();


        }


        rvRecipes.setAdapter(new RecyclerViewAdapterRecipe(getActivity(), recipeList));


    }

    @Override
    public void onClick(View view) {
        if (view == bReload) {

            errorLayout.setVisibility(View.GONE);
            pbLoading.setVisibility(View.VISIBLE);
            fetchRecipesJson();


        }
    }
}

package com.efcompany.nottitranquille;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.efcompany.nottitranquille.extratools.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LocationActivity extends AppCompatActivity {

    String locID;

    // JSON Node names
    private static final String TAG_SUCCESS = "code";
    private static final String TAG_ID = "id";
    private static final String TAG_LOCATION = "location";

    JSONArray locsjson = null;

    // Progress Dialog
    View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        // Getting result details from intent
        Intent i = getIntent();
        locID =i.getStringExtra(TAG_ID);


        //Get site
        SharedPreferences sharedPref = this.getSharedPreferences("com.efcompany.nottitranquille", MODE_PRIVATE);
        String site = sharedPref.getString("connectto", "");
        if (site.equals("")) {
            Toast.makeText(this, "Insert the site", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ConnectionActivity.class);
            startActivity(intent);
        }
        site += "/api/search.jsp";

        // Show a progress spinner, and perform the conection attempt.
        mProgressView = findViewById(R.id.locationProgress);


        StringRequest postRequest = new StringRequest(Request.Method.POST, site,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Parser", response);
                        try {
                            JSONObject json_response = new JSONObject(response);
                            if (json_response.getString(TAG_SUCCESS).equals("1")) {
                                // Location found
                                // Getting Location details
                                locsjson = json_response.getJSONArray(TAG_LOCATION);

                                //TODO: show on screen

                            } else {
                                Toast.makeText(LocationActivity.this, R.string.strerrNoLocation, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Parser", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//
                params.put(TAG_ID,locID);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postRequest);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}

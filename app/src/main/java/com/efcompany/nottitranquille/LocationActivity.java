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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

    TextView tvID;
    TextView tvName;
    TextView tvType;
    TextView tvNation;
    TextView tvCity;
    TextView tvAddress;
    ImageView ivMainImage;
    TextView tvPrice;
    Button bBook;
    TextView tvDescription;
    TextView tvGuests;
    TextView tvBathrooms;
    TextView tvBeds;
    TextView tvBedrooms;
    Button bPhotos;
    Button bReviews;

    String locID;
    String locName;
    String locType;
    String locNation;
    String locCity;

    // JSON Node names
    private static final String TAG_SUCCESS = "code";
    private static final String TAG_ID = "id";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_MAINIMAGE = "mainimage";
    private static final String TAG_PRICE = "price";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_GUESTS = "guests";
    private static final String TAG_BATHROOMS = "bathrooms";
    private static final String TAG_BEDS = "beds";
    private static final String TAG_BEDROOMS = "bedrooms";
    private static final String TAG_COMMODITIES = "commodities";
    private static final String TAG_NAME = "name";
    private static final String TAG_NATION = "nation";
    private static final String TAG_CITY = "city";
    private static final String TAG_TYPE = "type";


    JSONObject locsjson = null;

    // Progress Dialog
    View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        tvID =  (TextView) findViewById(R.id.tvID);
        tvName =  (TextView) findViewById(R.id.tvName);
        tvType =  (TextView) findViewById(R.id.tvType);
        tvNation =  (TextView) findViewById(R.id.tvNation);
        tvCity =  (TextView) findViewById(R.id.tvCity);
        tvAddress =  (TextView) findViewById(R.id.tvAddress);
        ivMainImage = (ImageView) findViewById(R.id.ivMainImage);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        bBook = (Button) findViewById(R.id.bBook);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvGuests = (TextView) findViewById(R.id.tvGuests);
        tvBathrooms = (TextView) findViewById(R.id.tvBathrooms);
        tvBeds = (TextView) findViewById(R.id.tvBeds);
        tvBedrooms = (TextView) findViewById(R.id.tvBedrooms);
        bPhotos = (Button) findViewById(R.id.bPhotos);
        bReviews = (Button) findViewById(R.id.bReviews);


        // Getting result details from intent
        Intent i = getIntent();
        locID =i.getStringExtra(TAG_ID);
        locName = i.getStringExtra(TAG_NAME);
        locType = i.getStringExtra(TAG_TYPE);
        locNation = i.getStringExtra(TAG_NATION);
        locCity = i.getStringExtra(TAG_CITY);


        //Get site
        SharedPreferences sharedPref = this.getSharedPreferences("com.efcompany.nottitranquille", MODE_PRIVATE);
        String site = sharedPref.getString("connectto", "");
        if (site.equals("")) {
            Toast.makeText(this, "Insert the site", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ConnectionActivity.class);
            startActivity(intent);
        }
        site += "/api/showOffer.jsp";

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
                                locsjson = json_response.getJSONObject(TAG_LOCATION);

                                //TODO: show on screen
                               // ivMainImage
                                tvID.append(locID);
                                tvName.append(locName);
                                tvType.append(locType);
                                tvNation.append(locNation);
                                tvCity.append(locCity);
                                tvAddress.append(locsjson.getString(TAG_ADDRESS));
                                tvPrice.append(locsjson.getString(TAG_PRICE));
                                tvDescription.append(locsjson.getString(TAG_DESCRIPTION));
                                tvGuests.append(locsjson.getString(TAG_GUESTS));
                                tvBathrooms.append(locsjson.getString(TAG_BATHROOMS));
                                tvBeds.append(locsjson.getString(TAG_BEDS));
                                tvBedrooms.append(locsjson.getString(TAG_BEDROOMS));
                                //commodities

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

package com.efcompany.nottitranquille;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.efcompany.nottitranquille.extratools.AppController;
import com.efcompany.nottitranquille.extratools.GenericRequest;
import com.efcompany.nottitranquille.model.Location;
import com.efcompany.nottitranquille.model.SearchData;
import com.google.gson.Gson;

import org.joda.time.DateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText etNation;
    EditText etCity;
    DatePicker dpCheckIn;
    DatePicker dpCheckOut;
    Spinner spPriceRange;
    Button bAdvancedSearch;
    Button bSearch;

    String[] priceRanges;

    String nation;
    String city;
    int price;

    // JSON Node names
    private static final String TAG_SUCCESS = "code";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_NATION = "nation";
    private static final String TAG_CITY = "city";
    private static final String TAG_CHECKIN = "checkin";
    private static final String TAG_CHECKOUT = "checkout";
    private static final String TAG_PRICERANGE = "pricerange";
    private static final String TAG_LOCATIONS = "results";
    private static final String TAG_SOURCE = "source";

    private String site;
    SearchData query;

    // Progress Dialog
    View mProgressView;

    Gson gson;
    JSONArray locsjson = null;
    List<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etNation = (EditText) findViewById(R.id.etNation);
        etCity = (EditText) findViewById(R.id.etCity);
        dpCheckIn = (DatePicker) findViewById(R.id.dpCheckIn);
        dpCheckOut = (DatePicker) findViewById(R.id.dpCheckOut);
        spPriceRange = (Spinner) findViewById(R.id.spPriceRange);
        bAdvancedSearch = (Button) findViewById(R.id.bAdvSearch);
        bSearch = (Button) findViewById(R.id.bSearch);

        query = new SearchData();

        priceRanges = new String[]{getString(R.string.strAny), getString(R.string.strBelow100), getString(R.string.strBelow200),
                getString(R.string.strBelow500), getString(R.string.strOver500)};

        ArrayAdapter<String> padapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, priceRanges);

        // Show a progress spinner, and perform the conection attempt.
        mProgressView = findViewById(R.id.searchProgress);

        //Get the URL
        SharedPreferences sharedPref = this.getSharedPreferences("com.efcompany.nottitranquille", MODE_PRIVATE);
        site = sharedPref.getString("connectto", "");
        if (site.equals("")){
            Toast.makeText(this, R.string.strNoSite, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ConnectionActivity.class);
            startActivity(intent);
        }
        //site += "/search.php";
        site += "/api/search.jsp";

        gson = new Gson();

        spPriceRange.setAdapter(padapter);
        spPriceRange.setOnItemSelectedListener(this);

        bAdvancedSearch.setOnClickListener(this);
        bSearch.setOnClickListener(this);
    }

    //TODO Non gli piacciono i metodi tradizionali. scoprire cosa vuole oggigiorno
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                query.setPricerange("Fino+a+100+euro");
                break;
            case 1:
                query.setPricerange("Fino+a+200+euro");
                break;
            case 2:
                query.setPricerange("Fino+a+500+euro");
                break;
            case 3:
                query.setPricerange("Nessun+limite");
                break;
        }
//        switch (position){
//            case 0:
//                price
//                break;
//            case 1:
//                etIn2.setVisibility(View.VISIBLE);
//                etIn1.setHint(R.string.strOp1AHint);
//                etIn2.setHint(R.string.strOp1BHint);
//                break;
//            case 2:
//                etIn2.setVisibility(View.GONE);
//                etIn1.setHint(R.string.strOp2Hint);
//                break;
//            case 3:
//                etIn2.setVisibility(View.GONE);
//                etIn1.setHint(R.string.strOp3Hint);
//                break;
//            case 4:
//                etIn2.setVisibility(View.VISIBLE);
//                etIn1.setHint(R.string.strOp4AHint);
//                etIn2.setHint(R.string.strOp4BHint);
//                break;
//            case 5:
//                etIn2.setVisibility(View.VISIBLE);
//                etIn1.setHint(R.string.strOp5AHint);
//                etIn2.setHint(R.string.strOp5BHint);
//                break;
//            case 6:
//                etIn2.setVisibility(View.GONE);
//                etIn1.setHint(R.string.strOp6Hint);
//                break;
//            case 7:
//                etIn2.setVisibility(View.GONE);
//                etIn1.setHint(R.string.strOp7Hint);
//                break;
//        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        showProgress(true);
        if (v.getId()==bSearch.getId()){
            nation = etNation.getText().toString();
            city = etCity.getText().toString();
            boolean cancel = false;
            View focusView = null;

            // Check if the user filed the form.
            if (TextUtils.isEmpty(nation)) {
                etNation.setError(getString(R.string.error_field_required));
                focusView = etNation;
                cancel = true;
            }
            if (TextUtils.isEmpty(city)) {
                etCity.setError(getString(R.string.error_field_required));
                focusView = etCity;
                cancel = true;
            }


            DateTime checkin = new DateTime(dpCheckIn.getCalendarView().getDate());
            DateTime checkout = new DateTime(dpCheckOut.getCalendarView().getDate());
            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.

                showProgress(false);
                focusView.requestFocus();
            }
            //TODO Controllo DatePicker (Potrebbe servire dialog)
            else if (checkout.isBefore(checkin)) {
                showProgress(false);
                Toast.makeText(this, getString(R.string.strerrWrongDate), Toast.LENGTH_LONG);
            }
            else{
                //Log.d("Date",new DateTime(dpCheckIn.getCalendarView().getDate()).toString() );
                //Gather the data for the query
                query.setNation(nation);
                query.setCity(city);
                query.setCheckin(new DateTime(dpCheckIn.getCalendarView().getDate()));
                query.setCheckout(new DateTime(dpCheckOut.getCalendarView().getDate()));
                //TODO Connect
                JsonObjectRequest jsonObjReq = null;
                try {
                    jsonObjReq = new JsonObjectRequest(Request.Method.POST, site,
                            new JSONObject(gson.toJson(query)) , new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Parser", response.toString());

                            try {
                                VolleyLog.v("Response:%n %s", response.toString(4));
                                if (response.getString(TAG_SUCCESS).equals("1")) {
                                    // Locations found
                                    // Getting Array of Locations
                                    locsjson = response.getJSONArray(TAG_LOCATIONS);

//                                    // Looping through All Locations
//                                    for (int i = 0; i < locsjson.length(); i++) {
//                                        locations.add(gson.fromJson(locsjson.getJSONObject(i).toString(), Location.class));
//                                    }
                                    showProgress(false);
                                    Intent in =new Intent(SearchActivity.this, ResultsActivity.class);
                                    in.putExtra("json", locsjson.toString());
                                    in.putExtra("query", query.toString());
                                    startActivity(in);


                                } else{
                                    Toast.makeText(SearchActivity.this, R.string.strerrNoLocation, Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("Parser", "Error: " + error.getMessage());
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(jsonObjReq);
            }
        }
        if (v.getId()==bAdvancedSearch.getId()){
            // Starting new intent
            Intent in = new Intent(SearchActivity.this, AdvancedSearchActivity.class);
            // Sending info to next activity
            in.putExtra(TAG_NATION, etNation.getText().toString());
            in.putExtra(TAG_CITY, etNation.getText().toString());
            in.putExtra(TAG_CHECKIN, dpCheckIn.getCalendarView().getDate());
            in.putExtra(TAG_CHECKOUT, dpCheckOut.getCalendarView().getDate());
            in.putExtra(TAG_PRICERANGE, price);
            in.putExtra(TAG_SOURCE,1);
            // starting new activity
            startActivity(in);
        }
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

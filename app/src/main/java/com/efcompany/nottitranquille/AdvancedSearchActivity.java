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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.efcompany.nottitranquille.ConnectionActivity;
import com.efcompany.nottitranquille.R;
import com.efcompany.nottitranquille.ResultsActivity;
import com.efcompany.nottitranquille.extratools.AppController;
import com.efcompany.nottitranquille.extratools.GenericRequest;
import com.efcompany.nottitranquille.model.AdvSearchData;
import com.efcompany.nottitranquille.model.Location;
import com.efcompany.nottitranquille.model.SearchData;
import com.efcompany.nottitranquille.model.enumeration.LocationType;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdvancedSearchActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText etNation;
    EditText etCity;
    DatePicker dpCheckIn;
    DatePicker dpCheckOut;
    Spinner spPriceRange;
    Spinner spLocationType;
    Spinner spMaxTenants;
    CheckBox cbWiFi;
    CheckBox cbAirConditioner;
    CheckBox cbStrongBox;
    Button bAdvancedSearch;
    Button bSearch;

    String[] priceRanges;
    String[] locationTypes;
    String[] maxTenants;

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
    AdvSearchData query;

    // Progress Dialog
    View mProgressView;

    Gson gson;
    JSONArray locsjson = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        etNation = (EditText) findViewById(R.id.etNation);
        etCity = (EditText) findViewById(R.id.etCity);
        dpCheckIn = (DatePicker) findViewById(R.id.dpCheckIn);
        dpCheckOut = (DatePicker) findViewById(R.id.dpCheckOut);
        spPriceRange = (Spinner) findViewById(R.id.spPriceRange);
        spLocationType = (Spinner) findViewById(R.id.spLocationType);
        spMaxTenants = (Spinner) findViewById(R.id.spMaxTenants);
        cbWiFi = (CheckBox) findViewById(R.id.cbWiFi);
        cbAirConditioner = (CheckBox) findViewById(R.id.cbAirConditioner);
        cbStrongBox = (CheckBox) findViewById(R.id.cbStrongBox);
        bSearch = (Button) findViewById(R.id.bSearch);

        query = new AdvSearchData();

        priceRanges = new String[]{getString(R.string.strAny), getString(R.string.strBelow100), getString(R.string.strBelow200),
                getString(R.string.strBelow500), getString(R.string.strOver500)};
        locationTypes = new String[] {getString(R.string.strAny),getString(R.string.strBedandBreakfast), getString(R.string.strHotel),
                getString(R.string.strRural)};
        maxTenants = new String[] {"1","2","3","4","5"};

        ArrayAdapter<String> padapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, priceRanges);
        ArrayAdapter<String> ladapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, locationTypes);
        ArrayAdapter<String> tadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, maxTenants);

        // Show a progress spinner, and perform the conection attempt.
        mProgressView = findViewById(R.id.searchProgress);

        // Getting result details from intent
        Intent i = getIntent();
        if (i.getIntExtra(TAG_SOURCE, 0)==1) {
            if (i.getStringExtra(TAG_NATION)!=null) {
                if (!i.getStringExtra(TAG_NATION).isEmpty()) {
                    etNation.setText(i.getStringExtra(TAG_NATION));
                }
            }
            if (i.getStringExtra(TAG_CITY)!=null) {
                if (!i.getStringExtra(TAG_CITY).isEmpty()) {
                    etCity.setText(i.getStringExtra(TAG_CITY));
                }
            }

            if (i.getStringExtra(TAG_CHECKIN)!=null) {
                if (!i.getStringExtra(TAG_CHECKIN).isEmpty()) {
                    DateTime dtin = new DateTime(i.getStringExtra(TAG_CHECKIN));
                    dpCheckIn.updateDate(dtin.getYear(), dtin.getMonthOfYear() - 1, dtin.getDayOfMonth());
                }
            }
            if (i.getStringExtra(TAG_CHECKOUT)!=null) {
                if (!i.getStringExtra(TAG_CHECKOUT).isEmpty()) {
                    DateTime dtin = new DateTime(i.getStringExtra(TAG_CHECKOUT));
                    dpCheckOut.updateDate(dtin.getYear(), dtin.getMonthOfYear() - 1, dtin.getDayOfMonth());
                }
            }
            //TODO Pricerange non più int
            if (i.getIntExtra(TAG_PRICERANGE, 99) != 99) {
                spPriceRange.setSelection(i.getIntExtra(TAG_PRICERANGE, 99));
                //TODO Scoprire come settare lo spinner
            }
        }

        //Get the URL
        SharedPreferences sharedPref = this.getSharedPreferences("com.efcompany.nottitranquille", MODE_PRIVATE);
        site = sharedPref.getString("connectto", "");
        if (site.equals("")){
            Toast.makeText(this, R.string.strNoSite, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ConnectionActivity.class);
            startActivity(intent);
        }
        //site += "/Advsearch.php";
        site += "/api/search.jsp";

        gson = new Gson();


        spPriceRange.setAdapter(padapter);
        spPriceRange.setOnItemSelectedListener(this);

        spLocationType.setAdapter(ladapter);
        spLocationType.setOnItemSelectedListener(this);

        spMaxTenants.setAdapter(tadapter);
        spMaxTenants.setOnItemSelectedListener(this);

        //bAdvancedSearch.setOnClickListener(this);
        bSearch.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_advanced_search, menu);
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


    //TODO Trovare modo per usare elenco automatico
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId()==spPriceRange.getId()){
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

        }
        else if (parent.getId()==spLocationType.getId()){
            switch (position){
                case 0:
                    query.setLocationtype("Any");
                    break;
                case 1:
                    query.setLocationtype("BedBreakfast");
                    break;
                case 2:
                    query.setLocationtype("Hotel");
                    break;
                case 3:
                    query.setLocationtype("Rural");
                    break;
            }
        }
        else if (parent.getId()==spMaxTenants.getId()){
            query.setMaxtenant(position);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        showProgress(true);
        //TODO Controllo DatePicker (Potrebbe servire dialog)
        DateTime checkin = new DateTime(dpCheckIn.getCalendarView().getDate());
        DateTime checkout = new DateTime(dpCheckOut.getCalendarView().getDate());
        if (checkout.isBefore(checkin)) {
            showProgress(false);
            Toast.makeText(this, getString(R.string.strerrWrongDate), Toast.LENGTH_LONG);
        }

        nation = etNation.getText().toString();
        city = etCity.getText().toString();

        //Gather the data for the query
        if (TextUtils.isEmpty(nation)) {
            query.setNation("Any");
        } else {
            query.setNation(nation);
        }
        if (TextUtils.isEmpty(city)) {
            query.setCity("Any");
        } else {
            query.setCity(city);
        }
        //TODO Scoprire come NON Impostare il calendario
        query.setCheckin(new DateTime(dpCheckIn.getCalendarView().getDate()));
        query.setCheckout(new DateTime(dpCheckOut.getCalendarView().getDate()));

        //TODO Trovare modo per usare elenco automatico
        List<String> commodities = new ArrayList<>();
        if (cbWiFi.isChecked()){
            commodities.add("WiFi=on");
        }
        if (cbAirConditioner.isChecked()){
            commodities.add("AirConditioner=on");
        }
        if (cbStrongBox.isChecked()){
            commodities.add("StrongBox=on");
        }
        query.setCommodities(commodities);

        //TODO Connect
        JsonObjectRequest jsonObjReq = null;
        try {
            jsonObjReq = new JsonObjectRequest(Request.Method.POST, site, new JSONObject(gson.toJson(query)), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Parser", response.toString());


                    try {
                        if (response.getString(TAG_SUCCESS).equals("1")){
                            // Locations found
                            // Getting Array of Locations
                            locsjson = response.getJSONArray(TAG_LOCATIONS);
                            showProgress(false);
                            Intent in =new Intent(AdvancedSearchActivity.this, ResultsActivity.class);
                            in.putExtra("json", response.toString());
                            in.putExtra("query", query.toString());
                            startActivity(in);
                        } else{
                            Toast.makeText(AdvancedSearchActivity.this, R.string.strerrNoLocation, Toast.LENGTH_LONG).show();
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

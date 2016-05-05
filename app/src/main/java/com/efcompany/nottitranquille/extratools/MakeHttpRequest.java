package com.efcompany.nottitranquille.extratools;

/**
 * Created by Administrator on 3/23/16.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 03/02/16.
 */
public class MakeHttpRequest extends Activity {
    public MakeHttpRequest() {
    }



    JSONObject jObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String site = intent.getStringExtra("site");
        String mail = intent.getStringExtra("mail");
        String password = intent.getStringExtra("password");
        Map<String, String> fields = new HashMap<String, String>();
        fields.put("mail", mail);
        fields.put("password", password);

        makeHttpRequest(site,fields);
    }

    public void makeHttpRequest(String url, final Map<String,String> parameters) {


        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(parameters),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent data = new Intent();
                        data.putExtra("json", response.toString());
                        if (getParent() == null) {
                            setResult(Activity.RESULT_OK, data);
                        } else {
                            getParent().setResult(Activity.RESULT_OK, data);
                        }
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
//        // Adding request to request queue
//        RequestFuture<String> future = RequestFuture.newFuture();
//        AppController.getInstance().addToRequestQueue(req);
//        String result = null;
//        try {
//            result = future.get(); // this line will block
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        try {
//            jObj = new JSONObject(result);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jObj;
    }
}

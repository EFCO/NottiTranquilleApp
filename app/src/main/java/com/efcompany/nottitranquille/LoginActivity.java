//<div>Icons made by <a href="http://www.flaticon.com/authors/ocha" title="OCHA">OCHA</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a>             is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a></div>
package com.efcompany.nottitranquille;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.efcompany.nottitranquille.extratools.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    // UI references.
    EditText mEmailView;
    EditText mPasswordView;
    View mProgressView;
    View mEmailLoginFormView;
    Button mEmailSignInButton;



    private String site;
    private String codedPassword;
    String email;

    static final int LOG_IN = 1;
    static final int CONNECT = 2;
    static final int REGISTRATION = 3;

    private static final String TAG_SUCCESS = "code";


    Map<String, String> fields = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get the URL
        SharedPreferences sharedPref = this.getSharedPreferences("com.efcompany.nottitranquille", MODE_PRIVATE);
        site = sharedPref.getString("connectto", "");
        if (site.equals("")){
            Toast.makeText(this, R.string.strNoSite, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ConnectionActivity.class);
            startActivity(intent);
        }
        //site += "/login.php";
        site += "/api/access.jsp";


        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mProgressView = findViewById(R.id.login_progress);
        mEmailLoginFormView = findViewById(R.id.email_login_form);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


    }

    public void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
       }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and
            // perform the user login attempt.
            showProgress(true);

            //Encoding the password in SHA-256
            try{
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes("UTF-8"));
                        StringBuffer hexString = new StringBuffer();

                //Binary to Hexadecimal
                for (int i = 0; i < hash.length; i++) {
                    String hex = Integer.toHexString(0xff & hash[i]);
                    if(hex.length() == 1) hexString.append('0');
                            hexString.append(hex);
                }

                codedPassword = hexString.toString();

            }
            catch (NoSuchAlgorithmException nsae){
                Log.d("Login Error", "NoSuchAlgorithmException");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }



                    fields.put("username", email);
                    fields.put("password", codedPassword);
                    fields.put("login", "login");



            //Call to the server and response handling
            StringRequest postRequest = new StringRequest(Request.Method.POST,
                    site,  new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                        try {
                            VolleyLog.v("Response:%n %s", response);
                            try {
                                JSONObject json_response = new JSONObject(response);
                                if (json_response.getString(TAG_SUCCESS).equals("1")) {
                                    // Person found
                                    if (getParent() == null) {
                                        setResult(Activity.RESULT_OK);
                                    } else {
                                        getParent().setResult(Activity.RESULT_OK);
                                    }
                                    finish();

                                } else {
                                    mPasswordView.setError(json_response.getString("message"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    //Hide Progress Spinner
                    showProgress(false);

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                    //Hide Progress Spinner
                    showProgress(false);
                    //Show Error Message
                    Toast.makeText(LoginActivity.this, R.string.strerrVolleyConnection, Toast.LENGTH_LONG).show();
                }
            }
            ){

                @Override
                protected Map<String, String> getParams() {

                    return fields;
                }

                /* (non-Javadoc)
    * @see com.android.volley.toolbox.StringRequest#parseNetworkResponse(com.android.volley.NetworkResponse)
    */

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    // since we don't know which of the two underlying network vehicles
                    // will Volley use, we have to handle and store session cookies manually
                    AppController.getInstance().checkSessionCookie(response.headers);

                    return super.parseNetworkResponse(response);
                }

                /* (non-Javadoc)
                 * @see com.android.volley.Request#getHeaders()
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = super.getHeaders();

                    if (headers == null
                            || headers.equals(Collections.emptyMap())) {
                        headers = new HashMap<String, String>();
                    }

                    AppController.getInstance().addSessionCookie(headers);

                    return headers;
                }};

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(postRequest);
        }
        }


    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id ==R.id.connection_settings){
            Intent intent = new Intent(this, ConnectionActivity.class);
            startActivityForResult(intent, CONNECT);
        }
        if (id ==R.id.connection_settings){
            Intent intent = new Intent(this, ConnectionActivity.class);
            startActivityForResult(intent, CONNECT);
        }
        if (id ==R.id.signup_settings){
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivityForResult(intent, REGISTRATION);
        }

        return super.onOptionsItemSelected(item);
    }
}

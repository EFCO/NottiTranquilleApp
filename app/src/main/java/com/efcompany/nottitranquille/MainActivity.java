//<div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a>             is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a></div>
package com.efcompany.nottitranquille;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    #per i setting (ovvero commodities e locationtypes)
//    link: localhost:8080/api/getSetting.jsp
//    parametri: setting=commodities o setting=locationtypes

    static final int LOG_IN = 1;
    static final int CONNECT = 2;
    static final int REGISTRATION = 3;
    Button bSearch;
    Button bAdvSearch;
    Button bRandomSearch;

    ConnectivityManager conmng;
    NetworkInfo activeNetwork;

    private static final String TAG_SOURCE = "source";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bSearch = (Button) findViewById(R.id.bSearch);
        bAdvSearch = (Button) findViewById(R.id.bAdvSearch);
        bRandomSearch = (Button) findViewById(R.id.bRandomSearch);

        //That is, if it has just been started
        if (savedInstanceState==null) {
            checkInternetConnection();
        }

        bSearch.setOnClickListener(this);
        bAdvSearch.setOnClickListener(this);
        bRandomSearch.setOnClickListener(this);


    }



    private void checkInternetConnection() {
        conmng = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = conmng.getActiveNetworkInfo();
        if ((activeNetwork == null) || (!activeNetwork.isConnectedOrConnecting())){
            AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity.this);
            alertbox.setTitle(R.string.errNotconnected);
            alertbox.setMessage(R.string.errmsgNotConnected);
            alertbox.setPositiveButton(R.string.errbtTurnOn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivityForResult(intent, CONNECT);

                }
            });
            alertbox.setNegativeButton(R.string.strClose, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    finish();
                }
            });
            alertbox.show();
        }
    }

    private void logIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, LOG_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == LOG_IN) {
            // Make sure the request was successful
            if (resultCode != RESULT_OK) {
                finish();
           }
        }
        if (requestCode == CONNECT) {
            // Make sure the request was successful
            if ((activeNetwork == null) || (!activeNetwork.isConnectedOrConnecting())) {
                finish();
            }
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
        if (id ==R.id.login_settings){
            logIn();
        }
        if (id ==R.id.signup_settings){
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivityForResult(intent, REGISTRATION);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == bSearch.getId()){

            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        if (v.getId() == bAdvSearch.getId()){

            Intent intent = new Intent(this, AdvancedSearchActivity.class);
            intent.putExtra(TAG_SOURCE,0);
            startActivity(intent);
        }
        if (v.getId() == bRandomSearch.getId()){

           //TODO call php and redirect response
        }
    }

}

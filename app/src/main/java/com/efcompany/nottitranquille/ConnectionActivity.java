package com.efcompany.nottitranquille;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etconnAddr;
    Button bconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        etconnAddr = (EditText) findViewById(R.id.etConnAddr);
        bconfirm = (Button) findViewById(R.id.bconfirm);

        bconfirm.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connection, menu);
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
    public void onClick(View v) {
        //Save the URL
        SharedPreferences sharedPref = this.getSharedPreferences("com.efcompany.nottitranquille", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (!etconnAddr.getText().toString().equals("")){
            editor.putString("connectto","http://" +  etconnAddr.getText().toString());
            editor.apply();
            finish();
        }
        //If the user pressed the "Confirm" button without writing anything
        else{
            etconnAddr.setError(getString(R.string.error_field_required));
            etconnAddr.requestFocus();
        }

    }
}

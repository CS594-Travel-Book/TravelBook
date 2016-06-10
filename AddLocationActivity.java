package com.android.project.travelbook;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anu on 06-08-2016.
 */
public class AddLocationActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Add Location Activity:", "On create");



        setContentView(R.layout.activity_addlocation);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void onButtonClicked(View v){
        DialogFragment newFragment = new AddLocationActivityFragment.DatePickerFragment();
        newFragment.show(getFragmentManager(), "Date Picker");

    }
    public void Save_Location(View view) throws JSONException {

        //EditText notes = (EditText) findViewById(R.id.notes);

       /* radioGroup = (RadioGroup) findViewById(R.id.radioSex);
        int selectedId = radioButton.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) getView().findViewById(selectedId);
*/

        final String note = "note";
        final String address = "cal state";
        final String category = "Restuarants";
        final String email = "xyz";
        final String date ="06/09/2016";

        String url = "http://travelbook.gear.host/android_files/post_location.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Response ", response);
                        Toast.makeText(AddLocationActivity.this, "Regiistered Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

            @Override
            public Map<String, String> getParams() {
                Map<String, String> p = new HashMap<String, String>();
                p.put("tag", "saveLocation");
                p.put("address", address);
                p.put("date", date);
                p.put("category", category);
                p.put("notes", note);
                p.put("email", email);
                return p;
            }

        };
        //RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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


}

package com.android.project.travelbook;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Add Location Activity:", "On create");
        Intent i = getIntent();
        email = i.getExtras().getString("email");
        Log.v("AddLocation email",email);
        setContentView(R.layout.activity_addlocation);
        Button button = (Button) findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                try {
                    Save_Location(view);
                    startActivity(new Intent(AddLocationActivity.this, AddLocationImageActivity.class).putExtra(Intent.EXTRA_TEXT, email));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }


        });


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

        TextView tv = (TextView) findViewById(R.id.tv);
        TextView notes = (TextView) findViewById(R.id.textView1);
        EditText add = (EditText) findViewById(R.id.address_text);
        RadioGroup radioCategoryGroup =(RadioGroup) findViewById(R.id.radioCategory);
        RadioButton radioCategoryButton ;
        int selectedId = radioCategoryGroup.getCheckedRadioButtonId();
        radioCategoryButton = (RadioButton) findViewById(selectedId);

        final String note = notes.getText().toString();
        final String address = add.getText().toString();
        final String category = radioCategoryButton.getText().toString();
        //    final String email = "xyz";
        final String date =tv.getText().toString();


        String url = "http://travelbook.gear.host/android_files/post_location.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Response ", response);
                        Toast.makeText(AddLocationActivity.this, "Location Added Successfully", Toast.LENGTH_SHORT).show();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
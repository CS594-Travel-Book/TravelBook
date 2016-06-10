package com.android.project.travelbook;

/**
 * Created by Anu on 06-09-2016.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void post(View view) throws JSONException {

        EditText email = (EditText) findViewById(R.id.email);
        final String email_value = email.getText().toString();

        EditText password = (EditText) findViewById(R.id.password);
        final String password_value = password.getText().toString();

        EditText name = (EditText) findViewById(R.id.name);
        final String name_value = name.getText().toString();

        String url = "http://travelbook.gear.host/android_files/post_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Response ", response);
                        Toast.makeText(RegisterActivity.this, "Regiistered Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error ", error.getMessage());

                    }
                }) {

            @Override
            public Map<String, String> getParams() {
                Map<String, String> p = new HashMap<String, String>();
                p.put("tag", "register");
                p.put("username", email_value);//email
                p.put("password", password_value);//password
                p.put("name", name_value);//name

                return p;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}

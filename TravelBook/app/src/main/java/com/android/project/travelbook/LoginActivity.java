package com.android.project.travelbook;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity {

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Check_Login(View view){

        EditText ed_email = (EditText)findViewById(R.id.email);
        email = ed_email.getText().toString();

        EditText ed_password = (EditText)findViewById(R.id.password);
        password = ed_password.getText().toString();

        GetData gd = new GetData();
        gd.execute();
    }

    public void register(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    class GetData extends AsyncTask<Void, Void, String> {

        Boolean canLogin = false;

        protected void onPostExecute(String checkLogin) {

            if(canLogin){
                //startActivity(new Intent(LoginActivity.this, MainActivity.class));

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                EditText editText = (EditText) findViewById(R.id.email);
                String email = editText.getText().toString();
                intent.putExtra("email", email);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Invalid Id/Password", Toast.LENGTH_LONG).show();
            }

        }
        @Override
        protected String doInBackground(Void... params) {

            try {
                String link = "http://travelbook.gear.host/android_files/fetch_data.php";
                URL url = new URL(link);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                JSONArray json_array = new JSONArray(sb.toString());
                for (int i=0; i<json_array.length();i++){
                    JSONObject json_object = json_array.getJSONObject(i);
                    String username = json_object.getString("username");
                    String passwd = json_object.getString("passwd");
                    Log.v("Username " + i, username);
                    Log.v("password "+i, passwd);

                    if(username.equals(email) && password.equals(passwd) ){
                        canLogin = true;
                    }
                }
                in.close();
                Log.v("String Builder", sb.toString());
            } catch (Exception e) {
                Log.v("String Builder", e.getMessage());
            }

            return "Hi";
        }
    }

}
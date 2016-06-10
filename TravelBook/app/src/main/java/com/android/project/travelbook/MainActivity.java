package com.android.project.travelbook;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Location_Adapter location_adapter;

    String sessionemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Location_Item> location_list = new ArrayList<Location_Item>();
        listView = (ListView) findViewById(R.id.location_view_list);
        location_adapter = new Location_Adapter(this, location_list);
        listView.setAdapter(location_adapter);

        showPermissionDialog();
        Log.v("Main Activity:", "On create");

        Intent i = getIntent();
        sessionemail = i.getExtras().getString("email");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here
                Intent intent = new Intent(MainActivity.this, AddLocationActivity.class);

                intent.putExtra("email", sessionemail);
                startActivity(intent);


            }
        });
        GetData gd = new GetData();
        gd.execute();

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static boolean checkPermission(final Context context) {
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            Log.v("check permission", "return true");
            return true;
        }
        Log.v("check permission", "return false");
        return false;
    }
    int PERMISSION_LOCATION_REQUEST_CODE = 123;
    private void showPermissionDialog() {
        if (!checkPermission(this)) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_LOCATION_REQUEST_CODE);
        }
    }


    class GetData extends AsyncTask<Void, Void, ArrayList<Location_Item>> {

        Boolean canLogin = false;

        protected void onPostExecute(ArrayList<Location_Item> location_list) {

            super.onPostExecute(location_list);
            location_adapter.clear();
            listView = (ListView) findViewById(R.id.location_view_list);
            location_adapter = new Location_Adapter(MainActivity.this, location_list);
            listView.setAdapter(location_adapter);

        }
        @Override
        protected ArrayList<Location_Item> doInBackground(Void... params) {

            ArrayList<Location_Item> location_list_return = new ArrayList<Location_Item>();
            try {
                String link = "http://travelbook.gear.host/android_files/fetch_history.php";
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
                    String address = json_object.getString("loc_address");
                    String date = json_object.getString("visit_date");
                    String category = json_object.getString("category");
                    String notes = json_object.getString("notes");
                    String email = json_object.getString("email");
                    if(email.equals(sessionemail)){
                        Location_Item ll = new Location_Item();
                        ll.setAddress(address);
                        ll.setVisit_date(date);
                        ll.setCategory(category);
                        ll.setNotes(notes);

                        location_list_return.add(ll);

                    }
                   /* if(email.equals(sessionemail)){
                        canLogin = true;
                    }*/
                }
                in.close();
                Log.v("String Builder", sb.toString());
            } catch (Exception e) {
                Log.v("String Builder", e.getMessage());
            }

            return location_list_return;
        }


    }
}

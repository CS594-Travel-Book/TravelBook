package com.android.alqahtanireem.pro;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    public static final String LOG_TAG = "ForecastFragment";
    ArrayAdapter adapter;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    //Refresh button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }


    @Override


    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_refresh) {
            Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_LONG).show();
            FetchDataTask task = new FetchDataTask();
            task.execute();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        ListView listView = (ListView) rootView.findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.item_text, new ArrayList<String>());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getActivity(), adapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }


    class FetchDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(Void... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;



            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


            Double lat =7.612998 ,lng =-75.585938;
            try {
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Log.v(LOG_TAG, "1");
                lat = location.getLatitude();
                Log.v(LOG_TAG, "2");
                lng = location.getLongitude();
                Log.v(LOG_TAG, "3");

            } catch (SecurityException e) {

                // lets the user know there is a problem with the gps
                Log.v(LOG_TAG, "Security Exception");
            }






            try {

                //final String FORECAST_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=-33.867591,151.201196&location_type=ROOFTOP&result_type=street_address";
                final String FORECAST_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?" + "latlng=" + lat + "," + lng + "&location_type=ROOFTOP&result_type=street_address";
                final String APPID_PARAM = "key";


                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon().
                        appendQueryParameter(APPID_PARAM, BuildConfig.API_KEY).build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Url: " + url.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
                Log.v(LOG_TAG, forecastJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            try {
                return getDataFromJson(forecastJsonStr);

            }catch (JSONException e) {
                e.printStackTrace();

            }

            return null;
        }


        @Override
        protected void onPostExecute(String[] data) {

            super.onPostExecute(data);
            adapter.clear();
            adapter.addAll(new ArrayList<String>(Arrays.asList(data)));

        }


        /* The date/time conversion code is going to be moved outside the asynctask later,
        * so for convenience we're breaking it out into its own method now.
        */
        private String getReadableDateString(long time) {
            // Because the API returns a unix timestamp (measured in seconds),
            // it must be converted to milliseconds in order to be converted to valid date.
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        }


        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         * <p>
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */


        private String[] getDataFromJson(String forecastJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_RESULT = "results";
            final String OWM_address = "formatted_address";
            final String OWM_type = "types";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray reviewArray = forecastJson.getJSONArray(OWM_RESULT);

            Time dayTime = new Time();
            dayTime.setToNow();

            // we start at the day returned by local time. Otherwise this is a mess.
            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

            // now we work exclusively in UTC
            dayTime = new Time();

            String[] resultStrs = new String[1];
            for (int i = 0; i < reviewArray.length(); i++) {

                String address ,type;

                JSONObject dayForecast = reviewArray.getJSONObject(i);

                address = dayForecast.getString(OWM_address);
                type = dayForecast.getString(OWM_type);



                resultStrs[i] = address +"\n\n" + type ;
            }


            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Forecast entry: " + s);
            }
            return resultStrs;

        }





    }

}











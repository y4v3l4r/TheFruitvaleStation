package com.yarency.android.thefruitvalestation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WarmSpringsActivity extends AppCompatActivity {

    TextView firstTrainTextView;
    TextView secondTrainTextView;
    TextView thirdTrainTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warm_springs);
        firstTrainTextView = (TextView) findViewById(R.id.first_train);
        secondTrainTextView = (TextView) findViewById(R.id.second_train);
        thirdTrainTextView = (TextView) findViewById(R.id.third_train);
    }
    public void checkTimes(View view) {
// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.bart.gov/api/etd.aspx?cmd=etd&orig=ftvl&json=y&key=MW9S-E7SL-26DU-VV8V";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject responseJson = new JSONObject(response);
                            JSONObject root = responseJson.getJSONObject("root");
                            JSONArray station = root.getJSONArray("station");
                            JSONObject fruitvale = station.getJSONObject(0);
                            JSONArray etd = fruitvale.getJSONArray("etd");
                            JSONObject warmSprings = etd.getJSONObject(3);
                            JSONArray estimate = warmSprings.getJSONArray("estimate");
                            JSONObject firstTrain = estimate.getJSONObject(0);
                            String firstTrainString = firstTrain.getString("minutes");
                            JSONObject secondTrain = estimate.getJSONObject(1);
                            String secondTrainMinutes = secondTrain.getString("minutes");
                            JSONObject thirdTrain = estimate.getJSONObject(2);
                            String thirdTrainMinutes = thirdTrain.getString("minutes");

                            firstTrainTextView.setText(firstTrainString);
                            secondTrainTextView.setText(secondTrainMinutes);
                            thirdTrainTextView.setText(thirdTrainMinutes);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                firstTrainTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

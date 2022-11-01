package com.example.clocky;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BIOActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);
        mTextViewResult = findViewById(R.id.text_view_result);

        mQueue = Volley.newRequestQueue(this);

        jsonParse();



    }
    private void jsonParse() {

        String url = "https://api.myjson.com/bins/js480";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Bio");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject Bio = jsonArray.getJSONObject(i);

                                String Name = Bio.getString("Name");
                                String occupation = Bio.getString("Occupation");
                                String dept = Bio.getString("Dept");
                                String inst = Bio.getString("Institution");
                                String country = Bio.getString("Country");
                                String cont = Bio.getString("Contact");
                                String email = Bio.getString("email");

                                mTextViewResult.append("NAME : "+Name+"\nOCCUPATION : " +occupation+ "\nDEPARTMENT : "+dept+ "\nINSTITUTION : " +inst+ "\nCOUNTRY : "+country+"\nCONTACT : "+cont+"\nE-MAIL : "+email);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}

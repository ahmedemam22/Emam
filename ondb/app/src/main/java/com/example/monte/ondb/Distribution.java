package com.example.monte.ondb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Distribution extends AppCompatActivity {
    RequestQueue requestQueue;
    private Map<String, String> params;
    StringBuilder sbParams = new StringBuilder();
    String url = "http://10.0.3.2:8080/myproject/distibute.php";
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> distribtion = new ArrayList<String>();

    Button ok;
    String build = "ABCDE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution);
        ok = findViewById(R.id.ok);
        requestQueue = Volley.newRequestQueue(Distribution.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonarray = response.getJSONArray("allstudents");
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject respons = jsonarray.getJSONObject(i);
                                name.add(respons.getString("email"));


                            }
                          //  Toast.makeText(Distribution.this, name.get(0), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Distribution.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
                for(int i=0;i<build.length();i++){
                    for (int j=0;j<5;j++){
                        distribtion.add(build.charAt(i)+""+(j+1));
                        distribtion.add(build.charAt(i)+""+(j+1));


                    }
                }
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        params=new HashMap<>();
                        String[]arr=new String[name.size()];
                        String[]arr1=new String[name.size()];
                        for (int i=0;i<name.size();i++){
                            arr[i]=name.get(i);
                            arr1[i]=distribtion.get(i);

                        }


                        params.put("email",Arrays.toString(arr));
                        params.put("room",Arrays.toString(arr1));
                        int i = 0;
                        for (String key : params.keySet()) {
                            try {
                                if (i != 0){
                                    sbParams.append("&");
                                }
                                sbParams.append(key).append("=")
                                        .append(URLEncoder.encode(params.get(key), "UTF-8"));

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            i++;
                        }
                        Intent x=new Intent(getApplicationContext(),Adminfirst.class);
                        startActivity(x);
                        new SendPostRequest().execute();


                    }

                });






            }

    public class SendPostRequest extends AsyncTask<String, Void, String>


    {
        protected void onPreExecute()
        {

        }

        protected String doInBackground(String... arg0)
        {
            try {
                URL url = new URL("http://192.168.43.95:8080/myproject/distribution.php");//=" + arg0[0] + "&lname=" + arg0[1]+"&email="+arg0[2]+"&password="+arg0[3]+"&grade="+arg0[4]+"&college="+college+"&mobile="+arg0[6]+"&distance="+arg0[7]);


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Accept-Charset", "UTF-8");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.connect();

                String paramsString = sbParams.toString();
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;

                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                return buffer.toString();
            } catch (Exception e) {
                return new String ("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result)

        {

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }

    }
}

//

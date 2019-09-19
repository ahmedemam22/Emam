package com.example.monte.ondb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    int d;
    Button next;
    private Map<String,String> params;
    StringBuilder sbParams = new StringBuilder();

    String college;



    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        next=findViewById(R.id.next);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        final GPSTracker gps=new GPSTracker(getApplicationContext());
        double x=gps.getLatitude();
        double y=gps.getLongitude();


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(30.021091,31.2067526);// -
        //mMap.addMarker(new MarkerOptions().position(sydney).title("cairo university")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,7));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("my location")).showInfoWindow();
                Location loc1 = new Location("");
                loc1.setLatitude(30.021091);
                loc1.setLongitude(31.2067526);

                Location loc2 = new Location("");
                loc2.setLatitude(latLng.latitude);
                loc2.setLongitude(latLng.longitude);

                float distanceInMeters = loc1.distanceTo(loc2);
                d= (int) distanceInMeters/1000;

                //Toast.makeText(MapsActivity.this,d+"" , Toast.LENGTH_SHORT).show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences share=getApplicationContext().getSharedPreferences("data",0);
                String fname=share.getString("fname","");
                String lname=share.getString("lname","");
                String email=share.getString("email","");
                String password=share.getString("password","");
                String grade=share.getString("grade","");
                String mobile=share.getString("mobile","");
                 college=share.getString("college","");
                String encoding1=share.getString("personal","");
                String encoding2=share.getString("birth","");
                params=new HashMap<>();
                params.put("fname",fname);
                params.put("lname",lname);
                params.put("email",email);
                params.put("password",password);
                params.put("grade",grade);
                params.put("mobile",mobile);
                params.put("college",college);
                params.put("religion",share.getString("religion",""));
                params.put("image_pers",encoding1);
                params.put("image_birth",encoding2);
                params.put("distance",d+"");

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






               new SendPostRequest().execute();//(fname,lname,email,password,grade,college,mobile,d+"");
                Intent x=new Intent(getApplicationContext(),Congratulation.class);

                startActivity(x);

                //Toast.makeText(MapsActivity.this, d+"", Toast.LENGTH_SHORT).show();

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
                URL url = new URL("http://10.0.3.2:8080/myproject/send.php");//=" + arg0[0] + "&lname=" + arg0[1]+"&email="+arg0[2]+"&password="+arg0[3]+"&grade="+arg0[4]+"&college="+college+"&mobile="+arg0[6]+"&distance="+arg0[7]);


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

package com.example.monte.ondb;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adminfirst extends AppCompatActivity {
    Button add1,add2;
    int count;
    String type;
    String url="http://10.0.3.2:8080/myproject/accept.php";

    RequestQueue requestQueue;
    private ArrayList<String> accept=new ArrayList<String>();
    private ArrayList<String>waiting=new ArrayList<String>();
    ArrayList<String>email=new ArrayList<String>();
    ArrayList<String>room=new ArrayList<String>();
    String build="ABCDE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminfirst);
        Toolbar t=findViewById(R.id.toolbar);
        setSupportActionBar(t);
       SharedPreferences share=getApplicationContext().getSharedPreferences("request",0);
       count= share.getInt("count",4);

          type=share.getString("type","");
           if (count==1){
               Intent x=new Intent(Adminfirst.this,Request.class);
               PendingIntent y=PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(),x,0);

               Notification n=new Notification.Builder(getApplicationContext())
                       .setContentTitle(type)
                       .setSmallIcon(R.drawable.serices)
                       .setContentIntent(y)

                       .build();

               NotificationManager nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
               nm.notify(0,n);
           }
        requestQueue= Volley.newRequestQueue(Adminfirst.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(com.android.volley.Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            JSONArray jsonarray = response.getJSONArray("allstudents");
                            for (int i = 0; i <jsonarray.length(); i++) {
                                JSONObject respons = jsonarray.getJSONObject(i);

                                // String id = respons.getString("id");
                                if(!respons.getString("book").equals("")){
                                    if(i<20){
                                        accept.add(respons.getString("email"));

                                    }
                                    else {
                                        waiting.add(respons.getString("email"));
                                    }
                                }



                                //email.add(emal);
                                //dis=respons.getInt("distance");
                                //distance.add(dis);






                                //text.append("\n" + id + "-" + name + "-" + email + "-" + pass + "\n" + "--------" + "\n");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Adminfirst.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);



        add1=findViewById(R.id.addagency);
        add2=findViewById(R.id.addsuper);
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent x=new Intent(getApplicationContext(),Agency.class);
                startActivity(x);
            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(getApplicationContext(),Supervisor.class);
                startActivity(x);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mail,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.send) {
           String[] TO = new String[accept.size()];
            for (int i=0;i<accept.size();i++){
                TO[i]=accept.get(i);
            }

            Log.i("Send email", "");

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");


            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Student Hostels");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Student,\n" +
                    "We announce you that your request to join the student’s hostel has been accepted, and you are now temporary registered.\n" +
                    "To confirm your registering, you have to submit the following documents to the university students hostel administrator office and pay amount of: 750 LE as monthly fees including food service within 2 weeks from receiving this email. otherwise, your registration will be canceled automatically.\n" +
                    "Required documents:\n" +
                    "National ID \n" +
                    "Birth certification\n" +
                    "High school certification\n" +
                    "Preparatory certification\n" +
                    "\n" +
                    "Student Hostels System\n" +
                    "Cairo University\n");

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                finish();
                Log.i("finished", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(Adminfirst.this,
                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }

        }

        if (item.getItemId() == R.id.waiting) {
            String[] TO = new String[waiting.size()];
            for (int i=0;i<waiting.size();i++){
                TO[i]=waiting.get(i);
            }

            Log.i("Send email", "");

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");


            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Student Hostels");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Student,\n" +
                    "We announce you that according to acceptance priorities, your request to join the student’s hostel has been hold and you are now in a waiting list. \n" +
                    "Wait for a confirmation mail within 3 weeks from receiving this email. Otherwise, unfortunately your request is rejected.\n" +
                    "Regards,\n" +
                    "Student Hostels System\n" +
                    "Cairo University\n");

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                finish();
                Log.i("finished", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(Adminfirst.this,
                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }

        }
        if (item.getItemId() == R.id.confirm) {
            Intent x=new Intent(getApplication(),Confirm.class);
            x.putStringArrayListExtra("array",accept);
            x.putStringArrayListExtra("array1",waiting);
            startActivity(x);


        }
        if (item.getItemId() == R.id.show) {
            Intent x=new Intent(getApplication(),Show_rate.class);
            startActivity(x);


        }
        if (item.getItemId() == R.id.distribute) {
            Intent x=new Intent(getApplicationContext(),Distribution.class);
            startActivity(x);

        }
        return super.onOptionsItemSelected(item);
    }
    public List<String> getaccept() {
        return accept;
    }
    public List<String> getawait() {
        return waiting;
    }
}

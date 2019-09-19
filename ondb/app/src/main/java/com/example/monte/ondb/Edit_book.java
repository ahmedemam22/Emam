package com.example.monte.ondb;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
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

public class Edit_book extends AppCompatActivity {
    Button edit;
    EditText text;
    String url="http://10.0.3.2:8080/myproject/accept.php";
    RequestQueue requestQueue;
    private ArrayList<String>accept=new ArrayList<String>();
    private ArrayList<String>waiting=new ArrayList<String>();

    private ArrayList<Integer>distance=new ArrayList<Integer>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        edit=findViewById(R.id.edit);
        text=findViewById(R.id.text);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(getApplicationContext(),Booking.class);
                startActivity(x);
            }
        });
        requestQueue= Volley.newRequestQueue(Edit_book.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null,
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
                                    else{
                                        waiting.add(respons.getString("email"));
                                    }
                                    distance.add(respons.getInt("distance"));
                                }



                                    //email.add(emal);
                                    //dis=respons.getInt("distance");
                                    //distance.add(dis);






                                //text.append("\n" + id + "-" + name + "-" + email + "-" + pass + "\n" + "--------" + "\n");

                            }
                            Toast.makeText(Edit_book.this, waiting.get(0)+"", Toast.LENGTH_SHORT).show();
                            /*String[] TO = new String[accept.size()];
                            for (int i=0;i<accept.size();i++){
                                TO[i]=accept.get(i);
                            }
                            String[] CC = {"xyz@gmail.com"};
                            Log.i("Send email", "");

                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setData(Uri.parse("mailto:"));
                            emailIntent.setType("text/plain");


                            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                            emailIntent.putExtra(Intent.EXTRA_CC, CC);
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                            emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

                            try {
                                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                                finish();
                                Log.i("finished", "");
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(Edit_book.this,
                                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Edit_book.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
    public List<String> getaccept() {
        return accept;
    }
    public List<String> getawait() {
        return waiting;
    }
}

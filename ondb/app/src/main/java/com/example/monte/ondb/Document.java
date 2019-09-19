package com.example.monte.ondb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Document extends AppCompatActivity {
    RequestQueue requestQueue;
    ImageView image;
    LinearLayout open,close;
    Button edit,document;

   TextView name,email,phone,college;
    public  String   mail="",img="",nam="",emai="",phon="",colleg="",pass;
    ArrayList<String>value=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        Toolbar t=findViewById(R.id.toolbar);
        setSupportActionBar(t);
        open=findViewById(R.id.open);
        close=findViewById(R.id.close);
        new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                close.setVisibility(View.GONE);
                open.setVisibility(View.VISIBLE);

            }
        }.start();
        document=findViewById(R.id.document);
        document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(getApplicationContext(),My_document.class);
                startActivity(x);
            }
        });
edit=findViewById(R.id.editprofile);
edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent x=new Intent(getApplicationContext(),Edit_profile.class);
        startActivity(x);
    }
});

        name=findViewById(R.id.name);
        Toast.makeText(this, name.getText().toString(), Toast.LENGTH_SHORT).show();
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        college=findViewById(R.id.college);
        image=findViewById(R.id.student);


        SharedPreferences share=getApplicationContext().getSharedPreferences("email",0);
        mail=share.getString("email","");
        String url=("http://10.0.3.2:8080/myproject/images.php?email="+mail);

            requestQueue= Volley.newRequestQueue(Document.this);
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONArray jsonarray = response.getJSONArray("allstudents");
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject respons = jsonarray.getJSONObject(i);
                                    img=respons.getString("imagepers");
                                    nam=respons.getString("fname")+" "+respons.getString("lname");
                                    pass=respons.getString("password");

                                    emai=respons.getString("email");
                                    phon=respons.getString("mobile");
                                    colleg=respons.getString("college");
                                    SharedPreferences share=getApplicationContext().getSharedPreferences("student",0);
                                    SharedPreferences.Editor editor=share.edit();
                                    editor.putString("email",emai);
                                    editor.putString("mobile",phon);
                                    editor.putString("password",pass);
                                    editor.commit();
                                    Picasso.get().load("http://10.0.3.2:8080/myproject/imagepers/"+img).into(image);

                                    name.setText(nam);
                                    email.setText(emai);
                                    phone.setText(phon);
                                    college.setText("college: "+colleg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Document.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);














    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout) {
            Intent x=new Intent(Document.this,MainActivity.class);
            startActivity(x);
        }
         if(item.getItemId()==R.id.contact) {
            Intent x=new Intent(Document.this,chat.class);
            startActivity(x);
        }
        if(item.getItemId()==R.id.book) {
            Intent x=new Intent(Document.this,Booking.class);
            startActivity(x);
        }
        if(item.getItemId()==R.id.rate) {
            Intent x=new Intent(Document.this,Rate.class);
            startActivity(x);
        }
        if(item.getItemId()==R.id.browse) {
            Intent x=new Intent(Document.this,Show_adv.class);
            startActivity(x);
        }
        return super.onOptionsItemSelected(item);
    }
}

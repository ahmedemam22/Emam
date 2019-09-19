package com.example.monte.ondb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Agency extends AppCompatActivity {
    EditText text1,text2,text3,text4;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency);
        register=findViewById(R.id.register);
        text1=findViewById(R.id.name);
        text2=findViewById(R.id.email);
        text3=findViewById(R.id.password);
        text4=findViewById(R.id.address);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=text1.getText().toString();
                String email=text2.getText().toString();
                String password=text3.getText().toString();
                String address=text4.getText().toString();
                if(name.equals("")||email.equals("")||password.equals("")||address.equals("")){
                    Toast.makeText(Agency.this, "complete agency data", Toast.LENGTH_SHORT).show();                }
                else{
                    new SendPostRequest().execute(name,email,password,address);



                }

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
                URL url = new URL("http://10.0.3.2:8080/myproject/agency.php?name=" + arg0[0] + "&email=" + arg0[1]+"&password="+arg0[2]+"&address="+arg0[3]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;

                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                return buffer.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result)

        {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
    }
}

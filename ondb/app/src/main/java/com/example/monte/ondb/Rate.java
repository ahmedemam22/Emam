package com.example.monte.ondb;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Rate extends AppCompatActivity {
    RadioGroup super_rate,food,system,service;
    RadioButton radio1,radio2,radio3,radio4;
    private Map<String,String> params;
    StringBuilder sbParams = new StringBuilder();
    Button submit;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        SharedPreferences share=getApplicationContext().getSharedPreferences("email",0);
        email=share.getString("email","");
        super_rate=findViewById(R.id.super_rate);
        service=findViewById(R.id.service);
        food=findViewById(R.id.food);
        system=findViewById(R.id.system);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = super_rate.getCheckedRadioButtonId();
                radio1 = (RadioButton) findViewById(selectedId);
                 int selected = food.getCheckedRadioButtonId();
                radio2 = (RadioButton) findViewById(selected);
                int select = system.getCheckedRadioButtonId();
                radio3 = (RadioButton) findViewById(select);
                int selec = service.getCheckedRadioButtonId();
                radio4 = (RadioButton) findViewById(selec);
                params=new HashMap<>();
                params.put("food_rate",radio2.getText().toString());
                params.put("service",radio4.getText().toString());
                params.put("system",radio3.getText().toString());
                params.put("email",email);




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
                URL url = new URL("http://10.0.3.2:8080/myproject/rate.php");//=" + arg0[0] + "&lname=" + arg0[1]+"&email="+arg0[2]+"&password="+arg0[3]+"&grade="+arg0[4]+"&college="+college+"&mobile="+arg0[6]+"&distance="+arg0[7]);


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

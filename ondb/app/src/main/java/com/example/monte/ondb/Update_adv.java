package com.example.monte.ondb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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

public class Update_adv extends AppCompatActivity {
    private Map<String,String> params;
    StringBuilder sbParams = new StringBuilder();
    Button update;
    TextView text;
    Spinner spin1,spin2;
    String id,start,end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_adv);
        update=findViewById(R.id.publish);
        text=findViewById(R.id.idads);
        spin1=findViewById(R.id.from);
        spin2=findViewById(R.id.to);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                start =position+"".toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                end =position+"".toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=text.getText().toString();
                params=new HashMap<>();
                params.put("id",id);
                params.put("start",start);
                params.put("end",end);



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
                URL url = new URL("http://10.0.3.2:8080/myproject/update_ad.php");//=" + arg0[0] + "&lname=" + arg0[1]+"&email="+arg0[2]+"&password="+arg0[3]+"&grade="+arg0[4]+"&college="+college+"&mobile="+arg0[6]+"&distance="+arg0[7]);


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

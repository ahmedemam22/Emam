package com.example.monte.ondb;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import java.util.List;
import java.util.Map;

public class Confirm extends AppCompatActivity {
    RecyclerView recycler;
    private Map<String,String> params;
    StringBuilder sbParams = new StringBuilder();
    private Recycler_confirm recyclerView_confirm;
    private List<Product> list=new ArrayList<Product>();
    private List<String> value=new ArrayList<String>();
    private List<String> student=new ArrayList<String>();
    private List<String> student1=new ArrayList<String>();

    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        student=getIntent().getStringArrayListExtra("array");
        student1=getIntent().getStringArrayListExtra("array1");
        for (int i=0;i<student.size();i++){
            list.add(new Product(student.get(i),  false));
        }
        for (int i=0;i<student1.size();i++){
            list.add(new Product(student1.get(i),  false));
        }


        recycler=findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),1);
        recycler.setLayoutManager(gridLayoutManager);

        recyclerView_confirm=new Recycler_confirm(list, getApplicationContext());
        recycler.setAdapter(recyclerView_confirm);
        ok=findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=recyclerView_confirm.getList();
                String[]arr=new String[value.size()];
                for (int i=0;i<value.size();i++){
                    arr[i]=value.get(i);

                }
                params=new HashMap<>();
                String ahmed=Arrays.toString(arr);
                Toast.makeText(Confirm.this, ahmed, Toast.LENGTH_SHORT).show();

               params.put("array",ahmed);
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
                String[] TO = new String[25-value.size()];
                for (int j=0;j<TO.length;j++){
                    if(j>=student1.size()){
                        break;
                    }
                    TO[j]=student1.get(j);

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
                    Toast.makeText(Confirm.this,

                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
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
                URL url = new URL("http://10.0.3.2:8080/myproject/array.php");//=" + arg0[0] + "&lname=" + arg0[1]+"&email="+arg0[2]+"&password="+arg0[3]+"&grade="+arg0[4]+"&college="+college+"&mobile="+arg0[6]+"&distance="+arg0[7]);


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

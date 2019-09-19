package com.example.monte.ondb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class chat extends AppCompatActivity {
    private DatabaseReference root;
    private ListView list;
    EditText text;
    Button send;
    private String key;
    private ArrayList<String>data=new ArrayList<String>();
    ArrayAdapter<String>arrayAdapter;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        root=FirebaseDatabase.getInstance().getReference().child("madyna");
        list=findViewById(R.id.list);
        text=findViewById(R.id.text);
        send=findViewById(R.id.send);
        name="ahmed";
        arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.item,data);
        list.setAdapter(arrayAdapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object>map=new HashMap<>();
                key=root.push().getKey();
                root.updateChildren(map);
                DatabaseReference msg=root.child(key);
                Map<String,Object>map2=new HashMap<>();
                map2.put("name",name);
                map2.put("msg",text.getText().toString());
                msg.updateChildren(map2);

            }
        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                add(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
    String chat_msg,chat_name;
    private  void add(DataSnapshot dataSnapshot){
        Iterator i=dataSnapshot.getChildren().iterator();
        text.setText("");
        while (i.hasNext()){
            chat_msg= (String)((DataSnapshot)i.next()).getValue();
            chat_name= (String) ((DataSnapshot) i.next()).getValue();
            data.add(chat_name+" : "+chat_msg);
            arrayAdapter.notifyDataSetChanged();
            list.setSelection(data.size());


        }



    }

}
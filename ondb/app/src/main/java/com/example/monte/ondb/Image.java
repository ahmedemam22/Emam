package com.example.monte.ondb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Image extends AppCompatActivity {
ImageView image;
String link,pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        link = getIntent().getExtras().getString("link","");
        pic=getIntent().getExtras().getString("pers","");
        image=findViewById(R.id.image);
        Picasso.get().load(link+pic).into(image);
    }
}

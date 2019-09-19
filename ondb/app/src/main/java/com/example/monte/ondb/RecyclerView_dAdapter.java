package com.example.monte.ondb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by monte on 6/18/2019.
 */

public class RecyclerView_dAdapter extends RecyclerView.Adapter<RecyclerView_dAdapter.ViewHolder> {
    private List<List_Item>list;
    private Context context;
    public RecyclerView_dAdapter(List<List_Item> list, Show_adv context){
        this.list=list;
        this.context= (Context) context;



    }
    @NonNull
    @Override
    public RecyclerView_dAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_dAdapter.ViewHolder holder, int position) {
        holder.room.setText(list.get(position).getRoom());
        holder.floor.setText(list.get(position).getFloor());
        holder.price.setText(list.get(position).getPrice());
        //holder.image.setImageResource(list.get(position).getImage());
        Picasso.get().load(list.get(position).getImage()).into(holder.image);

//"http://10.0.3.2:8080/myproject/adv/"+list.get(position).getImage()


    }

    @Override
    public int getItemCount() {
        return (null!=list?list.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView card;
        private ImageView image;
        private TextView room;
        private TextView floor;
        private TextView price;



        public ViewHolder(View itemView) {
            super(itemView);
            card=itemView.findViewById(R.id.card);
            image=itemView.findViewById(R.id.img);
            room=itemView.findViewById(R.id.room);
            floor=itemView.findViewById(R.id.floor);
            price=itemView.findViewById(R.id.price);

        }
    }
}

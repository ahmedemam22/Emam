package com.example.monte.ondb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monte on 6/21/2019.
 */

public class Recycler_confirm extends RecyclerView.Adapter<Recycler_confirm.ViewHolder> {
    private List<Product> list;
    private Context context;
    private List<String> value;
    public Recycler_confirm(List<Product> list, Context context){
        value=new ArrayList<String>();
        this.list=list;
                this.context=context;

    }
    @NonNull
    @Override
    public Recycler_confirm.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.confirming,parent,false);
        Recycler_confirm.ViewHolder viewHolder=new Recycler_confirm.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Recycler_confirm.ViewHolder holder, int position) {
        Product filterM = list.get(position);
        holder.email.setText(filterM.getemail());
        if (filterM.getselect()) {
            holder.selectionState.setChecked(true);
        } else {
            holder.selectionState.setChecked(false);
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView email;
        private CheckBox selectionState;
        public ViewHolder(View itemView) {
            super(itemView);
            email=itemView.findViewById(R.id.email);
            selectionState=itemView.findViewById(R.id.check);
            selectionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    if (isChecked) {
                        Toast.makeText(Recycler_confirm.this.context, email.getText(), Toast.LENGTH_SHORT).show();
                        value.add(email.getText().toString());

                    }
                }
            });

        }
    }
    public List<String> getList() {
        return value;
    }
}

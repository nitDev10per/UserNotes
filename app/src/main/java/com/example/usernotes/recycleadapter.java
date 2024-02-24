package com.example.usernotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycleadapter extends RecyclerView.Adapter<recycleadapter.viewHolder> {

    Context context;
    ArrayList<dataClass> arr;
    ViewNote vn;

    recycleadapter(Context context, ArrayList<dataClass> arr,ViewNote vn){
        this.context=context;
        this.arr=arr;
        this.vn=vn;
    }

    @NonNull
    @Override
    public recycleadapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.box,parent,false);
        viewHolder viewholder=new viewHolder(v);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull recycleadapter.viewHolder holder, int position) {
        final int pos=position;
        holder.txt.setText(arr.get(position).txt);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vn.removeN(pos);
            }
        });
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vn.viewNote(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txt;
        CardView card;
        Button btn;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
           img=itemView.findViewById(R.id.imageView);
           txt=itemView.findViewById(R.id.textTitle);
           card=itemView.findViewById(R.id.card);
           btn=itemView.findViewById(R.id.delete);
        }
    }
}

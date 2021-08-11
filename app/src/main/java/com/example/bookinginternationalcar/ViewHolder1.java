package com.example.bookinginternationalcar;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder1 extends RecyclerView.ViewHolder {


    public ViewHolder1(@NonNull View itemView) {
        super(itemView);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClicklistener.onItemlongClick(view,getAdapterPosition());
                return false;
            }
        });
    }

    private  ViewHolder1.Clicklistener mClicklistener;

    public void setData(Context applicationContext, String name, String email, String initial, String Date, String destination, String Time,String pass) {
        TextView textView = itemView.findViewById(R.id.hell);

        textView.setText("Name: " + name + "\n"  + "Email: "  + email +"\n"+ "Coming From: " + initial + "\n"+"Date:" +Date +"\n" + "place you are going to:" + destination +"\n" + "Time:" +Time+"\n"+"Passport"+pass);
    }

    public interface Clicklistener{
        void onItemlongClick(View view , int position);
    }

    public void setOnClickListener(ViewHolder1.Clicklistener clickListener){
        mClicklistener = (Clicklistener) clickListener;
    }

}
package com.example.planner;
import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ViewHolder> {
    ArrayList<Day> search, days ;
    ArrayList<UserType> types;
    Context context;

    public searchAdapter ( ArrayList<Day>search,ArrayList<Day>days,ArrayList<UserType> types, Context context){
        this.search=search;
        this.days=days;
        this.context=context;
        this.types=types;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return  new ViewHolder(v);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        CardView cardView;
        ViewHolder( View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.findDay);
            cardView=itemView.findViewById(R.id.cv);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Day day =search.get(position);
        holder.textView.setText(day.getDayName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lookIntent = new Intent(context,MainActivity.class);
                lookIntent.putExtra("pos",day.getIndex());
                Log.d("find day",Integer.toString(position));
                lookIntent.putParcelableArrayListExtra("days",days);
                lookIntent.putParcelableArrayListExtra("types",types);
                lookIntent.setAction("find");
                context.startActivity(lookIntent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return search.size();
    }
}

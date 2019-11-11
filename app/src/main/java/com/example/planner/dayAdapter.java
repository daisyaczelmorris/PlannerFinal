package com.example.planner;
import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class dayAdapter extends RecyclerView.Adapter<dayAdapter.ViewHolder> {
    ArrayList<Day> dayList;
    ArrayList<UserType> types;
    Context context;

    public dayAdapter (ArrayList<Day>dayList, ArrayList<UserType> types, Context context){
        this.dayList=dayList;
        this.context=context;
        this.types=types;

    }

    @Override

    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
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
        Day day=dayList.get(position);
        holder.textView.setText(day.getDayName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lookIntent = new Intent(context,MainActivity.class);
                lookIntent.putExtra("pos",position);
                Log.d("find day",Integer.toString(position));
                lookIntent.putParcelableArrayListExtra("days",dayList);
                lookIntent.putParcelableArrayListExtra("types",types);
                lookIntent.setAction("find");
                context.startActivity(lookIntent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return dayList.size();
    }
}

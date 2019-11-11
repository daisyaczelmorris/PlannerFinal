package com.example.planner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FindDay extends AppCompatActivity {
    private ArrayList<Day> days;
    private ArrayList<Day> filteredDays=new ArrayList<>();
    private ArrayList<UserType>types;
    private RecyclerView recyclerView;
    private EditText search;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_day_layout);
        init();
    }
    private void init(){
        search= findViewById(R.id.search);
        Intent intent=getIntent();
        days=intent.getParcelableArrayListExtra("days");
        types=intent.getParcelableArrayListExtra("types");
        adapter = new dayAdapter(days,types,this);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }


    public void Search(View view) {
        String s = search.getText().toString();
        filteredDays=new ArrayList<>();
        if(s!=null){
            for(Day d:days){
                String dayName = d.getDayName();
                if(dayName.matches(s)){
                    filteredDays.add(d);
                    Log.d("filteredDays","add");
                }
            }
        }
        if(filteredDays.size()!=0){
            adapter = new searchAdapter(filteredDays,days,types,this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
        else{
            Toast.makeText(getBaseContext(), "Couldn't Find "+s, Toast.LENGTH_SHORT).show();

        }

    }
}

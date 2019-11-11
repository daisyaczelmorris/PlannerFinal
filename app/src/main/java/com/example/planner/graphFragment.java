package com.example.planner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

 public class graphFragment extends Fragment {
     BarChart barChart;
     BarData barData;
     BarDataSet barDataSet;
     ArrayList barEntries;

     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_graph, container, false);

         return view;
     }
     private void getEntries() {

         MainActivity mainActivity=(MainActivity) getActivity();
         double[] entries= new double[mainActivity.getUserTypes().size()];

         for(Day d: mainActivity.getUserDays()){
             for(UserActivity act: d.getActivities()){
                 double entry =act.getdHour()+(act.getdMin()/1);
                 int type = act.getType().getIndex();
                 //dealing with default
                 if(type==-1){}
                 else {
                     entries[type] = entries[type] + entry;
                 }
             }
         }
         barEntries = new ArrayList<>();
         int i=0;
        for (double e:entries){
            barEntries.add(new BarEntry((float) i,(float) e));
            i++;
        }





     }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

         barChart = getView().findViewById(R.id.BarChart);
         getEntries();
         barDataSet = new BarDataSet(barEntries, "");
         barData = new BarData(barDataSet);
         barChart.setData(barData);
         setColors();
         barDataSet.setValueTextColor(Color.BLACK);
         barDataSet.setValueTextSize(18f);
         super.onViewCreated(view, savedInstanceState);
     }

     private void setColors() {
         MainActivity m=(MainActivity) getActivity();
         int[]colors=new int[12];
         colors[0]= getResources().getColor(R.color.c1);
         colors[1]=getResources().getColor(R.color.c2);
         colors[2]=getResources().getColor(R.color.c3);
         colors[3]=getResources().getColor(R.color.c4);
         colors[4]=getResources().getColor(R.color.c5);
         colors[5]=getResources().getColor(R.color.c6);
         colors[6]=getResources().getColor(R.color.c7);
         colors[7]=getResources().getColor(R.color.c8);
         colors[8]=getResources().getColor(R.color.c9);
         colors[9]=getResources().getColor(R.color.c10);
         colors[10]=getResources().getColor(R.color.c11);
         colors[11]=getResources().getColor(R.color.c12);
         int[]typeColors=new int[m.getUserTypes().size()];
         int j=0;
         for(int i=0;i<12;i++){

             for(int k=0;k<m.getUserTypes().size();k++){
                 String s= "#" + Integer.toHexString(colors[i]);


                 String s1 ="#"+Integer.toHexString( m.getUserTypes().get(k).getColour());

                 if(s.matches(s1)){
                     typeColors[j]=colors[i];
                     j++;
                 }

             }


         }

         barDataSet.setColors(typeColors);


         }


     }




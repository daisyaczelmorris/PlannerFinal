package com.example.planner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddActActivity extends AppCompatActivity {
   private int dayIndex,actType, editIndex,actIndex;
   private boolean edit=false,typeset=false;
   private ArrayList<Day> d;
   private ArrayList<UserType>userTypes=new ArrayList<>();
   private ArrayList<TextView>textViews=new ArrayList<>();
   private UserType defaultType = new UserType("", 333333333, -1);
   private EditText actName;
   private Spinner startH,startMin,hours,mins;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_act_);
        //init text views for types
         actName=findViewById(R.id.actName);
         startH= findViewById(R.id.startHour);
         startMin = findViewById(R.id.startMinute);
         hours =findViewById(R.id.dHours);
         mins =findViewById(R.id.dMins);
        textViews.add((TextView) findViewById(R.id.t1));
        textViews.add((TextView) findViewById(R.id.t2));
        textViews.add((TextView) findViewById(R.id.t3));
        textViews.add((TextView) findViewById(R.id.t4));
        textViews.add((TextView) findViewById(R.id.t5));
        textViews.add((TextView) findViewById(R.id.t6));
        textViews.add((TextView) findViewById(R.id.t7));
        textViews.add((TextView) findViewById(R.id.t8));
        textViews.add((TextView) findViewById(R.id.t9));
        textViews.add((TextView) findViewById(R.id.t10));
        textViews.add((TextView) findViewById(R.id.t11));
        textViews.add((TextView) findViewById(R.id.t12));
        //comming from main or add get types,day index & days, update types
        Intent intent = getIntent();
        String s = intent.getAction();
        userTypes=intent.getParcelableArrayListExtra("types");
        d=intent.getParcelableArrayListExtra("days");
        dayIndex =intent.getExtras().getInt("index");
        if(s=="addtype"){
            actType=intent.getExtras().getInt("typeIndex");
            if(actType==-1)typeset=false;
            else{ typeset=true;}
            edit=intent.getExtras().getBoolean("edit");
            actName.setText(intent.getExtras().getString("name"));
            startH.setSelection(intent.getExtras().getInt("sH"));
            startMin.setSelection(intent.getExtras().getInt("sM"));
            hours.setSelection(intent.getExtras().getInt("dH"));
            mins.setSelection(intent.getExtras().getInt("dM"));

        }
        if(s=="editact"){

            Button b=findViewById(R.id.delete);
            b.setVisibility(View.VISIBLE);
             editIndex = intent.getExtras().getInt("actIndex");
             actIndex=0;
            for(int i=0;i<d.get(dayIndex).getActivities().size();i++){
                if(d.get(dayIndex).getActivities().get(i).getStartH()*4+d.get(dayIndex).getActivities().get(i).getStartM()== editIndex){
                    actIndex= i;
                    actName.setText(d.get(dayIndex).getActivities().get(i).getName());
                    startH.setSelection(d.get(dayIndex).getActivities().get(i).getStartH());
                    startMin.setSelection(d.get(dayIndex).getActivities().get(i).getStartM());
                    hours.setSelection(d.get(dayIndex).getActivities().get(i).getdHour());
                    mins.setSelection(d.get(dayIndex).getActivities().get(i).getdMin());
                    actType=d.get(dayIndex).getActivities().get(i).getType().getIndex();
                    if(actType!=-1)typeset=true;
                    edit=true;


                }
            }


        }

        updateTypes();
    }
//set colour for types
    private void updateTypes() {
        for(int i=0;i<userTypes.size();i++) {
           textViews.get(i).setBackgroundColor(userTypes.get(i).getColour());
           textViews.get(i).setText(userTypes.get(i).getName());
        }
    }
   //when add type button clicked start addType activity
    public void addType(View view) {

        int sHour,sMin,dhours,dMin;
        String aName;
        sHour=startH.getSelectedItemPosition();
        sMin=startMin.getSelectedItemPosition();
        dhours=hours.getSelectedItemPosition();
        dMin=mins.getSelectedItemPosition();
        aName=actName.getText().toString();

        Intent addType = new Intent(this,AddTypeActivity.class);
        addType.putExtra("index", dayIndex);
        addType.putExtra("typeindex",actType);
        addType.putExtra("edit",edit);
        addType.putParcelableArrayListExtra("days",d);
        addType.putParcelableArrayListExtra("types",userTypes);
        addType.putExtra("sH",sHour);
        addType.putExtra("sM",sMin);
        addType.putExtra("dH",dhours);
        addType.putExtra("dM",dMin);
        addType.putExtra("name",aName);
        startActivity(addType);
    }
//when valid type clicked type Index assigned type index
    public void SelectType(View view) {

        String s=view.getTransitionName();
        if(Integer.parseInt(s)<userTypes.size()) {
            actType = Integer.parseInt(s);
            typeset = true;
        }

    }


    public void Done(View view) {

        int sHour,sMin,dhours,dMin;
        String aName;
        sHour=startH.getSelectedItemPosition();
        sMin=startMin.getSelectedItemPosition();
        dhours=hours.getSelectedItemPosition();
        dMin=mins.getSelectedItemPosition();
        aName=actName.getText().toString();
        aName=aName.replaceAll(",","");
        boolean setAct=true;
        if(dhours+dMin!=0&&!aName.equals("")) {
            int actEndCode = sHour * 4 + sMin + dhours * 4 + dMin;
            int actStartCode= sHour*4+sMin;
            if( actEndCode <37) {
                for(UserActivity a: d.get(dayIndex).getActivities()){
                    int aStartCode= a.getStartH()*4+a.getStartM();
                    int aEndCode= aStartCode+a.getdHour()*4+a.getdMin();

                    if((aStartCode> actStartCode && aStartCode<actEndCode)||(aEndCode> actStartCode&&aEndCode<actEndCode)){
                        setAct=false;
                        Toast.makeText(getBaseContext(), "You already have an activity at that time", Toast.LENGTH_SHORT).show();
                    }


                }
                if(setAct==true) {
                    UserType type;
                    if (!typeset) {
                        type = defaultType;
                    } else {
                        type = userTypes.get(actType);
                    }
                    UserActivity act = new UserActivity(sHour, sMin, dhours, dMin, aName, type);
                    if (edit) {
                        Log.d("edit", "here");
                        d.get(dayIndex).getActivities().set(actIndex, act);
                    } else {
                        d.get(dayIndex).addActivity(act);
                    }
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setAction("add");
                    intent.putExtra("index", dayIndex);
                    intent.putParcelableArrayListExtra("types", userTypes);
                    intent.putParcelableArrayListExtra("days", d);
                    startActivity(intent);
                }

            }
            else {
                Toast.makeText(getBaseContext(), "Your start time and duration goes beyond the available times", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(getBaseContext(), "You did not enter an Act Name Or duration was zero", Toast.LENGTH_SHORT).show();


        }

    }


    public void Delete(View view) {
        //UsersActs should have index for this
        d.get(dayIndex).getActivities().remove(actIndex);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction("add");
        intent.putExtra("index", dayIndex);
        intent.putParcelableArrayListExtra("types", userTypes);
        intent.putParcelableArrayListExtra("days", d);
        startActivity(intent);


    }
}

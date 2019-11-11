package com.example.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements OnGestureListener {
    private graphFragment graph;
    private static final String file="days70.txt";
    private static int totalDays=0;
    private int currentDay,startTime=6;
    private TextView[] allTextViews =new TextView[36];
    private TextView[] allsTextViews = new TextView[36];
    private TextView[] textViewsHours=new TextView[9];
    private  TextView dayMessage;
    private ArrayList<Day> userDays = new ArrayList<>();
    private ArrayList <UserType>userTypes=new ArrayList<>();
    private  UserType defaultType = new UserType("", 333333333, -1);
    private GestureDetector gestureDetector;
    private ViewPager pager;

    public ArrayList<UserType> getUserTypes() {
        return userTypes;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    //grounds for D/HD not taught to consider back stack
    private void init(){
        gestureDetector = new GestureDetector(MainActivity.this, MainActivity.this);
        pager =findViewById(R.id.container);
        setupViewPager(pager);
        pager.setVisibility(View.INVISIBLE);
        dayMessage = findViewById(R.id.dayMessage);
        loadTvs();
        //if coming from find or addAct get extras, update current day & file
        if(!isTaskRoot()){
            Intent i = getIntent();
            String s = i.getAction();
            //find day
            if(s=="find") {
                userDays = getIntent().getParcelableArrayListExtra("days");
                userTypes=getIntent().getParcelableArrayListExtra("types");
                //get position of day chosen
                int pos = getIntent().getIntExtra("pos", 0);
                //changes view to chosen day
                updateCurrentDay(pos);
                //writes new current day
                Write();
            }
            //add act
            if(s=="add"){
                //gets index of current day with new act
                int day= getIntent().getExtras().getInt("index");
                userDays=getIntent().getParcelableArrayListExtra("days");
                //changes view to current day & makes new activity visible
                userTypes=getIntent().getParcelableArrayListExtra("types");
                updateCurrentDay(day);
                //writes new act
                Write();
            }

        }
        //reads from file on first create
        else{
            Read();
        }
        //if user has no days yet, set message
        if (getUserDays().isEmpty()) {

            dayMessage.setText("You don't have any days");
        }

    }
  private void loadTvs(){
            textViewsHours[0] = findViewById(R.id.tv1s);
            textViewsHours[1] =  findViewById(R.id.tv2s);
            textViewsHours[2] =  findViewById(R.id.tv3s);
            textViewsHours[3] =  findViewById(R.id.tv4s);
            textViewsHours[4] =  findViewById(R.id.tv5s);
            textViewsHours[5] =  findViewById(R.id.tv6s);
            textViewsHours[6] =  findViewById(R.id.tv7s);
            textViewsHours[7] =  findViewById(R.id.tv8s);
            textViewsHours[8] =  findViewById(R.id.tv9s);

            allTextViews[0] = findViewById(R.id.tv1);
            allTextViews[1] = findViewById(R.id.tv1_1);
            allTextViews[2] = findViewById(R.id.tv1_2);
            allTextViews[3] = findViewById(R.id.tv1_3);
            allTextViews[4] = findViewById(R.id.tv2);
            allTextViews[5] = findViewById(R.id.tv2_1);
            allTextViews[6] = findViewById(R.id.tv2_2);
            allTextViews[7] = findViewById(R.id.tv2_3);
            allTextViews[8] = findViewById(R.id.tv3);
            allTextViews[9] = findViewById(R.id.tv3_1);
            allTextViews[10] = findViewById(R.id.tv3_2);
            allTextViews[11] = findViewById(R.id.tv3_3);
            allTextViews[12] =findViewById(R.id.tv4);
            allTextViews[13] = findViewById(R.id.tv4_1);
            allTextViews[14] = findViewById(R.id.tv4_2);
            allTextViews[15] = findViewById(R.id.tv4_3);
            allTextViews[16] = findViewById(R.id.tv5);
            allTextViews[17] = findViewById(R.id.tv5_1);
            allTextViews[18] = findViewById(R.id.tv5_2);
            allTextViews[19] = findViewById(R.id.tv5_3);
            allTextViews[20] = findViewById(R.id.tv6);
            allTextViews[21] = findViewById(R.id.tv6_1);
            allTextViews[22] = findViewById(R.id.tv6_2);
            allTextViews[23] = findViewById(R.id.tv6_3);
            allTextViews[24] = findViewById(R.id.tv7);
            allTextViews[25] = findViewById(R.id.tv7_1);
            allTextViews[26] = findViewById(R.id.tv7_2);
            allTextViews[27] = findViewById(R.id.tv7_3);
            allTextViews[28] = findViewById(R.id.tv8);
            allTextViews[29] = findViewById(R.id.tv8_1);
            allTextViews[30] = findViewById(R.id.tv8_2);
            allTextViews[31] = findViewById(R.id.tv8_3);
            allTextViews[32] = findViewById(R.id.tv9);
            allTextViews[33] = findViewById(R.id.tv9_1);
            allTextViews[34] = findViewById(R.id.tv9_2);
            allTextViews[35] = findViewById(R.id.tv9_3);

            allsTextViews[0] = findViewById(R.id.tv1s);
            allsTextViews[1] = findViewById(R.id.tv1_1s);
            allsTextViews[2] = findViewById(R.id.tv1_2s);
            allsTextViews[3] = findViewById(R.id.tv1_3s);
            allsTextViews[4] = findViewById(R.id.tv2s);
            allsTextViews[5] = findViewById(R.id.tv2_1s);
            allsTextViews[6] = findViewById(R.id.tv2_2s);
            allsTextViews[7] = findViewById(R.id.tv2_3s);
            allsTextViews[8] = findViewById(R.id.tv3s);
            allsTextViews[9] = findViewById(R.id.tv3_1s);
            allsTextViews[10] = findViewById(R.id.tv3_2s);
            allsTextViews[11] = findViewById(R.id.tv3_3s);
            allsTextViews[12] = findViewById(R.id.tv4s);
            allsTextViews[13] = findViewById(R.id.tv4_1s);
            allsTextViews[14] = findViewById(R.id.tv4_2s);
            allsTextViews[15] = findViewById(R.id.tv4_3s);
            allsTextViews[16] = findViewById(R.id.tv5s);
            allsTextViews[17] = findViewById(R.id.tv5_1s);
            allsTextViews[18] = findViewById(R.id.tv5_2s);
            allsTextViews[19] = findViewById(R.id.tv5_3s);
            allsTextViews[20] = findViewById(R.id.tv6s);
            allsTextViews[21] = findViewById(R.id.tv6_1s);
            allsTextViews[22] = findViewById(R.id.tv6_2s);
            allsTextViews[23] = findViewById(R.id.tv6_3s);
            allsTextViews[24] = findViewById(R.id.tv7s);
            allsTextViews[25] = findViewById(R.id.tv7_1s);
            allsTextViews[26] = findViewById(R.id.tv7_2s);
            allsTextViews[27] = findViewById(R.id.tv7_3s);
            allsTextViews[28] = findViewById(R.id.tv8s);
            allsTextViews[29] = findViewById(R.id.tv8_1s);
            allsTextViews[30] = findViewById(R.id.tv8_2s);
            allsTextViews[31] = findViewById(R.id.tv8_3s);
            allsTextViews[32] = findViewById(R.id.tv9s);
            allsTextViews[33] = findViewById(R.id.tv9_1s);
            allsTextViews[34] = findViewById(R.id.tv9_2s);
            allsTextViews[35] = findViewById(R.id.tv9_3s);
            for(TextView tv:allsTextViews){
                tv.bringToFront();
            }
    }
    private void setupViewPager(ViewPager pager){
        FragmentClass adapter=new FragmentClass(getSupportFragmentManager());
        graphFragment t= new graphFragment();
        adapter.addFragment(t);
        pager.setAdapter(adapter);
        graph = (graphFragment) adapter.getItem(0);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void Read(){
        String text;
        FileInputStream inputStream =null;
        StringBuilder stringBuilder = new StringBuilder();
        {
            try {
                inputStream = openFileInput(file);
                Log.d("",Integer.toString(inputStream.available()));

                    InputStreamReader streamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(streamReader);
                    while ((text = bufferedReader.readLine()) != null) {
                        stringBuilder.append(text);
                    }
                    String[] userData = stringBuilder.toString().split(",");
                    splitArray(userData);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //take array of all saved data and transform into list of days with activities
    private void splitArray(String[] dayData){
        Log.d("",dayData.toString());
        //set current day and total days
        totalDays=Integer.parseInt(dayData[0]);
        currentDay=Integer.parseInt(dayData[1]);
        Integer nTypes=Integer.parseInt(dayData[2]);
        Integer typeColour=3;
        Integer typeName=4;
        for(int i=0;i<nTypes;i++){
            Integer colour = Integer.parseInt(dayData[typeColour]);
            String name = dayData[typeName];
            UserType t = new UserType(name,colour,i);
            userTypes.add(t);
            typeColour=typeColour+2;
            typeName=typeName+2;
        }
        //set index for other values
        int dayName=typeColour;
        int dayIndex=typeName;
        int actNum=typeName+1;

        int actName=typeName+2;
        int actType=typeName+3;
        int actStartH=typeName+4;
        int actStartM =typeName+5;
        int actdH=typeName+6;
        int actdM=typeName+7;
        for(int i =0;i<totalDays;i++){
            //create new day

            Day d= new Day(dayData[dayName],Integer.parseInt(dayData[dayIndex]));
            int nActs=Integer.parseInt(dayData[actNum]);
               for (int j = 0; j < nActs; j++) {
                   //add acts to day

                    int typecheck = Integer.parseInt(dayData[actType]);
                   UserType t;
                    if(typecheck!=-1) {
                        t = userTypes.get(Integer.parseInt(dayData[actType]));
                    }
                    else{
                        t = defaultType;
                    }
                       UserActivity act = new UserActivity(Integer.parseInt(dayData[actStartH]), Integer.parseInt(dayData[actStartM]), Integer.parseInt(dayData[actdH]), Integer.parseInt(dayData[actdM]), dayData[actName],t);
                       d.addActivity(act);
                       //increment actName&Start by two for each act (two boxes for each act)
                       actName = actName + 6;
                       actStartH = actStartH + 6;
                       actType=actType+6;
                       actdM=actdM+6;
                       actdH=actdH+6;
                       actStartM=actStartM+6;
               }
            //add new day to userDays
            userDays.add(d);
            //increment actName&Start by 3 (3 boxes for each day)
            actName=actName+3;
            actStartH=actStartH+3;
            actType=actType+3;
            actdM=actdM+3;
            actdH=actdH+3;
            actStartM=actStartM+3;
            //increment dayName,dayIndex& ActNum by 3 (3 boxes for each day)+2*NActs(2 boxes for every act)
            dayName=dayName+3+(nActs*6);
            dayIndex=dayIndex+3+(nActs*6);
            actNum=actNum+3+(nActs*6);
           }
        //set view to current day
        if(userDays.size()!=0)updateCurrentDay(currentDay);
        else{
            for (TextView tv : allsTextViews) {
                tv.setText("");
            }
            dayMessage.setText("You don't have any days");}

    }

    private void Write(){
        FileOutputStream fos=null;
        try {
            fos=openFileOutput(file,MODE_PRIVATE);
            fos.write(Integer.toString(totalDays).getBytes());
            fos.write(',');
            fos.write(Integer.toString(currentDay).getBytes());
            fos.write(',');
            fos.write(Integer.toString(userTypes.size()).getBytes());
            fos.write(',');
            for (UserType t:userTypes){
                fos.write(Integer.toString(t.getColour()).getBytes());
                fos.write(',');
                fos.write(t.getName().getBytes());
                fos.write(',');
            }
            for(Day d:userDays){
                    fos.write(d.getDayName().getBytes());
                    fos.write(',');
                    fos.write(Integer.toString(d.getIndex()).getBytes());
                    fos.write(',');
                    fos.write(Integer.toString(d.getActivities().size()).getBytes());
                    fos.write(',');
                    for(UserActivity u:d.getActivities()) {
                        fos.write(u.getName().getBytes());
                        fos.write(',');
                        fos.write(Integer.toString(u.getType().getIndex()).getBytes());
                        fos.write(',');
                        fos.write(Integer.toString(u.getStartH()).getBytes());
                        fos.write(',');
                        fos.write(Integer.toString(u.getStartM()).getBytes());
                        fos.write(',');
                        fos.write(Integer.toString(u.getdHour()).getBytes());
                        fos.write(',');
                        fos.write(Integer.toString(u.getdMin()).getBytes());
                        fos.write(',');



                    }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    //drop down menu click handler
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //

            //Change Day Name Selected
            case R.id.changeDayName:
                ChangeDayName();
                return true;
            //Delete Day Selected DeleteDay called
            case R.id.deleteDay:
                DeleteDay();
                return true;
            //Add Day selected AddDay Called
            case R.id.addDay:
                AddDay();
                return true;
            //Find Day Selected, Start Find Day Activity
            case R.id.findDay:
                Intent findIntent = new Intent(this, FindDay.class);
                findIntent.putParcelableArrayListExtra("days",userDays);
                findIntent.putParcelableArrayListExtra("types",userTypes);
                startActivity(findIntent);
                return true;
            //Add Activity Selected, Start AddAct Intent
            case R.id.AddAct:
                Intent addActIntent= new Intent(this, AddActActivity.class);
                //only add act if there is at least 1 day
                if(totalDays>0) {
                    addActIntent.putExtra("index",currentDay);
                    addActIntent.putParcelableArrayListExtra("days", userDays);
                    addActIntent.setAction("addact");
                    addActIntent.putParcelableArrayListExtra("types",userTypes);
                    startActivity(addActIntent);
                    return true;
                }
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ChangeDayName(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Enter new name for "+userDays.get(currentDay).getDayName());
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String dayName=input.getText().toString();
                if(!dayName.matches("")) {
                    userDays.get(currentDay).setDayName(dayName);
                    updateCurrentDay(currentDay);
                    Write();
                }
                else
                {
                    Toast.makeText(getBaseContext(), "You did not enter a Day Name", Toast.LENGTH_SHORT).show();
                    ChangeDayName();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }

        });
        builder.show();



    }

    private void DeleteDay(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are You sure You Want to Delete "+userDays.get(currentDay).getDayName());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userDays.remove(currentDay);
                totalDays--;
                    if (currentDay == 0) {
                        updateCurrentDay(currentDay + 1);
                    } else {
                        updateCurrentDay(currentDay - 1);
                    }
                Write();

            }

        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void AddDay(){
        //new alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Day Name");
        //Text Input for new day Name
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //new day Created with textInput & totalDays As index
                String dayName=input.getText().toString();
                dayName=dayName.replaceAll(",","");

                if(!dayName.matches("")) {
                    dayName = input.getText().toString();
                    Day d = new Day(dayName, totalDays);
                    userDays.add(d);
                    //current day set to new day
                    totalDays++;
                    updateCurrentDay(totalDays-1);
                    //reset background colour for new day
                    for (TextView tv : allTextViews) {
                        tv.setBackgroundColor(255);
                        tv.setText("");
                    }
                    addTimes();
                    //total days incremented
                    //write new day to file
                    Write();
                }
                else{
                    Toast.makeText(getBaseContext(), "You did not enter a Day Name", Toast.LENGTH_SHORT).show();
                    AddDay();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

   //sets the the textViews text to times 6:00-2:00
    private void addTimes(){
        int i=startTime;
        String time=" am";
        for(TextView tv: textViewsHours){
            tv.setText(Integer.toString(i)+":00"+time);
           if(i==12)i=0;
           if(i==11)time=" pm";
           i++;
        }
    }
    private void ClearScreen(){

        for (TextView tv : allsTextViews) {
            tv.setText("");
        }
        for (TextView tv : allTextViews) {
            tv.setBackgroundColor(255);
        }
        addTimes();
    }
    //updates the views to day in userDays using dayIndex
    private void updateCurrentDay(int dayIndex){
        if (totalDays>0) {
            //update currentDay
            currentDay = dayIndex;
            //set dayName text
            ClearScreen();
            TextView dayMessage = findViewById(R.id.dayMessage);
            dayMessage.setText(userDays.get(currentDay).getDayName());
            //add times
            //If current Day has activities call addActivity
            if (userDays.get(currentDay).getActivities().size() != 0) {
                addActivitys(userDays.get(currentDay));
            }
        }
        else{
            for (TextView tv : allsTextViews) {
                tv.setText("");
            }
            dayMessage.setText("You don't have any days");
        }
    }
    //updates textviews with a days userActvities
    private void addActivitys(Day day){
        //get days activities
        List<UserActivity> acts = day.getActivities();
        //for each activity if act.startHour=textBox pos(j)
        // update that textBox text& colour
        for(UserActivity act:acts){
            int startH,startM,durationH,durationM;
            startH=act.getStartH();
            startM=act.getStartM();
            durationM=act.getdMin();
            durationH=act.getdHour();
            int sTime= (startH*4)+startM;
            int duration= (durationH*4)+durationM;
            Log.d("text",allsTextViews[sTime].getText().toString());
            if(allsTextViews[sTime].getText().toString().equals("")){
                Log.d("empty tvs","here");
                allsTextViews[sTime].append("                 "+act.getName()+"  "+act.getType().getName());
            }
           else{  Log.d("full tvs","here");
               allsTextViews[sTime].append("  "+act.getName()+"  "+act.getType().getName());}

            for(int i=sTime;i<sTime+duration;i++){
                TextView tv= allTextViews[i];
                tv.setBackgroundColor(act.getType().getColour());
            }

        }
    }
    public ArrayList<Day> getUserDays() {
        return userDays;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        // TODO Auto-generated method stub

        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if (motionEvent.getX() - motionEvent1.getX() > 50) {

            if (currentDay < totalDays-1) {
                updateCurrentDay(currentDay + 1);
                return true;
            }


            return true;
        }

        if (motionEvent1.getX() - motionEvent.getX() > 50) {

            if (currentDay != 0) {
                updateCurrentDay(currentDay - 1);
                return true;
            }
        }


        return true;
    }

    public void EditAct(View view) {
        int tvid=view.getId();
        int startIndex=0;
        for(int i=0;i<allsTextViews.length;i++){

            if( tvid== allsTextViews[i].getId()
            ){
                startIndex=i;
            }
        }

        for (UserActivity u: userDays.get(currentDay).getActivities()){
            if (u.getStartH()*4+u.getStartM()==startIndex){
                Intent editAct = new Intent(this, AddActActivity.class);
                editAct.putExtra("index",currentDay);
                editAct.putParcelableArrayListExtra("days", userDays);
                editAct.setAction("editact");
                editAct.putExtra("actIndex",u.getStartH()*4+u.getStartM());
                editAct.putParcelableArrayListExtra("types",userTypes);
                startActivity(editAct);

            }

        }

    }

    public void ShowGraph(View view) {
        boolean state =((ToggleButton) view).isChecked();
        if(state) {
            for (TextView tv : allsTextViews) {
                tv.setText("");
            }
            pager.setVisibility(View.VISIBLE);
        }
        else{
            pager.setVisibility(View.INVISIBLE);
            updateCurrentDay(currentDay);
        }

    }
}

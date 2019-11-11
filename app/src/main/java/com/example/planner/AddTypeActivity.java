package com.example.planner;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddTypeActivity extends AppCompatActivity {
    String typeName;
    int typeColour = 0, dayindex, sH, sM, dH, dM, currentTypeIndex;
    String actName;
    ArrayList<UserType> userTypes = new ArrayList<>();
    ArrayList<Day> days = new ArrayList<>();
    boolean edit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get intent from addActvitiy
        Intent i = getIntent();
        setContentView(R.layout.add_type_layout);
        sH = i.getExtras().getInt("sH");
        sM = i.getExtras().getInt("sM");
        dH = i.getExtras().getInt("dH");
        dM = i.getExtras().getInt("dM");
        edit = i.getExtras().getBoolean("edit");
        actName = i.getExtras().getString("name");
        currentTypeIndex = i.getExtras().getInt("currentTypeIndex");
        days = i.getParcelableArrayListExtra("days");
        userTypes = i.getParcelableArrayListExtra("types");
        dayindex = i.getIntExtra("index", 0);
    }

    //when colourTextView is clicked type Colour is assigned
    public void colourClickHandler(View view) {
        ColorDrawable colour = (ColorDrawable) view.getBackground();
        int colorNum = colour.getColor();
        typeColour = colorNum;

    }

    public void Done(View view) {
        EditText name = findViewById(R.id.typeNameet);
        typeName = name.getText().toString();
        typeName = typeName.replaceAll(",", "");

        if (typeName != null && typeColour != 0) {

            UserType type = new UserType(typeName, typeColour, userTypes.size());
            userTypes.add(type);
            Intent main = new Intent(this, AddActActivity.class);
            main.putParcelableArrayListExtra("days", days);
            main.putParcelableArrayListExtra("types", userTypes);
            main.putExtra("index", dayindex);
            main.putExtra("sH", sH);
            main.putExtra("sM", sM);
            main.putExtra("dH", dH);
            main.putExtra("dM", dM);
            main.putExtra("edit", edit);
            main.putExtra("name", actName);
            main.putExtra("currentTypeIndex", currentTypeIndex);
            ArrayList<UserType> newtype = new ArrayList<>();
            newtype.add(type);
            main.setAction("addtype");
            startActivity(main);
        }

        else

        {
            Toast.makeText(getBaseContext(), "You didn't select a color and/or valid name", Toast.LENGTH_SHORT).show();
        }
    }
    }


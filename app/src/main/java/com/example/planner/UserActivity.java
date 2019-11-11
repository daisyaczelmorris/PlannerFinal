package com.example.planner;

import android.os.Parcel;
import android.os.Parcelable;

public class UserActivity implements Parcelable {
    private UserType type;
    private int startH,startM,dMin,dHour;
    private String name;

    public UserActivity(int sH,int sM,int dH,int dM,String name,UserType type){
        this.name=name;
        this.startH=sH;
        this.startM=sM;
        this.dHour=dH;
        this.dMin=dM;
        this.type =type;

    }

    protected UserActivity(Parcel in) {
        startH = in.readInt();
        startM = in.readInt();
        dMin = in.readInt();
        dHour = in.readInt();
        name = in.readString();
        type =in.readParcelable(UserType.class.getClassLoader());
    }

    public static final Creator<UserActivity> CREATOR = new Creator<UserActivity>() {
        @Override
        public UserActivity createFromParcel(Parcel in) {
            return new UserActivity(in);
        }

        @Override
        public UserActivity[] newArray(int size) {
            return new UserActivity[size];
        }
    };

    public int getStartH() {
        return startH;
    }

    public int getStartM() {
        return startM;
    }

    public int getdMin() {
        return dMin;
    }

    public ClassLoader getClassLoader(){
        return getClassLoader();
    }

    public int getdHour() {
        return dHour;
    }

    public void setdHour(int dHour) {
        this.dHour = dHour;
    }

    public String getName() {
        return name;
    }
    public UserType getType(){return type;}
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(startH);
        parcel.writeInt(startM);
        parcel.writeInt(dMin);
        parcel.writeInt(dHour);
        parcel.writeString(name);
        parcel.writeParcelable(type,0);
    }
}

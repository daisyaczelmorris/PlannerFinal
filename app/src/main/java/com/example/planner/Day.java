package com.example.planner;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Day implements Parcelable {
    private ArrayList<UserActivity> activities;
    private String dayName;
    private int index;
    public Day(String name,int ind){
        this.index=ind;
        this.dayName=name;
        this.activities = new ArrayList<>();
    }

    protected Day(Parcel in) {
        dayName = in.readString();
        index= in.readInt();
        activities = in.readArrayList(UserActivity.class.getClassLoader());
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };



    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dayName);
        parcel.writeInt(index);
        parcel.writeList(activities);
    }



    public List<UserActivity> getActivities() {
        return activities;
    }

    public void addActivity(UserActivity act){
        activities.add(act);

    }

    public int getIndex() {
        return index;
    }
}

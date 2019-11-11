package com.example.planner;

import android.os.Parcel;
import android.os.Parcelable;


public class UserType implements Parcelable {
    private int colour;
    private String name;
    private  int index;
    public int getIndex(){
        return index;
    }
    public int getColour() {
        return colour;
    }
    public String getName() {
        return name;
    }

    public UserType(String name, int colour,int index){
        this.name =name;
        this.colour=colour;
        this.index=index;
    }

    protected UserType(Parcel in) {
        colour = in.readInt();
        name = in.readString();
        index=in.readInt();
    }

    public static final Creator<UserType> CREATOR = new Creator<UserType>() {
        @Override
        public UserType createFromParcel(Parcel in) {
            return new UserType(in);
        }

        @Override
        public UserType[] newArray(int size) {
            return new UserType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(colour);
        parcel.writeString(name);
        parcel.writeInt(index);
    }
}

package com.yingjie.addressselector.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * create by chenyingjie on 2020/6/11
 * desc
 */
public class Area implements Parcelable {

    public String areaName;
    public String id;

    protected Area(Parcel in) {
        areaName = in.readString();
        id = in.readString();
    }

    public static final Creator<Area> CREATOR = new Creator<Area>() {
        @Override
        public Area createFromParcel(Parcel in) {
            return new Area(in);
        }

        @Override
        public Area[] newArray(int size) {
            return new Area[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(areaName);
        dest.writeString(id);
    }
}

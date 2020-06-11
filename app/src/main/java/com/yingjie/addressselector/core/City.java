package com.yingjie.addressselector.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * create by chenyingjie on 2020/6/11
 * desc
 */
public class City implements Parcelable {

    public String cityName;
    public String cityId;
    public List<Area> areas;

    protected City(Parcel in) {
        cityName = in.readString();
        cityId = in.readString();
        areas = in.createTypedArrayList(Area.CREATOR);
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityName);
        dest.writeString(cityId);
        dest.writeTypedList(areas);
    }
}

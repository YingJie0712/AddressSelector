package com.yingjie.addressselector.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by chen.yingjie on 2019/3/23
 */
public class RegionBean implements Parcelable{
    public String provinceName;
    public String provinceId;
    public List<City> citys;

    protected RegionBean(Parcel in) {
        provinceName = in.readString();
        provinceId = in.readString();
    }

    public static final Creator<RegionBean> CREATOR = new Creator<RegionBean>() {
        @Override
        public RegionBean createFromParcel(Parcel in) {
            return new RegionBean(in);
        }

        @Override
        public RegionBean[] newArray(int size) {
            return new RegionBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(provinceName);
        dest.writeString(provinceId);
    }
}

package com.yingjie.addressselector;

import java.util.List;

/**
 * Created by chen.yingjie on 2019/3/23
 */
public class RegionBean {
    public String provinceName;
    public String provinceId;
    public List<City> citys;


    public static class City {
        public String cityName;
        public String cityId;
        public List<Area> areas;

        public static class Area {
            public String areaName;
            public String id;
        }
    }
}

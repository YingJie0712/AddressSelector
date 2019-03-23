package com.yingjie.addressselector;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen.yingjie on 2019/3/23
 */
public class GsonU {

    private static final Gson gson = new Gson();
    private static final JsonParser parser = new JsonParser();
    private static final String jsonFile = "region.json";// assets文件夹中

    public static List<RegionBean> getJsonData(Context context) {
        List<RegionBean> result = new ArrayList<>();
        try {
            String jsonString = loadJson2Str(context, jsonFile);
            JsonArray jsonArray = stringToJsonArray(jsonString);
            if (jsonArray == null) {
                return result;
            }

            for (JsonElement j : jsonArray) {
                result.add(gson.fromJson(j, RegionBean.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String loadJson2Str(Context context, String fileName) {
        String result = "";
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = context.getAssets().open(fileName);
            bos = new ByteArrayOutputStream();
            byte[] bytes = new byte[4 * 1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            result = new String(bos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static JsonArray stringToJsonArray(String str) {
        try {
            return parser.parse(str).getAsJsonArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

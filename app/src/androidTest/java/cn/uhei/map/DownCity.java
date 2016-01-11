package cn.uhei.map;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cn.uhei.map.Bean.China;
import cn.uhei.map.database.Db;

/**
 * Created by Administrator on 2016/1/9.
 */
public class DownCity extends AndroidTestCase {

    public static final String TAG = "DownCity";
    public Db db;
    private SQLiteDatabase dbRead, dbWrita;
    //广东省
    private String url = "http://api.map.baidu.com/place/v2/search?ak=xMIlYtbWA3ImOX40wnrhDCxt&output=json&query=%E7%BB%B4%E4%BF%AE%E5%BA%97&page_size=10&page_num=0&scope=1&region=%E5%B9%BF%E4%B8%9C%E7%9C%81";


    public void text() {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //输出
                Log.i("info", s);

                //解析json数据
                try {
                    dealData(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        Volley.newRequestQueue(getContext().getApplicationContext()).add(request);
    }


    //解析方法
    public void dealData(String s) throws JSONException {
        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<China>>() {
        }.getType();
        //s: url
        JSONObject object = new JSONObject(s);
        ArrayList<China> chinas = gson.fromJson(object.getString("results"), listType);


        db = new Db(getContext());
        dbWrita = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values = new ContentValues();

        for (China china : chinas) {
            String name = china.getName();
            Log.i(TAG, name);

            values.clear();
            values.put("city", name);
            long l = dbWrita.insert("gds", null, values);
            Log.i(TAG, "成功插入" + l + "条记录");
        }
        dbWrita.close();
        db.close();
    }


}


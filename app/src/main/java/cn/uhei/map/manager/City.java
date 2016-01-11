package cn.uhei.map.manager;

import android.content.Context;
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

/**
 * Created by Administrator on 2016/1/9.
 */
public class City {
    private static final String TAG = "CarManager";


    public void getDate(Context context, String url) {

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
        try {
            Volley.newRequestQueue(context.getApplicationContext()).add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //解析方法
    public void dealData(String s) throws JSONException {
        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<China>>() {
        }.getType();
        //s: url
        JSONObject object = new JSONObject(s);
        ArrayList<China> chinas = gson.fromJson(object.getString("results"), listType);


        for (China china : chinas) {
            String name = china.getName();
            Log.i(TAG, name);


        }
    }
}

package cn.uhei.map;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;

import cn.uhei.map.Bean.Car;


/**
 * Created by Administrator on 2016/1/8.
 */
public class CarManager {
    private static final String TAG = "CarManager";

    private String  url = "http://api.map.baidu.com/place/v2/search?ak=" +
            "xMIlYtbWA3ImOX40wnrhDCxt" +
            "&output=json" +
            "&query=%E7%BB%B4%E4%BF%AE%E5%BA%97" +
            "&page_size=1" +
            "&page_num=0" +
            "&scope=1" +
            "&region=%E5%B9%BF%E5%B7%9E";

    public void getDate(Context context) {

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //输出
                Log.i("info",s);

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
        Volley.newRequestQueue(context.getApplicationContext()).add(request);
    }

    //解析方法
    public void dealData(String s) throws JSONException {
        Gson gson = new Gson();
        //总条数
        Car car = gson.fromJson(s, Car.class);
        System.out.println("总数：" + car.getTotal());

    }


}

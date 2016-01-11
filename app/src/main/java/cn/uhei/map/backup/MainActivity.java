package cn.uhei.map.backup;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;

import cn.uhei.map.Bean.Car;
import cn.uhei.map.Bean.Shop;
import cn.uhei.map.R;
import cn.uhei.map.database.Db;


/**
 * 百度地图
 * q(query) :查询 关键词
 * region：城市
 * output: 输出格式 jason, xml（默认）
 * page_size ：返回几条记录，默认10，最大20
 * ak ：访问密钥，必填
 * total： 总页面数量，默认从0开始
 * ak=xMIlYtbWA3ImOX40wnrhDCxt
 * <p>
 * region=全国，返回省份
 * reqgin=广东 ，返回市 *
 * reqgin = 广州 ，返回详细信息
 * <p>
 * http://api.map.baidu.com/place/v2/search?ak=xMIlYtbWA3ImOX40wnrhDCxt&output=json&query=%E7%BB%B4%E4%BF%AE%E5%BA%97&page_size=10&page_num=0&scope=1&region=%E5%85%A8%E5%9B%BD
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private Db db;
    private SQLiteDatabase dbRead, dbWrita;
    private int total;

    private String url;
    private String city;
    private String urlcity;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            getDate();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取json数据
     */
    public void getDate() throws UnsupportedEncodingException {
        //获取城市
        db = new Db(MainActivity.this);
        dbRead = db.getReadableDatabase();
        Cursor c = dbRead.query("gds", null, null, null, null, null, null);
        //遍历城市
        while (c.moveToNext()) {
            city = c.getString(c.getColumnIndex("city"));
            Log.i(TAG,  city+ "开始查询" );
            //转码
            urlcity = URLEncoder.encode(city, "UTF-8");


        url = "http://api.map.baidu.com/place/v2/search?ak=" +
                "xMIlYtbWA3ImOX40wnrhDCxt" +
                "&output=json" +
                "&query=%E7%BB%B4%E4%BF%AE%E5%BA%97" +
                "&page_size=1" +
                "&page_num=0" +
                "&scope=1" +
                "&region="+urlcity;

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //输出
//                Log.i("info", s);

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
        Volley.newRequestQueue(getApplicationContext()).add(request);

    }

    }

    /**
     * 处理数据
     *
     * @param s
     * @throws JSONException
     */

    public void dealData(String s) throws JSONException {
        Gson gson = new Gson();
        //总条数
        Car car = gson.fromJson(s, Car.class);
        total = car.getTotal();
        Log.i(TAG, city+" 的维修厂总数是:" + car.getTotal());

        //--------------------------------------------------------------------
        /**
         * 组装分页地址
         */

            url = null;
            //遍历总条数
            for (int i = 0; i < car.getTotal(); i++) {
                url = null;
                url = "http://api.map.baidu.com/place/v2/search?ak=" +
                        "xMIlYtbWA3ImOX40wnrhDCxt" +
                        "&output=json" +
                        "&query=%E7%BB%B4%E4%BF%AE%E5%BA%97" +
                        "&page_size=1" +
                        "&page_num=" + i + "" +
                        "&scope=1" +
                        "&region=" + urlcity;
                Item item = new Item();
                item.getItem(url);

            }



        //---------------------------------------------------------------
    }

    /**
     * 内部类
     * 解析维修厂数据
     */


    public class Item {
        public void getItem(String url) {
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    try {
                        dealItem(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            Volley.newRequestQueue(getApplicationContext()).add(request);

        }

        /**
         * 处理项目
         *
         * @param s url
         * @throws JSONException
         */
        private void dealItem(String s) throws JSONException {
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Shop>>() {
            }.getType();

            JSONObject object = new JSONObject(s);

            ArrayList<Shop> shops = gson.fromJson(object.getString("results"), listType);

            db = new Db(MainActivity.this);
            dbWrita = db.getWritableDatabase();

            for (Shop shop :shops) {
                String name = shop.getName();
                String telephone = shop.getTelephone();
                String address = shop.getAddress();

                Log.i(TAG, name + ":" + telephone + ":" + address);

                //插入数据操作
//                insetData(name,telephone,address);
            }
            dbWrita.close();
            db.close();

        }
    }

    /**
     * 插入数据库
     *
     * @param name
     * @param telephone
     * @param address
     */
    private void insetData(String name, String telephone, String address) {

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("telephone", telephone);
        values.put("address", address);

        long l = dbWrita.insert("gztable", null, values);

        Log.i(TAG, "第" + l + "成功插入");
    }


}


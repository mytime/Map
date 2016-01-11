package cn.uhei.map;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import cn.uhei.map.database.Db;


/**
 *
 */
public class Test extends AndroidTestCase {
    public void test() {

        Db db = new Db(getContext());
        SQLiteDatabase dbWrita = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name","奇瑞汽车广州平益特约维修站");
        values.put("telephone","(020)82564506");
        values.put("address","黄埔区中山大道东塘口南路9号(近来来俱乐部)");

        dbWrita.insert("city",null,values);

        dbWrita.close();
        db.close();

    }
}

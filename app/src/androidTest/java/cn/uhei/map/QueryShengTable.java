package cn.uhei.map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import cn.uhei.map.database.Db;

/**
 * Created by Administrator on 2016/1/9.
 */
public class QueryShengTable extends AndroidTestCase {
    public static final String TAG = "QueryShengTable";

    public void test(){
        Db db = new Db(getContext());

        SQLiteDatabase dbRead = db.getReadableDatabase();

        Cursor c = dbRead.query("sheng",null,null,null,null,null,null);

        while (c.moveToNext()){
            String province = c.getString(c.getColumnIndex("province"));
            Log.i(TAG,province);
        }

        dbRead.close();
        db.close();


    }
}

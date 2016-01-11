package cn.uhei.map;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import cn.uhei.map.database.Db;

/**
 * Created by Administrator on 2016/1/9.
 */
public class DeleteCity extends AndroidTestCase {
    public void test() {
        Db db = new Db(getContext());
//        Db db = new Db(getContext());
        SQLiteDatabase dbWrita = db.getWritableDatabase();

//        从省表删除市
        String[] str = {"广州"};
        for (int i = 0; i <str.length ; i++) {
            String mStr = str[i];
            try {
                dbWrita.delete("gds","city=?",new String[]{mStr});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

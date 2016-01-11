package cn.uhei.map;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import cn.uhei.map.database.Db;

/**
 * Created by Administrator on 2016/1/9.
 */
public class DeleteShengTable extends AndroidTestCase{
    public void test() {
        Db db = new Db(getContext());
//        Db db = new Db(getContext());
        SQLiteDatabase dbWrita = db.getWritableDatabase();

//        从省表删除市
        String[] str = {"北京市","上海市","广州市","成都市","深圳市","东莞市"};
        for (int i = 0; i <str.length ; i++) {
            String mStr = str[i];
            try {
                dbWrita.delete("sheng","province=?",new String[]{mStr});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

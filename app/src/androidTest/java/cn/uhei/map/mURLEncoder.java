package cn.uhei.map;

import android.test.AndroidTestCase;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * url后参数的转码与解码
 * URLEncoder.encode 转码
 * URLDecoder.decode 解码
 */
public class mURLEncoder extends AndroidTestCase {
    public void test() throws UnsupportedEncodingException {

        String strTest = "?=abc?中%1&2<3,4>";
        //转成url 码
        strTest = URLEncoder.encode(strTest, "UTF-8");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>"+ strTest);

        strTest = URLDecoder.decode(strTest, "UTF-8");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>"+ strTest);
    }
}

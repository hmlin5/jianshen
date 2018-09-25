package com.gimc.user.common.utils;

import java.text.DecimalFormat;
import java.util.Random;



/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ShineU
 * @date 2015年11月12日 下午5:29:26
 * @version 1.0
 */

public class NumberUtil {
    public static String fomaterFloat(Float value) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.0");
        String result = null;
        if (null == value) {
            return result;
        }
        result = decimalFormat.format(value.floatValue());
        return result;
    }
    
    
    public static int nextInt(final int min, final int max) {
        Random rand = new Random();
        int tmp = Math.abs(rand.nextInt());
        return tmp % (max - min + 1) + min;
    }


    public static String getRandomString(int length) {
	    //随机字符串的随机字符库
	    String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    StringBuffer sb = new StringBuffer();
	    int len = KeyString.length();
	    for (int i = 0; i < length; i++) {
	       sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
	    }
	    return sb.toString();
}
    
}

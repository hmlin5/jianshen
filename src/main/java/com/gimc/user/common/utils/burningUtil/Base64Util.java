package com.gimc.user.common.utils.burningUtil;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 *    Base64加密解密工具类
 *
 * @author jqlin
 */
public class Base64Util {
    private static final Logger logger = Logger.getLogger(Base64Util.class);
    private static final String charset = "utf-8";

    /**
     * 解密
     * 
     * @param data
     * @return
     * @author jqlin
     */
    public static byte[] decode(String data) {
        try {
            if (null == data) {
                return null;
            }
            return Base64.decodeBase64(data.getBytes(charset));
          //  return new String(Base64.decodeBase64(data.getBytes(charset)), charset);
        } catch (UnsupportedEncodingException e) {
            logger.error(String.format("字符串：%s，解密异常", data), e);
        }

        return null;
    }

    /**
     * 加密
     * 
     * @param data
     * @return
     * @author jqlin
     */
    public static String encode(byte[] data) {
        try {
            if (null == data) {
                return null;
            }
            // return new String(Base64.encodeBase64(data.getBytes(charset)), charset);
             return new String(Base64.encodeBase64(data), charset);
        } catch (UnsupportedEncodingException e) {
            logger.error(String.format("字符串：%s，加密异常", data), e);
        }

        return null;
    }

}
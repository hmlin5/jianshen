package com.gimc.user.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5算法
 * 在该项目中，用于将明文密码转成暗文密码的算法
 */
public final class Md5Util {	
	private Md5Util(){}
	/**
	 * 将明文密码转成暗文密码
	 * @param password 明文密码  123456
	 * @return String 暗文密码 32位16进制字符串的MD5算法
	 */
	public static String encodeByMd5(String password){
		byte[] byteArray = null;
		try {
			//MessageDigest是Java中封装MD5和SHA算法的对象
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			//用Java中的MD5算法完成明文密码转暗文密码，Java中的工具类，只能将String转成byte[]，不能将byte[]转成32位16进制字符串
			byteArray = md5.digest(password.getBytes());
			//将byteArray转成16进制字符串
		} catch (Exception e) {
			e.printStackTrace();
		}
		return byteArrayToHexString(byteArray);
	}
	/**
	 * 将byteArray转成16进制字符串
	 */
	private static String byteArrayToHexString(byte[] byteArray) {
		StringBuffer sb = new StringBuffer();
		//迭代
		for(byte b : byteArray){//16次
			String hexString = byteToHexString(b);
			sb.append(hexString);
		}
		return sb.toString();
	}
	/**
	 * 将byte转成16进制字符串
	 * -31 E1
	 * 10  0A
	 */
	private static String byteToHexString(byte b) {
		//将byte类型覆给int类型
		int n = b;
		//如果n为负数
		if(n<0){
			n = 256 + n;
		}
		//确保n为正数
		//d1表示商，如14
		int d1 = n / 16;
		//d1表示商,如1
		int d2 = n % 16;
		return hex[d1] + hex[d2];
	}
	private static String[] hex = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
	/**
	 * 测试
	 */
//	public static void main(String[] args) throws Exception{
//		String password = "654321";
//		String passwordMD5 = Md5Util.encodeByMd5(password);	
		
//		System.out.println(password);
//		System.out.println(passwordMD5);
//	}
}

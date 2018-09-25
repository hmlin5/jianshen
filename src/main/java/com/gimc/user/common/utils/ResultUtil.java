package com.gimc.user.common.utils;

import java.util.Map;

import com.gimc.user.common.constant.SysStatusCode;

/**
 * 返回结果工具类
 */
public class ResultUtil {

	public static Map<String,Object> getMapResult(Map<String,Object> result,SysStatusCode sysCode,String msg,Object data){
		result.put("code", sysCode.getKEY());
		result.put("msg", msg);
		result.put("data", data);
		return result;
	}
	
	public static Map<String,Object> getMapResult(Map<String,Object> result,SysStatusCode sysCode,Object data){
		result.put("code", sysCode.getKEY());
		result.put("msg", sysCode.getVALUE());
		result.put("data", data);
		return result;
	}
	
	public static Map<String,Object> getMapResult(Map<String,Object> result,SysStatusCode sysCode){
		result.put("code", sysCode.getKEY());
		result.put("msg", sysCode.getVALUE());
		result.put("data", null);
		return result;
	}
}

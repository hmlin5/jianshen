package com.gimc.user.common.config;

import java.util.HashMap;
import java.util.Map;

import com.gimc.user.modules.sys.entity.SysConfig;



public class SysConfigData {
    
	
	/** 参数配置 （存储在数据库） */
	public static Map<String, SysConfig> sysConfigMap = new HashMap<String, SysConfig>();
	
	public static String getConfigValue(String proKey){
		return sysConfigMap.get(proKey).getProvalue();
	}
	
}

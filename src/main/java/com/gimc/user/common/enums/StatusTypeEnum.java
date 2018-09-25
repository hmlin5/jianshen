package com.gimc.user.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 */
public enum StatusTypeEnum {
	 STATUSYES("有效","1"),
	 STATUSNO("无效","0");

	 /** 描述  */
	 private String desc;
	 /** 枚举值 */
	 private String value;
	 
	 private StatusTypeEnum(String desc , String value){
		 this.desc = desc;
		 this.value = value;
	 }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	 
	/**
	 * 根据枚举名称获取枚举值.
	 * 
	 * @param name
	 * @return
	 */
	public static String getCode(String name){
		String code="";
		StatusTypeEnum[] enumAry=StatusTypeEnum.values();
		for(int i=0;i<enumAry.length;i++){
			if(name.equals(enumAry[i].getValue())){
				code=enumAry[i].getDesc();
				break;
			}
		}
		return code;
	}

	
	public static StatusTypeEnum getEnum(String value){
		StatusTypeEnum resultEnum = null;
		StatusTypeEnum[] enumAry = StatusTypeEnum.values();
		for(int i = 0;i<enumAry.length;i++){
			if(enumAry[i].getValue() == value){
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList(){
		StatusTypeEnum[] ary = StatusTypeEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value",String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		StatusTypeEnum[] ary = StatusTypeEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key,map);
		}
		return enumMap;
	}

}

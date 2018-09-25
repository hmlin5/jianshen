package com.gimc.user.common.utils.excel.fieldtype;

import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.modules.sys.entity.Area;
import com.gimc.user.modules.sys.utils.UserUtils;

/**
 * 字段类型转换
 * @author QiuZhu
 * @version 2013-03-10
 */
public class AreaType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		for (Area e : UserUtils.getAreaList(new Area())){
			if (StringUtils.trimToEmpty(val).equals(e.getName())){
				return e;
			}
		}
		return null;
	}

	/**
	 * 获取对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Area)val).getName() != null){
			return ((Area)val).getName();
		}
		return "";
	}
}

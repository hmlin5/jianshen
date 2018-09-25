/**
 * 
 */
package com.gimc.user.common.utils.excel.fieldtype;

import java.util.List;

import com.gimc.user.common.utils.Collections3;
import com.gimc.user.common.utils.SpringContextHolder;
import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.modules.sys.entity.Role;
import com.gimc.user.modules.sys.service.SystemService;
import com.google.common.collect.Lists;

/**
 * 字段类型转换
 * @author QiuZhu
 * @version 2013-5-29
 */
public class RoleListType {

	private static SystemService systemService = SpringContextHolder.getBean(SystemService.class);
	
	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		List<Role> roleList = Lists.newArrayList();
		List<Role> allRoleList = systemService.findAllRole();
		for (String s : StringUtils.split(val, ",")){
			for (Role e : allRoleList){
				if (StringUtils.trimToEmpty(s).equals(e.getName())){
					roleList.add(e);
				}
			}
		}
		return roleList.size()>0?roleList:null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null){
			@SuppressWarnings("unchecked")
			List<Role> roleList = (List<Role>)val;
			return Collections3.extractToString(roleList, "name", ", ");
		}
		return "";
	}
	
}
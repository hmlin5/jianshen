/**
 * 
 */
package com.gimc.user.modules.sys.dao;

import com.gimc.user.common.persistence.TreeDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author QiuZhu
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	Office selectName(String name);
	
	Office selectCode(String code);
	
	Office getParent(String id);
}

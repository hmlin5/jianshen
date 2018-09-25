/**
 * 
 */
package com.gimc.user.modules.sys.dao;

import com.gimc.user.common.persistence.TreeDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author QiuZhu
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}

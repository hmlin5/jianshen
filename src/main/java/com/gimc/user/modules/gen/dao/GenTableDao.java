/**
 * 
 */
package com.gimc.user.modules.gen.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.gen.entity.GenTable;

/**
 * 业务表DAO接口
 * @author QiuZhu
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableDao extends CrudDao<GenTable> {
	
}

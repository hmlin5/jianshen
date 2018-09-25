/**
 * 
 */
package com.gimc.user.modules.gen.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.gen.entity.GenScheme;

/**
 * 生成方案DAO接口
 * @author QiuZhu
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenSchemeDao extends CrudDao<GenScheme> {
	
}

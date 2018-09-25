/**
 * 
 */
package com.gimc.user.modules.b_group.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_group.entity.Group;

/**
 * 用户分组DAO接口
 * @author linhaomiao
 * @version 2018-06-07
 */
@MyBatisDao
public interface GroupDao extends CrudDao<Group> {
	
}
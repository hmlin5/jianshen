/**
 * 
 */
package com.gimc.user.modules.b_user_group.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_user_group.entity.UserGroup;

/**
 * 用户分组关系DAO接口
 * @author linhaomiao
 * @version 2018-06-07
 */
@MyBatisDao
public interface UserGroupDao extends CrudDao<UserGroup> {
	
}
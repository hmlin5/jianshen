/**
 * 
 */
package com.gimc.user.modules.b_user_test.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_user_test.entity.UserTest;

/**
 * 用户测试结果DAO接口
 * @author linhaomiao
 * @version 2018-05-13
 */
@MyBatisDao
public interface UserTestDao extends CrudDao<UserTest> {
	
}
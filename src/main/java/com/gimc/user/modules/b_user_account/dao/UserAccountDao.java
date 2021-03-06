/**
 * 
 */
package com.gimc.user.modules.b_user_account.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_user_account.entity.UserAccount;

/**
 * 用户账户DAO接口
 * @author linhaomiao
 * @version 2018-06-30
 */
@MyBatisDao
public interface UserAccountDao extends CrudDao<UserAccount> {
	
}
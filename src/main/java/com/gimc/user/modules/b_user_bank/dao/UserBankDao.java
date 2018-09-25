/**
 * 
 */
package com.gimc.user.modules.b_user_bank.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_user_bank.entity.UserBank;

/**
 * 用户绑定银行DAO接口
 * @author linhaomiao
 * @version 2018-07-04
 */
@MyBatisDao
public interface UserBankDao extends CrudDao<UserBank> {
	
}
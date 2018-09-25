/**
 * 
 */
package com.gimc.user.modules.b_user_withdraw.dao;

import java.util.List;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_user_withdraw.entity.MoneyDetail;
import com.gimc.user.modules.b_user_withdraw.entity.UserWithdraw;

/**
 * 用户提现记录DAO接口
 * @author linhaomiao
 * @version 2018-07-04
 */
@MyBatisDao
public interface UserWithdrawDao extends CrudDao<UserWithdraw> {
	
	
	List<MoneyDetail> findWithdrawAndPayInfoList(UserWithdraw userWithdraw);
}
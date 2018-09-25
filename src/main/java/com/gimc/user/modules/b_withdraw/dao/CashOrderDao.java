/**
 * 
 */
package com.gimc.user.modules.b_withdraw.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_withdraw.entity.CashOrder;

/**
 * 提现订单管理DAO接口
 * @author gu
 * @version 2018-05-09
 */
@MyBatisDao
public interface CashOrderDao extends CrudDao<CashOrder> {
	
}
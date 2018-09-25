/**
 * 
 */
package com.gimc.user.modules.b_pay_info.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_pay_info.entity.PayInfo;

/**
 * 支付信息DAO接口
 * @author linhaomiao
 * @version 2018-06-30
 */
@MyBatisDao
public interface PayInfoDao extends CrudDao<PayInfo> {
	
}
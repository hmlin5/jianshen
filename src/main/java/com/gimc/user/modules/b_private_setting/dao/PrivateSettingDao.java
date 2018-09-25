/**
 * 
 */
package com.gimc.user.modules.b_private_setting.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_private_setting.entity.PrivateSetting;

/**
 * 隐私设置DAO接口
 * @author linhaomiao
 * @version 2018-06-03
 */
@MyBatisDao
public interface PrivateSettingDao extends CrudDao<PrivateSetting> {
	
	PrivateSetting getUserId(String id);
	
}
/**
 * 
 */
package com.gimc.user.modules.b_openingbank.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_openingbank.entity.Openingbank;

/**
 * 开户行管理DAO接口
 * @author gu
 * @version 2018-05-08
 */
@MyBatisDao
public interface OpeningbankDao extends CrudDao<Openingbank> {
	
}
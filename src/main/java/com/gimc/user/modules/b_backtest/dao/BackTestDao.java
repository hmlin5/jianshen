/**
 * 
 */
package com.gimc.user.modules.b_backtest.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_backtest.entity.BackTest;

/**
 * 后台测试用户DAO接口
 * @author gu
 * @version 2018-05-05
 */
@MyBatisDao
public interface BackTestDao extends CrudDao<BackTest> {
	
}
/**
 * 
 */
package com.gimc.user.modules.sys.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.sys.entity.SysConfig;

/**
 * 配置参数表DAO接口
 * @author xf
 * @version 2018-01-08
 */
@MyBatisDao
public interface SysConfigDao extends CrudDao<SysConfig> {
	
}
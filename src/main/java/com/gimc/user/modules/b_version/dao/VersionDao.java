/**
 * 
 */
package com.gimc.user.modules.b_version.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_version.entity.Version;

/**
 * app版本DAO接口
 * @author linhaomiao
 * @version 2018-05-08
 */
@MyBatisDao
public interface VersionDao extends CrudDao<Version> {
	
}
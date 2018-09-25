/**
 * 
 */
package com.gimc.user.modules.sys.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.sys.entity.Log;

/**
 * 日志DAO接口
 * @author QiuZhu
 * @version 2014-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

}

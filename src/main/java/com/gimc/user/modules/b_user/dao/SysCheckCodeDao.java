/**
 * 
 */
package com.gimc.user.modules.b_user.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_user.entity.SysCheckCode;

/**
 * 短信验证码DAO接口
 * @author 短信验证码
 * @version 2018-01-08
 */
@MyBatisDao
public interface SysCheckCodeDao extends CrudDao<SysCheckCode> {
	
}
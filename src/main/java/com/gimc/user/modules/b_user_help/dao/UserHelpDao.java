/**
 * 
 */
package com.gimc.user.modules.b_user_help.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_user_help.entity.UserHelp;

/**
 * 用户帮助DAO接口
 * @author linhaomiao
 * @version 2018-09-03
 */
@MyBatisDao
public interface UserHelpDao extends CrudDao<UserHelp> {
	
}
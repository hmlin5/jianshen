/**
 * 
 */
package com.gimc.user.modules.b_user_inbody.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_user_inbody.entity.UserInbody;

/**
 * 用户Inbody数据DAO接口
 * @author linhaomiao
 * @version 2018-06-14
 */
@MyBatisDao
public interface UserInbodyDao extends CrudDao<UserInbody> {
	
	UserInbody getUserInbody(String id);
    
	void insertInbody(UserInbody userInbody);
	
	UserInbody TestUpdate(UserInbody userInbody);
	
}
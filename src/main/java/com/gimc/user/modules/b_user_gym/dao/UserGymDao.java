/**
 * 
 */
package com.gimc.user.modules.b_user_gym.dao;

import java.util.List;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_user_gym.entity.UserGym;

/**
 * 用户健身房关系DAO接口
 * @author linhaomiao
 * @version 2018-05-11
 */
@MyBatisDao
public interface UserGymDao extends CrudDao<UserGym> {

	/**
	 * 查找指定健身房的用户
	 * @return
	 */
	List<UserGym> findGymUserList(UserGym userGym);
	
	UserGym findGymUser(int id);
	
	void updateInbodyUser(UserGym userGym);
}
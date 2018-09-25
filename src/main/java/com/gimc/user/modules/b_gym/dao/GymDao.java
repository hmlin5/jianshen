/**
 * 
 */
package com.gimc.user.modules.b_gym.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_gym.entity.Gym;

/**
 * 健身房DAO接口
 * @author linhaomiao
 * @version 2018-05-03
 */
@MyBatisDao
public interface GymDao extends CrudDao<Gym> {
	
}
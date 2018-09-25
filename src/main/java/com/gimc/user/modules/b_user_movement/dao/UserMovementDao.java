/**
 * 
 */
package com.gimc.user.modules.b_user_movement.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_user_movement.entity.UserMovement;

/**
 * 用户推荐动作DAO接口
 * @author linhaomiao
 * @version 2018-05-30
 */
@MyBatisDao
public interface UserMovementDao extends CrudDao<UserMovement> {
	 int updateUserMovement(UserMovement userMovement);
}
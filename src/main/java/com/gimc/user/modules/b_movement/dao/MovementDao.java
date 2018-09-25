/**
 * 
 */
package com.gimc.user.modules.b_movement.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_movement.entity.Movement;

/**
 * 动作DAO接口
 * @author linhaomiao
 * @version 2018-05-03
 */
@MyBatisDao
public interface MovementDao extends CrudDao<Movement> {
	
}
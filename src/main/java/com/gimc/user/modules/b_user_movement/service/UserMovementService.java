/**
 * 
 */
package com.gimc.user.modules.b_user_movement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_user_movement.entity.UserMovement;
import com.gimc.user.modules.b_user_movement.dao.UserMovementDao;

/**
 * 用户推荐动作Service
 * @author linhaomiao
 * @version 2018-05-30
 */
@Service
@Transactional(readOnly = true)
public class UserMovementService extends CrudService<UserMovementDao, UserMovement> {

	@Autowired
	private UserMovementDao userMovementDao;
	public UserMovement get(String id) {
		return super.get(id);
	}
	
	public List<UserMovement> findList(UserMovement userMovement) {
		return super.findList(userMovement);
	}
	
	public Page<UserMovement> findPage(Page<UserMovement> page, UserMovement userMovement) {
		return super.findPage(page, userMovement);
	}
	
	@Transactional(readOnly = false)
	public void save(UserMovement userMovement) {
		super.save(userMovement);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserMovement userMovement) {
		super.delete(userMovement);
	}
	@Transactional(readOnly = false)
	public int updateUserMovement(UserMovement userMovement){
		return  userMovementDao.updateUserMovement(userMovement);
	}
	
}
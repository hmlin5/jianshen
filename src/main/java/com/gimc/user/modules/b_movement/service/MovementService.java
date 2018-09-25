/**
 * 
 */
package com.gimc.user.modules.b_movement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_movement.entity.Movement;
import com.gimc.user.modules.b_movement.dao.MovementDao;

/**
 * 动作Service
 * @author linhaomiao
 * @version 2018-05-03
 */
@Service
@Transactional(readOnly = true)
public class MovementService extends CrudService<MovementDao, Movement> {

	public Movement get(String id) {
		return super.get(id);
	}
	
	public List<Movement> findList(Movement movement) {
		return super.findList(movement);
	}
	
	public Page<Movement> findPage(Page<Movement> page, Movement movement) {
		return super.findPage(page, movement);
	}
	
	@Transactional(readOnly = false)
	public void save(Movement movement) {
		super.save(movement);
	}
	
	@Transactional(readOnly = false)
	public void delete(Movement movement) {
		super.delete(movement);
	}
	
}
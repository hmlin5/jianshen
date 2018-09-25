/**
 * 
 */
package com.gimc.user.modules.b_movement_catalog.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_movement_catalog.entity.MovementCatalog;
import com.gimc.user.modules.b_movement_catalog.dao.MovementCatalogDao;

/**
 * 动作分类Service
 * @author linhaomiao
 * @version 2018-05-02
 */
@Service
@Transactional(readOnly = true)
public class MovementCatalogService extends CrudService<MovementCatalogDao, MovementCatalog> {

	public MovementCatalog get(String id) {
		return super.get(id);
	}
	
	public List<MovementCatalog> findList(MovementCatalog movementCatalog) {
		return super.findList(movementCatalog);
	}
	
	public Page<MovementCatalog> findPage(Page<MovementCatalog> page, MovementCatalog movementCatalog) {
		return super.findPage(page, movementCatalog);
	}
	
	@Transactional(readOnly = false)
	public void save(MovementCatalog movementCatalog) {
		super.save(movementCatalog);
	}
	
	@Transactional(readOnly = false)
	public void delete(MovementCatalog movementCatalog) {
		
		loopDelete(movementCatalog);
		
		super.delete(movementCatalog);
	}
	
	
	@Transactional(readOnly = false)
	public void loopDelete(MovementCatalog movementCatalog){
		MovementCatalog condition = new MovementCatalog();
		condition.setParentId(movementCatalog.getId());
		List<MovementCatalog> findList = findList(condition);
		
		if (CollectionUtils.isNotEmpty(findList)) {
			for (MovementCatalog mc : findList) {
				loopDelete(mc);
			}
		}
		
		super.delete(movementCatalog);
		
	}
	
}
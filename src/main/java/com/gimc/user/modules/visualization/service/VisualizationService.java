/**
 * 
 */
package com.gimc.user.modules.visualization.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.visualization.entity.Visualization;
import com.gimc.user.modules.visualization.dao.VisualizationDao;

/**
 * 今日实时Service
 * @author xxx
 * @version 2018-07-17
 */
@Service
@Transactional(readOnly = true)
public class VisualizationService extends CrudService<VisualizationDao, Visualization> {

	public Visualization get(String id) {
		return super.get(id);
	}
	
	public List<Visualization> findList(Visualization visualization) {
		return super.findList(visualization);
	}
	
	public Page<Visualization> findPage(Page<Visualization> page, Visualization visualization) {
		return super.findPage(page, visualization);
	}
	
	@Transactional(readOnly = false)
	public void save(Visualization visualization) {
		super.save(visualization);
	}
	
	@Transactional(readOnly = false)
	public void delete(Visualization visualization) {
		super.delete(visualization);
	}
	
}
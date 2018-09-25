/**
 * 
 */
package com.gimc.user.modules.b_course_catalog.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_course_catalog.entity.CourseCatalog;
import com.gimc.user.modules.b_course_catalog.dao.CourseCatalogDao;

/**
 * 课程分类Service
 * @author linhaomiao
 * @version 2018-04-30
 */
@Service
@Transactional(readOnly = true)
public class CourseCatalogService extends CrudService<CourseCatalogDao, CourseCatalog> {

	public CourseCatalog get(String id) {
		return super.get(id);
	}
	
	public List<CourseCatalog> findList(CourseCatalog courseCatalog) {
		return super.findList(courseCatalog);
	}
	
	public Page<CourseCatalog> findPage(Page<CourseCatalog> page, CourseCatalog courseCatalog) {
		return super.findPage(page, courseCatalog);
	}
	
	@Transactional(readOnly = false)
	public void save(CourseCatalog courseCatalog) {
		super.save(courseCatalog);
	}
	
	@Transactional(readOnly = false)
	public void delete(CourseCatalog courseCatalog) {
		
		
		loopDelete(courseCatalog);
		
		super.delete(courseCatalog);
	}
	
	@Transactional(readOnly = false)
	public void loopDelete(CourseCatalog courseCatalog){
		CourseCatalog condition = new CourseCatalog();
		condition.setParentId(courseCatalog.getId());
		List<CourseCatalog> findList = findList(condition);
		
		if (CollectionUtils.isNotEmpty(findList)) {
			for (CourseCatalog cc : findList) {
				loopDelete(cc);
			}
			
		}
		
		super.delete(courseCatalog);
		
	}
	
}
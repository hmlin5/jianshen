/**
 * 
 */
package com.gimc.user.modules.b_course_catalog.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_course_catalog.entity.CourseCatalog;

/**
 * 课程分类DAO接口
 * @author linhaomiao
 * @version 2018-04-30
 */
@MyBatisDao
public interface CourseCatalogDao extends CrudDao<CourseCatalog> {
	
}
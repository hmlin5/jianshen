/**
 * 
 */
package com.gimc.user.modules.b_bmi_course.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_bmi_course.entity.BmiCourse;

/**
 * bmi-推荐课程关系DAO接口
 * @author linhaomiao
 * @version 2018-05-13
 */
@MyBatisDao
public interface BmiCourseDao extends CrudDao<BmiCourse> {
	
}
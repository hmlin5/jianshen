/**
 * 
 */
package com.gimc.user.modules.b_bmi_course.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_bmi_course.entity.BmiCourse;
import com.gimc.user.modules.b_bmi_course.dao.BmiCourseDao;

/**
 * bmi-推荐课程关系Service
 * @author linhaomiao
 * @version 2018-05-13
 */
@Service
@Transactional(readOnly = true)
public class BmiCourseService extends CrudService<BmiCourseDao, BmiCourse> {

	public BmiCourse get(String id) {
		return super.get(id);
	}
	
	public List<BmiCourse> findList(BmiCourse bmiCourse) {
		return super.findList(bmiCourse);
	}
	
	public Page<BmiCourse> findPage(Page<BmiCourse> page, BmiCourse bmiCourse) {
		return super.findPage(page, bmiCourse);
	}
	
	@Transactional(readOnly = false)
	public void save(BmiCourse bmiCourse) {
		super.save(bmiCourse);
	}
	
	@Transactional(readOnly = false)
	public void delete(BmiCourse bmiCourse) {
		super.delete(bmiCourse);
	}
	@Transactional(readOnly = false)
	public void deletes(BmiCourse bmiCourse) {
		super.deletes(bmiCourse);
		
	}
	
}
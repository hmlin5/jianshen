/**
 * 
 */
package com.gimc.user.modules.b_course.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_course.entity.Course;
import com.gimc.user.modules.b_course.dao.CourseDao;

/**
 * 课程Service
 * @author linhaomiao
 * @version 2018-04-30
 */
@Service
@Transactional(readOnly = true)
public class CourseService extends CrudService<CourseDao, Course> {

	public Course get(String id) {
		return super.get(id);
	}
	
	public List<Course> findList(Course course) {
		return super.findList(course);
	}
	
	public Page<Course> findPage(Page<Course> page, Course course) {
		return super.findPage(page, course);
	}
	
	@Transactional(readOnly = false)
	public void save(Course course) {
		super.save(course);
	}
	
	@Transactional(readOnly = false)
	public void delete(Course course) {
		super.delete(course);
	}
	
}
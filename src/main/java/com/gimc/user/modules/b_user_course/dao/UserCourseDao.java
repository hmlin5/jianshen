/**
 * 
 */
package com.gimc.user.modules.b_user_course.dao;

import java.util.List;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_rest_time.entity.RestTime;
import com.gimc.user.modules.b_user_course.entity.UserCourse;

/**
 * 会员推荐课程DAO接口
 * @author linhaomiao
 * @version 2018-05-30
 */
@MyBatisDao
public interface UserCourseDao extends CrudDao<UserCourse> {
	
	/**
	 * 去重查询(返回课程id)
	 * @param userCourse
	 * @return
	 */
	
	public void updateFinsh(UserCourse userCourse);
	
	public List<String> findDistinctList(UserCourse userCourse);
	
	public int deleteUserCourses(UserCourse userCourse);
	
}
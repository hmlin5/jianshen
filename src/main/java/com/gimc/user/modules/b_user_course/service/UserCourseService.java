/**
 * 
 */
package com.gimc.user.modules.b_user_course.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_user_course.entity.UserCourse;
import com.gimc.user.modules.b_user_gym.dao.UserGymDao;
import com.gimc.user.modules.b_user_gym.entity.UserGym;
import com.gimc.user.modules.b_user_movement.dao.UserMovementDao;
import com.gimc.user.modules.b_user_movement.entity.UserMovement;
import com.gimc.user.modules.b_user_movement.service.UserMovementService;
import com.gimc.user.modules.b_user_test.dao.UserTestDao;
import com.gimc.user.modules.b_user_test.entity.UserTest;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_course.dao.CourseDao;
import com.gimc.user.modules.b_course.entity.Course;
import com.gimc.user.modules.b_movement.dao.MovementDao;
import com.gimc.user.modules.b_movement.entity.Movement;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user.service.AppUserService;
import com.gimc.user.modules.b_user_course.dao.UserCourseDao;

/**
 * 会员推荐课程Service
 * @author linhaomiao
 * @version 2018-05-30
 */
@Service
@Transactional(readOnly = true)
public class UserCourseService extends CrudService<UserCourseDao, UserCourse> {

	@Autowired
	private UserTestDao userTestDao;
	@Autowired
	private AppUserService userService;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private MovementDao movementDao;
	@Autowired
	private UserMovementService userMovementService;
	@Autowired
	private UserGymDao userGymDao;
	
	@Autowired
	private UserCourseDao usercourseDao;
	
	public void updateCourse(UserCourse userCourse){
		usercourseDao.updateFinsh(userCourse);
	}
	
	
	public UserCourse get(String id) {
		return super.get(id);
	}
	
	public List<UserCourse> findList(UserCourse userCourse) {
		return super.findList(userCourse);
	}
	
	public Page<UserCourse> findPage(Page<UserCourse> page, UserCourse userCourse) {
		return super.findPage(page, userCourse);
	}
	
	@Transactional(readOnly = false)
	public void save(UserCourse userCourse) {
		super.save(userCourse);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserCourse userCourse) {
		super.delete(userCourse);
	}

	/**
	 * 会员第一次推荐课程
	 * @param user
	 * @param gymId
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<UserCourse> recommendCourse(AppUser user, String gymId) {

		//查询用户最近一次bmi指数 //TODO 不只是查用户评测结果, 还需要查询inbody等数据
		UserTest condition = new UserTest();
		condition.setUserId(user.getId());
		condition.getPage().setOrderBy(" a.create_date DESC ");
		List<UserTest> list = userTestDao.findList(condition);
		if (list!=null && list.size()>0) {
			UserTest userTest = list.get(0);
			
			String bmiIndex = userTest.getBmiIndex();
			String courseType = userService.getCourseByBmiIndex(Double.parseDouble(bmiIndex));
			
			Course course = new Course();
			course.setRecomendRage(courseType);
			List<Course> courseList = courseDao.findList(course);
			
			int recommendNum = userService.getCourseNumByBmiIndex(Double.parseDouble(bmiIndex));
			
			int actualNum = courseList.size() < recommendNum ? courseList.size() : recommendNum;
			
			for (int i = 0; i < actualNum; i++) {
				Course cou = courseList.get(i);
				
				UserCourse userCourse = new UserCourse();
				
				userCourse.setUserId(user.getId());
				userCourse.setCourseId(cou.getId());
				userCourse.setSeq(i+1);
				userCourse.setGymId(gymId);
				userCourse.setGymImgUrl(cou.getGymImgUrl());
				userCourse.setName(cou.getName());
				userCourse.setDescription(cou.getDescription());
				userCourse.setDuration(cou.getDuration());
				userCourse.setRecomendRage(cou.getRecomendRage());
				userCourse.setRecommendAge(1);
				userCourse.setFinishFlag("0");
				
				String newIds = "";
				String[] ids = cou.getMovementIds().split(",");
				for (String id : ids) {
					Movement m = movementDao.get(id);
					UserMovement um = new UserMovement();
					um.setParent(m.getId());
					m.setId(null);
					
					BeanUtils.copyProperties(m,um);
					
					if ("1".equals(courseType)) {
						um.setGroupNum(m.getZengjiGroupNum());
						um.setMovementNum(m.getZengjiMovementNum());
					}else if("2".equals(courseType)) {
						um.setGroupNum(m.getSuxingGroupNum());
						um.setMovementNum(m.getSuxingMovementNum());
					}else if("3".equals(courseType)){
						um.setGroupNum(m.getJianzhiGroupNum());
						um.setMovementNum(m.getJianzhiMovementNum());
					}
					
					um.setType(courseType);
					
					
					userMovementService.save(um);
					
					newIds += um.getId()+",";
				}
				
				userCourse.setMovementIds(newIds.substring(0, newIds.length()-1));
				
				save(userCourse);
			}
			
			//标记已推荐
			UserGym userGym = new UserGym();
			userGym.setUserId(user.getId());
			userGym.setGymId(gymId);
			userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
			List<UserGym> userGyms = userGymDao.findList(userGym);
			
			UserGym ug = userGyms.get(0);
			ug.setCourseRecommendFlag("1");
			userGymDao.update(ug);
			
			
			
			List<UserCourse> userCourseList = findUserCourseList(user, gymId);
		
			return userCourseList;
		}
		return null;
	}

	/**
	 * 获取用户的指定健身房的课程列表
	 * @param user
	 * @param gymId
	 * @return
	 */
	public List<UserCourse>  findUserCourseList(AppUser user, String gymId) {
		
		UserCourse condition = new UserCourse();
		condition.setUserId(user.getId());
		condition.setGymId(gymId);
		condition.getPage().setOrderBy(" a.seq ASC ");
		List<UserCourse> list = dao.findList(condition);
		
		return list;
		
	}
	
	/**
	 * 获取去重的母课程id
	 * @param userCourse
	 * @return
	 */
	public List<String> findDistinctList(UserCourse userCourse){
		return dao.findDistinctList(userCourse);
	}

	/**
	 * 删除指定用户指定健身房推荐的所有课程
	 * @param userId
	 * @param gymId
	 */
	@Transactional(readOnly=false)
	public void deleteUserCourses(String userId, String gymId) {
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(gymId)) {
			
			UserCourse condition = new UserCourse();
			condition.setUserId(userId);
			condition.setGymId(gymId);
			dao.deleteUserCourses(condition);
		}
		
	}


	/**
	 * 根据父课程和用户id查找子课程
	 * @param courseId
	 * @return
	 */
	public UserCourse findCourseByFatherCourse(String userId, String fatherId) {
		if (StringUtils.isBlank(fatherId) || StringUtils.isBlank(userId)) {
			return null;
		}
		
		UserCourse condition = new UserCourse();
		condition.setUserId(userId);
		condition.setCourseId(fatherId);
		List<UserCourse> list = dao.findList(condition);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		
		return null;
	}
	
	
	
	
}
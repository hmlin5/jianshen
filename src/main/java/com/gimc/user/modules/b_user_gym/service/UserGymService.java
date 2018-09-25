/**
 * 
 */
package com.gimc.user.modules.b_user_gym.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_user_gym.entity.UserGym;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_gym.dao.GymDao;
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_order.dao.OrderDao;
import com.gimc.user.modules.b_order.entity.Order;
import com.gimc.user.modules.b_user.dao.AppUserDao;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user_course.dao.UserCourseDao;
import com.gimc.user.modules.b_user_course.entity.UserCourse;
import com.gimc.user.modules.b_user_course.service.UserCourseService;
import com.gimc.user.modules.b_user_gym.dao.UserGymDao;

/**
 * 用户健身房关系Service
 * @author linhaomiao
 * @version 2018-05-11
 */
@Service
@Transactional(readOnly = true)
public class UserGymService extends CrudService<UserGymDao, UserGym> {

	@Autowired
	private AppUserDao appUserDao;
	@Autowired
	private GymDao gymDao;
	@Autowired
	private UserCourseDao userCourseDao;
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private UserGymDao userGymDao;
	
	public UserGym get(String id) {
		return super.get(id);
	}
	
	public UserGym getInbody(int id){
		return userGymDao.findGymUser(id);
	}
	
	public void saveInbodyUser(UserGym userGym){
		userGymDao.updateInbodyUser(userGym);
	}
	
	public UserGym get(UserGym userGym) {
		List<UserGym> list= super.findList(userGym);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<UserGym> findList(UserGym userGym) {
		return super.findList(userGym);
	}
	
	public Page<UserGym> findPage(Page<UserGym> page, UserGym userGym) {
		return super.findPage(page, userGym);
	}
	
	@Transactional(readOnly = false)
	public void save(UserGym userGym) {
		super.save(userGym);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserGym userGym) {
		super.delete(userGym);
	}
	
	
	
	/**
	 * 
	 * @param page
	 * @param userGym
	 * @return
	 */
	public Page<UserGym> findGymUserPage(Page<UserGym> page, UserGym userGym) {
		userGym.setPage(page);
		page.setList(dao.findGymUserList(userGym));
		return page;
	}
	
	/**
	 * 判断是否第一次推荐课程, true:表示是第一次, false则否
	 * @param id
	 * @param gymId
	 * @return
	 */
	public boolean judgeFirstRecommend(String id, String gymId) {
		
		UserGym userGym = new UserGym();
		userGym.setUserId(id);
		userGym.setGymId(gymId);
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		userGym.setRelation(CommonParam.USER_TYPE_SIJIAO);
		List<UserGym> list = dao.findList(userGym);
		
		if (list==null || list.size() == 0) {
			return true;
		}
		
		String flag = list.get(0).getCourseRecommendFlag();
		if ("1".equals(flag)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 获取指定健身房的教练id列表
	 * @param gymId
	 * @return
	 */
	public List<String> findCoachListByGymId(String gymId) {
		UserGym userGym = new UserGym();
		userGym.setGymId(gymId);
		userGym.setRelation(CommonParam.USER_TYPE_JIAOLIAN);
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		List<UserGym> list = dao.findList(userGym);
		if (list!=null && list.size()>0) {
			List<String> ids = new ArrayList<String>();
			
			for (UserGym ug : list) {
				ids.add(ug.getUserId());
			}
			
			return ids;
		}
		
		
		return null;
	}
	
	/**
	 * 获取用户的主管教练
	 * @param id
	 * @param gymId
	 * @return
	 */
	public AppUser findSupervisorCoach(String id, String gymId) {
		
		UserGym userGym = new UserGym();
		userGym.setUserId(id);
		userGym.setGymId(gymId);
		userGym.setRelation(CommonParam.USER_TYPE_SIJIAO);
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		List<UserGym> list = dao.findList(userGym);
		if (list != null && list.size()>0) {
			
			String coachId = list.get(0).getCoachId();
			if (StringUtils.isBlank(coachId)) {
				return null;
			}
			
			return appUserDao.get(coachId);
		}
		return null;
	}
	
	/**
	 * 获取教练所在的健身房
	 * @param user
	 * @return
	 */
	public Gym getGymByCoachId(AppUser user){
		
		UserGym condition = new UserGym();
		condition.setUserId(user.getId());
		condition.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		condition.setRelation(CommonParam.USER_TYPE_JIAOLIAN);
		List<UserGym> list = findList(condition);
		if (list!=null && list.size()>0) {
			UserGym userGym = list.get(0);
			String gymId = userGym.getGymId();
			Gym gym = gymDao.get(gymId);
			if (gym!=null) {
				return gym;
			}
		}
		
		return null;
	}
	
	/**
	 * 获取会员在指定健身房的会员截止时间
	 * @param stu
	 * @param id
	 * @return
	 */
	public Date getStuVipDeadLine(AppUser stu, String gymId) {
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			
			
			if (stu == null || StringUtils.isBlank(gymId)) {
				return null;
			}
			
			UserGym ug = new UserGym();
			ug.setUserId(stu.getId());
			ug.setGymId(gymId);
			ug.setBindFlag(CommonParam.USER_GYM_BIND_YES);
			List<UserGym> list = dao.findList(ug);
			
			if (list!=null && list.size()>0) {
				UserGym userGym = list.get(0);
				Date cardEndDate = userGym.getCardEndDate();
				
				if (cardEndDate != null) {
					String format = sdf.format(cardEndDate);
					Date parse = sdf.parse(format);
					return parse;
				}
				
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	
	/**
	 * 获取会员在指定健身房的未上推荐课程数
	 * @param stu
	 * @param gymId
	 * @return
	 */
	public int getStuLeftCourseNum(AppUser stu, String gymId) {
		
		int left = 0;
		
		UserCourse uc = new UserCourse();
		uc.setUserId(stu.getId());
		uc.setGymId(gymId);
		List<UserCourse> list = userCourseDao.findList(uc);
		if (list!=null && list.size()>0) {
			for (UserCourse c : list) {
				if (!CommonParam.COURSE_YIWANCHENG.equals(c.getFinishFlag())) {
					left++;
				}
				
			}
		}
		
		
		return left;
	}
	
	
	/**
	 * 会员教练数
	 * @param user
	 * @return
	 */
	public int findMyCoachNum(AppUser user) {
		
		
		List<String> coachList = new ArrayList<String>();
		//主管教练数
		UserGym userGym = new UserGym();
		userGym.setUserId(user.getId());
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		List<UserGym> list = dao.findList(userGym);
		for (UserGym ug : list) {
			if (StringUtils.isNotBlank(ug.getCoachId())) {
				coachList.add(ug.getCoachId());
			}
		}
		
		Order order = new Order();
		order.setStuId(user.getId());
		List<String> includeStatus = Arrays.asList(CommonParam.ORDER_STATUS_DAIPINGJIA, CommonParam.ORDER_STATUS_YIWANCHENG);
		order.setIncludeStatus(includeStatus);
		List<Order> orders = orderDao.findList(order);
		for (Order o : orders) {
			if (StringUtils.isNotBlank(o.getCoachId())) {
				coachList.add(o.getCoachId());
			}
		}
		
		if (coachList.size() == 0) {
			return 0;
		}
		
		LinkedHashSet<String> set = new LinkedHashSet<String>();
		set.addAll(coachList);
		coachList.clear();
		coachList.addAll(set);
		
		return coachList.size();
	}
	
	/**
	 * 获取教练的学员数
	 * @param user
	 * @return
	 */
	public int findMyStuNum(AppUser user) {
		List<String> stuList = new ArrayList<String>();
		//主管学员数
		UserGym userGym = new UserGym();
		userGym.setCoachId(user.getId());
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		List<UserGym> list = dao.findList(userGym);
		for (UserGym ug : list) {
			if (StringUtils.isNotBlank(ug.getUserId())) {
				stuList.add(ug.getUserId());
			}
		}
		//曾上课学员数
		Order order = new Order();
		order.setCoachId(user.getId());
		List<String> includeStatus = Arrays.asList(CommonParam.ORDER_STATUS_DAIPINGJIA, CommonParam.ORDER_STATUS_YIWANCHENG);
		order.setIncludeStatus(includeStatus);
		List<Order> orders = orderDao.findList(order);
		for (Order o : orders) {
			if (StringUtils.isNotBlank(o.getStuId())) {
				stuList.add(o.getStuId());
			}
		}
		
		if (stuList.size() == 0) {
			return 0;
		}
		
		LinkedHashSet<String> set = new LinkedHashSet<String>();
		set.addAll(stuList);
		stuList.clear();
		stuList.addAll(set);
		
		return stuList.size();
	}

	/**
	 * 维护app用户类型
	 * @param userId
	 */
	@Transactional(readOnly = false)
	public void updateAppUserType(String userId) {
		UserGym condition = new UserGym();
		condition.setUserId(userId);
		condition.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		List<UserGym> list = findList(condition);
		
		String userType = CommonParam.USER_TYPE_ZHUCE;
		boolean stuFlag = false;
		boolean coachFlag = false;
		for (UserGym ug : list) {
			if (!stuFlag && CommonParam.USER_TYPE_SIJIAO.equals(ug.getRelation())) {
				stuFlag = true;
			}
			if (!coachFlag && CommonParam.USER_TYPE_JIAOLIAN.equals(ug.getRelation())) {
				coachFlag = true;
			}
		}
		
		if (stuFlag == true && coachFlag == false) {
			userType = CommonParam.USER_TYPE_SIJIAO;
		}
		if (stuFlag == false && coachFlag == true) {
			 userType = CommonParam.USER_TYPE_JIAOLIAN;
		}
		if (stuFlag == true && coachFlag == true) {
			 userType = CommonParam.USER_TYPE_SIJIAO_JIAOLIAN;
		}
		
		//把已经推荐的课程删除
		
		AppUser appUser = appUserDao.get(userId);
		appUser.setUserType(userType);
		appUserDao.update(appUser);
		
	}
	
	//查找所有的request是否为空
		public Map<String,String> requestParams(HttpServletRequest request){
			Map<String,String> map=new HashMap<String,String>();
			Enumeration<String> parameterNames=request.getParameterNames();
			
			while(parameterNames.hasMoreElements()){
				String paramName=(String)parameterNames.nextElement();
				String[] paramValue=request.getParameterValues(paramName);
				if(paramValue.length==1){
					String param=paramValue[0];
					if(paramValue.length!=0){
						map.put(paramName, param);
					}
				}
			}	
			return map;
		}
	
}
package com.gimc.api.course;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gimc.user.modules.sys.entity.Dict;
import com.gimc.user.modules.sys.service.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gimc.user.common.config.Global;
import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.common.utils.burningUtil.CourseResult;
import com.gimc.user.common.utils.burningUtil.ListSortUtil;
import com.gimc.user.common.utils.burningUtil.LocationUtils;
import com.gimc.user.common.utils.burningUtil.PageBean;
import com.gimc.user.common.utils.burningUtil.RequestMap;
import com.gimc.user.common.utils.burningUtil.ResultMap;
import com.gimc.user.modules.b_bmi_course.service.BmiCourseService;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_course.entity.Course;
import com.gimc.user.modules.b_course.service.CourseService;
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_gym.service.GymService;
import com.gimc.user.modules.b_gym_img.entity.GymImg;
import com.gimc.user.modules.b_gym_img.service.GymImgService;
import com.gimc.user.modules.b_movement.entity.Movement;
import com.gimc.user.modules.b_movement.service.MovementService;
import com.gimc.user.modules.b_movement_catalog.entity.MovementCatalog;
import com.gimc.user.modules.b_movement_catalog.service.MovementCatalogService;
import com.gimc.user.modules.b_order.entity.Order;
import com.gimc.user.modules.b_rest_time.entity.RestTime;
import com.gimc.user.modules.b_rest_time.service.RestTimeService;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user.service.AppUserService;
import com.gimc.user.modules.b_user_course.entity.UserCourse;
import com.gimc.user.modules.b_user_course.service.UserCourseService;
import com.gimc.user.modules.b_user_gym.entity.UserGym;
import com.gimc.user.modules.b_user_gym.service.UserGymService;
import com.gimc.user.modules.b_user_movement.entity.UserMovement;
import com.gimc.user.modules.b_user_movement.service.UserMovementService;
import com.gimc.user.modules.b_user_test.entity.UserTest;
import com.gimc.user.modules.b_user_test.service.UserTestService;


@Controller
@RequestMapping("/api/course")
public class ApiCourseController {
	
	//Logger logger = Logger.getLogger(this.getClass());
	
	
	@Autowired
	private AppUserService userService;
	@Autowired
	private UserTestService userTestService;
	@Autowired
	private GymService gymService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private MovementService movementService;
	@Autowired
	private UserCourseService userCourseService;
	@Autowired
	private UserMovementService userMovementService;
	@Autowired
	private UserGymService userGymService;
	@Autowired
	private MovementCatalogService movementCatalogService;
	@Autowired
	private RestTimeService restTimeService;
	@Autowired
	private DictService dictService;
	
	
	
	
	
	/**
	 * 根据母课程查询所有含此课的我的所有会员
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/myStuListByCourse")
	public ResultMap myStuListByCourse(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		Date date = param.getDate(); // yyyy/MM/dd HH:mm
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		
		String courseId = param.getCourseId();
		if (date == null || StringUtils.isBlank(courseId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
	
		
		try {
			//获取教练所在健身房
			Gym gym = userGymService.getGymByCoachId(user);
			if (gym == null) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("您暂无绑定健身房");
			}
			
			//判断教练在选择课程和选择时间内是否有空
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = sdf.format(date);
			Date restDate = sdf.parse(dateStr);
			
			
			Course course = courseService.get(courseId);
			long duration = course.getDuration().longValue()*60*1000;
			Date endDate = restTimeService.getDateTimeByLong(date, duration);
			
			RestTime restTime = new RestTime();
			restTime.setBeginTime(date);
			restTime.setEndTime(endDate);
			restTime.setGymId(gym.getId());
			restTime.setRestDate(restDate);
			List<String> coachIds = restTimeService.findBusyCoachList(restTime);
			if (coachIds.contains(user.getId())) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("所选时间段您已有其他安排");
			}
			
			
			
			//获取名下所有会员id, 作为下一步条件
			user.setGymId(gym.getId());
			List<AppUser> list = userService.findMyStuListInGym(user);
			if (list == null || list.size() < 1) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("您名下暂无学员");
			}
			
			List<String> includeUserIds = new ArrayList<String>();
			for (AppUser au : list) {
				includeUserIds.add(au.getId());
			}
			
			
			UserCourse uc = new UserCourse();
			uc.setIncludeUserIds(includeUserIds);
			uc.setGymId(gym.getId());
			uc.setFinishFlag(CommonParam.COURSE_WEIKAISHI);
			uc.setCourseId(courseId);
			List<UserCourse> courses = userCourseService.findList(uc);
			
			
			includeUserIds.clear();
			for (UserCourse userCourse : courses) {
				includeUserIds.add(userCourse.getUserId());
			}
			//去重
			LinkedHashSet<String> set = new LinkedHashSet<String>();
			set.addAll(includeUserIds);
			includeUserIds.clear();
			includeUserIds.addAll(set);
			
			
			AppUser appUser = new AppUser();
			appUser.setIncludeIds(includeUserIds);
			appUser.setActivateFlag(CommonParam.ACTIVE_YES);
			List<AppUser> users = userService.findList(appUser);
			
			
			PageBean<AppUser> pageBean = new PageBean<AppUser>();
			if (users!=null && users.size()>0) {
				pageBean = new PageBean<AppUser>(currentPage, pageSize, users.size());
				pageBean.setPageData(users.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > users.size() ? users.size() : (pageBean.getStartIndex()+pageSize)));
			}
			
		
			return map.setState(CommonParam.STATE_OK).setData(pageBean);
			
			
		} catch (ParseException e) {
			e.printStackTrace();
			return map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙...");
		}
		
		
	
	}
	
	
	
	
	/**
	 * 查看名下所有学员的未上课课程列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/stuUnfinishCourseList")
	public ResultMap stuUnfinishCourseList(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		//Date date = param.getDate();
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		
		String courseName = param.getName();
		
	
		//获取教练所在健身房
		Gym gym = userGymService.getGymByCoachId(user);
		if (gym == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("您暂无绑定健身房");
		}
		
		//获取名下所有会员id, 作为下一步条件
		List<AppUser> list = userService.findMyStuList(user);
		if (list == null || list.size() < 1) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("您名下暂无学员");
		}
		
		List<String> includeUserIds = new ArrayList<String>();
		for (AppUser au : list) {
			includeUserIds.add(au.getId());
		}
		
		//获取所有去重母课程id
		UserCourse uc = new UserCourse();
		uc.setIncludeUserIds(includeUserIds);
		uc.setGymId(gym.getId());
		uc.setFinishFlag(CommonParam.COURSE_WEIKAISHI);
		List<String> courseIds = userCourseService.findDistinctList(uc);
		
		//获取所有母课程
		Course course = new Course();
		course.setIncludeIds(courseIds);
		course.setName(courseName);
		List<Course> courseList = courseService.findList(course);
		
		PageBean<Course> pageBean = new PageBean<Course>();
		if (courseList!=null && courseList.size()>0) {
			pageBean = new PageBean<Course>(currentPage, pageSize, courseList.size());
			pageBean.setPageData(courseList.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > courseList.size() ? courseList.size() : (pageBean.getStartIndex()+pageSize)));
		}
		
		
	
		return map.setState(CommonParam.STATE_OK).setData(pageBean);
	}
	
	
	
	
	/**
	 * 教练新增动作-最终提交
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/addMovement")
	public ResultMap addMovement(RequestMap param, HttpServletResponse response,HttpServletRequest request){
		ResultMap map = new ResultMap();
	
		String movementIds = param.getMovementIds();
		String courseId = param.getCourseId();
		if (StringUtils.isBlank(courseId) || StringUtils.isBlank(movementIds)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		UserCourse userCourse = userCourseService.get(courseId);
		String courseType = userCourse.getRecomendRage();
		
		String newIds = userCourse.getMovementIds();
		String[] ids = movementIds.split(",");
		for (String id : ids) {
			Movement m = movementService.get(id);
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
			
			newIds += ","+um.getId();
		}
		
		userCourse.setMovementIds(newIds);
		
		userCourseService.save(userCourse);
		
		
		List<UserMovement> list = new ArrayList<UserMovement>();
		String[] idArr = userCourse.getMovementIds().split(",");
		for (String id : idArr) {
			UserMovement um = userMovementService.get(id);
			list.add(um);
		}
		
		return map.setState(CommonParam.STATE_OK).setData(list);
	}
	
	
	
	/**
	 * 教练新增动作-选择动作列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/movementChooseList")
	public ResultMap movementChooseList(RequestMap param, HttpServletResponse response,HttpServletRequest request){
		ResultMap map = new ResultMap();
	
		String catalogId = param.getCatalogId();
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		
		
		if (StringUtils.isBlank(catalogId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		MovementCatalog catalog = movementCatalogService.get(catalogId);
		
		
		Movement condition = new Movement();
		condition.setCatalogIds(catalogId);
		condition.setContainFlag(true);
		List<Movement> list = movementService.findList(condition);
		
		PageBean<Movement> pageBean = new PageBean<Movement>();
		if (list!=null && list.size()>0) {
			pageBean = new PageBean<Movement>(currentPage, pageSize, list.size());
			pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
			
		}
		
		return map.setState(CommonParam.STATE_OK).setData(pageBean);
	}
	
	
	
	
	/**
	 * 教练新增动作-选择二级动作分类列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/movementSecondCatalogList")
	public ResultMap movementSecondCatalogList(RequestMap param, HttpServletResponse response,HttpServletRequest request){
		ResultMap map = new ResultMap();
	
		String catalogId = param.getCatalogId();
		if (StringUtils.isBlank(catalogId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		MovementCatalog mc = new MovementCatalog();
		mc.setParentId(catalogId);
		List<MovementCatalog> list = movementCatalogService.findList(mc);
		
//		CourseResult cr = new CourseResult();
//		cr.setName(parentCatalog.getName());
//		cr.setMcList(list);
		
		return map.setState(CommonParam.STATE_OK).setData(list);
	}
	
	
	/**
	 * 教练新增动作-选择一级动作分类列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/movementParentCatalogList")
	public ResultMap movementParentCatalogList(RequestMap param, HttpServletResponse response,HttpServletRequest request){
		ResultMap map = new ResultMap();
	
		MovementCatalog mc = new MovementCatalog();
		mc.setParentId("0");
		List<MovementCatalog> list = movementCatalogService.findList(mc);
		
		
		return map.setState(CommonParam.STATE_OK).setData(list);
	}
	
	
	/**
	 * 教练删除动作
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/deleteMovement")
	public ResultMap deleteMovement(RequestMap param, HttpServletResponse response,HttpServletRequest request){
		ResultMap map = new ResultMap();
	
		AppUser user = (AppUser) request.getAttribute("user");
		
		String courseId = param.getCourseId();
		String movementId = param.getMovementId();
		
		if (StringUtils.isBlank(courseId) || StringUtils.isBlank(movementId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		UserCourse userCourse = userCourseService.get(courseId);
		UserMovement userMovement = userMovementService.get(movementId);
		if (userMovement == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("该动作已删除");
		}
		userMovementService.delete(userMovement);
		
		//计算动作总数,只剩下一个动作不允许删除
		String movementIds = userCourse.getMovementIds();
		String[] ids = movementIds.split(",");
		if (ids.length < 2) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请保留至少一个动作");
		}
		
		
		String newIds = "";
		
		List<UserMovement> list = new ArrayList<UserMovement>();
		for (String id : ids) {
			
			if (movementId.equals(id)) {
				continue;
			}
			newIds += id+",";
			UserMovement um = userMovementService.get(id);
			list.add(um);
			
			
		}
		
		userCourse.setMovementIds(newIds.substring(0, newIds.length()-1));
		userCourse.setMovementNum(ids.length-1);
		userCourseService.save(userCourse);
		
		return map.setData(list).setState(CommonParam.STATE_OK);
	}
	
	
	
	
	
	/**
	 * 教练编辑动作列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/movementList")
	public ResultMap movementList(RequestMap param, HttpServletResponse response,HttpServletRequest request){
		ResultMap map = new ResultMap();
	
		AppUser user = (AppUser) request.getAttribute("user");
		
		String courseId = param.getCourseId();
		
		if (StringUtils.isBlank(courseId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		UserCourse userCourse = userCourseService.get(courseId);
		
		String ids = userCourse.getMovementIds();
		if (StringUtils.isBlank(ids)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("动作列表为空");
		}
		
		List<UserMovement> list = new ArrayList<UserMovement>();
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			UserMovement um = userMovementService.get(id);
			list.add(um);
		}
		
		
		return map.setState(CommonParam.STATE_OK).setData(list);
	
	}
	
	/**
	 * 教练查看名下会员课程列表
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/myStuCourseList")
	public ResultMap myStuCourseList(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		String userId = param.getUserId();
		if (StringUtils.isBlank(userId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("用户id为空");
		}
		AppUser stu = userService.get(userId);
		
		
		Gym gym = userGymService.getGymByCoachId(user);
		if (gym == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("系统异常");
		}
		
		String gymId = gym.getId();
		
		
		List<UserCourse> list = new ArrayList<UserCourse>();
		list = userCourseService.findUserCourseList(stu,gymId);
		
		if (list.size() == 0) {
			return map.setState(CommonParam.STATE_DATA_NOT_EXIST).setMsg("暂时没有推荐课程,请期待");
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("courseList", list);
		int finished = 0;
		int unfinished = list.size();
		for (UserCourse userCourse : list) {
			if ("1".equals(userCourse.getFinishFlag())) {
				finished++ ;
				unfinished--; 
			}
		}
		data.put("finished", finished);
		data.put("unfinished", unfinished);
		DecimalFormat df = new DecimalFormat("0%");  
		double a = (double)unfinished/list.size();
		String left = df.format(a);
		data.put("left",left);
		
		String courseTip = "已上"+finished+"节课，剩余"+unfinished+"节"; //剩余课程提示语
		data.put("courseTip", courseTip);
	
		return map.setState(CommonParam.STATE_OK).setData(data);
	}
	
	
	
	
	/**
	 * 查看用户推荐课程列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/myCourseList")
	public ResultMap myCourseList(HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
	
		AppUser user = (AppUser) request.getAttribute("user");
		
		String gymId = request.getParameter("gymId");
		if (StringUtils.isBlank(gymId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
		}
		
		List<UserCourse> list = new ArrayList<UserCourse>();
		//判断是否第一次推荐课程
		boolean recommendFlag = userGymService.judgeFirstRecommend(user.getId(),gymId);
		if (recommendFlag) {//第一次推荐
			list = userCourseService.recommendCourse(user,gymId);
		}else {
			list = userCourseService.findUserCourseList(user,gymId);
		}
		
		if (list==null||list.size() == 0) {
			return map.setState(CommonParam.STATE_DATA_NOT_EXIST).setMsg("暂时没有推荐课程,请期待");
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("courseList", list);
		int finished = 0;
		int unfinished = list.size();
		for (UserCourse userCourse : list) {
			if ("1".equals(userCourse.getFinishFlag())) {
				finished++ ;
				unfinished--; 
			}
		}
		data.put("finished", finished);
		data.put("unfinished", unfinished);
		DecimalFormat df = new DecimalFormat("0%");  
		double a = (double)unfinished/list.size();
		String left = df.format(a);
		data.put("left",left);
		
		String courseTip = "您已上"+finished+"节课，剩余"+unfinished+"节，继续努力哦！"; //剩余课程提示语
		data.put("courseTip", courseTip);
		
		return map.setData(data).setState(CommonParam.STATE_OK);
	}
	
	
	
	/**
	 * 查看用户推荐课程详情
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/courseDetail")
	public ResultMap courseDetail(HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
	
		AppUser user = (AppUser) request.getAttribute("user");
		
		String courseId = request.getParameter("courseId");
		if (StringUtils.isBlank(courseId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
		}
		
		//教练约课时课程选择 需要判断,需要app端传一个标识过来sourceFlag=true标识查询父课程
		String sourceFlag = request.getParameter("sourceFlag");
		UserCourse userCourse = new UserCourse();
		if ("true".equals(sourceFlag)) {
			Course course = courseService.get(courseId);
			course.setId(null);
			BeanUtils.copyProperties(course,userCourse);
		}else {
			userCourse = userCourseService.get(courseId);
		}
		
		
		//计算动作总数, //TODO 改成保存时计算
		String movementIds = userCourse.getMovementIds();
		String[] ids = movementIds.split(",");
		userCourse.setMovementNum(ids.length);
		
		List<CourseResult> movements = new ArrayList<CourseResult>();
		
		List<UserMovement> warnupMovements = new ArrayList<UserMovement>();
		List<UserMovement> equipMovements = new ArrayList<UserMovement>();
		Dict dict=new Dict();
		dict.setType("equipment_type");
		List<Dict>  list= dictService.findList(dict);
		String instrumentSummary="";
		for (String id : ids) {
			UserMovement um = userMovementService.get(id);
			for (Dict dict1 : list) {
				if(dict1.getValue().equals(um.getEquipmentType())&&!"1".equals(dict1.getValue())){
					instrumentSummary+=dict1.getLabel()+",";
				}
				if(dict1.getValue().equals(um.getEquipmentType())){
					um.setInstrumentSummary(dict1.getLabel());
				}
			}

			String p = um.getPelvicStability();
			String c = um.getCoreActivation();
			String r = um.getRespiratoryReconstruction();
			if ("2".equals(p) || "2".equals(c) || "2".equals(r) ) {
				warnupMovements.add(um);
			}else {
				equipMovements.add(um);
			}
		}
		if(StringUtils.isNotBlank(instrumentSummary)){
			userCourse.setInstrumentSummary(instrumentSummary.substring(0,instrumentSummary.length() - 1));
		}else{
			userCourse.setInstrumentSummary("无");
		}

		if (warnupMovements.size() > 0) {
			CourseResult cr1 = new CourseResult();
			cr1.setName("热身");
			cr1.setList(warnupMovements);
			movements.add(cr1);
		}
		if (equipMovements.size() > 0) {
			CourseResult cr2 = new CourseResult();
			cr2.setName("训练");
			cr2.setList(equipMovements);
			movements.add(cr2);
		}

		userCourse.setMovements(movements);
		
		return map.setData(userCourse).setState(CommonParam.STATE_OK);
	}
	
	
	/**
	 * 查看动作详情
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/moveMentDetail")
	public ResultMap moveMentDetail(HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
	
		
		String movementId = request.getParameter("movementId");
		if (StringUtils.isBlank(movementId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
		}
		
		
		UserMovement um = userMovementService.get(movementId);
		Dict dict=new Dict();
		dict.setType("equipment_type");
		List<Dict>  list= dictService.findList(dict);
		for (Dict dict1 : list) {
			if(dict1.getValue().equals(um.getEquipmentType())){
				um.setInstrumentSummary(dict1.getLabel());
			}
		}
		return map.setState(CommonParam.STATE_OK).setData(um);
	}
	
	
    
	
}

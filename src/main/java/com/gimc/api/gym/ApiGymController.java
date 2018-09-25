package com.gimc.api.gym;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.common.utils.burningUtil.ListSortUtil;
import com.gimc.user.common.utils.burningUtil.LocationUtils;
import com.gimc.user.common.utils.burningUtil.PageBean;
import com.gimc.user.common.utils.burningUtil.RequestMap;
import com.gimc.user.common.utils.burningUtil.ResultMap;
import com.gimc.user.modules.b_bmi_course.service.BmiCourseService;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_gym.service.GymService;
import com.gimc.user.modules.b_gym_img.entity.GymImg;
import com.gimc.user.modules.b_gym_img.service.GymImgService;
import com.gimc.user.modules.b_order.entity.Order;
import com.gimc.user.modules.b_order.service.OrderService;
import com.gimc.user.modules.b_rest_time.entity.RestTime;
import com.gimc.user.modules.b_rest_time.service.RestTimeService;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user.service.AppUserService;
import com.gimc.user.modules.b_user_course.entity.UserCourse;
import com.gimc.user.modules.b_user_course.service.UserCourseService;
import com.gimc.user.modules.b_user_gym.entity.UserGym;
import com.gimc.user.modules.b_user_gym.service.UserGymService;
import com.gimc.user.modules.b_user_test.entity.UserTest;
import com.gimc.user.modules.b_user_test.service.UserTestService;


@Controller
@RequestMapping("/api/gym")
public class ApiGymController {
	
	//Logger logger = Logger.getLogger(this.getClass());
	
	
	@Autowired
	private AppUserService userService;
	
	@Value("${RESOURECE_URL}")
	private String RESOURECE_URL;
	
	@Value("${userfiles.basedir}")  
    public  String MULTIMEDIA_PATH; 
	
	@Autowired
	private GymService gymService;
	@Autowired
	private GymImgService gymImgService;
	@Autowired
	private UserGymService userGymService;
	@Autowired
	private UserTestService userTestService;
	@Autowired
	private RestTimeService restTimeService;
	@Autowired
	private UserCourseService userCourseService;
	@Autowired
	private OrderService orderService;
	
	
	
	
	
	
	
	
	
	/**
	 * 查询在指定健身房学员或者教练上课时长排名
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/gymRank")
	public ResultMap gymRank(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		String userType = param.getUserType(); //2:学员, 4:教练
		String gymId = param.getGymId();
		if ( StringUtils.isBlank(gymId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		List<Order> list = new ArrayList<Order>();
		if (StringUtils.isBlank(userType)|| CommonParam.USER_TYPE_SIJIAO .equals(userType)) {
			list = orderService.findStuRankList(gymId);
		}else{
			list = orderService.findCoachRankList(gymId);
		}
		//分页
		PageBean<Order> pageBean = new PageBean<Order>();
		if (list!=null &&list.size()>0) {
			pageBean = new PageBean<Order>(currentPage, pageSize, list.size());
			pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
			
		}
		
		return map.setState(CommonParam.STATE_OK).setData(pageBean);
		
	}
	
	
	
	
	
	/**
	 * 查询用户在指定健身房的健身情况
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/userTrainDetail")
	public ResultMap userTrainDetail(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
	
		String userId = param.getUserId();
		String gymId = param.getGymId();
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(gymId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		String trainTime = "0"; //总训练时长
		int rank = 100; //在健身房的排名
	
		List<Order> list = orderService.findStuRankList(gymId);
		
		for (Order order : list) {
			if (userId.equals(order.getStuId())) {
				rank = order.getRank();
				trainTime = order.getTotalCourseTime();
			}
		}
		
		
		
		
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("trainTime", trainTime);
		result.put("rank", rank);
		
		return map.setState(CommonParam.STATE_OK).setData(result);
		
	}
	
	
	
	
	
	
	/**
	 * 查询指定健身房所有教练列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/gymAllCoachList")
	public ResultMap gymAllCoachList(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		String gymId = param.getGymId();
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		
		if (StringUtils.isBlank(gymId) ) {
			map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
			return map;
		}
		
		//获取指定健身房的所有教练id列表
		List<String> coachIds = userGymService.findCoachListByGymId(gymId);
		
		//指定为教练类型
		List<String> includeUserTypes = new ArrayList<String>(); 
		includeUserTypes.add(CommonParam.USER_TYPE_SIJIAO_JIAOLIAN);
		includeUserTypes.add(CommonParam.USER_TYPE_JIAOLIAN);
		
		//查询教练
		AppUser appUser = new AppUser();
		appUser.setActivateFlag(CommonParam.ACTIVE_YES);
		appUser.setIncludeUserTypes(includeUserTypes);
		appUser.setIncludeIds(coachIds);
		List<AppUser> userList = userService.findList(appUser);
	
		
		//分页
		PageBean<AppUser> pageBean = new PageBean<AppUser>();
		if (userList!=null && userList.size()>0) {
			pageBean = new PageBean<AppUser>(currentPage, pageSize, userList.size());
			pageBean.setPageData(userList.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > userList.size() ? userList.size() : (pageBean.getStartIndex()+pageSize)));
			
		}
		
		map.setState(CommonParam.STATE_OK).setData(pageBean);
		return map;
	
	}
	
	/**
	 * 查询教练所在健身房
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/coachGym")
	public ResultMap coachGym(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
	
		UserGym condition = new UserGym();
		condition.setUserId(user.getId());
		condition.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		condition.setRelation(CommonParam.USER_TYPE_JIAOLIAN);
		List<UserGym> list = userGymService.findList(condition);
		if (list!=null && list.size()>0) {
			UserGym userGym = list.get(0);
			String gymId = userGym.getGymId();
			Gym gym = gymService.get(gymId);
			if (gym!=null) {
				return map.setState(CommonParam.STATE_OK).setData(gym);
			}
		}
		return map.setState(CommonParam.STATE_ERROR).setMsg("该用户暂时没有绑定任何健身房");
	}
	
	
	/**
	 * 查询指定健身房空闲教练列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/gymCoachList")
	public ResultMap gymCoachList(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		
		String gymId = param.getGymId();
		Date date = param.getDate();
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		String courseId = param.getCourseId();
		
		if (StringUtils.isBlank(gymId) || date == null || StringUtils.isBlank(courseId)) {
			map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
			return map;
		}
		
		
		try {
			
			UserCourse userCourse = userCourseService.get(courseId);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String restDate = sdf.format(date);
			
			//获取指定健身房的所有教练id列表
			List<String> coachIds = userGymService.findCoachListByGymId(gymId);
			if (coachIds == null || coachIds.size() < 1) {
				return map.setState(CommonParam.STATE_OK).setData(new PageBean<AppUser>());
			}
			
			
			long duration = userCourse.getDuration().longValue()*60*1000;
			Date endDate = restTimeService.getDateTimeByLong(date, duration);
			
			//获取时间冲突的教练id列表
			RestTime condition = new RestTime();
			condition.setBeginTime(date);
			condition.setEndTime(endDate);
			condition.setGymId(gymId);
			condition.setRestDate(sdf.parse(restDate));
			List<String> excludeIds = restTimeService.findBusyCoachList(condition); 
			//指定为教练类型
			List<String> includeUserTypes = new ArrayList<String>(); 
			includeUserTypes.add(CommonParam.USER_TYPE_SIJIAO_JIAOLIAN);
			includeUserTypes.add(CommonParam.USER_TYPE_JIAOLIAN);
			
			
			//查询可以接单的教练
			AppUser appUser = new AppUser();
			appUser.setActivateFlag("1");
			appUser.setExcludeIds(excludeIds);
			appUser.setIncludeUserTypes(includeUserTypes);
			appUser.setIncludeIds(coachIds);
			
			//内存分页, 因为主管教练需要在第一位, 其他按评分降序排
			List<AppUser> userList = userService.findList(appUser);
			//排序, 评分降序排
			ListSortUtil<AppUser> listSortUtil = new ListSortUtil<AppUser>();
			listSortUtil.sort(userList, "coachScore", "desc");
			
			//如果教练列表中存在主管, 移动到第一位
			AppUser coachUser = userGymService.findSupervisorCoach(user.getId(),gymId);
			if (coachUser != null) {
				for (int i = 0; i < userList.size(); i++) {
					
					AppUser au = userList.get(i);
					if (au.getId().equals(coachUser.getId())) {
						userList.add(0, userList.remove(i));
					}
				}
			}
			
			//分页
			PageBean<AppUser> pageBean = new PageBean<AppUser>();
			if (userList!=null && userList.size()>0) {
				pageBean = new PageBean<AppUser>(currentPage, pageSize, userList.size());
				pageBean.setPageData(userList.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > userList.size() ? userList.size() : (pageBean.getStartIndex()+pageSize)));
				
			}
			
			map.setState(CommonParam.STATE_OK).setData(pageBean);
			return map;
			
		} catch (Exception e) {
			e.printStackTrace();
			return map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙");
		}
		
		
		
		
	}
	
	
	
	
	/**
	 * 查询我绑定的健身房详情
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/myGymDetail")
	public ResultMap myGymDetail(HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		
		AppUser user = (AppUser) request.getAttribute("user");
		
		
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
			return map;
		}
		
		
		UserGym condition = new UserGym();
		condition.setUserId(user.getId());
		condition.setGymId(id);
		condition.setFreezeFlag("0");
		condition.setBindFlag("0");
		//condition.setFreezingState("0");
		List<UserGym> userGymList = userGymService.findList(condition);
		
		if (userGymList == null || userGymList.size() == 0) {
			map.setState(CommonParam.STATE_DATA_NOT_EXIST).setMsg("无数据");
			return map;
		}
		
		UserGym userGym = userGymList.get(0);
		
		Gym gym = gymService.get(id);
		if (gym == null) {
			map.setState(CommonParam.STATE_DATA_NOT_EXIST).setMsg("无数据");
			return map;
		}
		
		//TODO 排名需要查询,暂时写死
		String trainTime = "0"; //总训练时长
		int rank = 100; //在健身房的排名
		
		List<Order> rankOrders = orderService.findStuRankList(id);
		
		for (Order order : rankOrders) {
			if (user.getId().equals(order.getStuId())) {
				rank = order.getRank();
				trainTime = order.getTotalCourseTime();
				gym.setRank(rank);
				gym.setCourseTime(Integer.parseInt(trainTime));
			}
		}
		
		UserTest ut = new UserTest();
		ut.setUserId(user.getId());
		ut.getPage().setOrderBy(" a.create_date DESC ");
		List<UserTest> list = userTestService.findList(ut);
		if (list!=null && list.size()>0) {
			UserTest userTest = list.get(0);
			gym.setBurningGoal(userTest.getBurningGoal());
		}
		
		gym.setLoginName(null);
		gym.setPassword(null);
		map.setState(CommonParam.STATE_OK).setData(gym);
		return map;
		
	}
	
	
	
	
	/**
	 * 查询用户已绑定健身房列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/myGymList")
	public Map<String, Object> myGymList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String,Object>();
		
		AppUser user = (AppUser) request.getAttribute("user");
		
		
		String longituteStr = request.getParameter("longitute");
		String latituteStr = request.getParameter("latitute");
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");
		if (StringUtils.isBlank(longituteStr) || StringUtils.isBlank(latituteStr) || StringUtils.isBlank(currentPageStr) || StringUtils.isBlank(pageSizeStr)) {
			map.put("state",  CommonParam.STATE_ERROR);
			map.put("msg", "请求异常");
			return map;
		}
		
		double longitute = Double.parseDouble(longituteStr);
		double latitute = Double.parseDouble(latituteStr);
		int currentPage = Integer.parseInt(currentPageStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		
		UserGym condition = new UserGym();
		condition.setUserId(user.getId());
		condition.setFreezeFlag("0");
		condition.setBindFlag("0");
		condition.setRelation(CommonParam.USER_TYPE_SIJIAO);
		//condition.setFreezingState("0");
		List<UserGym> userGymList = userGymService.findList(condition);
		
		if (userGymList == null || userGymList.size() == 0) {
			map.put("state",  CommonParam.STATE_DATA_NOT_EXIST);
			map.put("msg", "无数据");
			return map;
		}
		
		List<Gym> list = new ArrayList<Gym>();
		for (UserGym userGym : userGymList) {
			Gym gym = gymService.get(userGym.getGymId());
			list.add(gym);
		}
		
		
		//计算距离
		for (Gym gym : list) {
			String latStr = gym.getLatitute();
			String lngstr = gym.getLongitute();
			
			if (StringUtils.isBlank(latStr) || StringUtils.isBlank(lngstr)) {
				continue;
			}
			
			double lat= Double.parseDouble(latStr);
			double lng = Double.parseDouble(lngstr);
			
			double distance = LocationUtils.getDistance(latitute, longitute, lat, lng);
			
			  BigDecimal bd=new BigDecimal(distance).setScale(0, BigDecimal.ROUND_HALF_UP);
			    gym.setDistance(Integer.parseInt(bd.toString())); 
			
		}
		
		//排序, 按照距离升序
		ListSortUtil<Gym> listSortUtil = new ListSortUtil<Gym>();
		listSortUtil.sort(list, "distance", "asc");
		
		
		//分页
		PageBean<Gym> pageBean = new PageBean<Gym>();
		if (list!=null &&list.size()>0) {
			pageBean = new PageBean<Gym>(currentPage, pageSize, list.size());
			pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
			
		}
		
		
		
		map.put("state", CommonParam.STATE_OK);
		map.put("data", pageBean);
		
		return map;
	}
	
	
	
	
	
	
	/**
	 * 查看健身房轮播图片
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/gymImgList")
	public ResultMap gymImgList(HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		String gymId = request.getParameter("gymId");
		if (StringUtils.isBlank(gymId)) {
			map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
			return map;
		}
		
		
		GymImg condition = new GymImg();
		condition.setGymId(gymId);
		condition.getPage().setOrderBy(" a.seq asc ");
		List<GymImg> list = gymImgService.findList(condition);
		
		map.setState(CommonParam.STATE_OK).setData(list);
		
		return map;
	}
	
	
	
	/**
	 * 查询健身房详情
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/gymDetail")
	public ResultMap gymDetail(HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
			return map;
		}
		
		Gym gym = gymService.get(id);
		if (gym == null) {
			map.setState(CommonParam.STATE_DATA_NOT_EXIST).setMsg("无数据");
			return map;
		}
		
		
		gym.setLoginName(null);
		gym.setPassword(null);
		map.setState(CommonParam.STATE_OK).setData(gym);
		return map;
		
	}
	
	
	/**
	 * 查询附近健身房
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/gymList")
	public Map<String, Object> gymList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String,Object>();
		
		
		String longituteStr = request.getParameter("longitute");
		String latituteStr = request.getParameter("latitute");
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");
		if (StringUtils.isBlank(longituteStr) || StringUtils.isBlank(latituteStr) || StringUtils.isBlank(currentPageStr) || StringUtils.isBlank(pageSizeStr)) {
			map.put("state",  CommonParam.STATE_ERROR);
			map.put("msg", "请求异常");
			return map;
		}
		
		double longitute = Double.parseDouble(longituteStr);
		double latitute = Double.parseDouble(latituteStr);
		int currentPage = Integer.parseInt(currentPageStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		
		Gym condition = new Gym();
		//condition.setFreezingState("0");
		List<Gym> list = gymService.findList(condition);
		
		if (list == null || list.size() == 0) {
			map.put("state",  CommonParam.STATE_DATA_NOT_EXIST);
			map.put("msg", "无数据");
			return map;
		}
		//计算距离
		for (Gym gym : list) {
			String latStr = gym.getLatitute();
			String lngstr = gym.getLongitute();
			
			if (StringUtils.isBlank(latStr) || StringUtils.isBlank(lngstr)) {
				continue;
			}
			
			double lat= Double.parseDouble(latStr);
			double lng = Double.parseDouble(lngstr);
			
			double distance = LocationUtils.getDistance(latitute, longitute, lat, lng);
			
			  BigDecimal bd=new BigDecimal(distance).setScale(0, BigDecimal.ROUND_HALF_UP);
			    gym.setDistance(Integer.parseInt(bd.toString())); 
			
		}
		
		//排序, 按照距离升序
		ListSortUtil<Gym> listSortUtil = new ListSortUtil<Gym>();
		listSortUtil.sort(list, "distance", "asc");
		
		
		//分页
		PageBean<Gym> pageBean = new PageBean<Gym>(1,10,0);
		if (list!=null &&list.size()>0) {
			pageBean = new PageBean<Gym>(currentPage, pageSize, list.size());
			pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
		}
		
		
		map.put("state", CommonParam.STATE_OK);
		map.put("data", pageBean);
		
		return map;
	}
	
	
	
    
	
}

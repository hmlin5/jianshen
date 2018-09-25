package com.gimc.api.user;

import com.alibaba.fastjson.JSON;
import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.common.utils.burningUtil.ListSortUtil;
import com.gimc.user.common.utils.burningUtil.MessageResult;
import com.gimc.user.common.utils.burningUtil.PageBean;
import com.gimc.user.common.utils.burningUtil.RequestMap;
import com.gimc.user.common.utils.burningUtil.ResultMap;
import com.gimc.user.common.utils.burningUtil.ScheduleResult;
import com.gimc.user.common.web.BaseController;
import com.gimc.user.modules.b_bmi_course.service.BmiCourseService;
import com.gimc.user.modules.b_comment.entity.Comment;
import com.gimc.user.modules.b_comment.service.CommentService;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_feedback.entity.Feedback;
import com.gimc.user.modules.b_feedback.service.FeedbackService;
import com.gimc.user.modules.b_follow_relation.entity.FollowRelation;
import com.gimc.user.modules.b_follow_relation.service.FollowRelationService;
import com.gimc.user.modules.b_group.entity.Group;
import com.gimc.user.modules.b_group.service.GroupService;
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_movement.entity.Movement;
import com.gimc.user.modules.b_msg.entity.Msg;
import com.gimc.user.modules.b_msg.service.MsgService;
import com.gimc.user.modules.b_order.entity.Order;
import com.gimc.user.modules.b_order.service.OrderService;
import com.gimc.user.modules.b_pay_info.entity.PayInfo;
import com.gimc.user.modules.b_pay_info.service.PayInfoService;
import com.gimc.user.modules.b_private_setting.entity.PrivateSetting;
import com.gimc.user.modules.b_private_setting.service.PrivateSettingService;
import com.gimc.user.modules.b_rest_time.entity.RestTime;
import com.gimc.user.modules.b_rest_time.service.RestTimeService;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user.entity.SysCheckCode;
import com.gimc.user.modules.b_user.service.AppUserService;
import com.gimc.user.modules.b_user_account.entity.UserAccount;
import com.gimc.user.modules.b_user_account.service.UserAccountService;
import com.gimc.user.modules.b_user_gym.entity.UserGym;
import com.gimc.user.modules.b_user_gym.service.UserGymService;
import com.gimc.user.modules.b_user_help.entity.UserHelp;
import com.gimc.user.modules.b_user_help.service.UserHelpService;
import com.gimc.user.modules.b_user_inbody.entity.UserInbody;
import com.gimc.user.modules.b_user_inbody.service.UserInbodyService;
import com.gimc.user.modules.b_user_test.entity.UserTest;
import com.gimc.user.modules.b_user_test.service.UserTestService;
import com.gimc.user.modules.sys.service.SystemService;
import com.gimc.user.modules.sys.utils.DictUtils;
import io.rong.models.response.TokenResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.codec.binary.Base64;

@Controller
@RequestMapping("/api/user")
public class ApiUserController extends BaseController {
	
	
	@Autowired
	private AppUserService userService;
	
	@Value("${RESOURECE_URL}")
	private String RESOURECE_URL;
	
	@Value("${userfiles.basedir}")  
    public  String MULTIMEDIA_PATH; 
	
	@Autowired
	private UserTestService userTestService;
	@Autowired
	private BmiCourseService bmiCourseService;
	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private MsgService msgService;
	@Autowired
	private RestTimeService restTimeService;
	@Autowired
	private UserGymService userGymService;
	@Autowired
	private PrivateSettingService privateSettingService;
	@Autowired
	private FollowRelationService followRelationService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserInbodyService userInbodyService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private PayInfoService payInfoService;
	@Autowired
	private UserHelpService userHelpService;
	
	
	/**
	 * 更改支付密码
	 * @param request
	 * @param
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/mobile/updatePayPwd")
	public ResultMap updatePayPwd(RequestMap param,HttpServletRequest request) throws Exception{

		AppUser user = (AppUser) request.getAttribute("user");
		ResultMap map = new ResultMap();
		String phoneNum = param.getPhoneNum();
		String newPwd = param.getNewPwd();
		String checkCode = param.getCheckCode();
	
		if (StringUtils.isBlank(phoneNum)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请输入手机号码！！！");
		}
		if (StringUtils.isBlank(newPwd)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请输入新密码");
		}
		
		
		try {
			//验证验证码
			SysCheckCode ck = userService.getCheckCode(phoneNum);
			if(ck==null || !ck.getCheckCode().equals(checkCode)){
				return map.setState(CommonParam.STATE_ERROR).setMsg("验证码不正确！");
			}else{
				
				UserAccount account = userAccountService.findByUserId(user);
				
				if(account!=null){
					account.setPayPwd(SystemService.entryptPassword(newPwd));
					userAccountService.save(account);
					return map.setState(CommonParam.STATE_OK).setMsg("修改成功");
				}else{
					return map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙！"); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return map.setState(CommonParam.STATE_ERROR).setMsg("服务器异常，请稍后再试！");
		}
		
	}
	
	
	
	
	
	
	
	/**
	 * 查看用户的打赏(或者受赏)记录
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/rewardList")
	public ResultMap rewardList(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		String rewardType = param.getRewardType(); //查看打赏或者受赏记录,  2:打赏记录, 4:受赏记录
		if (StringUtils.isBlank(rewardType)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
	
		
		
		List<PayInfo> list = new ArrayList<PayInfo>();
		if (CommonParam.USER_TYPE_SIJIAO.equals(rewardType)) {
			
			list = payInfoService.findStuPayInfoList(user);
			for (PayInfo pi : list) {
				String payDetail = "打赏\""+pi.getCoachName()+"\""; 
				pi.setPayDetail(payDetail);
				
			}
			
			
		}else if (CommonParam.USER_TYPE_JIAOLIAN.equals(rewardType)) {
			list = payInfoService.findCoachPayInfoList(user);
			for (PayInfo pi : list) {
				String payDetail = "收到\""+pi.getStuName()+"\"打赏"; 
				pi.setPayDetail(payDetail);
				
			}
			
		}
		
		PageBean<PayInfo> pageBean = new PageBean<PayInfo>();
		if (list!=null && list.size()>0) {
			pageBean = new PageBean<PayInfo>(currentPage, pageSize, list.size());
			List<PayInfo> subList = list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize));
			pageBean.setPageData(subList);
			
		}
		
		return map.setState(CommonParam.STATE_OK).setData(pageBean);
	}
	
	
	
	
	/**
	 * 查看指定用户的inbody数据详情
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/inbodyDetail")
	public ResultMap inbodyDetail(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		String id = param.getId();
		if (StringUtils.isBlank(id)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
	
		UserInbody userInbody = userInbodyService.get(id);
		
		return map.setState(CommonParam.STATE_OK).setData(userInbody);
	}
	
	
	/**
	 * 查看指定用户的inbody数据列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/inbodyList")
	public ResultMap inbodyList(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		String userId = param.getUserId();
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		if (StringUtils.isBlank(userId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
	
		
		UserInbody condition = new UserInbody();
		condition.setUserId(userId);
		List<UserInbody> list = userInbodyService.findList(condition);
		
		PageBean<UserInbody> pageBean = new PageBean<UserInbody>();
		if (list!=null && list.size()>0) {
			pageBean = new PageBean<UserInbody>(currentPage, pageSize, list.size());
			pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
			
		}
		
		return map.setState(CommonParam.STATE_OK).setData(pageBean);
	}
	
	
	
	/**
	 * 教练添加inbody数据
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/addInbody")
	public ResultMap addInbody(UserInbody param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		String userId = param.getUserId();
		if (StringUtils.isBlank(userId) || param.getHeight() == null || param.getWeight() == null || param.getHeight().doubleValue() <= 0 || param.getWeight().doubleValue() <= 0) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数异常");
		}
	
		
		//判断教练是否会员的主管教练
		UserGym userGym = new UserGym();
		userGym.setUserId(userId);
		userGym.setCoachId(user.getId());
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		List<UserGym> list = userGymService.findList(userGym);
		if (list == null || list.size() < 1) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("您不是该会员的主管教练"); 
		}
		
		Gym gym = userGymService.getGymByCoachId(user);
		
		
		//计算bmi指数
		double weightD = param.getWeight();
		double heightD = param.getHeight();
		BigDecimal b = new BigDecimal(weightD/(heightD*heightD/10000));
		double bmiIndex = b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
		
		
		//计算腰臀比
		if (param.getHipline() != null && param.getWaistline() != null && param.getHipline().doubleValue()>0 && param.getWaistline().doubleValue()>0) {
			
			BigDecimal bd = new BigDecimal(param.getWaistline().doubleValue()/param.getHipline().doubleValue());  
			double whr = bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
			param.setWhr(whr+"");
		}else{
			param.setHipline(null);
			param.setWaistline(null);
		}
	
		param.setUserId(userId);
		param.setCoachId(user.getId());
		param.setGymId(gym.getId());
		if (param.getTestTime() == null) {
			param.setTestTime(new Date());
		}
		param.setBmiIndex(bmiIndex);
		userInbodyService.save(param);
		
		return map.setState(CommonParam.STATE_OK);
	}


	/**
	 * 教练删除inbody数据
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/deleteInbody")
	public ResultMap deleteInbody(UserInbody param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();

		AppUser user = (AppUser) request.getAttribute("user");

		if (StringUtils.isBlank(param.getId())) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数异常");
		}

		userInbodyService.delete(param);

		return map.setState(CommonParam.STATE_OK);
	}
	
	
	/**
	 * 查看指定用户的体能评测结果
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/testDetail")
	public ResultMap testDetail(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		String userId = param.getUserId();
		if (StringUtils.isBlank(userId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
	
		UserTest ut = userTestService.findByUserId(userId);
	
		return map.setState(CommonParam.STATE_OK).setData(ut);
	}
	
	
	
	/**
	 * 查看教练指定日期占用时间列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/timeOccupyList")
	public ResultMap timeOcupyList(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		Date date = param.getDate();
		if (date == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
	
		
		//休息时间列表
		RestTime condition = new RestTime();
		condition.setUserId(user.getId());
		condition.setRestDate(date);
		List<RestTime> restTimes = restTimeService.findList(condition);
		
		//订单列表
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		Date checkDay = sdf.parse(dateStr);*/
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date checkDayTomorrow = cal.getTime();
		
		Order order = new Order();
		List<String> excludeStatus = new ArrayList<String>();
		excludeStatus.add(CommonParam.ORDER_STATUS_YIQUXIAO);
		order.setExcludeStatus(excludeStatus);
		order.setCoachId(user.getId());
		order.setBeginAppointmentTime(date);
		order.setEndAppointmentTime(checkDayTomorrow);
		
		List<Order> orders = orderService.findList(order);
		
		List<ScheduleResult> srList = new ArrayList<ScheduleResult>();
		
		for (RestTime rt : restTimes) {
			ScheduleResult sr = new ScheduleResult();
			sr.setUserName(user.getNickName());
			sr.setStatusName("休息中");
			sr.setStartTime(rt.getRestStartTime());
			sr.setEndTime(rt.getRestEndTime());
			sr.setTimeLong(rt.getRestStartTime().getTime());
			
			srList.add(sr);
		}
		
		for (Order o : orders) {
			ScheduleResult sr = new ScheduleResult();
			sr.setUserName(o.getStuName());
			if (CommonParam.ORDER_STATUS_DAIQUEREN.equals(o.getStatus())) {
				sr.setStatusName("待确认");
			}else if(CommonParam.ORDER_STATUS_DAISHANGKE.equals(o.getStatus())){
				sr.setStatusName("待上课");
			}else{
				sr.setStatusName("忙...");
			}
			sr.setStartTime(o.getAppointmentTime());
			Date endTime = restTimeService.getDateTimeByLong(o.getAppointmentTime(), Long.parseLong(o.getCourseDuration()));
			sr.setEndTime(endTime);
			sr.setTimeLong(o.getAppointmentTime().getTime());
			
			srList.add(sr);
		}
		
		if (srList.size() > 0) {
			//排序, 按照开始时间升序
			ListSortUtil<ScheduleResult> listSortUtil = new ListSortUtil<ScheduleResult>();
			listSortUtil.sort(srList, "timeLong", "asc");
		}
		
		return map.setState(CommonParam.STATE_OK).setData(srList);
	}
	
	
	
	/**
	 * 会员查看我的私教列表
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/mobile/myCoachList")
	@ResponseBody
	public ResultMap myCoachList(RequestMap param, HttpServletRequest request, HttpServletResponse response, Model model){
		ResultMap map = new ResultMap();
	
		AppUser user = (AppUser) request.getAttribute("user");
		
		String name = param.getName();
		user.setName(name);
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		
		List<AppUser> list = userService.findMyCoachList(user);
		
		PageBean<AppUser> pageBean = new PageBean<AppUser>();
		if (list!=null && list.size()>0) {
			pageBean = new PageBean<AppUser>(currentPage, pageSize, list.size());
			pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
			
		}
	
		return map.setState(CommonParam.STATE_OK).setData(pageBean);
	}
	
	
	/**
	 * 评论列表
	 */
	@RequestMapping("/commentList")
	@ResponseBody
	public ResultMap commentList(RequestMap param, HttpServletRequest request, HttpServletResponse response, Model model){
		ResultMap map = new ResultMap();
		
		String userId = param.getUserId();
		
		AppUser user = userService.get(userId);
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		//int pageSize = 10;
		
		//获取评论列表
		List<Comment> comments = commentService.findCoachCommentList(user);
		//model.addAttribute("comments", comments);
		
		PageBean<Comment> pageBean = new PageBean<Comment>();
		if (comments!=null && comments.size()>0) {
			pageBean = new PageBean<Comment>(currentPage, pageSize, comments.size());
			pageBean.setPageData(comments.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > comments.size() ? comments.size() : (pageBean.getStartIndex()+pageSize)));
		}
		
		return map.setState(CommonParam.STATE_OK).setData(pageBean);
	}
	
	
	
	
	/**
	 * 会员个人中心H5连接
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userCenter")
	public String userCenter(RequestMap param, HttpServletRequest request, HttpServletResponse response, Model model){
		ResultMap map = new ResultMap();
		
		String userId = param.getUserId();
		String userType = param.getUserType();
		String meId = param.getMeId();
		
		
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(meId)) {
			return null; 
		}
		
		String meFlag = "0"; //是否本人查看, 0:不是, 1:是
		meFlag = userId.equals(meId)  ? "1" : "0";
		
		try {
			AppUser user = userService.get(userId);
			
			String homePageType = StringUtils.isBlank(userType) ? CommonParam.USER_TYPE_SIJIAO : userType;
			if ("0".equals(meFlag)) {
				String ut = user.getUserType();
				List<String> stuList = Arrays.asList(CommonParam.USER_TYPE_ZHUCE, CommonParam.USER_TYPE_PUTONG,CommonParam.USER_TYPE_SIJIAO);//这种方法生成的list，是不支持添加或删除元素的 
				
				if (stuList.contains(ut)) {
					homePageType = CommonParam.USER_TYPE_SIJIAO;
				}else {
					homePageType = CommonParam.USER_TYPE_JIAOLIAN;
				}
			}
			
			user.setHomePageType(homePageType);
			user.setMeFlag(meFlag);
			
			//当用户名是手机号码的时候，给手机号码进行加密
			if(user.getPhone().equalsIgnoreCase(user.getNickName())){
				user.setPhone(user.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
			}
			
			if (CommonParam.USER_TYPE_JIAOLIAN.equals(homePageType)) {
				
				//累计课程
				int totalCourseNum = orderService.findCoachTotalCourseNum(user);
				//累计会员
				int myStuNum = userGymService.findMyStuNum(user);
				
				user.setMyStuNum(myStuNum);
				user.setTotalCourseNum(totalCourseNum);
				
				//获取教练标签
				UserGym condition = new UserGym();
				condition.setCoachId(userId);
				condition.setBindFlag(CommonParam.USER_GYM_BIND_YES);
				List<UserGym> list = userGymService.findList(condition);
				if (list!=null && list.size()>0) {
					user.setLabel(list.get(0).getCoachLabel());
				}
				
				//获取评论列表
				/*List<Comment> comments = commentService.findCoachCommentList(user);
				model.addAttribute("comments", comments);*/
				
			}
			
			
			
			//个人关注数
			int followNum = followRelationService.getUserFollowNum(userId);
			
			//个人粉丝数
			int followerNum = followRelationService.getUserFollowerNum(userId);
			
			//判断相互关系
			String fr = followRelationService.checkFollowRelation(meId, userId);
			if ("10".equals(fr) ) {
				user.setRelationFlag("2");
			}else if("11".equals(fr)){
				user.setRelationFlag("3");
			}else{
				user.setRelationFlag("1");
			}
			
			
			user.setFollowNum(followNum);
			user.setFollowerNum(followerNum);
			
			model.addAttribute("user", user);
			
			//默认参数
			model.addAttribute("meBgImg", RESOURECE_URL+CommonParam.ME_BACKGROUD_IMG);
			model.addAttribute("stuBgImg", RESOURECE_URL+CommonParam.STU_CENTER_IMG);
			model.addAttribute("coachBgImg",  RESOURECE_URL+CommonParam.COACH_CENTER_IMG);
			
			return "modules/b_user/personal_homepage";
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	/**
	 * 教练查看名下会员详情
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/myStuDetail")
	public ResultMap myStuDetail(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		String userId = param.getUserId();
		if (StringUtils.isBlank(userId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("用户id为空");
		}
		
		try {
			AppUser stu = userService.get(userId);
			
			Gym gym = userGymService.getGymByCoachId(user);
			
			if (gym==null) {
				return map.setState(CommonParam.STATE_ERROR).setData(stu);
			}
			
			//TODO 待写活
			//个人关注数
			int followNum = followRelationService.getUserFollowNum(userId);
			
			//个人粉丝数
			int followerNum = followRelationService.getUserFollowerNum(userId);
			
			//会员截止时间
			Date vipDeadLine = userGymService.getStuVipDeadLine(stu,gym.getId());
			
			
			//剩余私教课
			int leftCourseNum = userGymService.getStuLeftCourseNum(stu,gym.getId());
			
			
			stu.setFollowNum(followNum);
			stu.setFollowerNum(followerNum);
			stu.setVipDeadLine(vipDeadLine);
			stu.setLeftCourseNum(leftCourseNum);
			
			return map.setState(CommonParam.STATE_OK).setData(stu);
			
		} catch (Exception e) {
			e.printStackTrace();
			return map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙...");
			
		}
		
		
	}
	
	
	
	/**
	 * 教练查看名下会员
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/myStuList")
	public ResultMap myStuList(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		String name = param.getName();
		user.setName(name);
		
		
		List<AppUser> list = userService.findMyStuList(user);
		
		PageBean<AppUser> pageBean = new PageBean<AppUser>();
		if (list!=null && list.size()>0) {
			pageBean = new PageBean<AppUser>(currentPage, pageSize, list.size());
			pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
		}
		
		
		return map.setState(CommonParam.STATE_OK).setData(pageBean);
	}
	
	
	
	/**
	 * 查询是否可以给对方用户打电话,phoneFlag返回"0"表示不可以, 返回"1"表示可以
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/checkPhoneAuth")
	public ResultMap checkPhoneAuth(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		String phoneFlag = "0";
		String tip = null;
		
		String toUserId = param.getToUserId();
		if (StringUtils.isBlank(toUserId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("对方用户id为空");
		}
	
		PrivateSetting ps = privateSettingService.findByUserId(toUserId);
		String ppl = ps.getPhonePublicLevel();
		
		//"00"表示两个用户之间没有关注, 
		//"10"表示userId用户关注了toUserId用户, 
		//"01"表示toUserId用户关注了userId用户,
		//"11"表示互相关注
		String followRelation = followRelationService.checkFollowRelation(user.getId(), toUserId);
		
		if (CommonParam.PHONE_TO_FOCUS_ME.equals(ppl)) {//followRelation为"10"或"11"才允许打电话
			if ("10".equals(followRelation) || "11".equals(followRelation)) {
				phoneFlag = "1";
			}else {
				tip = "需要关注对方才能打电话哦~";
			}
		}else if (CommonParam.PHONE_TO_BOTH_FOCUS.equals(ppl)) {
			if ("11".equals(followRelation)) {//followRelation为"11"才允许打电话
				phoneFlag = "1";
			}else{
				tip = "需要互相关注才能打电话哦~";
			}
		}else if (CommonParam.PHONE_TO_ONLY_ME.equals(ppl)) {
				tip = "对方设置了隐私权限,您不能给对方打电话~";
		}else if (CommonParam.PHONE_TO_ALL.equals(ppl)) {
				phoneFlag = "1";
		}else if(CommonParam.PHONE_TO_COACH.equals(ppl)) {
			String userType = user.getUserType();
			if (CommonParam.USER_TYPE_JIAOLIAN.equals(userType) || CommonParam.USER_TYPE_SIJIAO_JIAOLIAN.equals(userType)) {
				phoneFlag = "1";
			}else {
				tip = "对方设置了隐私权限,您不能给对方打电话~";
			}
		}else if(CommonParam.PHONE_TO_SUPERVISOR_COACH.equals(ppl)) {
			
			UserGym userGym = new UserGym();
			userGym.setUserId(user.getId());
			userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
			List<UserGym> list = userGymService.findList(userGym);
			if (list!=null && list.size()>0) {
				for (UserGym ug : list) {
					if (user.getId().equals(ug.getCoachId())) {
						phoneFlag = "1";
						break;
					}
				}
			}
			
			if ("0".equals(phoneFlag)) {
				tip = "对方设置了隐私权限,您不能给对方打电话~";
			}
		}else if(CommonParam.PHONE_TO_MY_STU.equals(ppl)) {
			AppUser coach = userService.get(toUserId);
			List<AppUser> list = userService.findMyStuList(coach);
			boolean flag = false;
			for (AppUser u : list) {
				if(u.getId().equals(user.getId())){
					flag = true;
					break;
				}
			}
			if (flag) {
				phoneFlag = "1";
			}else {
				tip = "对方设置了隐私权限,您不能给对方打电话~";
			}
		}
		
		MessageResult mr = new MessageResult();
		mr.setPhoneFlag(phoneFlag);
		mr.setTip(tip);
		
		return map.setState(CommonParam.STATE_OK).setData(mr);
	}
	
	







	/**
	 * 获取用户个人最新信息
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/freshUser")
	public ResultMap freshUser(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		//TODO 待完成
		//个人关注数
		int followNum = followRelationService.getUserFollowNum(user.getId());
		
		//个人粉丝数
		int followerNum = followRelationService.getUserFollowerNum(user.getId());
		
		//教练可用余额
		UserAccount uc = userAccountService.findByUserId(user);
		double usableMoney = 0.00;
		if (uc!=null && uc.getUsableAmount()>0) {
			usableMoney = uc.getUsableAmount();
		}
		BigDecimal b = new BigDecimal(usableMoney);  
		usableMoney = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		
		//教练受赏记录数
		List<PayInfo> coachPayInfoList = payInfoService.findCoachPayInfoList(user);
		
		int receivedMoneyNum = coachPayInfoList == null ? 0 : coachPayInfoList.size();
		
		//我的私教数
		int myCoachNum = userGymService.findMyCoachNum(user);
		
		
		//打赏记录数
		List<PayInfo> stuPayInfoList = payInfoService.findStuPayInfoList(user);
		int giveMoneyNum = stuPayInfoList == null ? 0 : stuPayInfoList.size();
		
		
		user.setFollowNum(followNum);
		user.setFollowerNum(followerNum);
		user.setUsableMoney(usableMoney);
		user.setMyCoachNum(myCoachNum);
		user.setReceivedMoneyNum(receivedMoneyNum);
		user.setGiveMoneyNum(giveMoneyNum);
		
		return map.setState(CommonParam.STATE_OK).setData(user);
		
	}
	
	/**
	 * 教练查询指定日期工作列表
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/workList")
	public ResultMap workList(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		Date date = param.getDate();
		String name = param.getName();
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		
	try {	
		
		if ( date == null && StringUtils.isNotBlank(name)) {//没有日期, 表示只按照学员名称模糊查询所有订单
			
			date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = sdf.format(date);
			Date checkDay = sdf.parse(dateStr);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(checkDay);
			cal.add(Calendar.DAY_OF_MONTH, 7); //暂时认为查询当天至7天后之间的工作
			Date checkDayTomorrow = cal.getTime();
			
			Order order = new Order();
			List<String> excludeStatus = new ArrayList<String>();
			excludeStatus.add(CommonParam.ORDER_STATUS_YIQUXIAO);
			order.setExcludeStatus(excludeStatus);
			order.setBeginAppointmentTime(checkDay);
			order.setEndAppointmentTime(checkDayTomorrow);
			order.setCoachId(user.getId());
			order.setStuName(name);
			
			List<Order> list = orderService.findList(order);
			
			PageBean<Order> pageBean = new PageBean<Order>();
			if (list!=null && list.size()>0) {
				pageBean = new PageBean<Order>(currentPage, pageSize, list.size());
				pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
			}
			return map.setState(CommonParam.STATE_OK).setData(pageBean);
			
			
		}else if( date == null && StringUtils.isBlank(name)){
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
			
		}
		
		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = sdf.format(date);
			Date checkDay = sdf.parse(dateStr);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(checkDay);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			Date checkDayTomorrow = cal.getTime();
			
			Order order = new Order();
			List<String> excludeStatus = new ArrayList<String>();
			excludeStatus.add(CommonParam.ORDER_STATUS_YIQUXIAO);
			order.setExcludeStatus(excludeStatus);
			order.setCoachId(user.getId());
			order.setBeginAppointmentTime(checkDay);
			order.setEndAppointmentTime(checkDayTomorrow);
			order.setStuName(name);
			
			List<Order> list = orderService.findList(order);
			
			PageBean<Order> pageBean = new PageBean<Order>();
			if (list!=null && list.size()>0) {
				pageBean = new PageBean<Order>(currentPage, pageSize, list.size());
				pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
			}
			return map.setState(CommonParam.STATE_OK).setData(pageBean);
			
			
		} catch (ParseException e) {
			e.printStackTrace();
			return map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙");
		}
		
		
				
	}
	
	
	
	
	/**
	 * 用户粉丝列表
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/followerList")
	public ResultMap followerList(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		String userId = param.getUserId();
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		
		if (StringUtils.isNotBlank(userId)) {
			FollowRelation fr = new FollowRelation();
			fr.setFollowerId(userId);
			List<FollowRelation> list = followRelationService.findList(fr);
			
			
			if (list == null || list.size() == 0) {
				PageBean<AppUser> pageBean = new PageBean<AppUser>();
				return map.setState(CommonParam.STATE_OK).setData(pageBean);
			}
			
			List<String> ids = new ArrayList<String>();
			for (FollowRelation followRelation : list) {
				ids.add(followRelation.getFollowId());
			}
			
			AppUser au = new AppUser();
			au.setIncludeIds(ids);
			au.setActivateFlag(CommonParam.ACTIVE_YES);
			List<AppUser> users = userService.findList(au);
			
			
			for (AppUser u : users) {
				String cfr = followRelationService.checkFollowRelation(user.getId(), u.getId());
				
				if ("11".equals(cfr)) {
					u.setRelationFlag(CommonParam.RELATION_BOTH_FOLLOW);
				}else if("10".equals(cfr)){
					u.setRelationFlag(CommonParam.RELATION_ME_FOLLOW);
				}else {
					u.setRelationFlag(CommonParam.RELATION_NO_FOLLOW);
				}
			}
			
			PageBean<AppUser> pageBean = new PageBean<AppUser>();
			if (users!=null && users.size()>0) {
				pageBean = new PageBean<AppUser>(currentPage, pageSize, users.size());
				pageBean.setPageData(users.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > users.size() ? users.size() : (pageBean.getStartIndex()+pageSize)));
				
			}
			return map.setState(CommonParam.STATE_OK).setData(pageBean);
			
			
			
			
		}else {
			
			FollowRelation fr = new FollowRelation();
			fr.setFollowerId(user.getId());
			List<FollowRelation> list = followRelationService.findList(fr);
			
			
			if (list == null || list.size() == 0) {
				PageBean<AppUser> pageBean = new PageBean<AppUser>();
				return map.setState(CommonParam.STATE_OK).setData(pageBean);
			}
			
			List<String> ids = new ArrayList<String>();
			for (FollowRelation followRelation : list) {
				ids.add(followRelation.getFollowId());
			}
			
			AppUser au = new AppUser();
			au.setIncludeIds(ids);
			au.setActivateFlag(CommonParam.ACTIVE_YES);
			List<AppUser> users = userService.findList(au);
			
			for (AppUser u : users) {
				String cfr = followRelationService.checkFollowRelation(user.getId(), u.getId());
				
				if ("11".equals(cfr)) {
					u.setRelationFlag(CommonParam.RELATION_BOTH_FOLLOW);
				}else{
					u.setRelationFlag(CommonParam.RELATION_NO_FOLLOW);
				}
			}
			
			
			
			PageBean<AppUser> pageBean = new PageBean<AppUser>();
			if (users!=null && users.size()>0) {
				pageBean = new PageBean<AppUser>(currentPage, pageSize, users.size());
				pageBean.setPageData(users.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > users.size() ? users.size() : (pageBean.getStartIndex()+pageSize)));
				
			}
			return map.setState(CommonParam.STATE_OK).setData(pageBean);
			
		}
		
		
		
	}
	
	
	
	
	
	/**
	 * 用户关注列表
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/followList")
	public ResultMap followList(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		String userId = param.getUserId();
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		
		if (StringUtils.isNotBlank(userId)) {
			FollowRelation fr = new FollowRelation();
			fr.setFollowId(userId);
			List<FollowRelation> list = followRelationService.findList(fr);
			
			if (list == null || list.size() == 0) {
				PageBean<AppUser> pageBean = new PageBean<AppUser>();
				return map.setState(CommonParam.STATE_OK).setData(pageBean);
			}
			
			
			List<String> ids = new ArrayList<String>();
			for (FollowRelation followRelation : list) {
				ids.add(followRelation.getFollowerId());
			}
			
			AppUser au = new AppUser();
			au.setIncludeIds(ids);
			au.setActivateFlag(CommonParam.ACTIVE_YES);
			List<AppUser> users = userService.findList(au);
			
			for (AppUser u : users) {
				String cfr = followRelationService.checkFollowRelation(user.getId(), u.getId());
				
				if ("11".equals(cfr)) {
					u.setRelationFlag(CommonParam.RELATION_BOTH_FOLLOW);
				}else if("10".equals(cfr)){
					u.setRelationFlag(CommonParam.RELATION_ME_FOLLOW);
				}else {
					u.setRelationFlag(CommonParam.RELATION_NO_FOLLOW);
				}
			}
			
			PageBean<AppUser> pageBean = new PageBean<AppUser>(1,10,0);
			if (users!=null && users.size()>0) {
				pageBean = new PageBean<AppUser>(currentPage, pageSize, users.size());
				pageBean.setPageData(users.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > users.size() ? users.size() : (pageBean.getStartIndex()+pageSize)));
				
			}
			return map.setState(CommonParam.STATE_OK).setData(pageBean);
			
			
			
			
		}else {
			
			FollowRelation fr = new FollowRelation();
			fr.setFollowId(user.getId());
			List<FollowRelation> list = followRelationService.findList(fr);
			
			if (list == null || list.size() == 0) {
				PageBean<AppUser> pageBean = new PageBean<AppUser>();
				return map.setState(CommonParam.STATE_OK).setData(pageBean);
			}
			
			List<String> ids = new ArrayList<String>();
			for (FollowRelation followRelation : list) {
				ids.add(followRelation.getFollowerId());
			}
			
			
			AppUser au = new AppUser();
			au.setIncludeIds(ids);
			au.setActivateFlag(CommonParam.ACTIVE_YES);
			List<AppUser> users = userService.findList(au);
			
			
			
			
			for (AppUser u : users) {
				String cfr = followRelationService.checkFollowRelation(user.getId(), u.getId());
				
				if ("11".equals(cfr)) {
					u.setRelationFlag(CommonParam.RELATION_BOTH_FOLLOW);
				}else {
					u.setRelationFlag(CommonParam.RELATION_ME_FOLLOW);
				}
			}
			
			PageBean<AppUser> pageBean = new PageBean<AppUser>();
			if (users!=null && users.size()>0) {
				pageBean = new PageBean<AppUser>(currentPage, pageSize, users.size());
				pageBean.setPageData(users.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > users.size() ? users.size() : (pageBean.getStartIndex()+pageSize)));
				
			}
			return map.setState(CommonParam.STATE_OK).setData(pageBean);
			
		}
		
		
		
	}
	
	
	
	
	
	/**
	 * 取消关注
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/deleteFollow")
	public ResultMap deleteFollow(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		String toUserId = param.getToUserId();
		
		FollowRelation condition = new FollowRelation();
		condition.setFollowId(user.getId());
		condition.setFollowerId(toUserId);
		List<FollowRelation> list = followRelationService.findList(condition);
		if (list!=null && list.size()>0) {
			followRelationService.delete(list.get(0));
			return map.setState(CommonParam.STATE_OK).setMsg("取消关注成功");
		}else {
			return map.setState(CommonParam.STATE_FOLLOW_DUPLICATE).setMsg("您尚未关注对方");
			
		}
		
	}
	
	
	
	/**
	 * 添加关注
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/addFollow")
	public ResultMap addFollow(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		String toUserId = param.getToUserId();
		
		FollowRelation condition = new FollowRelation();
		condition.setFollowId(user.getId());
		condition.setFollowerId(toUserId);
		List<FollowRelation> list = followRelationService.findList(condition);
		if (list!=null && list.size()>0) {
			return map.setState(CommonParam.STATE_FOLLOW_DUPLICATE).setMsg("您已关注对方");
		}
		
		FollowRelation fr = new FollowRelation();
		fr.setFollowId(user.getId());
		fr.setFollowerId(toUserId);
		followRelationService.save(fr);
		
		
		return map.setState(CommonParam.STATE_OK).setMsg("关注成功");
	}
	
	
	
	
	
	/**
	 * 查询是否可以给对方用户发消息,返回"0"表示不可以, 返回"1"表示可以
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/checkSendMessage")
	public ResultMap checkSendMessage(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		String sendFlag = "0";
		String tip = null;
		
		String toUserId = param.getToUserId();
		if (StringUtils.isBlank(toUserId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("发送用户id为空");
		}
	
		PrivateSetting ps = privateSettingService.findByUserId(toUserId);
		String mp = ps.getMessagePerson();
		
		//"00"表示两个用户之间没有关注, 
		//"10"表示userId用户关注了toUserId用户, 
		//"01"表示toUserId用户关注了userId用户,
		//"11"表示互相关注
		String followRelation = followRelationService.checkFollowRelation(user.getId(), toUserId);
		
		if (CommonParam.MESSAGE_PERSON_FOCUS_ME.equals(mp)) {//followRelation为"10"或"11"才允许发送消息
			if ("10".equals(followRelation) || "11".equals(followRelation)) {
				sendFlag = "1";
			}else {
				tip = "需要关注对方才能发送消息哦~";
			}
		}else if (CommonParam.MESSAGE_PERSON_BOTH_FOCUS.equals(mp)) {
			if ("11".equals(followRelation)) {//followRelation为"11"才允许发送消息
				sendFlag = "1";
			}else{
				tip = "需要互相关注才能发送消息哦~";
			}
		}
		
		MessageResult mr = new MessageResult();
		mr.setSendFlag(sendFlag);
		mr.setTip(tip);
		
		return map.setState(CommonParam.STATE_OK).setData(mr);
	}
	
	
	
	
	/**
	 * 修改用户隐私设置
	 * @param ps
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/updatePrivateSetting")
	public ResultMap updatePrivateSetting(PrivateSetting ps, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		
		PrivateSetting psDB = privateSettingService.findByUserId(user.getId());
		if (psDB == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("系统异常");
		}
		
		if (StringUtils.isNotBlank(ps.getMessagePerson())) {
			psDB.setMessagePerson(ps.getMessagePerson());
		}
		if (StringUtils.isNotBlank(ps.getPhonePublicLevel())) {
			psDB.setPhonePublicLevel(ps.getPhonePublicLevel());
		}
		if (StringUtils.isNotBlank(ps.getMesssageOrder())) {
			psDB.setMesssageOrder(ps.getMesssageOrder());
		}
		if (StringUtils.isNotBlank(ps.getMessageCourseTip())) {
			psDB.setMessageCourseTip(ps.getMessageCourseTip());
		}
		
		privateSettingService.save(psDB);
		
		return map.setState(CommonParam.STATE_OK).setData(psDB);
	}
	
	
	
	/**
	 * 查询用户隐私设置
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/myPrivateSetting")
	public ResultMap myPrivateSetting(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		
		PrivateSetting ps = privateSettingService.findByUserId(user.getId());
		if (ps==null) {
			ps = new PrivateSetting();
			ps.setUserId(user.getId());
			ps.setMessagePerson(CommonParam.MESSAGE_PERSON_FOCUS_ME);
			ps.setPhonePublicLevel(CommonParam.PHONE_TO_BOTH_FOCUS);
			ps.setMesssageOrder(CommonParam.MESSAGE_RECEIVED);
			ps.setMessageCourseTip(CommonParam.MESSAGE_RECEIVED);
			
			privateSettingService.save(ps);
			
			if (StringUtils.isBlank(ps.getId())) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙");
			}
			
			
		}
		
		
		
		return map.setState(CommonParam.STATE_OK).setData(ps);
	}
	
	
	
	/**
	 * 查询教练或健身房营业时间
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/getGymBusinessTime")
	public ResultMap getGymBusinessTime(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		Date date = param.getDate();
		String gymId = param.getGymId();
		
		//TODO 根据token查询健身房再查指定日期的营业时间
		
		if ( date == null ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
		}
		
		Map<String, Object> result = new HashMap<String,Object>();
		
		
			
			result.put("startTime", "06:00");
			result.put("duration", "18");
			
			return map.setData(result).setState(CommonParam.STATE_OK);
			
				
	}
	
	
	
	
	
	/**
	 * 查询指定日期休息时间列表
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/RestTimeList")
	public ResultMap RestTimeList(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		Date date = param.getDate();
		
		if ( date == null ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
		}
		
		
		RestTime condition = new RestTime();
		condition.setUserId(user.getId());
		condition.setRestDate(date);
		
		List<RestTime> list = restTimeService.findList(condition);
		return map.setData(list).setState(CommonParam.STATE_OK);
		
				
	}
	
	
	/**
	 * 教练删除休息时间
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/deleteRestTime")
	public ResultMap deleteRestTime(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		//AppUser user = (AppUser) request.getAttribute("user");
		
		//TODO 应该让用户只能删除自己的休息时间
		
		String id = param.getId();
		if (StringUtils.isBlank(id)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
		}
		
		
		restTimeService.delete(new RestTime(id));
		
		return map.setState(CommonParam.STATE_OK);
				
	}
	
	
	/**
	 * 教练添加休息时间
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/addRestTime")
	public ResultMap addRestTime(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		try {
			
			AppUser user = (AppUser) request.getAttribute("user");
			
			Date startTime = param.getStartTime();//休息开始时间, 也可以作为全天休息的日期传参
			double duration = param.getDuration();
			String allDayRest = param.getAllDayRest();
			
			if (startTime == null ) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
			}
			
			//获取用户绑定的健身房id
			String gymId = "";
			UserGym userGym = new UserGym();
			userGym.setUserId(user.getId());
			userGym.setRelation(CommonParam.USER_TYPE_JIAOLIAN);
			userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
			List<UserGym> findList = userGymService.findList(userGym);
			if (findList!=null && findList.size()>0) {
				gymId = findList.get(0).getGymId();
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String restDate = sdf.format(startTime);
			System.out.println("休息开始时间是: ======"+restDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(startTime);
			
			//插入休息时间的时候需要判断是否与已有休息时间重复,如果有重复, 不保存并给出提示, 
			
			//判断是否全天休息, 如果是需要将当天的休息记录全部删除,并且重新插入一条全天休息的数据
			if ("1".equals(allDayRest)) {
				
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				String restDate1 = sdf1.format(startTime);
				Date parse = sdf1.parse(restDate1);
				
				
				//判断时间是否有重复
				RestTime condition2 = new RestTime();
				condition2.setUserId(user.getId());
				condition2.setBeginTime(parse);
				condition2.setDuration(60*24+"");
				boolean conflictFlag = restTimeService.checkTimeConflictAllDayRest(condition2); //返回true表示有时间重复 全天休息允许当天有设置具体时间休息,但是不允许当天有上课
				if (conflictFlag) {//有重复不保存
					return map.setState(CommonParam.STATE_TIME_CONFLICT).setMsg("时间有冲突");
				}
				
				
				//删除当天的休息全部记录
				RestTime condition = new RestTime();
				condition.setRestDate(sdf.parse(restDate));
				condition.setUserId(user.getId());
				List<RestTime> list = restTimeService.findList(condition);
				restTimeService.deleteList(list);
				
				//保存一条全天休息的数据
				RestTime restTime = new RestTime();
				restTime.setUserId(user.getId());
				restTime.setDayOfWeek(cal.get(Calendar.DAY_OF_WEEK)+"");
				restTime.setAllDayRest("1");
				restTime.setRestDate(sdf.parse(restDate));
				restTime.setGymId(gymId);
				
				restTimeService.save(restTime);
				
				return map.setState(CommonParam.STATE_OK);
				
			}else{
				
				int addTime = (int) (duration * 60);
				cal.add(Calendar.MINUTE, addTime);
				Date endTime = cal.getTime();
				
				
				//判断时间是否有重复
				RestTime condition = new RestTime();
				condition.setUserId(user.getId());
				condition.setBeginTime(startTime);
				condition.setDuration(addTime+"");
//				condition.setEndTime(endTime);
//				condition.setRestDate(sdf.parse(restDate));
				boolean conflictFlag = restTimeService.checkTimeConflict(condition); //返回true表示有时间重复
				if (conflictFlag) {//有重复不保存
					return map.setState(CommonParam.STATE_TIME_CONFLICT).setMsg("时间有冲突");
				}
				
				
				//如果原来设置过全天休息, 需要把全天休息的数据清除
				RestTime condition2 = new RestTime();
				condition2.setRestDate(sdf.parse(restDate));
				condition2.setUserId(user.getId());
				condition2.setAllDayRest("1");
				List<RestTime> list = restTimeService.findList(condition2);
				restTimeService.deleteList(list);
				
				
				//没有重复则保存
				RestTime restTime = new RestTime();
				restTime.setUserId(user.getId());
				restTime.setRestStartTime(startTime);
				restTime.setRestEndTime(endTime);
				restTime.setDuration(duration+"");
				restTime.setDayOfWeek(cal.get(Calendar.DAY_OF_WEEK)+"");
				restTime.setAllDayRest("0");
				restTime.setRestDate(sdf.parse(restDate));
				restTime.setGymId(gymId);
				
				restTimeService.save(restTime);
				
				return map.setState(CommonParam.STATE_OK);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙");
			return map;
		}
		
	}
	
	
	
	/**
	 * 更改手机号码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updatePhone")
	public Map<String, Object> updatePhone(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		String accessToken = request.getParameter("accessToken");
		if (accessToken == null) {
			map.put("state",  CommonParam.STATE_TOKEN_NULL);
			map.put("msg", "请求异常");
			return map;
		}
		AppUser	user = userService.selectUserByAccessToken(accessToken);
		if(user == null){
			map.put("state",  CommonParam.STATE_TOKEN_DEPRECATED);
			map.put("msg", "登录已过期");
			return map;
		}
		
		String state = CommonParam.STATE_OK;
		String msg = "";
		map.put("data", null);
		
		
		String newPhoneNum = request.getParameter("newPhoneNum");
		String checkCode = request.getParameter("checkCode");
		
		if (StringUtils.isBlank(newPhoneNum)) {
			map.put("state",  CommonParam.STATE_ERROR);
			map.put("msg", "请求异常");
			return map;
		}
		
		System.out.println("输入验证码是===========: "+checkCode);
		
		try {
			//验证验证码
			SysCheckCode ck = userService.getCheckCode(newPhoneNum);
			if(ck==null || !ck.getCheckCode().equals(checkCode)){
				map.put("state",  CommonParam.STATE_ERROR);
				map.put("msg", "验证码不正确！");
				return map;
			}
			
			AppUser newPhoneUser = userService.selectActivatedUserByPhone(newPhoneNum);
			if (newPhoneUser != null) {
				map.put("state",  CommonParam.STATE_ERROR);
				map.put("msg", "该手机号码已被注册");
				return map;
			}
			
			if(user!=null){
				//生成access_token
				String access_token = UUID.randomUUID().toString().replace("-", "");
				user.setToken(access_token);
				user.setPhone(newPhoneNum);
				userService.update(user);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			state = CommonParam.STATE_ERROR;
			msg = "服务器异常，请稍后再试！";
		}
		
		map.put("state", state);
		map.put("msg", msg);
		user.setPassword(null);
		map.put("data", user);
		
		return map;
	}
	
	
	
	
	//TODO 
	@ResponseBody
	@RequestMapping("/mobile/feedback")
	public Map<String, Object> feedback(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String,Object>();
		String accessToken = request.getParameter("accessToken");
		if (StringUtils.isBlank(accessToken)) {
			map.put("state",  CommonParam.STATE_TOKEN_NULL);
			map.put("msg", "请求异常");
			return map;
		}
		AppUser	user = userService.selectUserByAccessToken(accessToken);
		if(user == null){
			map.put("state",  CommonParam.STATE_TOKEN_DEPRECATED);
			map.put("msg", "登录已过期");
			return map;
		}
		try {
			Feedback feedback=new Feedback();
			String  feedbackType=request.getParameter("feedbackType");
			String  feedbackPhone=request.getParameter("feedbackPhone");
			String  feedbackContent=request.getParameter("feedbackContent");
			String  pictureUrl=request.getParameter("pictureUrl");
		

			
			feedback.setFeedbackType(feedbackType);
			feedback.setPictureUrl(pictureUrl);
			feedback.setFeedbackPhone(feedbackPhone);
			feedback.setFeedbackContent(feedbackContent);
			feedback.setFeedbackTime(new Date());
			feedback.setFeedbackPerson(user.getLoginName());
			feedback.setFeedbackType("1");//待回复
			feedback.setCreateDate(new Date());
			feedbackService.save(feedback);
			map.put("state", CommonParam.STATE_OK);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state",  CommonParam.STATE_ERROR);
			map.put("msg", "服务器忙");
			return map;
		}

		
		try {
			Feedback feedback=new Feedback();
			String  feedbackType=request.getParameter("feedbackType");
			String  feedbackPhone=request.getParameter("feedbackPhone");
			String  feedbackContent=request.getParameter("feedbackContent");
			String  pictureUrl=request.getParameter("pictureUrl");
			String[] url=pictureUrl.split(",");
			boolean boo=true;
			for (String s : url) {
				Map<String, Object> map1= uploadFile(s,null,null);
				if (boo){
					feedback.setPictureUrl((String) map1.get("data"));
					boo=false;
				}else {
					feedback.setPictureUrl(feedback.getPictureUrl()+","+(String) map1.get("data"));
				}

			}

			
			feedback.setFeedbackType(feedbackType);
			feedback.setFeedbackPhone(feedbackPhone);
			feedback.setFeedbackContent(feedbackContent);
			feedback.setFeedbackTime(new Date());
			feedback.setFeedbackPerson(user.getLoginName());
			feedback.setFeedbackType("1");//待回复
			feedback.setCreateDate(new Date());
			feedbackService.save(feedback);
			map.put("state", CommonParam.STATE_OK);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state",  CommonParam.STATE_ERROR);
			map.put("msg", "服务器忙");
			return map;
		}

		return map;
		
		
		
	}
	
	
	/**
	 * 用户健康评测
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/test")
	public Map<String, Object> test(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String,Object>();
		String accessToken = request.getParameter("accessToken");
		if (StringUtils.isBlank(accessToken)) {
			map.put("state",  CommonParam.STATE_ERROR);
			map.put("msg", "请求异常");
			return map;
		}
		AppUser	user = userService.selectUserByAccessToken(accessToken);
		if(user == null){
			map.put("state",  CommonParam.STATE_TOKEN_DEPRECATED);
			map.put("msg", "登录已过期");
			return map;
		}
		
		try {
			String userName = request.getParameter("userName");
			String age = request.getParameter("age");
			String sex = request.getParameter("sex");
			String height = request.getParameter("height");
			String weight = request.getParameter("weight");
			String restHabbit = request.getParameter("restHabbit");
			String sportFrequency = request.getParameter("sportFrequency");
			String workDuration = request.getParameter("workDuration");
			String dietHabbit = request.getParameter("dietHabbit");
			String burningGoal = request.getParameter("burningGoal");
			
			if (StringUtils.isBlank(userName) || StringUtils.isBlank(age) || StringUtils.isBlank(sex) || StringUtils.isBlank(height) || StringUtils.isBlank(weight) ||
				 StringUtils.isBlank(restHabbit) || StringUtils.isBlank(sportFrequency) || StringUtils.isBlank(workDuration) || StringUtils.isBlank(dietHabbit) || StringUtils.isBlank(burningGoal)||Double.parseDouble(weight)>725||Double.parseDouble(height)>300) {
				
				map.put("state",  CommonParam.STATE_ERROR);
				map.put("msg", "请求参数异常");
				return map;
			}
			
			
			UserTest userTest = new UserTest();
			userTest.setUserId(user.getId());
			userTest.setUserName(userName);
			userTest.setAge(age);
			userTest.setSex(sex);
			userTest.setHeight((int)(Double.parseDouble(height))+"");
			userTest.setWeight((int)(Double.parseDouble(weight))+"");
			userTest.setRestHabbit(DictUtils.getDictLabel(restHabbit, "rest_habbit", ""));
			userTest.setSportFrequency(DictUtils.getDictLabel(sportFrequency, "sport_frequency", ""));
			userTest.setWorkDuration(DictUtils.getDictLabel(workDuration, "work_duration", ""));
			userTest.setDietHabbit(DictUtils.getDictLabel(dietHabbit, "diet_habbit", ""));
			userTest.setBurningGoal(DictUtils.getDictLabel(burningGoal, "burning_goal", ""));
			
			//测试结果
			userTest.setRestHabbitResult(DictUtils.getDictRemark(restHabbit, "rest_habbit", ""));
			userTest.setSportFrequencyResult(DictUtils.getDictRemark(sportFrequency, "sport_frequency", ""));
			userTest.setWorkDurationResult(DictUtils.getDictRemark(workDuration, "work_duration", ""));
			userTest.setDietHabbitResult(DictUtils.getDictRemark(dietHabbit, "diet_habbit", ""));
			userTest.setBurningGoalResult(DictUtils.getDictRemark(burningGoal, "burning_goal", ""));
			
			//计算bmi指数
			double weightD = Double.parseDouble(weight);
			double heightD = Double.parseDouble(height);
			BigDecimal b = new BigDecimal(weightD/(heightD*heightD/10000));
			double bmiIndex = b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			String course = userService.getCourseByBmiIndex(bmiIndex);
			
			
			userTest.setBmiIndex(bmiIndex+"");
			userTest.setRecommendCourse(DictUtils.getDictLabel(course, "course_recommend_type", ""));
			
			userTestService.save(userTest);
			
			
			userTest.setUser(user);;
			
			
			//标识用户已做完测试
			user.setFirstVisitStuFlag("1");
			user.setSex(sex);
			user.setRealName(userName);
			user.setHeight((int)(Double.parseDouble(height)));
			user.setWeight((int)(Double.parseDouble(weight)));
			user.setBurningGoal(DictUtils.getDictLabel(burningGoal, "burning_goal", ""));
			userService.save(user);
			
			map.put("state", CommonParam.STATE_OK);
			user.setPassword(null);
			map.put("data", userTest);

			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state",  CommonParam.STATE_ERROR);
			map.put("msg", "服务器忙");
			return map;
		}
		
		return map;
	}
	
	
	/**
	 * 发送验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendCheckCode")
	public Map<String, Object> sendCheckCode(HttpServletRequest request,HttpServletResponse response){
		String phoneNum = request.getParameter("phoneNum");
		
		response.setContentType("text/html;charset=UTF-8");
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		
		
		String state = CommonParam.STATE_OK;
		String msg = "";
		map.put("data", null);

		if(StringUtils.isNotBlank(phoneNum)){
			String oldCode = this.userService.canSendCheckCode(phoneNum);
			if(StringUtils.isBlank(oldCode)){
				this.userService.sendCheckCode(phoneNum);
			}else{
				state = CommonParam.STATE_ERROR;
				msg = "验证码已发送，请查看信息！";
			}
		}else{
			state = CommonParam.STATE_ERROR;
			msg = "参数为空";
		}
		map.put("state", state);
		map.put("msg", msg);
		
		String jsonResult = JSON.toJSONString(map);
		System.out.println("发送验证码jsonResult:"+jsonResult); 
		
		return map;
	}
	
	
	/**
	 * 注册
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/regist")
	public Map<String, Object> regist(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String phoneNum = request.getParameter("phoneNum");
		String pwd = request.getParameter("pwd");
		String checkCode = request.getParameter("checkCode");
		//String userType = request.getParameter("user_type");
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		String state = CommonParam.STATE_OK;
		String msg = "";
		map.put("data", null);
		
		
		boolean checkFlag = true;
		//验证验证码
		SysCheckCode ck = this.userService.getCheckCode(phoneNum);
		if(ck==null || !ck.getCheckCode().equals(checkCode)){
			state = CommonParam.STATE_ERROR;
			msg = "验证码不正确！";
			checkFlag = false;
		}
		
		
		
		//验证手机号
		if(checkFlag){
			AppUser user = userService.selectActivatedUserByPhone(phoneNum);
			if(user!=null){
				state = CommonParam.STATE_ERROR;
				msg = "该手机号已被注册！";
				checkFlag = false;
			}
		}
		
		//正式开始注册
		if(checkFlag){
			try {
				AppUser user = new AppUser();
				//判断后台是否已经录入
				AppUser userByPhone = userService.selectUserByPhone(phoneNum);
				if (userByPhone != null) {//已录入则更新
					user = userByPhone;
				}else{
					user.setSource(CommonParam.USER_SOURCE_APP); //设置来源, 系统中不存在该手机用户说明是来源为app注册
				}
				
				
				user.setPassword(SystemService.entryptPassword(pwd));
				user.setPhone(phoneNum);
				user.setNickName(userService.generateNickNameByPhone(phoneNum));  //默认昵称为手机号
				//user.setUserType(userType);
				user.setLoginName(phoneNum);
				user.setHeadImgUrl(RESOURECE_URL+CommonParam.USER_HEAD_DEFAULT);
				user.setBgImgUrl(RESOURECE_URL+CommonParam.ME_BACKGROUD_IMG);
				user.setStuImgUrl(RESOURECE_URL+CommonParam.STU_CENTER_IMG);
				user.setCoachImgUrl(RESOURECE_URL+CommonParam.COACH_CENTER_IMG);
				//生成access_token
				String access_token = UUID.randomUUID().toString().replace("-", "");
				user.setToken(access_token);
				Date date = new Date();
				user.setLoginTime(date);
				user.setActiveTime(date);
				user.setRegistTime(date);
				user.setActivateFlag(CommonParam.ACTIVE_YES);
				user.setFirstVisitCoachFlag("0");
				user.setFirstVisitStuFlag("0");
				user.setCoachScore(0.0);
				if (StringUtils.isBlank(user.getStuFlag())) {
					user.setStuFlag("0");
				}
				if (StringUtils.isBlank(user.getCoachFlag())) {
					user.setCoachFlag("0");
				}
				
				
				
				
				if (userByPhone != null) {
					userService.update(user);
				}else{
					user.setStuFlag("0");
					user.setCoachFlag("0");
					user.setUserType("1");
					userService.save(user);
				}
				
				//融云注册
				TokenResult result = msgService.registRcUser(user);
				if (StringUtils.isNotBlank(result.getToken())) {
					user.setRcToken(result.getToken());
					userService.update(user);
				}
				
				//生成默认隐私设置
				PrivateSetting ps = new PrivateSetting();
				ps.setUserId(user.getId());
				ps.setMessagePerson(CommonParam.MESSAGE_PERSON_FOCUS_ME);
				ps.setPhonePublicLevel(CommonParam.PHONE_TO_BOTH_FOCUS);
				ps.setMesssageOrder(CommonParam.MESSAGE_RECEIVED);
				ps.setMessageCourseTip(CommonParam.MESSAGE_RECEIVED);
				privateSettingService.save(ps);
				
				
				//发送系统注册成功消息
				msgService.sendRegistMsg(user);
				
				
				//生成默认分组 ,关注,粉丝, 会员, 教练
			/*	Group group = new Group();
				group.setUserId(user.getId());
				group.setName("我的会员");
				group.setDefaultFlag("1");
				group.setType(CommonParam.GROUP_TYPE_STU);
				groupService.save(group);
				
				Group group1 = new Group();
				group1.setUserId(user.getId());
				group1.setName("我的关注");
				group1.setDefaultFlag("1");
				group1.setType(CommonParam.GROUP_TYPE_FOLLOW);
				groupService.save(group1);*/
				
				//生成用户账户,数据库设置了userId唯一,可能有异常抛出, 处理异常不影响后面的业务
				try {
					UserAccount account = new UserAccount();
					account.setUserId(user.getId());
					account.setTotalAmount(0.0);
					account.setUsableAmount(0.0);
					account.setWithdrawAmount(0.0);
					
					userAccountService.save(account);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				user.setPassword(null);
				map.put("data", user);
				
			} catch (Exception e) {
				e.printStackTrace();
				state = CommonParam.STATE_ERROR;
				msg = "服务器异常，请稍后再试！"; 
			}
		}
		
		map.put("state", state);
		map.put("msg", msg);
		
		
		
		return map;
	}
	
	/**
	 * 选择进入系统目的
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/mobile/chooseUserType")
	public ResultMap chooseUserType(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResultMap map = new ResultMap();
		
		
		String userType = request.getParameter("userType");
		AppUser	user = (AppUser) request.getAttribute("user");
		
		
		if (StringUtils.isBlank(userType)) {
			map.setState(CommonParam.STATE_ERROR);
			map.setMsg("请求异常");
			return map;
		}
		
		if ("1".equals(userType) || "2".equals(userType) || "3".equals(userType) ) {
			user.setStuFlag("1");
		}else if("4".equals(userType)){
			user.setCoachFlag("1");
		}
		
		userService.update(user);
		
		map.setState(CommonParam.STATE_OK);
		map.setData(user);
		
		return map;
		
	}
	
	
	
	
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Map login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String phoneNum = request.getParameter("phoneNum");
		String pwd = request.getParameter("pwd");
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		String state  = CommonParam.STATE_OK;
		String msg = "";
		map.put("data", null);
		
		try {
			AppUser user = userService.selectActivatedUserByPhone(phoneNum);
			if(user==null){//判断是否激活
				state = CommonParam.STATE_ERROR;
				msg = "该手机号尚未注册！";
			}else{
				
				if (SystemService.validatePassword(pwd, user.getPassword())) {//验证密码
					//生成access_token
					String access_token = UUID.randomUUID().toString().replace("-", "");
					user.setToken(access_token);
					user.setLoginTime(new Date());
					userService.update(user);
					
					user.setPassword(null);
					
					
					
					//TODO 待完成
					//个人关注数
					int followNum = followRelationService.getUserFollowNum(user.getId());
					
					//个人粉丝数
					int followerNum = followRelationService.getUserFollowerNum(user.getId());
					
					//教练可用余额
					double usableMoney = 9999.99;
					
					//教练受赏记录数
					int receivedMoneyNum = 100;
					
					//我的私教数
					int myCoachNum = userGymService.findMyCoachNum(user);
					
					//打赏记录数
					
					int giveMoneyNum = 200;
					
					
					user.setFollowNum(followNum);
					user.setFollowerNum(followerNum);
					user.setUsableMoney(usableMoney);
					user.setMyCoachNum(myCoachNum);
					user.setReceivedMoneyNum(receivedMoneyNum);
					user.setGiveMoneyNum(giveMoneyNum);
					
					
					
					map.put("data", user);
					
				}else {
					state = CommonParam.STATE_ERROR;
					msg = "密码错误！";
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			state = CommonParam.STATE_ERROR;
			msg = "服务器异常，请稍后再试！";
		}
		
		
		map.put("state", state);
		map.put("msg", msg);
		
		return map;
	}
	
	
	
	
	
	/**
	 *修改用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/mobile/updateUser")
	public Map<String, Object> updateUser(AppUser u, HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		
		AppUser user = (AppUser) request.getAttribute("user");
		
		String sex = u.getSex();
		String headImgUrl = u.getHeadImgUrl();
		String bgImgUrl = u.getBgImgUrl();
		String intro = u.getIntro();
		
		if (StringUtils.isNotBlank(sex)) {
			user.setSex(sex);
		}
		if (StringUtils.isNotBlank(intro)) {
			user.setIntro(intro);
		}
		if (StringUtils.isNotBlank(headImgUrl)) {
			user.setHeadImgUrl(headImgUrl);
		}
		if (StringUtils.isNotBlank(bgImgUrl)) {
			user.setBgImgUrl(bgImgUrl);
		}
		
		
		if (StringUtils.isNotBlank(u.getNickName())) {
			user.setNickName(u.getNickName());
		}
		if (StringUtils.isNotBlank(u.getRealName())) {
			user.setRealName(u.getRealName());
		}
		if (u.getBirthday()!=null) {
			user.setBirthday(u.getBirthday());
		}
		if (u.getHeightMeter()!=null) {
			user.setHeight(u.getHeightMeter().intValue());
		}
		if (u.getWeightKilo() != null) {
			user.setWeight(u.getWeightKilo().intValue());
		}
		if (StringUtils.isNotBlank(u.getProvince())) {
			user.setProvince(u.getProvince());
		}
		if (StringUtils.isNotBlank(u.getCity())) {
			user.setCity(u.getCity());
		}
		if (StringUtils.isNotBlank(u.getDistrict())) {
			user.setDistrict(u.getDistrict());
		}
		if (StringUtils.isNotBlank(u.getBgImgUrl())) {
			user.setBgImgUrl(u.getBgImgUrl());
		}
		
		
		//标识用户已完善资料, 只有sex,intro,headImgUrl三个值不为空就认为是教练完善资料, 否则是普通的更新个人资料
		if (StringUtils.isNotBlank(sex) && StringUtils.isNotBlank(intro) && StringUtils.isNotBlank(headImgUrl)&&user.getUserType().equals(CommonParam.USER_TYPE_JIAOLIAN)) {
			user.setFirstVisitCoachFlag("1");
		}
		
		userService.save(user);
		
		map.put("state",  CommonParam.STATE_OK);
		map.put("data", user);
		return map;
	}
	
	
	
	/**
	 * 更改密码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updatePwd")
	public Map<String, Object> updatePwd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		String accessToken = request.getParameter("accessToken");
		String phoneNum = request.getParameter("phoneNum");
		String newPwd = request.getParameter("newPwd");
		String checkCode = request.getParameter("checkCode");
		String state = CommonParam.STATE_OK;
		String msg = "";
		AppUser	user=new AppUser();
		//更改密码分为登录状态下修改以及非登录状态下修改
		
		//登录状态下修改
		if(phoneNum==null&&accessToken!=null){
			map.put("state",  CommonParam.STATE_ERROR);
			map.put("msg", "请输入手机号码！！！");
			return map;
		}else if(phoneNum!=null&&accessToken!=null){
			user = userService.selectUserByAccessToken(accessToken);
			if(user == null){
				map.put("state",  CommonParam.STATE_TOKEN_DEPRECATED);
				map.put("msg", "登录已过期");
				return map;
			}
			
			try {
				//验证验证码
				SysCheckCode ck = userService.getCheckCode(phoneNum);
				if(ck==null || !ck.getCheckCode().equals(checkCode)){
					state = CommonParam.STATE_ERROR;
					msg = "验证码不正确！";
				}else{
					user = userService.selectUserByPhone(phoneNum);
					if(user!=null){
						user.setPassword(SystemService.entryptPassword(newPwd));
						//生成access_token
						String access_token = UUID.randomUUID().toString().replace("-", "");
						user.setToken(access_token);
						userService.update(user);
						msg="修改成功,请重新登录！！";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				state = CommonParam.STATE_ERROR;
				msg = "服务器异常，请稍后再试！";
			}
			
		}
		
		//非登录状态下修改
		else if(accessToken==null&&phoneNum!=null){
			try {
				//验证验证码
				SysCheckCode ck = userService.getCheckCode(phoneNum);
				if(ck==null || !ck.getCheckCode().equals(checkCode)){
					state = CommonParam.STATE_ERROR;
					msg = "验证码不正确！";
				}else{
					user = userService.selectUserByPhone(phoneNum);
					if(user!=null){
						user.setPassword(SystemService.entryptPassword(newPwd));
						//生成access_token
						String access_token = UUID.randomUUID().toString().replace("-", "");
						user.setToken(access_token);
						userService.update(user);
						msg="修改成功,请重新登录！！";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				state = CommonParam.STATE_ERROR;
				msg = "服务器异常，请稍后再试！";
			}
		}
		
		map.put("state", state);
		map.put("msg", msg);
		map.put("data", user);
		
		return map;
		
		
		
		/*Map<String, Object> map = new HashMap<String,Object>();
		String accessToken = request.getParameter("accessToken");
		String phoneNum = request.getParameter("phoneNum");
		String newPwd = request.getParameter("newPwd");
		String checkCode = request.getParameter("checkCode");
	
		if (phoneNum==null&&accessToken == null) {
			map.put("state",  CommonParam.STATE_ERROR);
			map.put("msg", "请输入手机号码！！！");
			return map;
		}
		
		AppUser	user = userService.selectUserByAccessToken(accessToken);
		if(user == null){
			map.put("state",  CommonParam.STATE_TOKEN_DEPRECATED);
			map.put("msg", "登录已过期");
			return map;
		}
		
		String state = CommonParam.STATE_OK;
		String msg = "";
		//map.put("data", null);
		if(phoneNum!=null&&accessToken==null){
		try {
			//验证验证码
			SysCheckCode ck = userService.getCheckCode(phoneNum);
			if(ck==null || !ck.getCheckCode().equals(checkCode)){
				state = CommonParam.STATE_ERROR;
				msg = "验证码不正确！";
			}else{
				user = userService.selectUserByPhone(phoneNum);
				if(user!=null){
					user.setPassword(SystemService.entryptPassword(newPwd));
					//生成access_token
					String access_token = UUID.randomUUID().toString().replace("-", "");
					user.setToken(access_token);
					userService.update(user);
					msg="修改成功";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			state = CommonParam.STATE_ERROR;
			msg = "服务器异常，请稍后再试！";
		}
		
	}	
		
		map.put("state", state);
		map.put("msg", msg);
		//user.setPassword(null);
		map.put("data", user);
	//	String jsonResult = JSON.toJSONString(map);
	//	System.out.println("修改密码jsonResult:"+jsonResult); 
		
		return map;*/
	}
	
	
	
	/**
	 * 上传文件
	 * @param request
	 * @param response
	 * @param
	 * @return
	 */
    @ResponseBody  
    @RequestMapping(value="/upload")  
    public Map<String, Object> uploadFile(@RequestParam String base64Data, HttpServletRequest request, HttpServletResponse response){  
		
		Map<String, Object> map = new HashMap<String,Object>();
		/*String accessToken = request.getParameter("accessToken");
		if (accessToken == null) {
			map.put("state",  CommonParam.STATE_ERROR);
			map.put("msg", "请求异常");
			return map;
		}
		AppUser	user = userService.selectUserByAccessToken(accessToken);
		if(user == null){
			map.put("state",  CommonParam.STATE_ERROR);
			map.put("msg", "登录已过期");
			return map;
		}
		*/
        try{    
        	
        	System.out.println(base64Data);
           // String dataPrix = "";  
            String data = base64Data;            
            if(base64Data == null || "".equals(base64Data)){  
                map.put("state",  CommonParam.STATE_ERROR);
    			map.put("msg", "上传失败，上传图片数据为空");
    			return map;
            }/*else{  
                String [] d = base64Data.split("base64,");  
                if(d != null && d.length == 2){  
                    dataPrix = d[0];  
                    data = d[1];  
                }else{ 
                	map.put("state",  CommonParam.STATE_ERROR);
        			map.put("msg", "上传失败，数据不合法");
        			return map;
                }  
            }    */           
            String suffix = ".jpg";  
           /* if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据  
                suffix = ".jpg";  
            } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据  
                suffix = ".ico";  
            } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据  
                suffix = ".gif";  
            } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据  
                suffix = ".png";  
            }else{  
                throw new Exception("请上传.jpg或者.png格式图片");  
            }  */
  
            //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包  
            try{  
            	
            	//Base64 base64 = new Base64();
                byte[] bs = Base64.decodeBase64(data.getBytes());
            	
            	//byte[] bs = Base64Utils.decodeFromString(data);  
            	String year = Calendar.getInstance().get(Calendar.YEAR)+"";
    			String month = Calendar.getInstance().get(Calendar.MONTH)+1+"";
    			String day = Calendar.getInstance().get(Calendar.DATE)+"";
				// 设定上传文件保存的目录 
    			String savePath = MULTIMEDIA_PATH+year+"/"+month+"/"+day ; 
    			String urlPath = RESOURECE_URL+year+"/"+month+"/"+day ; 
    			//如果保存路径不存在则创建
    			File f = new File(savePath);  
    			if (!f.exists()) {
    				f.mkdirs();  
    			}
    			String saveName = UUID.randomUUID().toString().replace("-", "")+suffix;//保存到服务器端的文件名称
    			String realPath = savePath+"/"+saveName;//上传文件在服务器端的真实路径
    			File file = new File(realPath);  
				
				//将字节流转换成文件
                 FileOutputStream fos = new FileOutputStream(file);     
                 fos.write(bs,0,bs.length);     
                 fos.flush();     
                 fos.close();  
                 
                 map.put("state", CommonParam.STATE_OK);
                 map.put("data", urlPath+"/"+saveName);
            	
                  
                /* user.setHeadImgUrl(urlPath+"/"+saveName);
                 userService.save(user);*/
            }catch(Exception ee){  
            	ee.printStackTrace();
                map.put("state",  CommonParam.STATE_ERROR);
    			map.put("msg", "上传失败,服务器忙");
    			return map;
            }                           
            return map;                
        }catch (Exception e) {                  
            return map;  
        }          
    }     
  
	
    
    /**
	 * 根据id查找用户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getInfoById")
	public Map<String, Object> getInfoById(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		
	
		String id = request.getParameter("id");
		
		
		String state = CommonParam.STATE_OK;
		String msg = "";
		map.put("data", null);
		
		try {
			AppUser user = userService.get(id);
			AppUser userReturn = new AppUser();
			userReturn.setId(user.getId());
			userReturn.setName(StringUtils.isBlank(user.getNickName())?CommonParam.USER_DEFAULT_NICKNAME : user.getNickName());
			userReturn.setNickName(StringUtils.isBlank(user.getNickName())?CommonParam.USER_DEFAULT_NICKNAME : user.getNickName());//app需求
			userReturn.setHeadImgUrl(user.getHeadImgUrl());
			userReturn.setPhone(user.getPhone());
			userReturn.setUserType(user.getUserType());
			userReturn.setIntro(user.getIntro());
			 
			
			map.put("data", userReturn);
			
		} catch (Exception e) {
			e.printStackTrace();
			state = CommonParam.STATE_ERROR;
			msg = "服务器异常，请稍后再试！";
		}
		
		map.put("state",state);
		map.put("msg", msg);
		
		return map;
	}
    
    
	@RequestMapping("/Yonghelp")
    @ResponseBody
    public String[] Yonghelp(HttpServletResponse response){
        Map<String,String[]> result=new HashMap<String,String[]>();
        String path="/home/media_burning/burningMedia/default/yonghu.doc";
        String[] resultMap=userService.getFile(path);
        result.put("result",resultMap);
        //return result;
       return resultMap;
    } 
    
	@RequestMapping("/registerHelp")
	@ResponseBody
	public String[] registerHelp(HttpServletResponse response){
        Map<String,String[]> result=new HashMap<String,String[]>();
        String path="/home/media_burning/burningMedia/default/zhuce.doc";
        String[] resultMap=userService.getFile(path);
        result.put("result",resultMap);
        return resultMap;
   }
	
	@RequestMapping("/operationHelp")
	public String operationHelp(Model model){
		UserHelp condition = new UserHelp();
		condition.setType("1");
		List<UserHelp> list = userHelpService.findList(condition);
		if (list!=null && list.size()>0) {
			model.addAttribute("userHelp", list.get(0));
		}
		
		return "modules/tran/train_details";
	}
	
    
	
}

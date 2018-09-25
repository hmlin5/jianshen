/**
 * 
 */
package com.gimc.user.modules.b_user_gym.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gimc.user.common.config.Global;
import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.web.BaseController;
import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.modules.b_bmi_course.entity.BmiCourse;
import com.gimc.user.modules.b_bmi_course.service.BmiCourseService;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_follow_relation.entity.FollowRelation;
import com.gimc.user.modules.b_follow_relation.service.FollowRelationService;
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_gym.service.GymService;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user.service.AppUserService;
import com.gimc.user.modules.b_user_course.service.UserCourseService;
import com.gimc.user.modules.b_user_gym.entity.UserGym;
import com.gimc.user.modules.b_user_gym.service.UserGymService;
import com.gimc.user.modules.b_user_inbody.entity.UserInbody;
import com.gimc.user.modules.b_user_inbody.service.UserInbodyService;
import com.gimc.user.modules.sys.entity.User;
import com.gimc.user.modules.sys.utils.UserUtils;

/**
 * 用户健身房关系Controller
 * @author linhaomiao
 * @version 2018-05-11
 */
@Controller
@RequestMapping(value = "${adminPath}/b_user_gym/userGym")
public class UserGymController extends BaseController {

	@Autowired
	private UserGymService userGymService;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private GymService gymService;
	@Autowired
	private BmiCourseService bmiCourseService;
	@Value("${RESOURECE_URL}")
	private String RESOURECE_URL;
	@Autowired
	private FollowRelationService followRelationService;
	
	@Autowired
	private UserInbodyService userInbodyService;
	@Autowired
	private UserCourseService userCourseService;
	
	@ModelAttribute
	public UserGym get(@RequestParam(required=false) String id) {
		UserGym entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userGymService.get(id);
		}
		if (entity == null){
			entity = new UserGym();
		}
		return entity;
	}
	
	/**
	 * 学员列表
	 * @param appUser
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("b_user_gym:userGym:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserGym userGym, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
		Gym gym = gymService.findByUserId(user.getId());
		userGym.setGymId(gym.getId());;
		userGym.setRelation(CommonParam.USER_TYPE_SIJIAO);
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		Page<UserGym> page = userGymService.findGymUserPage(new Page<UserGym>(request,
				response), userGym);
		model.addAttribute("page", page);
		return "modules/b_user_gym/userGymList";
	}

	
	
	@RequiresPermissions("b_user_gym:userGym:view")
	@RequestMapping(value = {"listCoach"})
	public String listCoach(UserGym userGym, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
		Gym gym = gymService.findByUserId(user.getId());
		userGym.setGymId(gym.getId());;
		userGym.setRelation(CommonParam.USER_TYPE_JIAOLIAN);
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		Page<UserGym> page = userGymService.findGymUserPage(new Page<UserGym>(request,
				response), userGym);
		model.addAttribute("page", page);
		return "modules/b_user_gym/userGymCoachList";
	}
	
	
	
	/**
	 * 查看会员
	 * @param userGym
	 * @param model
	 * @return
	 */
	@RequiresPermissions("b_user_gym:userGym:view")
	@RequestMapping(value = "form")
	public String form(UserGym userGym, Model model) {
		model.addAttribute("userGym", userGym);
		model.addAttribute("defaultHeadImgUrl", RESOURECE_URL+CommonParam.USER_HEAD_DEFAULT);
		
		//int userId=Integer.parseInt(userGym.getUserId());
		//String userId=userGym.getUserId();
	    
		//String id=userGym.getId();
		
		//System.out.println(userId+":UserId");
		String gymId = userGym.getGymId();
		if (StringUtils.isBlank(gymId)) {//如果gymId为空, 则从当前登录用户中查询对应的健身房
			User user = UserUtils.getUser();
			
			Gym gym = new Gym();
			gym.setUserId(Integer.parseInt(user.getId()));
			gym.setFreezingState(CommonParam.FREEZE_NO);
			List<Gym> gyms = gymService.findList(gym);
			if (gyms!=null && gyms.size()>0) {
				gymId = gyms.get(0).getId();
			}
		}
		
		UserGym condition = new UserGym();
		condition.setRelation(CommonParam.USER_TYPE_JIAOLIAN);
		condition.setGymId(gymId);
		condition.setBindFlag(CommonParam.USER_GYM_BIND_YES);

		List<UserGym> list = userGymService.findList(condition);
		model.addAttribute("coachList", list);
		
		return "modules/b_user_gym/userGymForm";
	}

	/**
	 * 查看教练
	 * @param appUser
	 * @param model
	 * @return
	 */
	@RequiresPermissions("b_user_gym:userGym:view")
	@RequestMapping(value = "formCoach")
	public String formCoach(UserGym userGym, Model model) {
		model.addAttribute("userGym", userGym);
		model.addAttribute("defaultHeadImgUrl", RESOURECE_URL+CommonParam.USER_HEAD_DEFAULT);
		return "modules/b_user_gym/userGymCoachForm";
	}
	
	
	
	/**
	 * 解绑会员(教练离职)
	 * @param appUser
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("b_user_gym:userGym:edit")
	@RequestMapping(value = "unbind")
	public String unbind(UserGym userGym,  Model model, RedirectAttributes redirectAttributes) {
		
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_NO);
		userGym.setUnbindTime(new Date());
		userGymService.save(userGym);
		
		//维护用户身份关系
		//遍历用户绑定健身房关系
		userGymService.updateAppUserType(userGym.getUserId());
		
		//如果是会员, 删除当前健身房推荐的所有课程 
		userCourseService.deleteUserCourses(userGym.getUserId(),userGym.getGymId());
		
		AppUser appUser = appUserService.get(userGym.getUserId());
		appUser.setToken(UUID.randomUUID().toString().replace("-", ""));
		appUserService.save(appUser);
		
		if (CommonParam.USER_TYPE_JIAOLIAN.equals(userGym.getRelation())) {
			addMessage(redirectAttributes, "操作成功");
			return "redirect:"+Global.getAdminPath()+"/b_user_gym/userGym/listCoach?repage";
		}
		addMessage(redirectAttributes, "解绑用户成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_gym/userGym/?repage";
		
	}
	
	
	
	/**
	 * 更新会员冻结状态
	 * @param appUser
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("b_user_gym:userGym:edit")
	@RequestMapping(value = "updateFreezeStatus")
	public String updateFreezeStatus(UserGym userGym,  Model model, RedirectAttributes redirectAttributes) {
		UserGym userGym2=userGymService.get(userGym.getId());
		AppUser appUser= appUserService.get(userGym2.getUserId());
		String access_token = UUID.randomUUID().toString().replace("-", "");
		appUser.setToken(access_token);
		if(userGym.getFreezeFlag().equals(CommonParam.FREEZE_YES)){
			appUser.setUserType(CommonParam.USER_TYPE_ZHUCE);
		}else{
			appUser.setUserType(CommonParam.USER_TYPE_SIJIAO);
		}

		appUserService.update(appUser);
		userGymService.save(userGym);
		
		addMessage(redirectAttributes, "修改状态成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_gym/userGym/?repage";
		
	}
	
	
	
	/**
	 * 保存会员
	 * @param appUser
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("b_user_gym:userGym:edit")
	@RequestMapping(value = "save")
	public String save(UserGym userGym,  Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
	    
		if (!beanValidator(model, userGym)){
			return form(userGym, model);
		}
		
		/*
		 * 如果是新增则先判断后查重: 
		 * 判断:该健身房是否已绑定该手机号,不允许同一个健身房绑定同一个手机号同时作为会员和教练
		 * 查重:b_app_user表是否存在该手机号的用户, 如果:
		 * 1-->不存在, 则往b_app_user中插入新用户, 维护字段包括:手机号, 用户类型(为私教会员), 真实姓名,默认头像, 同时往b_user_gym插入一个关联用户数据
		 * 2-->已存在,则需要维护b_app_user字段: 用户类型(如果用户类型是注册会员或者普通会员, 则改为私教会员, 如果是教练, 则改为教练+会员, 如果是教练+会员, 则不需要改变),真实姓名
		 * 
		 */
		
		//获取健身房
		User user = UserUtils.getUser();
		Gym gym = gymService.findByUserId(user.getId());
		AppUser appUser = appUserService.get(userGym.getUserId());
		if(appUser==null){
			appUser=appUserService.selectActivatedUserByPhone(userGym.getUserPhone());
		}
		
		if (StringUtils.isBlank(userGym.getId())) {//新增UserGym
			if (appUser == null) {//app没有注册过该手机号
				appUser = new AppUser();
				appUser.setPhone(userGym.getUserPhone());
				appUser.setHeadImgUrl(RESOURECE_URL+CommonParam.USER_HEAD_DEFAULT);
				appUser.setUserType(CommonParam.USER_TYPE_SIJIAO);
				appUser.setActiveGymId(gym.getId());
				appUser.setRealName(userGym.getUserName());
				
				 //设置用户来源, 如果手机号没有在系统中存在, 说明本次绑定是第一次录入, 可以确定来源为系统录入, 如果已有手机号, 则可能是别的健身房先录入,或者是app已注册
				appUser.setSource(CommonParam.USER_SOURCE_SYSTEM);
				
				//新增appUser
				appUserService.save(appUser);
				
				//保存用户和健身房关系
				userGym.setUserId(appUser.getId());
				userGym.setGymId(gym.getId());
				userGym.setRelation(CommonParam.USER_TYPE_SIJIAO);
				userGym.setInputTime(new Date());
				userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
				userGym.setFreezeFlag(CommonParam.FREEZE_NO);
				userGym.setCourseTime(0);
				
				userGymService.save(userGym);
				
				
				
				
				
			}else {//app已注册过该手机号, 判断该健身房是否已绑定该用户
				
				UserGym condition = new UserGym();
				condition.setUserId(appUser.getId());
				condition.setGymId(gym.getId());
				condition.setBindFlag(CommonParam.USER_GYM_BIND_YES);
				List<UserGym> list = userGymService.findList(condition);
				if (list!=null && list.size()>0) {//已绑定健身房
					addMessage(model, "该手机号码已绑定健身房");
					return form(userGym, model);
				}else {//未绑定健身房
					//保存用户和健身房关系
					userGym.setUserId(appUser.getId());
					userGym.setGymId(gym.getId());
					userGym.setRelation(CommonParam.USER_TYPE_SIJIAO);
					userGym.setInputTime(new Date());
					userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
					userGym.setFreezeFlag(CommonParam.FREEZE_NO);
					userGym.setCourseTime(0);
					userGymService.save(userGym);
					
					//更新活跃健身房, 用户token值
					appUser.setActiveGymId(gym.getId());
					appUser.setRealName(userGym.getUserName());
					String access_token = UUID.randomUUID().toString().replace("-", "");
					appUser.setToken(access_token);
					appUserService.save(appUser);
					
					//维护b_app_user字段: 用户类型 
					userGymService.updateAppUserType(userGym.getUserId());
					
				}
				
			}
			
			
			//维护会员与主管的关注关系
			if (userGym.getCoachId() != null) {
				
				FollowRelation fr1 = new FollowRelation();
				fr1.setFollowId(appUser.getId());
				fr1.setFollowerId(userGym.getCoachId());
				List<FollowRelation> frs1 = followRelationService.findList(fr1);
				if (frs1 == null || frs1.size() < 1) {
					followRelationService.save(fr1);
				}
				
				FollowRelation fr2 = new FollowRelation();
				fr2.setFollowId(userGym.getCoachId());
				fr2.setFollowerId(appUser.getId());
				List<FollowRelation> frs2 = followRelationService.findList(fr2);
				if (frs2 == null || frs2.size() < 1) {
					followRelationService.save(fr2);
				}
				
				
			}
			
			
			
		}else {//修改UserGym
		
			
			userGymService.save(userGym);
			
			
			String access_token = UUID.randomUUID().toString().replace("-", "");
			appUser.setPhone(userGym.getUserPhone());
			appUser.setToken(access_token);
			appUser.setRealName(userGym.getUserName());
			appUserService.update(appUser);
			
			
			//维护会员与主管的关注关系
			if (userGym.getCoachId() != null) {
				
				FollowRelation fr1 = new FollowRelation();
				fr1.setFollowId(appUser.getId());
				fr1.setFollowerId(userGym.getCoachId());
				List<FollowRelation> frs1 = followRelationService.findList(fr1);
				if (frs1 == null || frs1.size() < 1) {
					followRelationService.save(fr1);
				}
				
				FollowRelation fr2 = new FollowRelation();
				fr2.setFollowId(userGym.getCoachId());
				fr2.setFollowerId(appUser.getId());
				List<FollowRelation> frs2 = followRelationService.findList(fr2);
				if (frs2 == null || frs2.size() < 1) {
					followRelationService.save(fr2);
				}
				
				
			}
			
		}
		
		addMessage(redirectAttributes, "保存用户成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_gym/userGym/?repage";
	}
	
	/**
	 * 保存教练
	 * @param userGym
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("b_user_gym:userGym:edit")
	@RequestMapping(value = "saveCoach")
	public String saveCoach(UserGym userGym, Model model, RedirectAttributes redirectAttributes) {
	
		if (!beanValidator(model, userGym)){
			return formCoach(userGym, model);
		}
		
		/*
		 * 如果是新增则先判断后查重: 
		 * 判断:该健身房是否已绑定该手机号,不允许同一个健身房绑定同一个手机号同时作为会员和教练,也不允许同一个手机号作为教练绑定多个健身房
		 * 查重:b_app_user表是否存在该手机号的用户, 如果:
		 * 1-->不存在, 则往b_app_user中插入新用户, 维护字段包括:手机号, 用户类型(为私教会员), 默认头像, 同时往b_user_gym插入一个关联用户数据
		 * 2-->已存在,则需要维护b_app_user字段: 用户类型(如果用户类型是注册会员或者普通会员或者私教会员, 则改为教练+会员, 如果是教练或者教练+会员, 则不需要改变)
		 * 
		 */
		
		//获取健身房
		User user = UserUtils.getUser();
		Gym gym = gymService.findByUserId(user.getId());
		AppUser appUser = appUserService.selectUserByPhone(userGym.getUserPhone());
		
		if (StringUtils.isBlank(userGym.getId())) {
			if (appUser == null) {//后台没有录入过, app也没有注册过
				appUser = new AppUser();
				appUser.setPhone(userGym.getUserPhone());
				appUser.setRealName(userGym.getUserName());
				appUser.setHeadImgUrl(RESOURECE_URL+CommonParam.USER_HEAD_DEFAULT);
				appUser.setBgImgUrl(RESOURECE_URL+CommonParam.COACH_CENTER_IMG);
				appUser.setUserType(CommonParam.USER_TYPE_JIAOLIAN);
				appUser.setActivateFlag(CommonParam.ACTIVE_NO);
				appUser.setRealName(userGym.getUserName());
				
				//新增appUser
				appUserService.save(appUser);
				
				//保存用户和健身房关系
				userGym.setUserId(appUser.getId());
				userGym.setGymId(gym.getId());
				userGym.setRelation(CommonParam.USER_TYPE_JIAOLIAN);
				userGym.setInputTime(new Date());
				userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
				userGym.setFreezeFlag(CommonParam.FREEZE_NO);
				userGym.setCourseTime(0);
				userGymService.save(userGym);
				
				
			}else {//判断健身房是否已绑定该用户
				//是否绑定本健身房
				UserGym condition = new UserGym();
				condition.setUserId(appUser.getId());
				condition.setGymId(gym.getId());
				condition.setBindFlag(CommonParam.USER_GYM_BIND_YES);
				List<UserGym> list = userGymService.findList(condition);
				if (list!=null && list.size()>0) {//已绑定健身房
					addMessage(model, "该手机号码已绑定本健身房");
					return formCoach(userGym, model);
				}
				
				//是否绑定其他健身房教练
				UserGym condition1 = new UserGym();
				condition1.setUserId(appUser.getId());
				condition1.setRelation(CommonParam.USER_TYPE_JIAOLIAN);
				condition1.setBindFlag(CommonParam.USER_GYM_BIND_YES);
				List<UserGym> list1 = userGymService.findList(condition1);
				if (list1!=null && list1.size()>0) {//已绑定健身房
					addMessage(model, "该手机号码已绑定其他健身房教练");
					return formCoach(userGym, model);
				}else {//未绑定健身房
					//保存用户和健身房关系
					userGym.setUserId(appUser.getId());
					userGym.setGymId(gym.getId());
					userGym.setRelation(CommonParam.USER_TYPE_JIAOLIAN);
					userGym.setInputTime(new Date());
					userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
					userGym.setFreezeFlag(CommonParam.FREEZE_NO);
					userGym.setCourseTime(0);
					userGymService.save(userGym);
					
					//更新用户token值
					String access_token = UUID.randomUUID().toString().replace("-", "");
					appUser.setToken(access_token);
					appUser.setRealName(userGym.getUserName());
					appUserService.save(appUser);
					
					
					//维护b_app_user字段: 用户类型
					userGymService.updateAppUserType(userGym.getUserId());
				}
				
				
			}
			
		}else {
			userGymService.update(userGym);
			AppUser appUser1= appUserService.get(userGym.getUserId());
			appUser1.setPhone(userGym.getUserPhone());
			appUser1.setRealName(userGym.getUserName());
			appUserService.update(appUser1);
		}
		
		addMessage(redirectAttributes, "保存教练成功");
		
		return "redirect:"+Global.getAdminPath()+"/b_user_gym/userGym/listCoach?repage";
	}
	
	
	
	
	@RequiresPermissions("b_user_gym:userGym:edit")
	@RequestMapping(value = "delete")
	public String delete(UserGym userGym, RedirectAttributes redirectAttributes) {
		userGymService.delete(userGym);
		addMessage(redirectAttributes, "删除用户健身房关系成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_gym/userGym/?repage";
	}
	
	@RequiresPermissions("b_user_gym:userGym:edit")
	@RequestMapping(value = "form2")
	public String form2(UserGym userGym, Model model) {
		model.addAttribute("userGym", userGym);
		model.addAttribute("defaultHeadImgUrl", RESOURECE_URL+CommonParam.USER_HEAD_DEFAULT);
		
		//int userId=Integer.parseInt(userGym.getUserId());
		//System.out.println(userId);
		String userId=userGym.getUserId();
		String gymId = userGym.getGymId();
		if (StringUtils.isBlank(gymId)) {//如果gymId为空, 则从当前登录用户中查询对应的健身房
			User user = UserUtils.getUser();
			
			Gym gym = new Gym();
			gym.setUserId(Integer.parseInt(user.getId()));
			gym.setFreezingState(CommonParam.FREEZE_NO);
			List<Gym> gyms = gymService.findList(gym);
			if (gyms!=null && gyms.size()>0) {
				gymId = gyms.get(0).getId();
			}
		}
		
		UserGym condition = new UserGym();
		condition.setRelation(CommonParam.USER_TYPE_JIAOLIAN);
		condition.setGymId(gymId);
		condition.setBindFlag(CommonParam.USER_GYM_BIND_YES);
    
		
		UserInbody userInbody=userInbodyService.getUserInbody(userId);
		//UserInbody userInbody=userInbodyService.getInt(userId);
		//userGym.setUserInbody(userInbody);
		

		List<UserGym> list = userGymService.findList(condition);
		model.addAttribute("coachList", list);
		
		return "modules/b_user_gym/userGymForm";
	}

}
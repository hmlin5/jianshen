/**
 * 
 */
package com.gimc.user.modules.b_gym.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_gym.service.GymService;
import com.gimc.user.modules.sys.dao.RoleDao;
import com.gimc.user.modules.sys.dao.UserDao;
import com.gimc.user.modules.sys.entity.Area;
import com.gimc.user.modules.sys.entity.Office;
import com.gimc.user.modules.sys.entity.Role;
import com.gimc.user.modules.sys.entity.User;
import com.gimc.user.modules.sys.service.AreaService;
import com.gimc.user.modules.sys.service.SystemService;
import com.gimc.user.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;

/**
 * 健身房Controller
 * @author linhaomiao
 * @version 2018-05-03
 */
@Controller
@RequestMapping(value = "${adminPath}/b_gym/gym")
public class GymController extends BaseController {

	@Autowired
	private GymService gymService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private BmiCourseService bmiCourseService;
	@Autowired
	private AreaService aService;
	
	
	@ModelAttribute
	public Gym get(@RequestParam(required=false) String id) {
		Gym entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gymService.get(id);
		}
		if (entity == null){
			entity = new Gym();
		}
		return entity;
	}
	
	@RequiresPermissions("b_gym:gym:view")
	@RequestMapping(value = {"list", ""})
	public String list(Gym gym, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Gym> page = gymService.findPage(new Page<Gym>(request, response), gym); 
		model.addAttribute("page", page);
		return "modules/b_gym/gymList";
	}

	@RequiresPermissions("b_gym:gym:view")
	@RequestMapping(value = "form")
	public String form(Gym gym, Model model) {
		
		
		Area area=new Area();
		area.setType("2");
		List<Area> aList= aService.findAll(area);//查询省
		model.addAttribute("provinceList", aList);
		if(StringUtils.isNotBlank(gym.getCity())&&StringUtils.isNotBlank(gym.getProvince())){//查询市区
			Area city=new Area();
			city.setName(gym.getProvince());
			List<Area> list= areaService.findAll(city);
			city.setName(null);
			if(list!=null&&list.size()>0){
				city.setParent(new Area(list.get(0).getId()));
				list=areaService.findAll(city);
				model.addAttribute("cityList", list);
				for (Area area2 : list) {
					if(area2.getName().equals(gym.getCity())){
						Area district=new Area();
						district.setParent(new Area(area2.getId()));
						List<Area> list3= areaService.findAll(district);
						model.addAttribute("ditrictList", list3);
						break;
					}
				}
			}
			
		}
		
		model.addAttribute("gym", gym);
		return "modules/b_gym/gymForm";
	}

	/**
	 * 健身房到自己后台
	 * @param gym
	 * @param model
	 * @return
	 */
	@RequiresPermissions("b_gym:gym:view")
	@RequestMapping(value = "myGym")
	public String myGym(Gym gym, Model model) {
		
		User user = UserUtils.getUser();
		if (user==null) {
			return null;
		}
		gym = gymService.findByUserId(user.getId());
		
		
		
		
		Area area=new Area();
		area.setType("2");
		List<Area> aList= aService.findAll(area);//查询省
		model.addAttribute("provinceList", aList);
		if(StringUtils.isNotBlank(gym.getCity())&&StringUtils.isNotBlank(gym.getProvince())){//查询市区
			Area city=new Area();
			city.setName(gym.getProvince());
			List<Area> list= areaService.findAll(city);
			city.setName(null);
			if(list!=null&&list.size()>0){
				city.setParent(new Area(list.get(0).getId()));
				list=areaService.findAll(city);
				model.addAttribute("cityList", list);
				for (Area area2 : list) {
					if(area2.getName().equals(gym.getCity())){
						Area district=new Area();
						district.setParent(new Area(area2.getId()));
						List<Area> list3= areaService.findAll(district);
						model.addAttribute("ditrictList", list3);
						break;
					}
				}
			}
			
		}
		
		
		
		
		BmiCourse bmiCourse=new BmiCourse();
		bmiCourse.setUserId(user.getId());
		bmiCourse.setToUserId("0");
		bmiCourse= bmiCourseService.get(bmiCourse);
		if(bmiCourse!=null){
			gym.setBmiCourse(bmiCourse);
		}else{
			gym.setBmiCourse(bmiCourseService.get(new BmiCourse("1")));
		}
		
		
		model.addAttribute("gym", gym);
		return "modules/b_gym/gymForm";
	}

	
	
	
	
	@RequiresPermissions("b_gym:gym:edit")
	@RequestMapping(value = "save")
	public String save(Gym gym, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gym)){
			return form(gym, model);
		}
		if(gym.getUserId()!=null){
			User user= userDao.get(gym.getUserId().toString());
			user.setUpdateDate(new Date());
			user.setId(gym.getUserId().toString());
			user.setLoginName(gym.getLoginName());
			user.setPassword(gym.getPassword());
			if(StringUtils.isNotBlank(user.getPassword())){
				user.setPassword(SystemService.entryptPassword(user.getPassword()));
			}
			userDao.update(user);
		}else{
			User user1=new User();
			user1.setCreateDate(new Date());
			user1.setUpdateDate(new Date());
			user1.setLoginName(gym.getLoginName());
			user1.setOffice(new Office("1"));
			user1.setCompany(new Office("1"));
			user1.setCreateBy(new User("1"));
			user1.setUpdateBy(new User("1"));
			user1.setUserType("2");
			user1.setName(gym.getLoginName());
			user1.setPassword(gym.getPassword());
			if(StringUtils.isNotBlank(user1.getPassword())){
				user1.setPassword(SystemService.entryptPassword(user1.getPassword()));
			}
			userDao.insert(user1);
			User user2= userDao.getByLoginName(user1);
			gym.setUserId(Integer.parseInt(user2.getId()));
			List<Role> roleList = Lists.newArrayList();
			Role role = roleDao.get("13");
			roleList.add(role);
			user2.setRoleList(roleList);
			userDao.insertUserRole(user2);
		}
		if(gym.getBmiCourse()!=null){
			User user = UserUtils.getUser();
			BmiCourse bmiCourse=new BmiCourse();
			bmiCourse.setUserId(user.getId());
			bmiCourse.setToUserId("0");
			bmiCourse= bmiCourseService.get(bmiCourse);
			if(bmiCourse==null){
				
				gym.getBmiCourse().setId(null);
				gym.getBmiCourse().setUserId(user.getId());
				
			}
			gym.getBmiCourse().setCreatorType("2");
			bmiCourseService.save(gym.getBmiCourse());
		}
		
		if (StringUtils.isBlank(gym.getId())) {
			gym.setFreezingState(CommonParam.FREEZE_NO);
		}
		
		
		gymService.save(gym);
		addMessage(redirectAttributes, "保存健身房成功");
		if(gym.getBmiCourse()!=null){
			return "redirect:"+Global.getAdminPath()+"/b_gym/gym/myGym";
		}
		return "redirect:"+Global.getAdminPath()+"/b_gym/gym/?repage";
	}
	
	@RequiresPermissions("b_gym:gym:edit")
	@RequestMapping(value = "delete")
	public String delete(Gym gym, RedirectAttributes redirectAttributes) {
		gymService.delete(gym);
		addMessage(redirectAttributes, "删除健身房成功");
		return "redirect:"+Global.getAdminPath()+"/b_gym/gym/?repage";
	}
	@RequiresPermissions("b_gym:gym:edit")
	@RequestMapping(value = "updateFreezingState")
	public String updateFreezingState(Gym gym, RedirectAttributes redirectAttributes) {
		if(gym.getUserId()!=null){
			User user= userDao.get(gym.getUserId().toString());
			if(gym.getFreezingState().equals("0")){
				user.setFreezingState("1");
			}else {
				user.setFreezingState("0");
			}
			userDao.update(user);
		}
		if(gym.getFreezingState().equals("0")){
			gym.setFreezingState("1");
		}else{
			gym.setFreezingState("0");
		}
		gymService.save(gym);
		addMessage(redirectAttributes, "保存健身房成功");
		return "redirect:"+Global.getAdminPath()+"/b_gym/gym/?repage";
	}

}
/**
 * 
 */
package com.gimc.user.modules.b_user.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gimc.user.common.config.Global;
import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.common.web.BaseController;
import com.gimc.user.modules.b_bmi_course.service.BmiCourseService;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user.service.AppUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 用户Controller
 * 
 * @author gu
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/b_user/appUser")
public class AppUserController extends BaseController {

	@Autowired
	private AppUserService appUserService;
	@Autowired
	private BmiCourseService bmiCourseService;
	@ModelAttribute
	public AppUser get(@RequestParam(required = false) String id) {
		AppUser entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = appUserService.get(id);
		}
		if (entity == null) {
			entity = new AppUser();
		}
		return entity;
	}

	@RequiresPermissions("b_user:appUser:view")
	@RequestMapping(value = { "list", "" })
	public String list(AppUser appUser, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<AppUser> page = appUserService.findPage(new Page<AppUser>(request,
				response), appUser);
		model.addAttribute("page", page);
		return "modules/b_user/appUserList";
	}

	@RequiresPermissions("b_user:appUser:view")
	@RequestMapping(value = "form")
	public String form(AppUser appUser, Model model) {
		/*BmiCourse bmiCourse=new BmiCourse();
		User user= UserUtils.getUser();
		bmiCourse.setUserId(user.getId());
		if(appUser.getId()!=null){
			bmiCourse.setToUserId(appUser.getId());
			bmiCourse=bmiCourseService.get(bmiCourse);
			if(bmiCourse!=null){
				appUser.setBmiCourse(bmiCourse);
				appUser.setAssessment(1);
			}else{
				BmiCourse bmiCourse2=new BmiCourse();
				bmiCourse2.setId("1");
				appUser.setBmiCourse(bmiCourseService.get(bmiCourse2));
			}
			
		}else{
			BmiCourse bmiCourse2=new BmiCourse();
			bmiCourse2.setId("1");
			appUser.setBmiCourse(bmiCourseService.get(bmiCourse2));
		}	*/
		model.addAttribute("appUser", appUser);
		return "modules/b_user/appUserForm";
	}

	@RequiresPermissions("b_user:appUser:view")
	@RequestMapping(value = "addform")
	public String addform(AppUser appUser, Model model) {
		model.addAttribute("appUser", appUser);
		return "modules/b_user/addAppUserForm";
	}

	@RequiresPermissions("b_user:appUser:edit")
	@RequestMapping(value = "save")
	public String save(AppUser appUser, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, appUser)) {
			return form(appUser, model);
		}
		
		appUserService.save(appUser);
		/*if(appUser.getAssessment()==1){
			if(appUser.getBmiCourse().getId().equals("1")){
				appUser.getBmiCourse().setId(null);
				appUser.getBmiCourse().setToUserId(appUser.getId());
			}
			bmiCourseService.save(appUser.getBmiCourse());
		}else if(appUser.getAssessment()==2){
			BmiCourse bmiCourse=new BmiCourse();
			User user= UserUtils.getUser();
			bmiCourse.setUserId(user.getId());
			bmiCourse.setToUserId(appUser.getId());
			bmiCourse=bmiCourseService.get(bmiCourse);
			if(bmiCourse!=null){
				bmiCourseService.deletes(bmiCourse);
			}
		}*/
		addMessage(redirectAttributes, "保存用户列表成功");
		return "redirect:" + Global.getAdminPath() + "/b_user/appUser/?repage";
	}

	@RequiresPermissions("b_user:appUser:edit")
	@RequestMapping(value = "delete")
	public String delete(AppUser appUser, RedirectAttributes redirectAttributes) {
		appUserService.delete(appUser);
		addMessage(redirectAttributes, "删除用户列表成功");
		return "redirect:" + Global.getAdminPath() + "/b_user/appUser/?repage";
	}

	@RequiresPermissions("b_user:appUser:edit")
	@RequestMapping(value = "updatelockFlag")
	public String updatelockFlag(AppUser appUser,
			RedirectAttributes redirectAttributes) {
		if (appUser.getLockFlag().equals("0")) {
			appUser.setLockFlag("1");
		} else {
			appUser.setLockFlag("0");
		}
		appUserService.save(appUser);
		addMessage(redirectAttributes, "保存用户成功");
		return "redirect:" + Global.getAdminPath() + "/b_user/appUser/?repage";
	}
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(HttpServletResponse response) {
		AppUser appUser = new AppUser();
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AppUser> list = appUserService.findList(appUser);
		Map<String, Object> map1 = Maps.newHashMap();
		map1.put("id", "1");
		map1.put("pId", "0");
		map1.put("pIds", "0,");
		map1.put("name", "所有教练");
		mapList.add(map1);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = Maps.newHashMap();
			AppUser e = list.get(i);
			map.put("id", e.getId());
			map.put("pId", "1");
			map.put("pIds", "0,1,");
			map.put("name", StringUtils.replace(e.getRealName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

}
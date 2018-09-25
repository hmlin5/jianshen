/**
 * 
 */
package com.gimc.user.modules.b_user_course.web;

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
import com.gimc.user.modules.b_user_course.entity.UserCourse;
import com.gimc.user.modules.b_user_course.service.UserCourseService;

/**
 * 会员推荐课程Controller
 * @author linhaomiao
 * @version 2018-05-30
 */
@Controller
@RequestMapping(value = "${adminPath}/b_user_course/userCourse")
public class UserCourseController extends BaseController {

	@Autowired
	private UserCourseService userCourseService;
	
	@ModelAttribute
	public UserCourse get(@RequestParam(required=false) String id) {
		UserCourse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userCourseService.get(id);
		}
		if (entity == null){
			entity = new UserCourse();
		}
		return entity;
	}
	
	@RequiresPermissions("b_user_course:userCourse:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserCourse userCourse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserCourse> page = userCourseService.findPage(new Page<UserCourse>(request, response), userCourse); 
		model.addAttribute("page", page);
		return "modules/b_user_course/userCourseList";
	}

	@RequiresPermissions("b_user_course:userCourse:view")
	@RequestMapping(value = "form")
	public String form(UserCourse userCourse, Model model) {
		model.addAttribute("userCourse", userCourse);
		return "modules/b_user_course/userCourseForm";
	}

	@RequiresPermissions("b_user_course:userCourse:edit")
	@RequestMapping(value = "save")
	public String save(UserCourse userCourse, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userCourse)){
			return form(userCourse, model);
		}
		userCourseService.save(userCourse);
		addMessage(redirectAttributes, "保存会员推荐课程成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_course/userCourse/?repage";
	}
	
	@RequiresPermissions("b_user_course:userCourse:edit")
	@RequestMapping(value = "delete")
	public String delete(UserCourse userCourse, RedirectAttributes redirectAttributes) {
		userCourseService.delete(userCourse);
		addMessage(redirectAttributes, "删除会员推荐课程成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_course/userCourse/?repage";
	}

}
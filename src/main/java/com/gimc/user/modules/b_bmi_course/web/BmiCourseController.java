/**
 * 
 */
package com.gimc.user.modules.b_bmi_course.web;

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

/**
 * bmi-推荐课程关系Controller
 * @author linhaomiao
 * @version 2018-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/b_bmi_course/bmiCourse")
public class BmiCourseController extends BaseController {

	@Autowired
	private BmiCourseService bmiCourseService;
	
	@ModelAttribute
	public BmiCourse get(@RequestParam(required=false) String id) {
		BmiCourse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bmiCourseService.get(new BmiCourse(id));
		}
		if (entity == null){
			entity = new BmiCourse();
		}
		return entity;
	}
	
	@RequiresPermissions("b_bmi_course:bmiCourse:view")
	@RequestMapping(value = {"list", ""})
	public String list(BmiCourse bmiCourse, HttpServletRequest request, HttpServletResponse response, Model model) {
		bmiCourse.setId("1");
		Page<BmiCourse> page = bmiCourseService.findPage(new Page<BmiCourse>(request, response), bmiCourse); 
		model.addAttribute("page", page);
		return "modules/b_bmi_course/bmiCourseList";
	}

	@RequiresPermissions("b_bmi_course:bmiCourse:view")
	@RequestMapping(value = "form")
	public String form(BmiCourse bmiCourse, Model model) {
		model.addAttribute("bmiCourse", bmiCourse);
		return "modules/b_bmi_course/bmiCourseForm";
	}

	@RequiresPermissions("b_bmi_course:bmiCourse:edit")
	@RequestMapping(value = "save")
	public String save(BmiCourse bmiCourse, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bmiCourse)){
			return form(bmiCourse, model);
		}
		bmiCourseService.save(bmiCourse);
		bmiCourseService.updateAll(bmiCourse);
		addMessage(redirectAttributes, "保存bmi-推荐课程关系成功");
		return "redirect:"+Global.getAdminPath()+"/b_bmi_course/bmiCourse/?repage";
	}
	
	@RequiresPermissions("b_bmi_course:bmiCourse:edit")
	@RequestMapping(value = "delete")
	public String delete(BmiCourse bmiCourse, RedirectAttributes redirectAttributes) {
		bmiCourseService.delete(bmiCourse);
		addMessage(redirectAttributes, "删除bmi-推荐课程关系成功");
		return "redirect:"+Global.getAdminPath()+"/b_bmi_course/bmiCourse/?repage";
	}

}
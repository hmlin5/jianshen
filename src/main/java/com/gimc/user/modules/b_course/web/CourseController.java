/**
 * 
 */
package com.gimc.user.modules.b_course.web;

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

import com.alibaba.druid.stat.TableStat.Condition;
import com.gimc.user.common.config.Global;
import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.web.BaseController;
import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.modules.b_bmi_course.entity.BmiCourse;
import com.gimc.user.modules.b_bmi_course.service.BmiCourseService;
import com.gimc.user.modules.b_course.entity.Course;
import com.gimc.user.modules.b_course.service.CourseService;
import com.gimc.user.modules.b_course_catalog.entity.CourseCatalog;
import com.gimc.user.modules.b_course_catalog.service.CourseCatalogService;
import com.gimc.user.modules.b_movement.entity.Movement;
import com.gimc.user.modules.b_movement.service.MovementService;
import com.gimc.user.modules.sys.entity.Dict;
import com.gimc.user.modules.sys.entity.User;
import com.gimc.user.modules.sys.service.DictService;
import com.gimc.user.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;

/**
 * 课程Controller
 * @author linhaomiao
 * @version 2018-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/b_course/course")
public class CourseController extends BaseController {

	@Autowired
	private CourseService courseService;
	@Autowired
	private CourseCatalogService courseCatalogService;
	@Autowired
	private MovementService movementService;
	@Autowired
	private BmiCourseService bmiCourseService;
	@Autowired
	private DictService dictService;
	
	@ModelAttribute
	public Course get(@RequestParam(required=false) String id) {
		Course entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = courseService.get(id);
		}
		if (entity == null){
			entity = new Course();
		}
		return entity;
	}
	
	@RequiresPermissions("b_course:course:view")
	@RequestMapping(value = {"list", ""})
	public String list(Course course, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Course> page = courseService.findPage(new Page<Course>(request, response), course); 
		
		
		model.addAttribute("page", page);
		return "modules/b_course/courseList";
	}

	@RequiresPermissions("b_course:course:view")
	@RequestMapping(value = "form")
	public String form(Course course, Model model) {
		model.addAttribute("course", course);
		
		
		List<CourseCatalog> list = Lists.newArrayList();
		List<CourseCatalog> sourcelist = courseCatalogService.findList(new CourseCatalog());
		CourseCatalog.sortList(list, sourcelist, CourseCatalog.getRootId(), true);
		model.addAttribute("catalogList",list);
		List<Movement> movements = movementService.findList(new Movement());
		Dict dict= new Dict();
		BmiCourse course2=new BmiCourse();
		User user= UserUtils.getUser();
		course2.setUserId(user.getId());
		course2=bmiCourseService.get(course2);
		dict.setType("course_recommend_type");
		List<Dict> list2= dictService.findList(dict);
		if(course2!=null){
			for (Dict dict2 : list2) {
				if(dict2.getLabel().equals("增肌")){
					dict2.setLabel(dict2.getLabel()+":"+course2.getZengjiMin()+"~"+course2.getZengjiMax());
				}else if(dict2.getLabel().equals("塑形")){
					dict2.setLabel(dict2.getLabel()+":"+course2.getSuxingMin()+"~"+course2.getSuxingMax());
				}else if(dict2.getLabel().equals("减脂")){
					dict2.setLabel(dict2.getLabel()+":"+course2.getJianzhiMin()+"~"+course2.getJianzhiMax());
				}
			}
			
		}
		
		model.addAttribute("movementsList",movements);
		model.addAttribute("list2",list2);
		
		return "modules/b_course/courseForm";
	}

	@RequiresPermissions("b_course:course:edit")
	@RequestMapping(value = "save")
	public String save(Course course, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, course)){
			return form(course, model);
		}
		
		if (course.getCatalogId()!=null) {
			CourseCatalog cc = courseCatalogService.get(course.getCatalogId()+"");
			course.setCatalogName(cc.getName());
		}
		
		courseService.save(course);
		addMessage(redirectAttributes, "保存课程成功");
		return "redirect:"+Global.getAdminPath()+"/b_course/course/?repage";
	}
	
	@RequiresPermissions("b_course:course:edit")
	@RequestMapping(value = "delete")
	public String delete(Course course, RedirectAttributes redirectAttributes) {
		courseService.delete(course);
		addMessage(redirectAttributes, "删除课程成功");
		return "redirect:"+Global.getAdminPath()+"/b_course/course/?repage";
	}

}
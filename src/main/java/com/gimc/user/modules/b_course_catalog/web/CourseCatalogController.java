/**
 * 
 */
package com.gimc.user.modules.b_course_catalog.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
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
import com.gimc.user.modules.b_course_catalog.entity.CourseCatalog;
import com.gimc.user.modules.b_course_catalog.service.CourseCatalogService;
import com.gimc.user.modules.b_movement_catalog.entity.MovementCatalog;
import com.google.common.collect.Lists;

/**
 * 课程分类Controller
 * @author linhaomiao
 * @version 2018-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/b_course_catalog/courseCatalog")
public class CourseCatalogController extends BaseController {

	@Autowired
	private CourseCatalogService courseCatalogService;
	
	@ModelAttribute
	public CourseCatalog get(@RequestParam(required=false) String id) {
		CourseCatalog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = courseCatalogService.get(id);
		}
		if (entity == null){
			entity = new CourseCatalog();
		}
		return entity;
	}
	
	@RequiresPermissions("b_course_catalog:courseCatalog:view")
	@RequestMapping(value = {"list", ""})
	public String list(CourseCatalog courseCatalog, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*Page<CourseCatalog> page = courseCatalogService.findPage(new Page<CourseCatalog>(request, response), courseCatalog); 
		model.addAttribute("page", page);*/
		
		List<CourseCatalog> list = Lists.newArrayList();
		List<CourseCatalog> sourcelist = courseCatalogService.findList(new CourseCatalog());
		CourseCatalog.sortList(list, sourcelist, CourseCatalog.getRootId(), true);
        model.addAttribute("list", list);
		
		
		return "modules/b_course_catalog/courseCatalogList";
	}

	@RequiresPermissions("b_course_catalog:courseCatalog:view")
	@RequestMapping(value = "form")
	public String form(CourseCatalog courseCatalog, Model model) {
		model.addAttribute("courseCatalog", courseCatalog);
		
		/*List<CourseCatalog> list = Lists.newArrayList();
		List<CourseCatalog> sourcelist = courseCatalogService.findList(new CourseCatalog());
		CourseCatalog.sortList(list, sourcelist, CourseCatalog.getRootId(), true);*/
		CourseCatalog condition = new CourseCatalog();
		condition.setParentId("0");
		List<CourseCatalog> list = courseCatalogService.findList(condition);
		model.addAttribute("parentCatalogList", list);
		
		return "modules/b_course_catalog/courseCatalogForm";
	}

	@RequiresPermissions("b_course_catalog:courseCatalog:edit")
	@RequestMapping(value = "save")
	public String save(CourseCatalog courseCatalog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, courseCatalog)){
			return form(courseCatalog, model);
		}
		
		//根据父分类级别得到分类级别
		String level = "1";
		String parentId = courseCatalog.getParentId();
		if (StringUtils.isNotBlank(parentId)) {
			 CourseCatalog parentCatalog = courseCatalogService.get(parentId+"");
			
			if (parentCatalog!=null) {
				String plevel = parentCatalog.getLevel();
				level = plevel == null ? "1" : (Integer.parseInt(plevel)+1+"");
				
				courseCatalog.setParentName(parentCatalog.getName());
			}
		}else {
			courseCatalog.setParentId("0");
		}
		
		courseCatalog.setLevel(level);
		
		courseCatalogService.save(courseCatalog);
		addMessage(redirectAttributes, "保存课程分类成功");
		return "redirect:"+Global.getAdminPath()+"/b_course_catalog/courseCatalog/?repage";
	}
	
	@RequiresPermissions("b_course_catalog:courseCatalog:edit")
	@RequestMapping(value = "delete")
	public String delete(CourseCatalog courseCatalog, RedirectAttributes redirectAttributes) {
		courseCatalogService.delete(courseCatalog);
		addMessage(redirectAttributes, "删除课程分类成功");
		return "redirect:"+Global.getAdminPath()+"/b_course_catalog/courseCatalog/?repage";
	}

}
/**
 * 
 */
package com.gimc.user.modules.b_movement_catalog.web;

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
import com.gimc.user.modules.b_course_catalog.entity.CourseCatalog;
import com.gimc.user.modules.b_movement_catalog.entity.MovementCatalog;
import com.gimc.user.modules.b_movement_catalog.service.MovementCatalogService;
import com.gimc.user.modules.sys.entity.Menu;
import com.google.common.collect.Lists;

/**
 * 动作分类Controller
 * @author linhaomiao
 * @version 2018-05-02
 */
@Controller
@RequestMapping(value = "${adminPath}/b_movement_catalog/movementCatalog")
public class MovementCatalogController extends BaseController {

	@Autowired
	private MovementCatalogService movementCatalogService;
	
	@ModelAttribute
	public MovementCatalog get(@RequestParam(required=false) String id) {
		MovementCatalog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = movementCatalogService.get(id);
		}
		if (entity == null){
			entity = new MovementCatalog();
		}
		return entity;
	}
	
	@RequiresPermissions("b_movement_catalog:movementCatalog:view")
	@RequestMapping(value = {"list", ""})
	public String list(MovementCatalog movementCatalog, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*Page<MovementCatalog> page = movementCatalogService.findPage(new Page<MovementCatalog>(request, response), movementCatalog); 
		model.addAttribute("page", page);*/
		
		List<MovementCatalog> list = Lists.newArrayList();
		List<MovementCatalog> sourcelist = movementCatalogService.findList(new MovementCatalog());
		MovementCatalog.sortList(list, sourcelist, MovementCatalog.getRootId(), true);
        model.addAttribute("list", list);
		
		
		
		return "modules/b_movement_catalog/movementCatalogList";
	}

	@RequiresPermissions("b_movement_catalog:movementCatalog:view")
	@RequestMapping(value = "form")
	public String form(MovementCatalog movementCatalog, Model model) {
		model.addAttribute("movementCatalog", movementCatalog);
		
		MovementCatalog condition = new MovementCatalog();
		condition.setParentId("0");
		List<MovementCatalog> parentCatalogList = movementCatalogService.findList(condition);
		model.addAttribute("parentCatalogList",parentCatalogList);
		
		
		return "modules/b_movement_catalog/movementCatalogForm";
	}

	@RequiresPermissions("b_movement_catalog:movementCatalog:edit")
	@RequestMapping(value = "save")
	public String save(MovementCatalog movementCatalog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, movementCatalog)){
			return form(movementCatalog, model);
		}
		
		//根据父分类级别得到分类级别
		String level = "1";
		String parentId = movementCatalog.getParentId();
		if (StringUtils.isNotBlank(parentId) && !"0".equals(parentId)) {
			MovementCatalog parentCatalog = movementCatalogService.get(parentId);
			
			if (parentCatalog!=null) {
				String plevel = parentCatalog.getLevel();
				level = plevel == null ? "1" : (Integer.parseInt(plevel)+1+"");
				
				movementCatalog.setParentName(parentCatalog.getName());
			}
		}else if (StringUtils.isBlank(parentId)) {
			movementCatalog.setParentId("0");
		}
		
		
		movementCatalog.setLevel(level);
		
		
		movementCatalogService.save(movementCatalog);
		addMessage(redirectAttributes, "保存动作分类成功");
		
		return "redirect:"+Global.getAdminPath()+"/b_movement_catalog/movementCatalog/";
	}
	
	@RequiresPermissions("b_movement_catalog:movementCatalog:edit")
	@RequestMapping(value = "delete")
	public String delete(MovementCatalog movementCatalog, RedirectAttributes redirectAttributes) {
		movementCatalogService.delete(movementCatalog);
		addMessage(redirectAttributes, "删除动作分类成功");
		return "redirect:"+Global.getAdminPath()+"/b_movement_catalog/movementCatalog/?repage";
	}

}
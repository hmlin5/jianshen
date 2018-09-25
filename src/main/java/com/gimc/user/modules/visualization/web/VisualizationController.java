/**
 * 
 */
package com.gimc.user.modules.visualization.web;

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
import com.gimc.user.modules.visualization.entity.Visualization;
import com.gimc.user.modules.visualization.service.VisualizationService;

/**
 * 今日实时Controller
 * @author xxx
 * @version 2018-07-17
 */
@Controller
@RequestMapping(value = "${adminPath}/visualization/visualization")
public class VisualizationController extends BaseController {

	@Autowired
	private VisualizationService visualizationService;
	
	@ModelAttribute
	public Visualization get(@RequestParam(required=false) String id) {
		Visualization entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = visualizationService.get(id);
		}
		if (entity == null){
			entity = new Visualization();
		}
		return entity;
	}
	
	@RequiresPermissions("visualization:visualization:view")
	@RequestMapping(value = {"list", ""})
	public String list(Visualization visualization, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Visualization> page = visualizationService.findPage(new Page<Visualization>(request, response), visualization); 
		model.addAttribute("page", page);
		return "modules/visualization/visualizationList";
	}

	@RequiresPermissions("visualization:visualization:view")
	@RequestMapping(value = "form")
	public String form(Visualization visualization, Model model) {
		model.addAttribute("visualization", visualization);
		return "modules/visualization/visualizationForm";
	}

	@RequiresPermissions("visualization:visualization:edit")
	@RequestMapping(value = "save")
	public String save(Visualization visualization, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, visualization)){
			return form(visualization, model);
		}
		visualizationService.save(visualization);
		addMessage(redirectAttributes, "保存今日实时成功");
		return "redirect:"+Global.getAdminPath()+"/visualization/visualization/?repage";
	}
	
	@RequiresPermissions("visualization:visualization:edit")
	@RequestMapping(value = "delete")
	public String delete(Visualization visualization, RedirectAttributes redirectAttributes) {
		visualizationService.delete(visualization);
		addMessage(redirectAttributes, "删除今日实时成功");
		return "redirect:"+Global.getAdminPath()+"/visualization/visualization/?repage";
	}

}
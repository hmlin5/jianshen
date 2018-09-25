/**
 * 
 */
package com.gimc.user.modules.b_version.web;

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
import com.gimc.user.modules.b_version.entity.Version;
import com.gimc.user.modules.b_version.service.VersionService;

/**
 * app版本Controller
 * @author linhaomiao
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/b_version/version")
public class VersionController extends BaseController {

	@Autowired
	private VersionService versionService;
	
	@ModelAttribute
	public Version get(@RequestParam(required=false) String id) {
		Version entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = versionService.get(id);
		}
		if (entity == null){
			entity = new Version();
		}
		return entity;
	}
	
	@RequiresPermissions("b_version:version:view")
	@RequestMapping(value = {"list", ""})
	public String list(Version version, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Version> page = versionService.findPage(new Page<Version>(request, response), version); 
		model.addAttribute("page", page);
		return "modules/b_version/versionList";
	}

	@RequiresPermissions("b_version:version:view")
	@RequestMapping(value = "form")
	public String form(Version version, Model model) {
		model.addAttribute("version", version);
		return "modules/b_version/versionForm";
	}

	@RequiresPermissions("b_version:version:edit")
	@RequestMapping(value = "save")
	public String save(Version version, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, version)){
			return form(version, model);
		}
		versionService.save(version);
		addMessage(redirectAttributes, "保存app版本成功");
		return "redirect:"+Global.getAdminPath()+"/b_version/version/?repage";
	}
	
	@RequiresPermissions("b_version:version:edit")
	@RequestMapping(value = "delete")
	public String delete(Version version, RedirectAttributes redirectAttributes) {
		versionService.delete(version);
		addMessage(redirectAttributes, "删除app版本成功");
		return "redirect:"+Global.getAdminPath()+"/b_version/version/?repage";
	}

}
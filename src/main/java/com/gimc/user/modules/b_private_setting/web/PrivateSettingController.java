/**
 * 
 */
package com.gimc.user.modules.b_private_setting.web;

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
import com.gimc.user.modules.b_private_setting.entity.PrivateSetting;
import com.gimc.user.modules.b_private_setting.service.PrivateSettingService;

/**
 * 隐私设置Controller
 * @author linhaomiao
 * @version 2018-06-03
 */
@Controller
@RequestMapping(value = "${adminPath}/b_private_setting/privateSetting")
public class PrivateSettingController extends BaseController {

	@Autowired
	private PrivateSettingService privateSettingService;
	
	@ModelAttribute
	public PrivateSetting get(@RequestParam(required=false) String id) {
		PrivateSetting entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = privateSettingService.get(id);
		}
		if (entity == null){
			entity = new PrivateSetting();
		}
		return entity;
	}
	
	@RequiresPermissions("b_private_setting:privateSetting:view")
	@RequestMapping(value = {"list", ""})
	public String list(PrivateSetting privateSetting, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PrivateSetting> page = privateSettingService.findPage(new Page<PrivateSetting>(request, response), privateSetting); 
		model.addAttribute("page", page);
		return "modules/b_private_setting/privateSettingList";
	}

	@RequiresPermissions("b_private_setting:privateSetting:view")
	@RequestMapping(value = "form")
	public String form(PrivateSetting privateSetting, Model model) {
		model.addAttribute("privateSetting", privateSetting);
		return "modules/b_private_setting/privateSettingForm";
	}

	@RequiresPermissions("b_private_setting:privateSetting:edit")
	@RequestMapping(value = "save")
	public String save(PrivateSetting privateSetting, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, privateSetting)){
			return form(privateSetting, model);
		}
		privateSettingService.save(privateSetting);
		addMessage(redirectAttributes, "保存隐私设置成功");
		return "redirect:"+Global.getAdminPath()+"/b_private_setting/privateSetting/?repage";
	}
	
	@RequiresPermissions("b_private_setting:privateSetting:edit")
	@RequestMapping(value = "delete")
	public String delete(PrivateSetting privateSetting, RedirectAttributes redirectAttributes) {
		privateSettingService.delete(privateSetting);
		addMessage(redirectAttributes, "删除隐私设置成功");
		return "redirect:"+Global.getAdminPath()+"/b_private_setting/privateSetting/?repage";
	}

}
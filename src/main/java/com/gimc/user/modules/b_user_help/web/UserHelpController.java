/**
 * 
 */
package com.gimc.user.modules.b_user_help.web;

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
import com.gimc.user.modules.b_user_help.entity.UserHelp;
import com.gimc.user.modules.b_user_help.service.UserHelpService;

/**
 * 用户帮助Controller
 * @author linhaomiao
 * @version 2018-09-03
 */
@Controller
@RequestMapping(value = "${adminPath}/b_user_help/userHelp")
public class UserHelpController /*extends BaseController*/ {

	@Autowired
	private UserHelpService userHelpService;
	
	@ModelAttribute
	public UserHelp get(@RequestParam(required=false) String id) {
		UserHelp entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userHelpService.get(id);
		}
		if (entity == null){
			entity = new UserHelp();
		}
		return entity;
	}
	
	@RequiresPermissions("b_user_help:userHelp:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserHelp userHelp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserHelp> page = userHelpService.findPage(new Page<UserHelp>(request, response), userHelp); 
		model.addAttribute("page", page);
		return "modules/b_user_help/userHelpList";
	}

	@RequiresPermissions("b_user_help:userHelp:view")
	@RequestMapping(value = "form")
	public String form(UserHelp userHelp, Model model) {
		model.addAttribute("userHelp", userHelp);
		return "modules/b_user_help/userHelpForm";
	}

	@RequiresPermissions("b_user_help:userHelp:edit")
	@RequestMapping(value = "save")
	public String save(UserHelp userHelp, Model model, RedirectAttributes redirectAttributes) {
		/*if (!beanValidator(model, userHelp)){
			return form(userHelp, model);
		}*/
		userHelpService.save(userHelp);
		//addMessage(redirectAttributes, "保存用户帮助成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_help/userHelp/?repage";
	}
	
	@RequiresPermissions("b_user_help:userHelp:edit")
	@RequestMapping(value = "delete")
	public String delete(UserHelp userHelp, RedirectAttributes redirectAttributes) {
		userHelpService.delete(userHelp);
		//addMessage(redirectAttributes, "删除用户帮助成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_help/userHelp/?repage";
	}

}
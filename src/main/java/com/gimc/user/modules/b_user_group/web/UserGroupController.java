/**
 * 
 */
package com.gimc.user.modules.b_user_group.web;

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
import com.gimc.user.modules.b_user_group.entity.UserGroup;
import com.gimc.user.modules.b_user_group.service.UserGroupService;

/**
 * 用户分组关系Controller
 * @author linhaomiao
 * @version 2018-06-07
 */
@Controller
@RequestMapping(value = "${adminPath}/b_user_group/userGroup")
public class UserGroupController extends BaseController {

	@Autowired
	private UserGroupService userGroupService;
	
	@ModelAttribute
	public UserGroup get(@RequestParam(required=false) String id) {
		UserGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userGroupService.get(id);
		}
		if (entity == null){
			entity = new UserGroup();
		}
		return entity;
	}
	
	@RequiresPermissions("b_user_group:userGroup:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserGroup userGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserGroup> page = userGroupService.findPage(new Page<UserGroup>(request, response), userGroup); 
		model.addAttribute("page", page);
		return "modules/b_user_group/userGroupList";
	}

	@RequiresPermissions("b_user_group:userGroup:view")
	@RequestMapping(value = "form")
	public String form(UserGroup userGroup, Model model) {
		model.addAttribute("userGroup", userGroup);
		return "modules/b_user_group/userGroupForm";
	}

	@RequiresPermissions("b_user_group:userGroup:edit")
	@RequestMapping(value = "save")
	public String save(UserGroup userGroup, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userGroup)){
			return form(userGroup, model);
		}
		userGroupService.save(userGroup);
		addMessage(redirectAttributes, "保存用户分组关系成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_group/userGroup/?repage";
	}
	
	@RequiresPermissions("b_user_group:userGroup:edit")
	@RequestMapping(value = "delete")
	public String delete(UserGroup userGroup, RedirectAttributes redirectAttributes) {
		userGroupService.delete(userGroup);
		addMessage(redirectAttributes, "删除用户分组关系成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_group/userGroup/?repage";
	}

}
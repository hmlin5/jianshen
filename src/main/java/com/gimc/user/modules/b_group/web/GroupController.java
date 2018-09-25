/**
 * 
 */
package com.gimc.user.modules.b_group.web;

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
import com.gimc.user.modules.b_group.entity.Group;
import com.gimc.user.modules.b_group.service.GroupService;

/**
 * 用户分组Controller
 * @author linhaomiao
 * @version 2018-06-07
 */
@Controller
@RequestMapping(value = "${adminPath}/b_group/group")
public class GroupController extends BaseController {

	@Autowired
	private GroupService groupService;
	
	@ModelAttribute
	public Group get(@RequestParam(required=false) String id) {
		Group entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groupService.get(id);
		}
		if (entity == null){
			entity = new Group();
		}
		return entity;
	}
	
	@RequiresPermissions("b_group:group:view")
	@RequestMapping(value = {"list", ""})
	public String list(Group group, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Group> page = groupService.findPage(new Page<Group>(request, response), group); 
		model.addAttribute("page", page);
		return "modules/b_group/groupList";
	}

	@RequiresPermissions("b_group:group:view")
	@RequestMapping(value = "form")
	public String form(Group group, Model model) {
		model.addAttribute("group", group);
		return "modules/b_group/groupForm";
	}

	@RequiresPermissions("b_group:group:edit")
	@RequestMapping(value = "save")
	public String save(Group group, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, group)){
			return form(group, model);
		}
		groupService.save(group);
		addMessage(redirectAttributes, "保存用户分组成功");
		return "redirect:"+Global.getAdminPath()+"/b_group/group/?repage";
	}
	
	@RequiresPermissions("b_group:group:edit")
	@RequestMapping(value = "delete")
	public String delete(Group group, RedirectAttributes redirectAttributes) {
		groupService.delete(group);
		addMessage(redirectAttributes, "删除用户分组成功");
		return "redirect:"+Global.getAdminPath()+"/b_group/group/?repage";
	}

}
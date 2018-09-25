/**
 * 
 */
package com.gimc.user.modules.b_user_movement.web;

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
import com.gimc.user.modules.b_user_movement.entity.UserMovement;
import com.gimc.user.modules.b_user_movement.service.UserMovementService;

/**
 * 用户推荐动作Controller
 * @author linhaomiao
 * @version 2018-05-30
 */
@Controller
@RequestMapping(value = "${adminPath}/b_user_movement/userMovement")
public class UserMovementController extends BaseController {

	@Autowired
	private UserMovementService userMovementService;
	
	@ModelAttribute
	public UserMovement get(@RequestParam(required=false) String id) {
		UserMovement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userMovementService.get(id);
		}
		if (entity == null){
			entity = new UserMovement();
		}
		return entity;
	}
	
	@RequiresPermissions("b_user_movement:userMovement:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserMovement userMovement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserMovement> page = userMovementService.findPage(new Page<UserMovement>(request, response), userMovement); 
		model.addAttribute("page", page);
		return "modules/b_user_movement/userMovementList";
	}

	@RequiresPermissions("b_user_movement:userMovement:view")
	@RequestMapping(value = "form")
	public String form(UserMovement userMovement, Model model) {
		model.addAttribute("userMovement", userMovement);
		return "modules/b_user_movement/userMovementForm";
	}

	@RequiresPermissions("b_user_movement:userMovement:edit")
	@RequestMapping(value = "save")
	public String save(UserMovement userMovement, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userMovement)){
			return form(userMovement, model);
		}
		userMovementService.save(userMovement);
		addMessage(redirectAttributes, "保存用户推荐动作成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_movement/userMovement/?repage";
	}
	
	@RequiresPermissions("b_user_movement:userMovement:edit")
	@RequestMapping(value = "delete")
	public String delete(UserMovement userMovement, RedirectAttributes redirectAttributes) {
		userMovementService.delete(userMovement);
		addMessage(redirectAttributes, "删除用户推荐动作成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_movement/userMovement/?repage";
	}

}
/**
 * 
 */
package com.gimc.user.modules.b_user_withdraw.web;

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
import com.gimc.user.modules.b_user_withdraw.entity.UserWithdraw;
import com.gimc.user.modules.b_user_withdraw.service.UserWithdrawService;

/**
 * 用户提现记录Controller
 * @author linhaomiao
 * @version 2018-07-04
 */
@Controller
@RequestMapping(value = "${adminPath}/b_user_withdraw/userWithdraw")
public class UserWithdrawController extends BaseController {

	@Autowired
	private UserWithdrawService userWithdrawService;
	
	@ModelAttribute
	public UserWithdraw get(@RequestParam(required=false) String id) {
		UserWithdraw entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userWithdrawService.get(id);
		}
		if (entity == null){
			entity = new UserWithdraw();
		}
		return entity;
	}
	
	@RequiresPermissions("b_user_withdraw:userWithdraw:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserWithdraw userWithdraw, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserWithdraw> page = userWithdrawService.findPage(new Page<UserWithdraw>(request, response), userWithdraw); 
		model.addAttribute("page", page);
		return "modules/b_user_withdraw/userWithdrawList";
	}

	@RequiresPermissions("b_user_withdraw:userWithdraw:view")
	@RequestMapping(value = "form")
	public String form(UserWithdraw userWithdraw, Model model) {
		model.addAttribute("userWithdraw", userWithdraw);
		return "modules/b_user_withdraw/userWithdrawForm";
	}

	@RequiresPermissions("b_user_withdraw:userWithdraw:edit")
	@RequestMapping(value = "save")
	public String save(UserWithdraw userWithdraw, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userWithdraw)){
			return form(userWithdraw, model);
		}
		userWithdrawService.save(userWithdraw);
		addMessage(redirectAttributes, "保存用户提现记录成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_withdraw/userWithdraw/?repage";
	}
	
	@RequiresPermissions("b_user_withdraw:userWithdraw:edit")
	@RequestMapping(value = "delete")
	public String delete(UserWithdraw userWithdraw, RedirectAttributes redirectAttributes) {
		userWithdrawService.delete(userWithdraw);
		addMessage(redirectAttributes, "删除用户提现记录成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_withdraw/userWithdraw/?repage";
	}

}
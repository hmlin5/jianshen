/**
 * 
 */
package com.gimc.user.modules.b_user_account.web;

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
import com.gimc.user.modules.b_user_account.entity.UserAccount;
import com.gimc.user.modules.b_user_account.service.UserAccountService;

/**
 * 用户账户Controller
 * @author linhaomiao
 * @version 2018-06-30
 */
@Controller
@RequestMapping(value = "${adminPath}/b_user_account/userAccount")
public class UserAccountController extends BaseController {

	@Autowired
	private UserAccountService userAccountService;
	
	@ModelAttribute
	public UserAccount get(@RequestParam(required=false) String id) {
		UserAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userAccountService.get(id);
		}
		if (entity == null){
			entity = new UserAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("b_user_account:userAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserAccount userAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserAccount> page = userAccountService.findPage(new Page<UserAccount>(request, response), userAccount); 
		model.addAttribute("page", page);
		return "modules/b_user_account/userAccountList";
	}

	@RequiresPermissions("b_user_account:userAccount:view")
	@RequestMapping(value = "form")
	public String form(UserAccount userAccount, Model model) {
		model.addAttribute("userAccount", userAccount);
		return "modules/b_user_account/userAccountForm";
	}

	@RequiresPermissions("b_user_account:userAccount:edit")
	@RequestMapping(value = "save")
	public String save(UserAccount userAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userAccount)){
			return form(userAccount, model);
		}
		userAccountService.save(userAccount);
		addMessage(redirectAttributes, "保存用户账户成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_account/userAccount/?repage";
	}
	
	@RequiresPermissions("b_user_account:userAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(UserAccount userAccount, RedirectAttributes redirectAttributes) {
		userAccountService.delete(userAccount);
		addMessage(redirectAttributes, "删除用户账户成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_account/userAccount/?repage";
	}

}
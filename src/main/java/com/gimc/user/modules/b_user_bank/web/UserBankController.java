/**
 * 
 */
package com.gimc.user.modules.b_user_bank.web;

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
import com.gimc.user.modules.b_user_bank.entity.UserBank;
import com.gimc.user.modules.b_user_bank.service.UserBankService;

/**
 * 用户绑定银行Controller
 * @author linhaomiao
 * @version 2018-07-04
 */
@Controller
@RequestMapping(value = "${adminPath}/b_user_bank/userBank")
public class UserBankController extends BaseController {

	@Autowired
	private UserBankService userBankService;
	
	@ModelAttribute
	public UserBank get(@RequestParam(required=false) String id) {
		UserBank entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userBankService.get(id);
		}
		if (entity == null){
			entity = new UserBank();
		}
		return entity;
	}
	
	@RequiresPermissions("b_user_bank:userBank:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserBank userBank, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserBank> page = userBankService.findPage(new Page<UserBank>(request, response), userBank); 
		model.addAttribute("page", page);
		return "modules/b_user_bank/userBankList";
	}

	@RequiresPermissions("b_user_bank:userBank:view")
	@RequestMapping(value = "form")
	public String form(UserBank userBank, Model model) {
		model.addAttribute("userBank", userBank);
		return "modules/b_user_bank/userBankForm";
	}

	@RequiresPermissions("b_user_bank:userBank:edit")
	@RequestMapping(value = "save")
	public String save(UserBank userBank, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userBank)){
			return form(userBank, model);
		}
		userBankService.save(userBank);
		addMessage(redirectAttributes, "保存用户绑定银行成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_bank/userBank/?repage";
	}
	
	@RequiresPermissions("b_user_bank:userBank:edit")
	@RequestMapping(value = "delete")
	public String delete(UserBank userBank, RedirectAttributes redirectAttributes) {
		userBankService.delete(userBank);
		addMessage(redirectAttributes, "删除用户绑定银行成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_bank/userBank/?repage";
	}

}
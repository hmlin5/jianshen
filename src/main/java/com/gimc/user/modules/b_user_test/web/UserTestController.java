/**
 * 
 */
package com.gimc.user.modules.b_user_test.web;

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
import com.gimc.user.modules.b_user_test.entity.UserTest;
import com.gimc.user.modules.b_user_test.service.UserTestService;

/**
 * 用户测试结果Controller
 * @author linhaomiao
 * @version 2018-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/b_user_test/userTest")
public class UserTestController extends BaseController {

	@Autowired
	private UserTestService userTestService;
	
	@ModelAttribute
	public UserTest get(@RequestParam(required=false) String id) {
		UserTest entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userTestService.get(id);
		}
		if (entity == null){
			entity = new UserTest();
		}
		return entity;
	}
	
	@RequiresPermissions("b_user_test:userTest:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserTest userTest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserTest> page = userTestService.findPage(new Page<UserTest>(request, response), userTest); 
		model.addAttribute("page", page);
		return "modules/b_user_test/userTestList";
	}

	@RequiresPermissions("b_user_test:userTest:view")
	@RequestMapping(value = "form")
	public String form(UserTest userTest, Model model) {
		model.addAttribute("userTest", userTest);
		return "modules/b_user_test/userTestForm";
	}

	@RequiresPermissions("b_user_test:userTest:edit")
	@RequestMapping(value = "save")
	public String save(UserTest userTest, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userTest)){
			return form(userTest, model);
		}
		userTestService.save(userTest);
		addMessage(redirectAttributes, "保存用户测试结果成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_test/userTest/?repage";
	}
	
	@RequiresPermissions("b_user_test:userTest:edit")
	@RequestMapping(value = "delete")
	public String delete(UserTest userTest, RedirectAttributes redirectAttributes) {
		userTestService.delete(userTest);
		addMessage(redirectAttributes, "删除用户测试结果成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_test/userTest/?repage";
	}

}
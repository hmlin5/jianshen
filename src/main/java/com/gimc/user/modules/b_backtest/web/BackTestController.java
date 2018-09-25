/**
 * 
 */
package com.gimc.user.modules.b_backtest.web;

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
import com.gimc.user.modules.b_backtest.entity.BackTest;
import com.gimc.user.modules.b_backtest.service.BackTestService;

/**
 * 后台测试用户Controller
 * @author gu
 * @version 2018-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/b_backtest/backTest")
public class BackTestController extends BaseController {

	@Autowired
	private BackTestService backTestService;
	
	@ModelAttribute
	public BackTest get(@RequestParam(required=false) String id) {
		BackTest entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = backTestService.get(id);
		}
		if (entity == null){
			entity = new BackTest();
		}
		return entity;
	}
	
	@RequiresPermissions("b_backtest:backTest:view")
	@RequestMapping(value = {"list", ""})
	public String list(BackTest backTest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BackTest> page = backTestService.findPage(new Page<BackTest>(request, response), backTest); 
		model.addAttribute("page", page);
		return "modules/b_backtest/backTestList";
	}

	@RequiresPermissions("b_backtest:backTest:view")
	@RequestMapping(value = "form")
	public String form(BackTest backTest, Model model) {
		model.addAttribute("backTest", backTest);
		return "modules/b_backtest/backTestForm";
	}

	@RequiresPermissions("b_backtest:backTest:edit")
	@RequestMapping(value = "save")
	public String save(BackTest backTest, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, backTest)){
			return form(backTest, model);
		}
		backTestService.save(backTest);
		addMessage(redirectAttributes, "保存后台用户测试成功");
		return "redirect:"+Global.getAdminPath()+"/b_backtest/backTest/?repage";
	}
	
	@RequiresPermissions("b_backtest:backTest:edit")
	@RequestMapping(value = "delete")
	public String delete(BackTest backTest, RedirectAttributes redirectAttributes) {
		backTestService.delete(backTest);
		addMessage(redirectAttributes, "删除后台用户测试成功");
		return "redirect:"+Global.getAdminPath()+"/b_backtest/backTest/?repage";
	}

}
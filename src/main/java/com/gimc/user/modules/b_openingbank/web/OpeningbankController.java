/**
 * 
 */
package com.gimc.user.modules.b_openingbank.web;

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
import com.gimc.user.modules.b_openingbank.entity.Openingbank;
import com.gimc.user.modules.b_openingbank.service.OpeningbankService;

/**
 * 开户行管理Controller
 * @author gu
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/b_openingbank/openingbank")
public class OpeningbankController extends BaseController {

	@Autowired
	private OpeningbankService openingbankService;
	
	@ModelAttribute
	public Openingbank get(@RequestParam(required=false) String id) {
		Openingbank entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = openingbankService.get(id);
		}
		if (entity == null){
			entity = new Openingbank();
		}
		return entity;
	}
	
	@RequiresPermissions("b_openingbank:openingbank:view")
	@RequestMapping(value = {"list", ""})
	public String list(Openingbank openingbank, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Openingbank> page = openingbankService.findPage(new Page<Openingbank>(request, response), openingbank); 
		model.addAttribute("page", page);
		return "modules/b_openingbank/openingbankList";
	}

	@RequiresPermissions("b_openingbank:openingbank:view")
	@RequestMapping(value = "form")
	public String form(Openingbank openingbank, Model model) {
		model.addAttribute("openingbank", openingbank);
		return "modules/b_openingbank/openingbankForm";
	}

	@RequiresPermissions("b_openingbank:openingbank:edit")
	@RequestMapping(value = "save")
	public String save(Openingbank openingbank, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, openingbank)){
			return form(openingbank, model);
		}
		openingbankService.save(openingbank);
		addMessage(redirectAttributes, "保存开户行管理成功");
		return "redirect:"+Global.getAdminPath()+"/b_openingbank/openingbank/?repage";
	}
	
	@RequiresPermissions("b_openingbank:openingbank:edit")
	@RequestMapping(value = "delete")
	public String delete(Openingbank openingbank, RedirectAttributes redirectAttributes) {
		openingbankService.delete(openingbank);
		addMessage(redirectAttributes, "删除开户行管理成功");
		return "redirect:"+Global.getAdminPath()+"/b_openingbank/openingbank/?repage";
	}

}
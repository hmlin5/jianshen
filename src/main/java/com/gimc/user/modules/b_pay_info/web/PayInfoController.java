/**
 * 
 */
package com.gimc.user.modules.b_pay_info.web;

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
import com.gimc.user.modules.b_pay_info.entity.PayInfo;
import com.gimc.user.modules.b_pay_info.service.PayInfoService;

/**
 * 支付信息Controller
 * @author linhaomiao
 * @version 2018-06-30
 */
@Controller
@RequestMapping(value = "${adminPath}/b_pay_info/payInfo")
public class PayInfoController extends BaseController {

	@Autowired
	private PayInfoService payInfoService;
	
	@ModelAttribute
	public PayInfo get(@RequestParam(required=false) String id) {
		PayInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = payInfoService.get(id);
		}
		if (entity == null){
			entity = new PayInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("b_pay_info:payInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(PayInfo payInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PayInfo> page = payInfoService.findPage(new Page<PayInfo>(request, response), payInfo); 
		model.addAttribute("page", page);
		return "modules/b_pay_info/payInfoList";
	}

	@RequiresPermissions("b_pay_info:payInfo:view")
	@RequestMapping(value = "form")
	public String form(PayInfo payInfo, Model model) {
		model.addAttribute("payInfo", payInfo);
		return "modules/b_pay_info/payInfoForm";
	}

	@RequiresPermissions("b_pay_info:payInfo:edit")
	@RequestMapping(value = "save")
	public String save(PayInfo payInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, payInfo)){
			return form(payInfo, model);
		}
		payInfoService.save(payInfo);
		addMessage(redirectAttributes, "保存支付信息成功");
		return "redirect:"+Global.getAdminPath()+"/b_pay_info/payInfo/?repage";
	}
	
	@RequiresPermissions("b_pay_info:payInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(PayInfo payInfo, RedirectAttributes redirectAttributes) {
		payInfoService.delete(payInfo);
		addMessage(redirectAttributes, "删除支付信息成功");
		return "redirect:"+Global.getAdminPath()+"/b_pay_info/payInfo/?repage";
	}

}
/**
 * 
 */
package com.gimc.user.modules.b_withdraw.web;

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
import com.gimc.user.modules.b_withdraw.entity.CashOrder;
import com.gimc.user.modules.b_withdraw.service.CashOrderService;

/**
 * 提现订单管理Controller
 * @author gu
 * @version 2018-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/b_withdraw/cashOrder")
public class CashOrderController extends BaseController {

	@Autowired
	private CashOrderService cashOrderService;
	
	@ModelAttribute
	public CashOrder get(@RequestParam(required=false) String id) {
		CashOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cashOrderService.get(id);
		}
		if (entity == null){
			entity = new CashOrder();
		}
		return entity;
	}
	
	@RequiresPermissions("b_withdraw:cashOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(CashOrder cashOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CashOrder> page = cashOrderService.findPage(new Page<CashOrder>(request, response), cashOrder); 
		model.addAttribute("page", page);
		return "modules/b_withdraw/cashOrderList";
	}

	@RequiresPermissions("b_withdraw:cashOrder:view")
	@RequestMapping(value = "form")
	public String form(CashOrder cashOrder, Model model) {
		model.addAttribute("cashOrder", cashOrder);
		return "modules/b_withdraw/cashOrderForm";
	}

	@RequiresPermissions("b_withdraw:cashOrder:edit")
	@RequestMapping(value = "save")
	public String save(CashOrder cashOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cashOrder)){
			return form(cashOrder, model);
		}
		cashOrderService.save(cashOrder);
		addMessage(redirectAttributes, "保存提现订单管理成功");
		return "redirect:"+Global.getAdminPath()+"/b_withdraw/cashOrder/?repage";
	}
	
	@RequiresPermissions("b_withdraw:cashOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(CashOrder cashOrder, RedirectAttributes redirectAttributes) {
		cashOrderService.delete(cashOrder);
		addMessage(redirectAttributes, "删除提现订单管理成功");
		return "redirect:"+Global.getAdminPath()+"/b_withdraw/cashOrder/?repage";
	}

}
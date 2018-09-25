/**
 * 
 */
package com.gimc.user.modules.b_order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gimc.user.common.utils.DateUtils;
import com.gimc.user.common.utils.burningUtil.ExportExcel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gimc.user.common.config.Global;
import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.web.BaseController;
import com.gimc.user.common.utils.DateUtil;
import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_gym.service.GymService;
import com.gimc.user.modules.b_order.entity.Order;
import com.gimc.user.modules.b_order.service.OrderService;
import com.gimc.user.modules.b_pay_info.entity.PayInfo;
import com.gimc.user.modules.b_pay_info.service.PayInfoService;
import com.gimc.user.modules.b_user_account.service.UserAccountService;
import com.gimc.user.modules.sys.entity.User;
import com.gimc.user.modules.sys.utils.UserUtils;

/**
 * 订单Controller
 * @author linhaomiao
 * @version 2018-06-01
 */
@Controller
@RequestMapping(value = "${adminPath}/b_order/order")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private GymService gymService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private PayInfoService payInfoService;
	
	@ModelAttribute
	public Order get(@RequestParam(required=false) String id) {
		Order entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderService.get(id);
		}
		if (entity == null){
			entity = new Order();
		}
		return entity;
	}
	
	@RequiresPermissions("b_order:order:view")
	@RequestMapping(value = {"list", ""})
	public String list(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		User user = UserUtils.getUser();
		if (CommonParam.SYS_GYM_MANAGER.equals(user.getUserType())) {
			
			Gym gym = gymService.findByUserId(user.getId());
			order.setGymId(gym.getId());
			Page<Order> page = orderService.findPage(new Page<Order>(request, response), order); 
			model.addAttribute("page", page);
			return "modules/b_order/orderList";
			
			
		}else if (CommonParam.SYS_ALL_MANAGER.equals(user.getUserType()) || CommonParam.SYS_SUPER_MANAGER.equals(user.getUserType())) {
			Page<Order> page = orderService.findPage(new Page<Order>(request, response), order); 
			model.addAttribute("page", page);
			return "modules/b_order/orderList";
			
		}
		
		return "modules/b_order/orderList";
	}


	@RequiresPermissions("b_order:order:view")
	@RequestMapping(value = "balanc")
	public String balanc(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> includeStatus= new ArrayList<String>();
		includeStatus.add(CommonParam.ORDER_STATUS_DAIPINGJIA);
		includeStatus.add(CommonParam.ORDER_STATUS_YIWANCHENG);
		order.setIncludeStatus(includeStatus);
		User user = UserUtils.getUser();
		if (CommonParam.SYS_GYM_MANAGER.equals(user.getUserType())) {

			Gym gym = gymService.findByUserId(user.getId());
			order.setGymId(gym.getId());
			order.setIsGym("0");
			Page<Order> page = orderService.findPage(new Page<Order>(request, response), order);
			model.addAttribute("page", page);
			return "modules/b_order/orderBalancList";


		}else if (CommonParam.SYS_ALL_MANAGER.equals(user.getUserType()) || CommonParam.SYS_SUPER_MANAGER.equals(user.getUserType())) {
			order.setIsGym("1");
			order.setIsConfirm("0");
			Page<Order> page = orderService.findPage(new Page<Order>(request, response), order);
			model.addAttribute("page", page);
			return "modules/b_order/orderBalancList";

		}

		return "modules/b_order/orderBalancList";
	}

	@RequiresPermissions("b_order:order:view")
	@RequestMapping(value = "form")
	public String form(Order order, Model model) {
		model.addAttribute("order", order);
		return "modules/b_order/orderForm";
	}

	@RequiresPermissions("b_order:order:edit")
	@RequestMapping(value = "save")
	public String save(Order order, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, order)){
			return form(order, model);
		}
		User user = UserUtils.getUser();
		String name=UserUtils.getUser().getName();
		Date date=DateUtil.getCurrentDate();
		String time=DateUtil.formateDate2Str(date, "yyyy-MM-dd HH:mm:ss");
		order.setOpRecord(time+" "+name);

		if (CommonParam.ORDER_CONFIRM_YES.equals(order.getIsConfirm())&&CommonParam.ORDER_CONFIRM_NO.equals(order.getOldIsConfirm())) {
			order.setPlatformTime(new Date());
		}else if(CommonParam.ORDER_CONFIRM_YES.equals(order.getIsGym())&&CommonParam.ORDER_CONFIRM_NO.equals(order.getOldIsGym())){
			order.setGymTime(new Date());
		}
		orderService.save(order);
	
		//检查是否修改结算状态
		if (CommonParam.ORDER_CONFIRM_YES.equals(order.getIsConfirm())) {
			String oldIsConfirm = order.getOldIsConfirm();
			if (StringUtils.isNotBlank(oldIsConfirm) && CommonParam.ORDER_CONFIRM_NO.equals(oldIsConfirm)) {
				if (StringUtils.isNotBlank(order.getCoachId())) {
					PayInfo payInfo = payInfoService.findByOrderNum(order.getOrderNum());
					if (payInfo!=null && payInfo.getPayAmount()>0) {
						userAccountService.freezeToUsable(order.getCoachId(), payInfo.getPayAmount());
					}
				}

			}
		}
		
		addMessage(redirectAttributes, "保存订单成功");
		return "redirect:"+Global.getAdminPath()+"/b_order/order/?repage";
	}
	
	@RequiresPermissions("b_order:order:edit")
	@RequestMapping(value = "delete")
	public String delete(Order order, RedirectAttributes redirectAttributes) {
		orderService.delete(order);
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:"+Global.getAdminPath()+"/b_order/order/?repage";
	}
	@RequiresPermissions("b_order:order:edit")
	@RequestMapping(value = "updateStatus")
	public String updateStatus(Order order, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (CommonParam.SYS_GYM_MANAGER.equals(user.getUserType())) {
			order.setIsGym("1");
			order.setGymTime(new Date());
		}else if (CommonParam.SYS_ALL_MANAGER.equals(user.getUserType()) || CommonParam.SYS_SUPER_MANAGER.equals(user.getUserType())) {
			order.setIsConfirm("1");
			order.setPlatformTime(new Date());
		}
		orderService.save(order);

		return "redirect:"+Global.getAdminPath()+"/b_order/order/?repage";
	}

//结算列表
	@RequiresPermissions("b_order:order:view")
	@RequestMapping(value = "settlementList")
	public String settlementList(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = UserUtils.getUser();
		if (CommonParam.SYS_GYM_MANAGER.equals(user.getUserType())) {

			Gym gym = gymService.findByUserId(user.getId());
			order.setGymId(gym.getId());
			/*order.setIsGym("1");
			order.setIsConfirm("1");*/
			Page<Order> page = orderService.settlementList(new Page<Order>(request, response), order);
			model.addAttribute("page", page);
			return "modules/b_order/orderSettlementList";


		}else if (CommonParam.SYS_ALL_MANAGER.equals(user.getUserType()) || CommonParam.SYS_SUPER_MANAGER.equals(user.getUserType())) {
			/*order.setIsGym("1");
			order.setIsConfirm("1");*/
			Page<Order> page = orderService.settlementList(new Page<Order>(request, response), order);
			model.addAttribute("page", page);
			return "modules/b_order/orderSettlementList";

		}

		return "modules/b_order/orderSettlementList";
	}
	@RequiresPermissions("b_order:order:view")
	@RequestMapping(value = "export")
	public String exportFile(Order order, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			User user = UserUtils.getUser();
			Page<Order> page=null;
			if (CommonParam.SYS_GYM_MANAGER.equals(user.getUserType())) {

				Gym gym = gymService.findByUserId(user.getId());
				order.setGymId(gym.getId());
				/*order.setIsGym("1");
				order.setIsConfirm("1");*/
				page = orderService.settlementList(new Page<Order>(request, response,-1), order);



			}else if (CommonParam.SYS_ALL_MANAGER.equals(user.getUserType()) || CommonParam.SYS_SUPER_MANAGER.equals(user.getUserType())) {
				/*order.setIsGym("1");
				order.setIsConfirm("1");*/
				page = orderService.settlementList(new Page<Order>(request, response,-1), order);

			}
			String fileName = "结算列表"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";

			System.out.println("new ExportExcel before================");
			
			new ExportExcel("结算列表", "结算列表",Order.class,1).setDataList(page.getList()).write(response,request, fileName).dispose();
			
			System.out.println("new ExportExcel after================");
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出结算列表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/b_order/order/settlementList?repage";
	}
	@RequiresPermissions("b_order:order:edit")
	@RequestMapping(value = "updateBalancStatus")
	public String updateBalancStatus(Order order, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (CommonParam.SYS_GYM_MANAGER.equals(user.getUserType())) {
			order.setIsGym("1");
			order.setGymTime(new Date());
		}else if (CommonParam.SYS_ALL_MANAGER.equals(user.getUserType()) || CommonParam.SYS_SUPER_MANAGER.equals(user.getUserType())) {
			order.setIsConfirm("1");
			order.setPlatformTime(new Date());
		}
		orderService.save(order);

		return "redirect:"+Global.getAdminPath()+"/b_order/order/balanc?repage";
	}
}
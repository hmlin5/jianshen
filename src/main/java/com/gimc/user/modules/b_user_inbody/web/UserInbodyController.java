/**
 * 
 */
package com.gimc.user.modules.b_user_inbody.web;

import java.math.BigDecimal;
import java.util.Date;

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
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_gym.service.GymService;
import com.gimc.user.modules.b_user_inbody.entity.UserInbody;
import com.gimc.user.modules.b_user_inbody.service.UserInbodyService;
import com.gimc.user.modules.sys.entity.User;
import com.gimc.user.modules.sys.utils.UserUtils;

/**
 * 用户Inbody数据Controller
 * @author linhaomiao
 * @version 2018-06-14
 */
@Controller
@RequestMapping(value = "${adminPath}/b_user_inbody/userInbody")
public class UserInbodyController extends BaseController {

	@Autowired
	private UserInbodyService userInbodyService;
	@Autowired
	private GymService gymService;
	
	@ModelAttribute
	public UserInbody get(@RequestParam(required=false) String id) {
		UserInbody entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userInbodyService.get(id);
		}
		if (entity == null){
			entity = new UserInbody();
		}
		return entity;
	}
	
	@RequiresPermissions("b_user_inbody:userInbody:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserInbody userInbody, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserInbody> page = userInbodyService.findPage(new Page<UserInbody>(request, response), userInbody); 
		model.addAttribute("page", page);
		return "modules/b_user_inbody/userInbodyList";
	}

	@RequiresPermissions("b_user_inbody:userInbody:view")
	@RequestMapping(value = "listIframe")
	public String listIframe(UserInbody userInbody, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserInbody> page = userInbodyService.findPage(new Page<UserInbody>(request, response), userInbody); 
		model.addAttribute("page", page);
		return "modules/b_user_inbody/userInbodyList_iframe";
	}
	
	
	@RequiresPermissions("b_user_inbody:userInbody:view")
	@RequestMapping(value = "form")
	public String form(UserInbody userInbody, Model model) {
		model.addAttribute("userInbody", userInbody);
		return "modules/b_user_inbody/userInbodyForm";
	}

	//@RequiresPermissions("b_user_inbody:userInbody:edit")
	@RequestMapping(value = "save")
	public String save(UserInbody userInbody, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userInbody)){
			return form(userInbody, model);
		}
		
		User user = UserUtils.getUser();
		
		Gym gym = gymService.findByUserId(user.getId());
		if (gym!=null) {
			userInbody.setGymId(gym.getId());
		}
		
		if (userInbody.getTestTime() == null) {
			userInbody.setTestTime(new Date());
		}
		
		//计算bmi指数
		try {
			double weightD = userInbody.getWeight();
			double heightD = userInbody.getHeight();
			BigDecimal b = new BigDecimal(weightD/(heightD*heightD));  
			double bmiIndex = b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
			userInbody.setBmiIndex(bmiIndex);
		} catch (Exception e) {
			addMessage(model, "输入有误,请检查");
			return form(userInbody, model);
		}
		
		
		//计算腰臀比
		if (userInbody.getHipline() != null && userInbody.getWaistline() != null && userInbody.getHipline().doubleValue()>0 && userInbody.getWaistline().doubleValue()>0) {
			
			BigDecimal bd = new BigDecimal(userInbody.getWaistline().doubleValue()/userInbody.getHipline().doubleValue());  
			double whr = bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
			userInbody.setWhr(whr+"");
		}else{
			userInbody.setHipline(null);
			userInbody.setWaistline(null);
		}

		
		userInbodyService.save(userInbody);
		addMessage(redirectAttributes, "保存教练评估报告成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_inbody/userInbody/listIframe?userId="+userInbody.getUserId();
	}
	
	//@RequiresPermissions("b_user_inbody:userInbody:edit")
	@RequestMapping(value = "delete")
	public String delete(UserInbody userInbody, RedirectAttributes redirectAttributes) {
		userInbodyService.delete(userInbody);
		addMessage(redirectAttributes, "删除用户教练评估报告成功");
		return "redirect:"+Global.getAdminPath()+"/b_user_inbody/userInbody/listIframe?userId="+userInbody.getUserId();
	}

}
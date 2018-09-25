/**
 * 
 */
package com.gimc.user.modules.b_rest_time.web;

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
import com.gimc.user.modules.b_rest_time.entity.RestTime;
import com.gimc.user.modules.b_rest_time.service.RestTimeService;
import com.gimc.user.modules.b_user.service.AppUserService;

import java.util.*;

/**
 * 休息时间Controller
 * @author linhaomiao
 * @version 2018-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/b_rest_time/restTime")
public class RestTimeController extends BaseController {

	@Autowired
	private RestTimeService restTimeService;
	
	@Autowired
	private AppUserService appUserService;

	
	@ModelAttribute
	public RestTime get(@RequestParam(required=false) String id) {
		RestTime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = restTimeService.get(id);
		}
		if (entity == null){
			entity = new RestTime();
		}
		return entity;
	}
	
	@RequiresPermissions("b_rest_time:restTime:view")
	@RequestMapping(value = "listframe")
	public String listframe(RestTime restTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		
		Page<RestTime> page = restTimeService.findPage(new Page<RestTime>(request, response), restTime); 
		model.addAttribute("page", page);
	
		
		
		return "modules/b_rest_time/restTimeList";
	}
	
	@RequiresPermissions("b_rest_time:restTime:view")
	@RequestMapping(value = {"list",""})
	public String list(RestTime restTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		
		Page<RestTime> page = restTimeService.findPage(new Page<RestTime>(request, response), restTime); 
		model.addAttribute("page", page);
		
		return "modules/b_rest_time/restTimeList";
	}
	


	@RequiresPermissions("b_rest_time:restTime:view")
	@RequestMapping(value = "form")
	public String form(RestTime restTime, Model model) {
		model.addAttribute("restTime", restTime);
		return "modules/b_rest_time/restTimeForm";
	}

	@RequiresPermissions("b_rest_time:restTime:edit")
	@RequestMapping(value = "save")
	public String save(RestTime restTime, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, restTime)){
			return form(restTime, model);
		}
		restTimeService.save(restTime);
		addMessage(redirectAttributes, "保存休息时间成功");
		return "redirect:"+Global.getAdminPath()+"/b_rest_time/restTime/?repage";
	}
	
	@RequiresPermissions("b_rest_time:restTime:edit")
	@RequestMapping(value = "delete")
	public String delete(RestTime restTime, RedirectAttributes redirectAttributes) {
		restTimeService.delete(restTime);
		addMessage(redirectAttributes, "删除休息时间成功");
		return "redirect:"+Global.getAdminPath()+"/b_rest_time/restTime/?repage";
	}

}
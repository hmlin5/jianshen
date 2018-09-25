/**
 * 
 */
package com.gimc.user.modules.b_msg.web;

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
import com.gimc.user.modules.b_msg.entity.Msg;
import com.gimc.user.modules.b_msg.service.MsgService;

/**
 * 融云消息Controller
 * @author linhaomiao
 * @version 2018-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/b_msg/msg")
public class MsgController extends BaseController {

	@Autowired
	private MsgService msgService;
	
	@ModelAttribute
	public Msg get(@RequestParam(required=false) String id) {
		Msg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = msgService.get(id);
		}
		if (entity == null){
			entity = new Msg();
		}
		return entity;
	}
	
	@RequiresPermissions("b_msg:msg:view")
	@RequestMapping(value = {"list", ""})
	public String list(Msg msg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Msg> page = msgService.findPage(new Page<Msg>(request, response), msg); 
		model.addAttribute("page", page);
		return "modules/b_msg/msgList";
	}

	@RequiresPermissions("b_msg:msg:view")
	@RequestMapping(value = "form")
	public String form(Msg msg, Model model) {
		model.addAttribute("msg", msg);
		return "modules/b_msg/msgForm";
	}

	@RequiresPermissions("b_msg:msg:edit")
	@RequestMapping(value = "save")
	public String save(Msg msg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, msg)){
			return form(msg, model);
		}
		msgService.save(msg);
		addMessage(redirectAttributes, "保存融云消息成功");
		return "redirect:"+Global.getAdminPath()+"/b_msg/msg/?repage";
	}
	
	@RequiresPermissions("b_msg:msg:edit")
	@RequestMapping(value = "delete")
	public String delete(Msg msg, RedirectAttributes redirectAttributes) {
		msgService.delete(msg);
		addMessage(redirectAttributes, "删除融云消息成功");
		return "redirect:"+Global.getAdminPath()+"/b_msg/msg/?repage";
	}

}
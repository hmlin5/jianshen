/**
 * 
 */
package com.gimc.user.modules.b_feedback.web;

import java.text.SimpleDateFormat;
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
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_feedback.entity.Feedback;
import com.gimc.user.modules.b_feedback.service.FeedbackService;
import com.gimc.user.modules.b_msg.entity.Msg;
import com.gimc.user.modules.b_msg.service.MsgService;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user.service.AppUserService;

/**
 * 反馈列表Controller
 * @author gu
 * @version 2018-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/b_feedback/feedback")
public class FeedbackController extends BaseController {

	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private MsgService msgService;
	@Autowired
	private AppUserService appUserService;
	
	@ModelAttribute
	public Feedback get(@RequestParam(required=false) String id) {
		Feedback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = feedbackService.get(id);
		}
		if (entity == null){
			entity = new Feedback();
		}
		return entity;
	}
	
	@RequiresPermissions("b_feedback:feedback:view")
	@RequestMapping(value = {"list", ""})
	public String list(Feedback feedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Feedback> page = feedbackService.findPage(new Page<Feedback>(request, response), feedback); 
		model.addAttribute("page", page);
		return "modules/b_feedback/feedbackList";
	}

	@RequiresPermissions("b_feedback:feedback:view")
	@RequestMapping(value = "form")
	public String form(Feedback feedback, Model model) {
		model.addAttribute("feedback", feedback);
		return "modules/b_feedback/feedbackForm";
	}

	@RequiresPermissions("b_feedback:feedback:edit")
	@RequestMapping(value = "save")
	public String save(Feedback feedback, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, feedback)){
			return form(feedback, model);
		}
		feedbackService.save(feedback);
		addMessage(redirectAttributes, "保存反馈列表成功");
		return "redirect:"+Global.getAdminPath()+"/b_feedback/feedback/?repage";
	}
	
	
	@RequiresPermissions("b_feedback:feedback:edit")
	@RequestMapping(value = "replyFeedback")
	public String replyFeedback(Feedback feedback, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, feedback)){
			return form(feedback, model);
		}
		
		Date replyTime = feedback.getReplyTime() == null ? new Date() : feedback.getReplyTime();
		feedback.setReplyStatus("0");
		feedback.setReplyTime(replyTime);
		
		//发送消息
		
		try {
			if (StringUtils.isNotBlank(feedback.getFeedbackUserId())) {
				
				AppUser user = appUserService.get(feedback.getFeedbackUserId());
				if (user != null) {
					//发系统消息
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					
					String nickName = user.getNickName()==null?CommonParam.USER_DEFAULT_NICKNAME : user.getNickName();
					String content = "平台已对您于"+sdf.format(feedback.getFeedbackTime())+"提交的反馈做出了回复,回复内容如下："+feedback.getReplyContent();
					
					msgService.sendSystem(feedback.getFeedbackUserId(), content);
					
					Msg msg = new Msg();
					
					msg.setContent(content);
					msg.setTitle("意见反馈\"回复\"通知");
					msg.setToUserId(feedback.getFeedbackUserId());
					msg.setFromUserId("1");
					msg.setToUserName(nickName);
					msg.setFromUserName("系统");
					msg.setIsRead(CommonParam.MSG_NOT_READ);
					msg.setMsgType(CommonParam.MSG_TYPE_SYSTEM);
					
					msgService.save(msg);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		feedbackService.save(feedback);
		addMessage(redirectAttributes, "回复成功");
		return "redirect:"+Global.getAdminPath()+"/b_feedback/feedback/?repage";
	}
	
	
	@RequiresPermissions("b_feedback:feedback:edit")
	@RequestMapping(value = "delete")
	public String delete(Feedback feedback, RedirectAttributes redirectAttributes) {
		feedbackService.delete(feedback);
		addMessage(redirectAttributes, "删除反馈列表成功");
		return "redirect:"+Global.getAdminPath()+"/b_feedback/feedback/?repage";
	}

}
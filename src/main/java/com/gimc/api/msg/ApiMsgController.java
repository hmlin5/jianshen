package com.gimc.api.msg;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.common.utils.burningUtil.PageBean;
import com.gimc.user.common.utils.burningUtil.RequestMap;
import com.gimc.user.common.utils.burningUtil.ResultMap;
import com.gimc.user.modules.b_bmi_course.service.BmiCourseService;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_msg.entity.Msg;
import com.gimc.user.modules.b_msg.service.MsgService;
import com.gimc.user.modules.b_order.entity.Order;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user.entity.SysCheckCode;
import com.gimc.user.modules.b_user.service.AppUserService;
import com.gimc.user.modules.b_user_test.entity.UserTest;
import com.gimc.user.modules.b_user_test.service.UserTestService;
import com.gimc.user.modules.sys.service.SystemService;
import com.gimc.user.modules.sys.utils.DictUtils;


@Controller
@RequestMapping("/api/msg")
public class ApiMsgController {
	
	@Autowired
	private AppUserService userService;
	@Autowired
	private UserTestService userTestService;
	@Autowired
	private BmiCourseService bmiCourseService;
	@Autowired
	private MsgService msgService;
	
	
	
	
	/**
	 * 查看用户订单消息与系统消息未读数
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/test")
	public ResultMap test(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		try {
			msgService.sendSystem("11", "你是教练?");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return map.setState(CommonParam.STATE_OK).setMsg(CommonParam.rc_appKey);
	}
	
	
	
	
	/**
	 * 查看用户订单消息与系统消息未读数
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/msgNotReadNum")
	public ResultMap msgNotReadNum(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		Msg msg = new Msg();
		msg.setToUserId(user.getId());
		msg.setIsRead(CommonParam.MSG_NOT_READ);
		List<Msg> list = msgService.findList(msg);
		int num = 0;
		if (list!=null && list.size()>0) {
			num = list.size();
		}
		
		return map.setState(CommonParam.STATE_OK).setData(num);
	}
	
	
	
	
	/**
	 * 查看消息详情
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/msgDetail")
	public ResultMap msgDetail(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		String msgId = param.getMsgId();
		if (StringUtils.isBlank(msgId) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		Msg msg = msgService.get(msgId);
		if (user.getId().equals(msg.getToUserId()) || user.getId().equals(msg.getFromUserId())) {
			//检查消息已读状态, 若为未读改为已读
			msg.setIsRead(CommonParam.MSG_READ);
			msgService.update(msg);
			return map.setState(CommonParam.STATE_OK).setData(msg);
			
		}else {
			return map.setState(CommonParam.STATE_ERROR).setMsg("该消息不允许查看");
		}
		
	}
	
	
	
	
	/**
	 * 查看消息列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/msgList")
	public ResultMap msgList(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		
		String msgType = param.getMsgType();
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		if (StringUtils.isBlank(msgType) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		Msg msg = new Msg();
		msg.setToUserId(user.getId());
		msg.setMsgType(msgType);
		msg.setFromUserId("1");
		List<Msg> list = msgService.findList(msg);
		
		if (list!=null && list.size()>0) {
			PageBean<Msg> pageBean = new PageBean<Msg>(currentPage, pageSize, list.size());
			pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
			return map.setState(CommonParam.STATE_OK).setData(pageBean);
			
		}else {
			PageBean<Msg> pageBean = new PageBean<Msg>(currentPage, pageSize, 0);
			return map.setState(CommonParam.STATE_OK).setData(pageBean );
			
		}
		
		
	}
	
	
	
	
    
	
}

/**
 * 
 */
package com.gimc.user.modules.b_msg.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.modules.b_msg.entity.Msg;
import com.gimc.user.modules.b_order.entity.Order;
import com.gimc.user.modules.b_pay_info.entity.PayInfo;
import com.gimc.user.modules.b_pay_info.service.PayInfoService;
import com.gimc.user.modules.b_private_setting.service.PrivateSettingService;
import com.gimc.user.modules.b_user.dao.AppUserDao;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user_course.entity.UserCourse;

import io.rong.RongCloud;
import io.rong.messages.TxtMessage;
import io.rong.methods.message._private.Private;
import io.rong.methods.message.chatroom.Chatroom;
import io.rong.methods.message.discussion.Discussion;
import io.rong.methods.message.group.Group;
import io.rong.methods.message.history.History;
import io.rong.methods.message.system.MsgSystem;
import io.rong.models.message.PrivateMessage;
import io.rong.models.message.SystemMessage;
import io.rong.models.response.ResponseResult;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;

import com.gimc.user.modules.b_comment.entity.Comment;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_msg.dao.MsgDao;

/**
 * 融云消息Service
 * @author linhaomiao
 * @version 2018-05-18
 */
@Service
@Transactional(readOnly = true)
public class MsgService extends CrudService<MsgDao, Msg> {

	 private static final TxtMessage txtMessage = new TxtMessage("hello", "helloExtra");
	
	private RongCloud rongCloud ;
	
	@Autowired
	private PrivateSettingService psService;
	@Autowired
	private AppUserDao appUserDao;
	@Autowired
	private PayInfoService payInfoService;
	
	public void init() {
        // do some initialization work
    }
	
	
    public static void main(String[] args) throws Exception {

       // RongCloud rongCloud = RongCloud.getInstance("25wehl3u2seew", "VROM3Su8qcGcj");
        //自定义 api 地址方式
        RongCloud rongCloud = CommonParam.rongCloud;

        Private Private = rongCloud.message.msgPrivate;
        MsgSystem system = rongCloud.message.system;
        Group group = rongCloud.message.group;
        Chatroom chatroom = rongCloud.message.chatroom;
        Discussion discussion = rongCloud.message.discussion;
        History history = rongCloud.message.history;

        /**
         * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/message/system.html#send
         * 发送系统消息
         */
        String[] targetIds = {"11"};
        SystemMessage systemMessage = new SystemMessage()
                .setSenderId("1")
                .setTargetId(targetIds)
                //.setObjectName(txtMessage.getType())
                .setObjectName("app:SysMsg")
                .setContent(txtMessage)
                .setPushContent("this is a push")
                .setPushData("{'pushData':'hello'}")
                .setIsPersisted(0)
                .setIsCounted(0)
                .setContentAvailable(0);

        ResponseResult result = system.send(systemMessage);
        System.out.println("send system message:  " + result.toString());
        
        
       /* AppUser user = new AppUser();
        
        user.setId("13");
        user.setPhone("10087");
        user.setHeadImgUrl("http://120.76.205.34:8899/burningMedia/default/user_head_default.jpg");*/
        
   /*     Private msgPrivate = CommonParam.rongCloud.message.msgPrivate;
    	String[] targetIds = {"12"};
    	TxtMessage txtMessage = new TxtMessage("666", null);
    	
    	 PrivateMessage privateMessage = new PrivateMessage()
                 .setSenderId("11")//必选
                 .setTargetId(targetIds)//必选
                 .setObjectName(txtMessage.getType())//必选
                 .setContent(txtMessage);//必选
//                 .setPushContent("")//可选
//                 .setPushData("{\"pushData\":\"hello\"}")//可选
//                 .setCount("4")//可选
//                 .setVerifyBlacklist(0)//可选
//                 .setIsPersisted(0)//可选
//                 .setIsCounted(0)//可选
//                 .setIsIncludeSender(0);//可选
                
         ResponseResult privateResult = msgPrivate.send(privateMessage);
         System.out.println("send system message:  " + privateResult.toString());*/
        
    }
	
    
    
    public ResponseResult sendSystem(String targetId, String content) throws Exception{
    	
    	MsgSystem system = CommonParam.rongCloud.message.system;
    	String[] targetIds = {targetId};
    	TxtMessage txtMessage = new TxtMessage(content, null);
    	
    	SystemMessage systemMessage = new SystemMessage()
                 .setSenderId(CommonParam.APP_SYSTEM_ID)//必选
                 .setTargetId(targetIds)//必选
                 .setObjectName(txtMessage.getType())//必选
                 .setContent(txtMessage);//必选
                 /*.setPushContent("")//可选
                 .setPushData("{\"pushData\":\"hello\"}")//可选
                 .setCount("4")//可选
                 .setVerifyBlacklist(0)//可选
                 .setIsPersisted(0)//可选
                 .setIsCounted(0)//可选
                 .setIsIncludeSender(0);//可选*/                 
         ResponseResult privateResult = system.send(systemMessage);
    	
    	return privateResult;
    }
    
    
    
    
  /**
   * 发送单聊消息
   * @param targetId
   * @param senderId
   * @param content
   * @return
   * @throws Exception 
   */
    public ResponseResult sendPrivate(String targetId, String senderId, String content) throws Exception{
    	
    	Private msgPrivate = CommonParam.rongCloud.message.msgPrivate;
    	String[] targetIds = {targetId};
    	TxtMessage txtMessage = new TxtMessage(content, null);
    	
    	 PrivateMessage privateMessage = new PrivateMessage()
                 .setSenderId(senderId)//必选
                 .setTargetId(targetIds)//必选
                 .setObjectName(txtMessage.getType())//必选
                 .setContent(txtMessage);//必选
                 /*.setPushContent("")//可选
                 .setPushData("{\"pushData\":\"hello\"}")//可选
                 .setCount("4")//可选
                 .setVerifyBlacklist(0)//可选
                 .setIsPersisted(0)//可选
                 .setIsCounted(0)//可选
                 .setIsIncludeSender(0);//可选
*/                 
         ResponseResult privateResult = msgPrivate.send(privateMessage);
    	
    	
    	
    	return privateResult;
    }
    
    
    /**
     * 注册融云用户
     * @param appUser
     * @return
     * @throws Exception
     */
	public TokenResult registRcUser(AppUser appUser) throws Exception{
		rongCloud = CommonParam.rongCloud;
		
		UserModel user = new UserModel()
				.setId(appUser.getId())
				.setName(appUser.getPhone())
				.setPortrait(appUser.getHeadImgUrl());

		TokenResult result = rongCloud.user.register(user);
		
		return result;
	}
	
	
	
	
	
	@Transactional(readOnly=true)
	public void sendOrderMessage(Order order,String content,String coachContent,String title){
		
			try{
		
		Msg messUser = new Msg();
		messUser.setTitle("\""+title+"\"通知");
		messUser.setFromUserId(CommonParam.APP_SYSTEM_ID);
		messUser.setContent(content);
		messUser.setToUserId(order.getStuId());
		messUser.setFromUserName(CommonParam.APP_SYSTEM_NAME);
		messUser.setToUserName(order.getStuName());
		messUser.setIsRead(CommonParam.MSG_NOT_READ);
		messUser.setToUserType(CommonParam.USER_TYPE_SIJIAO);
		messUser.setMsgType(CommonParam.MSG_TYPE_ORDER);
		
		Msg messCoach=new Msg();
		messCoach.setTitle("\""+title+"\"通知");
		messCoach.setFromUserId(CommonParam.APP_SYSTEM_ID);
		messCoach.setContent(coachContent);
		messCoach.setToUserId(order.getCoachId());
		messCoach.setFromUserName(CommonParam.APP_SYSTEM_NAME);
		messCoach.setToUserName(order.getCoachName());
		messCoach.setIsRead(CommonParam.MSG_NOT_READ);
		messCoach.setToUserType(CommonParam.USER_TYPE_SIJIAO);
		messCoach.setMsgType(CommonParam.MSG_TYPE_ORDER);
		
		
		
		sendSystem(order.getCoachId(), coachContent);
		sendSystem(order.getStuId(),content);
	
		
		save(messUser);
		save(messCoach);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public Msg get(String id) {
		return super.get(id);
	}
	
	public List<Msg> findList(Msg msg) {
		return super.findList(msg);
	}
	
	public Page<Msg> findPage(Page<Msg> page, Msg msg) {
		return super.findPage(page, msg);
	}
	
	@Transactional(readOnly = false)
	public void save(Msg msg) {
		super.save(msg);
	}
	
	@Transactional(readOnly = false)
	public void delete(Msg msg) {
		super.delete(msg);
	}



	public RongCloud getRongCloud() {
		return rongCloud;
	}



	public void setRongCloud(RongCloud rongCloud) {
		this.rongCloud = rongCloud;
	}
	
	
	
	/**
	 * 新开线程发送消息 //TODO 
	 * @param request
	 * @param msg
	 */
	public void info(HttpServletRequest request, String msg){
		
			LogRunnable lr = new LogRunnable(request, msg);
			Thread thread = new Thread(lr);
			thread.start();
	}

	class LogRunnable implements Runnable {  
		  
		private HttpServletRequest request;
		private String msg;
		  
		    public LogRunnable() {
				super();
			}

			public LogRunnable(HttpServletRequest request, String msg) {
				super();
				this.request = request;
				this.msg = msg;
			}

			@Override  
		    public void run() {  
					
		    }  
	}

	
	
	@Transactional(readOnly=false)
	public void sendPublicMessage(AppUser stu, AppUser coach,String msgType) {
		
		try {
			
			if (stu!=null) {
				
				Msg mess = new Msg();
				mess.setTitle(stu.getMsgTitle());
				mess.setFromUserId(CommonParam.APP_SYSTEM_ID);
				mess.setContent(stu.getMsgContent());
				mess.setToUserId(stu.getId());
				mess.setFromUserName(CommonParam.APP_SYSTEM_NAME);
				mess.setToUserName(stu.getRealName());
				mess.setIsRead(CommonParam.MSG_NOT_READ);
				mess.setToUserType(CommonParam.USER_TYPE_SIJIAO);
				mess.setMsgType(msgType);
				mess.setCreateDate(new Date());
				
				sendSystem(stu.getId(), stu.getMsgContent());
				save(mess);
			}
			
			
			if (coach != null) {
				
				Msg mess2 = new Msg();
				mess2.setTitle(coach.getMsgTitle());
				mess2.setFromUserId(CommonParam.APP_SYSTEM_ID);
				mess2.setContent(coach.getMsgContent());
				mess2.setToUserId(coach.getId());
				mess2.setFromUserName(CommonParam.APP_SYSTEM_NAME);
				mess2.setToUserName(coach.getRealName());
				mess2.setIsRead(CommonParam.MSG_NOT_READ);
				mess2.setToUserType(CommonParam.USER_TYPE_SIJIAO);
				mess2.setMsgType(CommonParam.MSG_TYPE_ORDER);
				mess2.setCreateDate(new Date());
				
				sendSystem(coach.getId(), coach.getMsgContent());
				save(mess2);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
	
	
	
	
	/**
	 * 注册成功消息
	 * @param toUser
	 */
	@Transactional(readOnly=false)
	public void sendRegistMsg(AppUser toUser){
		
		try {
			String content = "恭喜您！您成为注册用户了，快去开启您的健身之旅吧";

			Msg mess = new Msg();
			mess.setTitle("注册成功");
			mess.setFromUserId(CommonParam.APP_SYSTEM_ID);
			mess.setContent(content);
			mess.setToUserId(toUser.getId());
			mess.setFromUserName(CommonParam.APP_SYSTEM_NAME);
			mess.setToUserName(toUser.getRealName());
			mess.setIsRead(CommonParam.MSG_NOT_READ);
			mess.setToUserType(CommonParam.USER_TYPE_ZHUCE);
			mess.setMsgType(CommonParam.MSG_TYPE_SYSTEM);
			
			sendSystem(toUser.getId(), content);
			
			save(mess);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 评价回复消息
	 * @param toUser
	 */
	@Transactional(readOnly=false)
	public void sendCommentReply(AppUser toUser, AppUser fromUser, Comment comment){
		
		try {
			String content = "您的教练\""+fromUser.getRealName()+"\"对您的评价做出了回复,详情请点击查看";

			Msg mess = new Msg();
			mess.setTitle("\"评价回复\"通知");
			mess.setFromUserId(CommonParam.APP_SYSTEM_ID);
			mess.setContent(content);
			mess.setToUserId(toUser.getId());
			mess.setFromUserName(CommonParam.APP_SYSTEM_NAME);
			mess.setToUserName(toUser.getPhone());
			mess.setIsRead(CommonParam.MSG_NOT_READ);
			mess.setToUserType(CommonParam.USER_TYPE_SIJIAO);
			mess.setMsgType(CommonParam.MSG_TYPE_SYSTEM);
			
			sendSystem(toUser.getId(), content);
			
			save(mess);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/*
	 * 预约订单通知
	 */
	@Transactional(readOnly=false)
	public void sendAppoinmentCourse(AppUser user, AppUser coachUser, Order order, UserCourse userCourse) {
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			String content = "您预约的"+sdf.format(order.getAppointmentTime())+"课程为\"第"+userCourse.getSeq()+"节\"-\""+userCourse.getName()+"\"，教练\""+coachUser.getRealName()+"\"已接受了您的预约申请。查看路径：订单-待上课";
			user.setMsgTitle("\"预约成功\"通知");
			user.setMsgContent(content);
			
			
			String content2 = "会员\""+user.getRealName()+"\"预约您于"+sdf.format(order.getAppointmentTime())+"上\"第"+userCourse.getSeq()+"节\"-\""+userCourse.getName()+"\"，"+"别忘了准时赴约上课哦。查看路径：订单-待上课";
			coachUser.setMsgTitle("\"会员预约\"通知");
			coachUser.setMsgContent(content2);
			
			sendPublicMessage(user, coachUser, CommonParam.MSG_TYPE_ORDER);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
	
	
	
	
	/**
	 * 开始上课通知
	 * @param stu
	 * @param coachUser
	 * @param order
	 * @param userCourse
	 */
	@Transactional(readOnly=false)
	public void sendCourseStart(AppUser stu, AppUser coach, Order order, UserCourse userCourse) {
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			String content = "您预约的"+sdf.format(order.getAppointmentTime())+"\"第"+userCourse.getSeq()+"节\"-\""+userCourse.getName()+"\"，教练\""+coach.getRealName()+"\"已点击开始上课咯~";
			stu.setMsgTitle("\"开始上课\"通知");
			stu.setMsgContent(content);
		
			if (psService.checkCourseNotify(stu.getId())) {
				sendPublicMessage(stu, null, CommonParam.MSG_TYPE_ORDER);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 结束课程通知
	 * @param stu
	 * @param coach
	 * @param order
	 * @param userCourse
	 */
	@Transactional(readOnly=false)
	public void sendCourseFinish(AppUser toUser, AppUser finishUser, Order order, UserCourse userCourse) {
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			
			if (order.getStuId().equals(toUser.getId())) {//给学员发送
				String content = "您预约的"+sdf.format(order.getAppointmentTime())+"\"第"+userCourse.getSeq()+"节\"-\""+userCourse.getName()+"\"，教练\""+finishUser.getRealName()+"\"已点击结束课程，建议："+order.getRemarks();
				toUser.setMsgTitle("\"课程结束\"通知");
				toUser.setMsgContent(content);
				
				if (psService.checkCourseNotify(toUser.getId())) {
					sendPublicMessage(toUser, null, CommonParam.MSG_TYPE_ORDER);
				}
				
			}else if (order.getCoachId().equals(toUser.getId())) {//给教练发送
				String content = "会员\""+toUser.getRealName()+"\"预约您的"+sdf.format(order.getAppointmentTime())+"\"第"+userCourse.getSeq()+"节\"-\""+userCourse.getName()+"\"，会员\""+finishUser.getRealName()+"\"已点击结束课程，建议："+order.getRemarks();
				toUser.setMsgTitle("\"课程结束\"通知");
				toUser.setMsgContent(content);
				
				if (psService.checkCourseNotify(toUser.getId())) {
					sendPublicMessage(toUser, null, CommonParam.MSG_TYPE_ORDER);
				}
			}
		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * 抢单通知
	 * @param
	 * @param
	 * @param
	 * @param
	 */
	@Transactional(readOnly=false)
	public void sendRobCourse(AppUser stu, AppUser coach, Order order, UserCourse userCourse) {
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			String content = "教练\""+coach.getRealName()+"\"抢先接受了您的一键约课，课程是\"第"+userCourse.getSeq()+"节\"-\""+userCourse.getName()+"\"，上课时间为" +sdf.format(order.getAppointmentTime())+"。查看路径：订单-待上课";
			stu.setMsgTitle("\"预约成功\"通知");
			stu.setMsgContent(content);
		
			if (psService.checkOrderNotify(stu.getId())) {
				sendPublicMessage(stu, null, CommonParam.MSG_TYPE_ORDER);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 学员抢单通知
	 * @param
	 * @param
	 * @param
	 * @param
	 */
	@Transactional(readOnly=false)
	public void sendRobStu(AppUser stu, AppUser coach, Order order, UserCourse userCourse) {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			String content = "学员\""+stu.getRealName()+"\"抢先接受了您的一键约课，课程是\"第"+userCourse.getSeq()+"节\"-\""+userCourse.getName()+"\"，上课时间为" +sdf.format(order.getAppointmentTime())+"。查看路径：订单-待上课";
			coach.setMsgTitle("\"预约成功\"通知");
			coach.setMsgContent(content);


			if (psService.checkOrderNotify(coach.getId())) {
				sendPublicMessage(null, coach, CommonParam.MSG_TYPE_ORDER);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * 发送教练安排订单消息
	 * @param stu
	 * @param coach
	 * @param order
	 * @param userCourse
	 */
	@Transactional(readOnly=false)
	public void sendAnPai(AppUser stu, AppUser coach, Order order, UserCourse userCourse) {
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			String content = "您的教练\""+coach.getRealName()+"\"约您于"+sdf.format(order.getAppointmentTime())+"到健身房上第"+userCourse.getSeq()+"节课-"+userCourse.getName()+"，快去看看吧。查看路径：订单-待确认";
			stu.setMsgTitle("\"教练排课\"通知");
			stu.setMsgContent(content);
			
			sendPublicMessage(stu, null, CommonParam.MSG_TYPE_ORDER);
			/*if (psService.checkOrderNotify(stu.getId())) {
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * 发送学员拒绝安排订单消息
	 * @param stu
	 * @param coach
	 * @param order
	 * @param userCourse
	 */
	@Transactional(readOnly=false)
	public void sendRefuseCourse(AppUser stu, AppUser coach, Order order, UserCourse userCourse) {
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			String content = "很抱歉！您安排会员\""+stu.getRealName()+"\"的"+sdf.format(order.getAppointmentTime())+"课程为\"第"+userCourse.getSeq()+"节\"-\""+userCourse.getName()+"\"，会员\""+stu.getRealName()+"\"拒绝了您的课程安排，若有需要，请重新预约";
			coach.setMsgTitle("\"约课失败\"通知");
			coach.setMsgContent(content);
			
			sendPublicMessage(null, coach, CommonParam.MSG_TYPE_ORDER);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	/**
	 * 发送学员接受安排订单消息
	 * @param stu
	 * @param coach
	 * @param order
	 * @param userCourse
	 */
	@Transactional(readOnly=false)
	public void sendAcceptCourse(AppUser stu, AppUser coach, Order order, UserCourse userCourse) {
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			String content = "您安排会员\""+stu.getRealName()+"\"的"+sdf.format(order.getAppointmentTime())+"课程为\"第"+userCourse.getSeq()+"节\"-\""+userCourse.getName()+"\"，会员\""+stu.getRealName()+"\"已接受了您的课程安排。查看路径：订单-待上课";
			coach.setMsgTitle("\"约课成功\"通知");
			coach.setMsgContent(content);
			
			sendPublicMessage(null, coach, CommonParam.MSG_TYPE_ORDER);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

	/**
	 * 发送打赏通知
	 * @param order
	 */
	@Transactional(readOnly=false)
	public void sendReward(AppUser stu, AppUser coach, Order order) {
		
		
			try {
				PayInfo payInfo = payInfoService.findByOrderNum(order.getOrderNum());
				if (payInfo != null && CommonParam.REFUND_NO.equals(payInfo.getRefundflag())) {
					String content = "恭喜您！会员\""+stu.getRealName()+"\"打赏了您"+payInfo.getPayAmount()+"元，订单完成后就可以解冻使用了哦~";
					coach.setMsgTitle("\"打赏\"通知");
					coach.setMsgContent(content);
					
					sendPublicMessage(null, coach, CommonParam.MSG_TYPE_ORDER);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
	}


	/**
	 * 取消课程通知
	 * @param toUser
	 * @param
	 * @param
	 * @param
	 */
	@Transactional(readOnly=false)
	public void sendCancelOrder(AppUser toUser, AppUser cancelUser,  Order order, UserCourse userCourse) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			
			if (order.getStuId().equals(toUser.getId())) {//给学员发送
				String content = "教练\""+cancelUser.getRealName()+"\"已取消了您预约Ta授课的订单"+sdf.format(order.getAppointmentTime())+"\"第"+userCourse.getSeq()+"节\"-\""+userCourse.getName()+"\"，理由："+order.getCancelReason()+"，若有需要，请重新预约哦~";
				toUser.setMsgTitle("\"取消课程\"通知");
				toUser.setMsgContent(content);
				
				if (psService.checkOrderNotify(toUser.getId())) {
					sendPublicMessage(toUser, null, CommonParam.MSG_TYPE_ORDER);
				}
				
			}else if (order.getCoachId().equals(toUser.getId())) {//给教练发送
				String content = "会员\""+cancelUser.getRealName()+"\"已取消了预约您的订单"+sdf.format(order.getAppointmentTime())+"\"第"+userCourse.getSeq()+"节\"-\""+userCourse.getName()+"\"，理由："+order.getCancelReason();
				toUser.setMsgTitle("\"取消课程\"通知");
				toUser.setMsgContent(content);
				
				if (psService.checkOrderNotify(toUser.getId())) {
					sendPublicMessage(toUser, null, CommonParam.MSG_TYPE_ORDER);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}		
	
	
	
	
	
	
	
	
	
	
	
}
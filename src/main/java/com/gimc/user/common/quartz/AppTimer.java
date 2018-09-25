package com.gimc.user.common.quartz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gimc.user.common.utils.DateUtil;
import com.gimc.user.modules.b_msg.entity.Msg;
import com.gimc.user.modules.b_user_gym.entity.UserGym;
import com.gimc.user.modules.b_user_gym.service.UserGymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.service.CrudService;
import com.gimc.user.common.utils.DateAlgorithm;
import com.gimc.user.common.utils.burningUtil.ResultMap;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_course.entity.Course;
import com.gimc.user.modules.b_course.service.CourseService;
import com.gimc.user.modules.b_msg.service.MsgService;
import com.gimc.user.modules.b_order.dao.OrderDao;
import com.gimc.user.modules.b_order.entity.Order;
import com.gimc.user.modules.b_order.service.OrderService;
import com.gimc.user.modules.b_private_setting.service.PrivateSettingService;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user.service.AppUserService;
import com.gimc.user.modules.b_user_course.entity.UserCourse;
import com.gimc.user.modules.b_user_course.service.UserCourseService;

@Service
@Transactional(readOnly = false)
public class AppTimer extends CrudService<OrderDao, Order>{
	
	@Autowired
	private MsgService msgService;
	
	@Autowired
	private OrderService orderservice;
	
	@Autowired
	private CourseService courseservice;
	
	@Autowired
	private UserCourseService userCourseService;

	
	@Autowired
	private DateAlgorithm dateAlgorithm;
	
	@Autowired
	private PrivateSettingService privateSessting;
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UserGymService userGymService;
	
	
	public void timmer(){
	Order order2=new Order();
	Course course=new Course();
	
	List<Order> orderList=orderservice.findList(order2);
	List<Course> coList=courseservice.findList(course);
	
	 Date date=new Date();
		
	for(Order order:orderList){
		
		if(order.getStatus().equalsIgnoreCase(CommonParam.ORDER_STATUS_DAIQUEREN)){
			//当订单过时取消
					Date createDate=order.getCreateDate();
					Calendar cal = Calendar.getInstance();
					cal.setTime(createDate);
					cal.add(Calendar.HOUR,24);// 24小时制
			        createDate = cal.getTime();
 					if((order.getAppointmentTime().getDay()==date.getDay()&&order.getAppointmentTime().getTime()<date.getTime())||(("2".equals(order.getType())||"3".equals(order.getType()))&&createDate.getTime()<date.getTime())){
						order.setStatus(CommonParam.ORDER_STATUS_YIQUXIAO);
						order.setStartTime(date);
						order.setEndTime(date);
						orderservice.UpdateStatus(order);
						
						//过时取消的时候，将订单设置成未开始
						UserCourse userCourse=new UserCourse();
						userCourse.setFinishFlag(CommonParam.COURSE_WEIKAISHI);
						userCourse.setUserId(order.getStuId());
						userCourse.setName(order.getClassName());
						userCourse.setGymId(order.getGymId());
						
						userCourseService.updateCourse(userCourse);


						//返回课程数
						UserGym userGym=new UserGym();
						userGym.setUserId(order.getStuId());
						userGym.setGymId(order.getGymId());
						userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
						List<UserGym> userGymList= userGymService.findList(userGym);
						if(userGymList!=null&&userGymList.size()>0){
							userGym=userGymList.get(0);
							if(userGym.getRestNumber()!=null){
								userGym.setRestNumber(userGym.getRestNumber()+1);
								userGymService.update(userGym);
							}


						}
						
					}

		}
		
	
		 for(Course cou:coList){
			 
			 Date appointmentTime=order.getAppointmentTime();
			 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			 String dateFormat=format.format(appointmentTime);
			 order.setStartTime(order.getAppointmentTime());
			 String stu_id=order.getStuId();
		     AppUser user=appUserService.getStuId(stu_id);
			 
			//调取时间差算法类
		     Long[] resutlt=dateAlgorithm.Datepoor(date, appointmentTime);
			 
			 //224342342344
			 
			 
			 if(order.getStatus().equalsIgnoreCase(CommonParam.ORDER_STATUS_DAISHANGKE)){
				 if(format.format(date).equalsIgnoreCase(format.format(appointmentTime))){
					 order.setStartTime(date);
					 order.setStatus(CommonParam.ORDER_STATUS_SHANGKEZHONG);
					 orderservice.updateStatusFor(order);
				 }
			 }
			 
			 if(order.getStatus().equalsIgnoreCase(CommonParam.ORDER_STATUS_SHANGKEZHONG)){
				 
			   	   Date startDate=order.getStartTime();  
				   
				   Long[] poor=dateAlgorithm.Datepoor(date, startDate);
				   
				 //若当前系统时间=上课开始时间+课程时长+3小时
				   if(poor[1]==3&&poor[2]>=0){
					   order.setEndTime(date);
					   order.setStatus(CommonParam.ORDER_STATUS_DAIPINGJIA);
					   orderservice.updateStatusFor(order);
					   //更新课程状态
					   String courseId = order.getCourseId();
					   UserCourse userCourse = userCourseService.get(courseId);
					   if (userCourse != null) {
						   userCourse.setFinishFlag(CommonParam.COURSE_YIWANCHENG);
						   userCourseService.save(userCourse);
					   }

				   }
				  
			 }
			 
					
					//238427
     
     if(user!=null){
    	 	 
     switch (privateSessting.checkPrivate(user)) {
	case 1:
		try{
		//待上课状态
		 if(order.getStatus().equalsIgnoreCase(CommonParam.ORDER_STATUS_DAISHANGKE)){
			 //系统时间等于预约时间
			 if(format.format(date).equals(format.format(appointmentTime))){
				 
			     msgService.sendOrderMessage(order,"您预约"+dateFormat+
			    		                                 " "+order.getClassName()+
			    		                                 "已到时，系统已自动默认上课咯~",
			    		                           "会员"+order.getStuName()
			    		                                +"预约您的"+dateFormat
			    		                                +" "+order.getClassName()+"已到时,系统已自动默认开始上课咯~","开始上课");
		
			     
			 }
			 //系统时间<预约时间，教练点击了上课
			 if(date.getTime()<appointmentTime.getTime()&&order.getStatus().equalsIgnoreCase(CommonParam.ORDER_STATUS_SHANGKEZHONG)){
				 msgService.sendOrderMessage(order,"订单状态发生改变，请查看！！","订单自动转换为上课状态！！！","开始上课");
			 }
			 //预约时间离当前系统时间相差24小时时                     
			 if(resutlt[0]==1&&resutlt[1]==0&&resutlt[2]==0&&resutlt[3]==0){
				 msgService.sendOrderMessage(order,dateFormat+"别忘了准时赴约上课哦，您预约的是"+
						                                          " "+order.getClassName(),
						                            "会员"+order.getStuName()
						                                 +"预约您的"+dateFormat
						                                 +" "+order.getClassName()+",别忘了准时赴约哦","上课提醒");
		
			 }
			 
			 //预约时间离当前系统时间相差1小时时 
			 if(resutlt[0]==0&&resutlt[1]==1&&resutlt[2]==0&&resutlt[3]==0){
				 msgService.sendOrderMessage(order,dateFormat+"别忘了准时赴约上课哦，目前距离上课时间还剩下1小时，您预约的是"+
						                                          " "+order.getClassName(),
						                             "会员"+order.getStuName()
						                                  +"预约您的"+dateFormat
						                                  +" "+order.getClassName()+",目前距离上课时间还剩下1小时，别忘了准时赴约上课哦","上课提醒");
	
			 }
		   }
		 
		   
		   //上课中
		   if(order.getStatus().equalsIgnoreCase(CommonParam.ORDER_STATUS_SHANGKEZHONG)){
			   Date startDate=order.getStartTime();  
			   
			   Long[] poor=dateAlgorithm.Datepoor(date, startDate);
			   
			   //若当前系统时间=上课开始时间+课程时长+3小时
			   if(poor[1]==3&&poor[2]>=0){
				   
				   msgService.sendOrderMessage(order,"您预约"+dateFormat
							                                   +order.getClassName()
							                                   +"，课程时长约"+cou.getDuration()+"分钟，课程已结束，系统已自动"
							                                   + "确认结束课程",
							                          "会员"+order.getStuName()
							                               +"预约您的"+dateFormat
							                               +" "+order.getClassName()
							                               +",课程时长约"+cou.getDuration()+"分钟，课程已结束，系统已自动确认确认结束课程","超时\"课程结束\"");
			
			   }
		   }
		   //10分钟发送待评价
		   if(order.getStatus().equalsIgnoreCase(CommonParam.ORDER_STATUS_DAIPINGJIA)){
			   Date endTime=order.getEndTime();

			   Long[] poor=dateAlgorithm.Datepoor(date, endTime);

			   //若当前系统时间=10分钟
			   if(poor[0]==0&&poor[1]==0&&poor[2]==10){
				   Msg messUser = new Msg();
				   messUser.setTitle("\""+"会员"+"\"通知");
				   messUser.setFromUserId(CommonParam.APP_SYSTEM_ID);
				   messUser.setContent("亲爱的会员：您在"+dateFormat+"的课程已上完，上课教练是"+order.getCoachName()+"，为了更好改进服务，请您给"+order.getCoachName()+"教练作出客观评价吧。评价路径：订单-全部");
				   messUser.setToUserId(order.getStuId());
				   messUser.setFromUserName(CommonParam.APP_SYSTEM_NAME);
				   messUser.setToUserName(order.getStuName());
				   messUser.setIsRead(CommonParam.MSG_NOT_READ);
				   messUser.setToUserType(CommonParam.USER_TYPE_SIJIAO);
				   messUser.setMsgType(CommonParam.MSG_TYPE_ORDER);
				   msgService.sendSystem(order.getStuId(),"亲爱的会员：您在"+dateFormat+"的课程已上完，上课教练是"+order.getCoachName()+"，为了更好改进服务，请您给"+order.getCoachName()+"教练作出客观评价吧。评价路径：订单-全部");
				   msgService.save(messUser);
			   }
		   }
		   
	 	} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		break;
	//1 0	
	case 2:
		try{
			//待上课状态
			 if(order.getStatus().equalsIgnoreCase(CommonParam.ORDER_STATUS_DAISHANGKE)){
				 //系统时间等于预约时间
				 if(format.format(date).equals(format.format(appointmentTime))){
					 msgService.sendOrderMessage(order,"您预约"+dateFormat+
                             " "+order.getClassName()+
                             "已到时，系统已自动默认上课咯~",
                       "会员"+order.getStuName()
                            +"预约您的"+dateFormat
                            +" "+order.getClassName()+"已到时,系统已自动默认开始上课咯~","开始上课");
				 }
				 //系统时间<预约时间，教练点击了上课
				 if(date.getTime()<appointmentTime.getTime()&&order.getStatus().equalsIgnoreCase(CommonParam.ORDER_STATUS_SHANGKEZHONG)){
					 msgService.sendOrderMessage(order,"订单状态发生改变，请查看！！","订单自动转换为上课状态！！！","开始上课");
				 }
				 //预约时间离当前系统时间相差24小时时                     
				 if(resutlt[0]==1&&resutlt[1]==0&&resutlt[2]==0&&resutlt[3]==0){
					 msgService.sendOrderMessage(order,dateFormat+"别忘了准时赴约上课哦，您预约的是"+
							                                          " "+order.getClassName(),
							                            "会员"+order.getStuName()
							                                 +"预约您的"+dateFormat
							                                 +" "+order.getClassName()+",别忘了准时赴约哦","上课提醒");
				
				 }
				 
				 //预约时间离当前系统时间相差1小时时 
				 if(resutlt[0]==0&&resutlt[1]==1&&resutlt[2]==0&&resutlt[3]==0){
					 msgService.sendOrderMessage(order,dateFormat+"别忘了准时赴约上课哦，目前距离上课时间还剩下1小时，您预约的是"+
							                                          " "+order.getClassName(),
							                             "会员"+order.getStuName()
							                                  +"预约您的"+dateFormat
							                                  +" "+order.getClassName()+",目前距离上课时间还剩下1小时，别忘了准时赴约上课哦","上课提醒");
			
				 }
			   }
			 
		
			   //上课中
			   if(order.getStatus().equalsIgnoreCase(CommonParam.ORDER_STATUS_SHANGKEZHONG)){
				   Date startDate=order.getStartTime();  
				   
				   Long[] poor=dateAlgorithm.Datepoor(date, startDate);
				   
				   //若当前系统时间=上课开始时间+课程时长+3小时
				   if(poor[1]==3&&poor[2]==0){
					   
					   msgService.sendOrderMessage(order,"您预约"+dateFormat
								                                   +order.getClassName()
								                                   +"，课程时长约"+cou.getDuration()+"分钟，课程已结束，系统已自动"
								                                   + "确认结束课程",
								                          "会员"+order.getStuName()
								                               +"预约您的"+dateFormat
								                               +" "+order.getClassName()
								                               +",课程时长约"+cou.getDuration()+"分钟，课程已结束，系统已自动确认确认结束课程","超时\"课程结束\"");
				
				   }
			 }
			   
		 	} catch (Exception e) {
				e.printStackTrace();
			} 
			
		
		
		
		break;
	//0 1	
	case 3:
		try{
			//待上课状态
			 if(order.getStatus().equalsIgnoreCase(CommonParam.ORDER_STATUS_DAISHANGKE)){
				 //系统时间等于预约时间
				 if(format.format(date).equals(format.format(appointmentTime))){
					 order.setStartTime(date);
					 order.setStatus(CommonParam.ORDER_STATUS_SHANGKEZHONG);
					 
					 orderservice.updateStatusFor(order);
				     msgService.sendOrderMessage(order,"您预约"+dateFormat+
				    		                                 " "+order.getClassName()+
				    		                                 "已到时，系统已自动默认上课咯~",
				    		                           "会员"+order.getStuName()
				    		                                +"预约您的"+dateFormat
				    		                                +" "+order.getClassName()+"已到时,系统已自动默认开始上课咯~","开始上课");
				
				 }
			   }
			
			   
		 	} catch (Exception e) {
				e.printStackTrace();
			} 
		
		break;
	//0 0	
	case 4:
		break;	

	default:
		break;
	 }
    } 
   }  
  }
 }
}

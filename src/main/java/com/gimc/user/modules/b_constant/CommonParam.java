package com.gimc.user.modules.b_constant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gimc.user.common.utils.PropertiesLoader;

import io.rong.RongCloud;

@Component
public class CommonParam {
	
	
	//属性文件加载对象
	private static PropertiesLoader loader = new PropertiesLoader("application.properties");
	
	public static String CHECK_CODE_TAB_NAME = "教练来了";  
	
	public static String USER_DEFAULT_NICKNAME = "无名氏";
    
	//融云
	public static RongCloud rongCloud ;
	public static String rc_appKey ;
	public static String rc_appSecret ;
	
	//支付宝
	public static String ALI_APPID;
	public static String ALI_PRIVATE_KEY;
	public static String ALI_PUBLIC_KEY;   //验签用
	public static String ALI_APP_PUBLIC_KEY; //支付用
	public static String ALI_GATEWAYURL;
	public static String ALI_NOTIFY_URL;
	public static String ALI_SIGN_TYPE;
	
	
	//微信支付
	public static String WX_APPID; 
	public static String WX_SECRET; 
	public static String WX_MCH_ID; 
	public static String WX_NOTIFY_URL; 
	public static String WX_REFUND_NOTIFY_URL; 
	public static String WX_CERT_PATH; 
	
	
	// 用户类型(1：注册用户 ,2：私教用户 ,3：普通用户,  4：教练, 5:教练+私教用户 )
	public static String USER_TYPE_ZHUCE = "1";
	public static String USER_TYPE_SIJIAO = "2";
	public static String USER_TYPE_PUTONG = "3";
	public static String USER_TYPE_JIAOLIAN = "4";
	public static String USER_TYPE_SIJIAO_JIAOLIAN = "5";
	
	//管理员类型
	public static String SYS_SUPER_MANAGER = "0"; // 超级后台
	public static String SYS_ALL_MANAGER = "1"; // 总后台
	public static String SYS_GYM_MANAGER = "2"; //健身房后台

	//用户来源
	public static String USER_SOURCE_APP = "1";  // 来自app注册
	public static String USER_SOURCE_SYSTEM = "2";  // 来自系统录入
	
	public static String APP_SYSTEM_ID = "1"; //表示系统的用户id
	public static String APP_SYSTEM_NAME ="系统"; //表示系统的用户名称
	
	//用户激活状态
	public static String ACTIVE_NO = "0";
	public static String ACTIVE_YES = "1";
	
	//关注关系
	public static String RELATION_NO_FOLLOW = "1";  //我不关注
	public static String RELATION_ME_FOLLOW = "2";  //我已关注
	public static String RELATION_BOTH_FOLLOW = "3"; //互相关注
	
	//绑定标识, 0:绑定, 1: 解绑
	public static String USER_GYM_BIND_YES = "0";
	public static String USER_GYM_BIND_NO = "1";
	
	//是否为存量客户,0:是, 1:否
	public static String PERSIST_USER_YES = "0";
	public static String PERSIST_USER_NO = "1";
	
	//冻结标识, 0:否, 1:是
	public static String FREEZE_YES = "1";
	public static String FREEZE_NO = "0";
	
	// 用户默认头像地址后缀
	public static String USER_HEAD_DEFAULT = "default/user_head_default.jpg";
	// app"我的"界面背景图片地址后缀
	public static String ME_BACKGROUD_IMG = "default/me_backgroud_img_750x550.jpg";
	// app个人中心学员背景图片地址后缀
	public static String STU_CENTER_IMG = "default/stu_center_img_750x550.jpg";
	// app个人中心教练背景图片地址后缀
	public static String COACH_CENTER_IMG = "default/coach_center_img_750x550.jpg";
	// 健身房默认图片地址后缀
	public static String GYM_DEFAULT_LOGO_IMG = "default/gym_default_logo_img_750x550.jpg";
	
	//订单类型
	public static String ORDER_TYPE_YUYUE = "1";  //预约订单
	public static String ORDER_TYPE_YIJIAN = "2";  //一键订单
	public static String ORDER_TYPE_ANPAI = "3";  //安排订单
	
	//订单状态
	public static String ORDER_STATUS_DAIQUEREN = "1"; //待确认
	public static String ORDER_STATUS_DAISHANGKE = "2"; //待上课
	public static String ORDER_STATUS_SHANGKEZHONG = "3"; //上课中
	public static String ORDER_STATUS_DAIPINGJIA = "4"; //待评价
	public static String ORDER_STATUS_YIWANCHENG = "5"; //已完成
	public static String ORDER_STATUS_YIQUXIAO = "6"; //已取消

	//订单是否结算
	public static String ORDER_CONFIRM_NO = "0"; //未结算
	public static String ORDER_CONFIRM_YES = "1"; //已结算
	
	//订单是否可以评论
	public static String ORDER_CAN_ENVALUATE_YES = "0";  //可以
	public static String ORDER_CAN_ENVALUATE_NO = "1";  //不可以
	
	//支付方式
	public static String PAY_BY_ALI = "1";  //支付宝
	public static String PAY_BY_WX = "2";  //微信
	//支付标识
	public static String PAY_NO = "0";  //未支付
	public static String PAY_YES = "1";  //已支付
	//退款标识
	public static String REFUND_NO = "0"; //未退款
	public static String REFUND_YES = "1";//已退款
	
	
	
	//消息常量
	public static String MESSAGE_PERSON_FOCUS_ME = "1";  //私聊消息发送级别: 关注我的
	public static String MESSAGE_PERSON_BOTH_FOCUS = "2"; //私聊消息发送级别: 双向关注
	public static String MESSAGE_RECEIVED = "0"; //接收消息
	public static String MESSAGE_NOT_RECEIVED = "1"; //不接收消息
	
	public static String MSG_TYPE_ORDER = "1";  //订单消息
	public static String MSG_TYPE_SYSTEM = "2";  //系统消息
	
	public static String MSG_READ = "1";	//消息已读
	public static String MSG_NOT_READ = "0"; //消息未读
	
	//分组常量 
	public static String GROUP_TYPE_FOLLOW = "1";  // 关注分组
	public static String GROUP_TYPE_FANS = "2";  //粉丝分组
	public static String GROUP_TYPE_STU = "3";  //会员分组
	public static String GROUP_TYPE_COACH = "4";  // 教练分组
	
	//手机查看权限常量
	public static String PHONE_TO_BOTH_FOCUS = "1";  //双向关注
	public static String PHONE_TO_FOCUS_ME = "2";  //关注我的
	public static String PHONE_TO_ONLY_ME = "3";  //仅自己
	public static String PHONE_TO_ALL = "4";  //公开
	public static String PHONE_TO_COACH = "5";  //仅课程主管与其他私教可见
	public static String PHONE_TO_SUPERVISOR_COACH = "6";  //仅课程主管
	public static String PHONE_TO_MY_STU = "7";  //仅会员(名下和服务过的会员)
	
	//返回状态常量
	public static String STATE_OK = "200"; //状态正常
	public static String STATE_ERROR = "500";//通用错误
	public static String STATE_TOKEN_NULL = "501";//token为空
	public static String STATE_TOKEN_DEPRECATED = "502";//token已过时
	public static String STATE_DATA_NOT_EXIST = "503";//查无数据
	public static String STATE_DATA_PWD_NULL = "504";//没有设置支付密码
	public static String STATE_TIME_CONFLICT = "450";//时间冲突
	public static String STATE_FOLLOW_DUPLICATE = "451";//关注重复操作
	
	//课程常量
	public static String COURSE_WEIKAISHI = "0";  //未开始
	public static String COURSE_YIWANCHENG = "1"; //已完成
	public static String COURSE_DAISHANGKE = "2"; //待上课
	public static String COURSE_YUYUEZHONG = "3"; //预约中未接单
	
	//提现状态
	public static String WITHDRAW_STATUS_SHENQINGZHONG = "1";  //申请中
	public static String WITHDRAW_STATUS_CHULIZHONG = "2";  //银行处理中
	public static String WITHDRAW_STATUS_CHENGGONG = "3"; //提现成功
	public static String WITHDRAW_STATUS_SHIBAI = "4"; //提现失败
	public static String WITHDRAW_STATUS_YIQUXIAO = "5"; //已取消
	
	//打赏状态
	public static String REWARD_STATUS_DONGJIEZHONG = "1"; //冻结中
	public static String REWARD_STATUS_KESHIYONG = "2"; //可使用
	public static String REWARD_STATUS_YITUIHUI = "3"; //已退回
			
 	
	static {
		rc_appKey = loader.getProperty("rc_appKey");
		rc_appSecret = loader.getProperty("rc_appSecret");
		rongCloud = RongCloud.getInstance(rc_appKey, rc_appSecret);
		
		ALI_APPID = loader.getProperty("ALI_APPID");
		ALI_PRIVATE_KEY = loader.getProperty("ALI_PRIVATE_KEY");
		ALI_PUBLIC_KEY = loader.getProperty("ALI_PUBLIC_KEY");
		ALI_APP_PUBLIC_KEY = loader.getProperty("ALI_APP_PUBLIC_KEY");
		ALI_GATEWAYURL = loader.getProperty("ALI_GATEWAYURL");
		ALI_NOTIFY_URL = loader.getProperty("ALI_NOTIFY_URL");
		ALI_SIGN_TYPE = loader.getProperty("ALI_SIGN_TYPE");
		
		WX_APPID = loader.getProperty("WX_APPID");
		WX_SECRET = loader.getProperty("WX_SECRET");
		WX_MCH_ID = loader.getProperty("WX_MCH_ID");
		WX_NOTIFY_URL = loader.getProperty("WX_NOTIFY_URL");
		WX_REFUND_NOTIFY_URL = loader.getProperty("WX_REFUND_NOTIFY_URL");
		WX_CERT_PATH = loader.getProperty("WX_CERT_PATH");
		
		
	}
	
	
	
	

}

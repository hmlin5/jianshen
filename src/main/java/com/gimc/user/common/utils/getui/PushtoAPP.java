package com.gimc.user.common.utils.getui;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style6;
import com.gimc.user.common.config.Global;


public class PushtoAPP {    
    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
	private static String appId = Global.getConfig("AppID_GT");
    private static String appKey = Global.getConfig("AppKey_GT");
    private static String masterSecret = Global.getConfig("MasterSecret_GT");
	private static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	
	private static String TITLE = "";
	private static String CONTENT = "";
	private static String TOUCHUAN = "";

	public static void pushtoAPP(String title, String content, String touchuan){
		IGtPush push = new IGtPush(host, appKey, masterSecret);

		TITLE = title;
		CONTENT = content;
		TOUCHUAN = touchuan;
		
        TransmissionTemplate template = getTransmissionTemplate();
        
        //推送给APP全部用户
        AppMessage message = new AppMessage();
        message.setData(template);

        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        //推送给App的目标用户需要满足的条件
        AppConditions cdt = new AppConditions(); 
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(appId);
        message.setAppIdList(appIdList);
        //手机类型
//        List<String> phoneTypeList = new ArrayList<String>();
//        phoneTypeList.add("ANDROID");
//        phoneTypeList.add("APPLE");
        
        //省份
        //List<String> provinceList = new ArrayList<String>();
        //自定义tag
        //List<String> tagList = new ArrayList<String>();

//        cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
//        cdt.addCondition(AppConditions.REGION, provinceList);
//        cdt.addCondition(AppConditions.TAG,tagList);
        message.setConditions(cdt);
        
        IPushResult ret = push.pushMessageToApp(message,"任务别名_toApp"); 
        
        /**
        //指定CID推送
        SingleMessage message = new SingleMessage();
        message.setData(template);

        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId("e4295f7d80cb5dfc4973dabcf8a71cd4");
        
        IPushResult ret = push.pushMessageToSingle(message,target);*/
        
        System.out.println(ret.getResponse().toString());
	}
	
    public static void main(String[] args) throws Exception {
    	pushtoAPP("品金投资", "品金投资，你值得拥有", "386=====http://120.76.205.34:8055/pjsmwp/htcj/findTrainById?id=15=====每周展望");
    }

//    public static LinkTemplate linkTemplateDemo() throws Exception {
//        LinkTemplate template = new LinkTemplate();
//        template.setAppId(appId);
//        template.setAppkey(appKey);
//        template.setTitle(TITLE);
//        template.setText(CONTENT);
//        template.setLogo("push.png");
//        template.setLogoUrl("http://www.pjsmwp.com/images/push.png");
//        template.setIsRing(true);
//        template.setIsVibrate(true);
//        template.setIsClearable(true);
//        template.setUrl("http://www.baidu.com");
//
//        return template;
//    }
    
    public static NotificationTemplate notificationTemplateDemo() {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(TOUCHUAN);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("0");
        payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setCategory("$由客户端定义");
        //简单模式APNPayload.SimpleMsg 
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(CONTENT));
        template.setAPNInfo(payload);
        
        Style6 style = new Style6();
        // 设置通知栏标题与内容
        style.setTitle(TITLE);
        style.setText(CONTENT);
        // 配置通知栏图标
        style.setLogo("push.png");
        // 配置通知栏网络图标
        style.setLogoUrl("http://www.pjsmwp.com/images/push.png");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        
        template.setStyle(style);

        return template;
    }
    
    public static TransmissionTemplate getTransmissionTemplate() {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionType(2);
        template.setTransmissionContent(TOUCHUAN);
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("0");
        payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setCategory("$由客户端定义");
        //简单模式APNPayload.SimpleMsg 
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(CONTENT));
        //字典模式使用下者
        //payload.setAlertMsg(getDictionaryAlertMsg());
        template.setAPNInfo(payload);
        return template;
    }

}
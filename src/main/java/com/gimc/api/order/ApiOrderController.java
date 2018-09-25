package com.gimc.api.order;

import java.io.BufferedReader;
import java.io.Writer;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.gimc.user.common.persistence.BaseEntity;
import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.common.utils.burningUtil.AESUtil;
import com.gimc.user.common.utils.burningUtil.HttpUtil;
import com.gimc.user.common.utils.burningUtil.OrderUtil;
import com.gimc.user.common.utils.burningUtil.PageBean;
import com.gimc.user.common.utils.burningUtil.RequestMap;
import com.gimc.user.common.utils.burningUtil.ResultMap;
import com.gimc.user.common.web.BaseController;
import com.gimc.user.modules.b_comment.entity.Comment;
import com.gimc.user.modules.b_comment.service.CommentService;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_course.entity.Course;
import com.gimc.user.modules.b_course.service.CourseService;
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_gym.service.GymService;
import com.gimc.user.modules.b_movement.service.MovementService;
import com.gimc.user.modules.b_msg.entity.Msg;
import com.gimc.user.modules.b_msg.service.MsgService;
import com.gimc.user.modules.b_order.entity.Order;
import com.gimc.user.modules.b_order.service.OrderService;
import com.gimc.user.modules.b_pay_info.entity.PayInfo;
import com.gimc.user.modules.b_pay_info.service.PayInfoService;
import com.gimc.user.modules.b_rest_time.entity.RestTime;
import com.gimc.user.modules.b_rest_time.service.RestTimeService;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user.service.AppUserService;
import com.gimc.user.modules.b_user_account.entity.UserAccount;
import com.gimc.user.modules.b_user_account.service.UserAccountService;
import com.gimc.user.modules.b_user_course.entity.UserCourse;
import com.gimc.user.modules.b_user_course.service.UserCourseService;
import com.gimc.user.modules.b_user_gym.entity.UserGym;
import com.gimc.user.modules.b_user_gym.service.UserGymService;
import com.gimc.user.modules.b_user_movement.service.UserMovementService;
import com.gimc.user.modules.b_user_test.service.UserTestService;

import wxpay.MyConfig;
import wxpay.WXPay;
import wxpay.WXPayUtil;

@Controller
@RequestMapping("/api/order")
public class ApiOrderController extends BaseController{

	
	@Autowired
	private AppUserService userService;
	@Autowired
	private UserTestService userTestService;
	@Autowired
	private GymService gymService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private MovementService movementService;
	@Autowired
	private UserCourseService userCourseService;
	@Autowired
	private UserMovementService userMovementService;
	@Autowired
	private UserGymService userGymService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private RestTimeService restTimeService;
	@Autowired
	private MsgService msgService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private PayInfoService payInfoService;
	@Autowired
	private UserAccountService userAccountService;

	
	
	
	
	/**
	 * 微信支付结果异步通知
	 * @param param
	 * @param request
	 */
	@RequestMapping("/wxRefundNotify")
	public void wxRefundNotify(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("wxRefundNotify=============================");
		
		 BufferedReader reader = null;
		Writer writer = null;
		
         
		try {


			  reader = request.getReader();
	          String line = "";
	          String xmlString = null;
	          StringBuffer inputString = new StringBuffer();
	          while ((line = reader.readLine()) != null) {
	              inputString.append(line);
	          }
	          
	          xmlString = inputString.toString();// 支付结果通知的xml格式数据
	          request.getReader().close();
	          
	          System.out.println("退款通知的xml格式数据============================="+xmlString);
			
	          
	          Map<String, String> notifyMap = WXPayUtil.xmlToMap(xmlString);  // 转换成map
	          
	          String return_code = notifyMap.get("return_code");//此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	          
	          
	          if ("SUCCESS".equalsIgnoreCase(return_code)) {
				
	        	  String req_info = notifyMap.get("req_info");
	        	  
	        	  String decryptData = AESUtil.decryptData(req_info);
	        	  
	        	  System.out.println("解密后的通知数据==============="+decryptData);
	        	  
	        	  Map<String, String> infoMap = WXPayUtil.xmlToMap(decryptData);  // 转换成map
	        	  String refund_status = infoMap.get("refund_status");//退款状态 SUCCESS-退款成功  CHANGE-退款异常  REFUNDCLOSE—退款关闭
	        	  if ("SUCCESS".equalsIgnoreCase(refund_status)) {
	        		  //TODO 处理业务(暂时不做,认为不影响退款操作, 因为如果异步通知有问题, 很有可能就是微信端的问题,目前为直接在发起微信退款申请时做订单退款处理,如果这里做退款业务处理,需要发起退款申请时改为不做处理)
	        		  String transaction_id = infoMap.get("transaction_id");//微信支付订单号
	        		  String out_trade_no = infoMap.get("out_trade_no");//订单号
	        		  String total_fee = infoMap.get("total_fee");//总金额,单位为分
	        		  double addFee = Double.parseDouble(total_fee)/100;
	        		  
	        	  }
	        	 
	        	  String result = "<xml>"
							+"<return_code><![CDATA[SUCCESS]]></return_code>"
							+"<return_msg><![CDATA[OK]]></return_msg>"
							+"</xml>";
			
					writer = response.getWriter();
					writer.write(result);
	        	  
	          }
	          
	          
		     
				
				
			
		        
		      
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
			
		
		
		
	}
	
	
	
	
	
	
	
	/**
	 * 微信支付结果异步通知
	 * @param param
	 * @param request
	 */
	@RequestMapping("/wxPayNotify")
	public void wxPayNotify(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("wxPayNotify=============================");
		
		 BufferedReader reader = null;
		Writer writer = null;
		
          
		try {


			  reader = request.getReader();
	          String line = "";
	          String xmlString = null;
	          StringBuffer inputString = new StringBuffer();
	          while ((line = reader.readLine()) != null) {
	              inputString.append(line);
	          }
	          
	          xmlString = inputString.toString();// 支付结果通知的xml格式数据
	          request.getReader().close();
	          
	          System.out.println("支付结果通知的xml格式数据============================="+xmlString);
			
				
			
		      /*  MyConfig config = new MyConfig();
		        WXPay wxpay = new WXPay(config);*/

		        Map<String, String> notifyMap = WXPayUtil.xmlToMap(xmlString);  // 转换成map

		        String return_code = notifyMap.get("return_code");//此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
		        String result_code = notifyMap.get("result_code");//业务结果
		        //TODO 暂时不验签. app没法根据后台生成的签名sign拉起微信(注:app端对以前的项目也有同样问题, 把sign换成随机字符串就可以拉起微信)
		        /*  if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
		            // 签名正确
		            // 进行处理。
		            // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
		        	
		        	System.out.println("签名正确========================");
		        }
		        else {
		            // 签名错误，如果数据里没有sign字段，也认为是签名错误
		        	System.out.println("签名错误========================");
		        }*/
		        if (!"SUCCESS".equalsIgnoreCase(return_code) || !"SUCCESS".equalsIgnoreCase(result_code)) {
					return ;
				}
		        
		        
		        String transaction_id = notifyMap.get("transaction_id");//微信支付订单号
		        String out_trade_no = notifyMap.get("out_trade_no");//订单号
		        String total_fee = notifyMap.get("total_fee");//总金额,单位为分
		        
		        double addFee = Double.parseDouble(total_fee)/100;
		        
		        PayInfo payInfo = new PayInfo();
				
				
				//修改订单的信息
				Order order = orderService.findByOrderNum(out_trade_no);
				if (order!=null ) {
					PayInfo payInfoDb = payInfoService.findByOrderNum(order.getOrderNum());
					if (payInfoDb == null) {
						order.setAddFee(addFee);
						orderService.save(order);
						
						try {
							payInfo.setWxTradeNo(transaction_id);
							payInfo.setOrderNum(out_trade_no);
							payInfo.setPayWay(CommonParam.PAY_BY_WX);
							if (StringUtils.isNotBlank(order.getStuId())) {
								payInfo.setStuId(order.getStuId());
								payInfo.setStuName(order.getStuName());
							}
							if (StringUtils.isNotBlank(order.getCoachId())) {//有可能为空, 
								payInfo.setCoachId(order.getCoachId());
								payInfo.setCoachName(order.getCoachName());
								
								//更新教练账户余额
								userAccountService.reward(order.getCoachId(), addFee);
								
								
							}
							payInfo.setPayflag(CommonParam.PAY_YES);
							payInfo.setRefundflag(CommonParam.REFUND_NO);
							payInfo.setPayTime(new Date());
							payInfo.setPayAmount(addFee);
							payInfo.setStatus(CommonParam.REWARD_STATUS_DONGJIEZHONG);
							
							payInfoService.save(payInfo);
							
							String result = "<xml>"
											+"<return_code><![CDATA[SUCCESS]]></return_code>"
											+"<return_msg><![CDATA[OK]]></return_msg>"
											+"</xml>";
							
							writer = response.getWriter();
							writer.write(result);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					
					
				}
		        
		        
		        
		      
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
			
		
		
		
	}
	
	
	
	
	/**
	 * 创建微信支付订单
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/createWxPayOrder")
	public ResultMap createWxPayOrder(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		
		ResultMap map = new ResultMap();
		
		String orderId = param.getOrderId();
		Double addFee = param.getAddFee();
		if (StringUtils.isBlank(orderId) || addFee == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		Order order = orderService.get(orderId);
		if (order == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("无该订单");
		}
		PayInfo payInfo = payInfoService.findByOrderNum(order.getOrderNum());
		if ( payInfo!=null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("你已支付过感谢费, 无须重复支付");
		}
		
		
         String message = "";
         try {
            //   String mch_id = CommonParam.WX_MCH_ID;   //商户号
               String notify_url = CommonParam.WX_NOTIFY_URL; //通知地址
//               String appid = CommonParam.WX_APPID;        //应用ID
//               String App_Secret = CommonParam.WX_SECRET;
//               String nonce_str = WXPayUtil.generateNonceStr();            //随机字符串
//               String sign = "";                   //签名
//               String sign_type = "MD5";                       //签名类型
               String body = "教练来了-感谢费";                     //商品描述 ps:腾讯充值中心-QQ会员充值
               String out_trade_no = order.getOrderNum();         //商户订单号
               int total_fee =(int) (addFee * 100); //交易金额,单位为分
               String spbill_create_ip = InetAddress.getLocalHost().getHostAddress(); //终端IP
               String trade_type = "APP";          //交易类型
              // String attach = userId + "|" + bean.getBank_id();      //附加数据 ps:用户|支付方式
             /*  
               StringBuffer sb = new StringBuffer();
               sb.append("appid=").append(appid).append("&");
             //  sb.append("attach=").append(attach).append("&");
               sb.append("body=").append(body).append("&");
               sb.append("mch_id=").append(mch_id).append("&");
               sb.append("notify_url=").append(notify_url).append("&");
               sb.append("nonce_str=").append(nonce_str).append("&");
               sb.append("out_trade_no=").append(out_trade_no).append("&");
               sb.append("sign_type=").append(sign_type).append("&");
               sb.append("spbill_create_ip=").append(spbill_create_ip).append("&");
               sb.append("total_fee=").append(total_fee).append("&");
               sb.append("trade_type=").append(trade_type).append("&");
               String params = sb.toString();
               //需要签名的数据
               String stringSignTemp = params + "&key=" + App_Secret;
               //MD5签名方式
               sign = WXPayUtil.MD5(stringSignTemp).toUpperCase();
               Map<String,String> m = new HashMap<String, String>();
               m.put("appid", appid);
              // m.put("attach", attach);
               m.put("body", body);
               m.put("mch_id", mch_id);
               m.put("notify_url", notify_url);
               m.put("nonce_str", nonce_str);
               m.put("out_trade_no", out_trade_no);
               m.put("sign", sign);
               m.put("sign_type", sign_type);
               m.put("spbill_create_ip", spbill_create_ip);
               m.put("total_fee", total_fee+"");
               m.put("trade_type", trade_type);*/
               
               MyConfig config = new MyConfig();
               WXPay wxpay = new WXPay(config);//部分参数设置在MyConfig类

               Map<String, String> data = new HashMap<String, String>();
               data.put("body", body);
               data.put("notify_url", notify_url);
               data.put("out_trade_no", out_trade_no);
               data.put("spbill_create_ip", spbill_create_ip);
               data.put("total_fee", total_fee+"");
               data.put("trade_type", trade_type);  
               //data.put("sign", sign);  
              // data.put("product_id", "12");

               
               Map<String, String> resp = wxpay.unifiedOrder(data);
               
               System.out.println("签名sign======================"+resp.get("sign"));
               System.out.println("随机数字符串======================"+resp.get("nonce_str"));
               
               //解析返回的XML数据
               if(!resp.isEmpty() && resp.get("return_code").equals("SUCCESS")){
                     if(resp.get("result_code").equals("SUCCESS")) {
                          System.out.println("InfoMsg:--- 微信统一下单请求交易成功");
                          return map.setState(CommonParam.STATE_OK).setData(resp);
                          
                     }else {
                           message = resp.get("err_code_des");
                           System.out.println("errorMsg:--- 微信统一下单请求交易解析失败" + message);
                           return map.setState(CommonParam.STATE_ERROR).setMsg("下单失败...");
                     }
               }else {
                     System.out.println("errorMsg:--- 微信统一下单请求交易解析失败" + message);
                     return map.setState(CommonParam.STATE_ERROR).setMsg("下单失败...");
               }
              
         } catch (Exception e) {
               System.out.println("errorMsg:--- 微信统一下单请求交易失败" + e.getMessage());
               return map.setState(CommonParam.STATE_ERROR).setMsg("下单失败...");         
          }

		
		
		
	}
	
	
	
	
	/**
	 * 搜索订单
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/searchOrder")
	public ResultMap searchOrder(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		
		String userType = param.getUserType();
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		String keyword = param.getKeyword();
		if (StringUtils.isBlank(userType) || StringUtils.isBlank(keyword) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
	
		List<Order> list = null;
		
		if (CommonParam.USER_TYPE_JIAOLIAN.equals(userType)) {//教练订单
			Gym gym = userGymService.getGymByCoachId(user);
			if (gym == null) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("系统异常");
			}
			
			Order order = new Order();
			order.setOrNullFlag(true); 
			order.setCoachId(user.getId());
			order.setGymId(gym.getId());
			
			order.setStuName(keyword);
			order.setClassName(keyword);
			
			list = orderService.searchOrder(order);
			
			
		}else if (CommonParam.USER_TYPE_SIJIAO.equals(userType)) {//私教会员订单
				Order order = new Order();
				order.setStuId(user.getId());
				order.setContainFlag(true);
				order.setAllCheckFlag(true);
				
				order.setCoachName(keyword);
				order.setGymName(keyword);
				order.setClassName(keyword);
				
				list = orderService.searchOrder(order);
			
		}else{
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求用户类型有误");
		}
		
		
		PageBean<Order> pageBean = new PageBean<Order>();
		if (list!=null && list.size()>0) {
			pageBean = new PageBean<Order>(currentPage, pageSize, list.size());
			pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
			
		}
		return map.setState(CommonParam.STATE_OK).setData(pageBean);
			
		
	}
	
	
	/**
	 * 测试支付宝退款
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/aliRefund")
	public ResultMap aliRefund(RequestMap param, HttpServletRequest request){
		ResultMap map = new ResultMap();
		
		String orderId = param.getOrderId();
		Order order = orderService.get(orderId);
		
		PayInfo payInfo = payInfoService.findByOrderNum(order.getOrderNum());
		
		boolean aliRefund = orderService.aliRefund(order, payInfo);
		
		
		return map.setData(aliRefund);
	}
	
	/**
	 * 测试微信退款
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/wxRefund")
	public ResultMap wxRefund(RequestMap param, HttpServletRequest request){
		ResultMap map = new ResultMap();
		
		String orderId = param.getOrderId();
		Order order = orderService.get(orderId);
		
		PayInfo payInfo = payInfoService.findByOrderNum(order.getOrderNum());
		
		boolean wxRefund = orderService.wxRefund(order, payInfo);
		
		
		return map.setData(wxRefund);
	}
	
	
	
	/**
	 * 创建支付宝支付订单
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/createAliPayOrder")
	public ResultMap createAliPayOrder(RequestMap param, HttpServletRequest request){
		ResultMap map = new ResultMap();
		
		String orderId = param.getOrderId();
		Double addFee = param.getAddFee();
		if (StringUtils.isBlank(orderId) || addFee == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		Order order = orderService.get(orderId);
		if (order == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("无该订单");
		}
		PayInfo payInfo = payInfoService.findByOrderNum(order.getOrderNum());
		if ( payInfo!=null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("你已支付过感谢费, 无须重复支付");
		}
		
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(CommonParam.ALI_GATEWAYURL, CommonParam.ALI_APPID, CommonParam.ALI_PRIVATE_KEY, "json", "utf-8", CommonParam.ALI_APP_PUBLIC_KEY, CommonParam.ALI_SIGN_TYPE);
		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest req = new AlipayTradeAppPayRequest();
		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("感谢费");
		model.setSubject("教练来了订单支付");
		model.setOutTradeNo(order.getOrderNum());
		model.setTimeoutExpress("30m");
		model.setTotalAmount(addFee+"");
		model.setProductCode("QUICK_MSECURITY_PAY");
		req.setBizModel(model);
		req.setNotifyUrl(CommonParam.ALI_NOTIFY_URL);
		try {
		        //这里和普通的接口调用不同，使用的是sdkExecute
		        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(req);
		        System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
		        
		        //TODO 保存订单消息, 支付宝的订单号, 退款用
		      //  order.setAddFee(addFee);
		      //  orderService.save(order);
		        
		        
		        return map.setState(CommonParam.STATE_OK).setData(response);
		    } catch (AlipayApiException e) {
		        e.printStackTrace();
		        return map.setState(CommonParam.STATE_ERROR).setData("服务器忙...");
		}
		
		
		
	}
	
	/**
	 * 支付宝异步通知
	 * @param param
	 * @param request
	 */
	@RequestMapping("/aliPayNotify")
	public void aliPayNotify(RequestMap param, HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("aliPayNotify=============================");
		
		Writer writer = null;
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    String valueStr = "";
		    for (int i = 0; i < values.length; i++) {
		        valueStr = (i == values.length - 1) ? valueStr + values[i]
		                    : valueStr + values[i] + ",";
		  	}
		    //乱码解决，这段代码在出现乱码时使用。
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		try {
			
			boolean flag = AlipaySignature.rsaCheckV1(params, CommonParam.ALI_PUBLIC_KEY, "utf-8",CommonParam.ALI_SIGN_TYPE);
			
			if (flag) {
				
				
				
				PayInfo payInfo = new PayInfo();
				
				String trade_no = params.get("trade_no"); // 支付宝交易号
				String out_trade_no = params.get("out_trade_no"); //商户订单号
				String trade_status = params.get("trade_status"); //交易状态
				String total_amount = params.get("total_amount"); //订单金额
				String refund_fee = params.get("refund_fee"); //总退款金额
				
				//修改订单的信息
				Order order = orderService.findByOrderNum(out_trade_no);
				if (order!=null ) {
					PayInfo payInfoDb = payInfoService.findByOrderNum(order.getOrderNum());
					if (payInfoDb == null) {
						order.setAddFee(Double.parseDouble(total_amount));
						orderService.save(order);
						
						try {
							payInfo.setAliTradeNo(trade_no);
							payInfo.setOrderNum(out_trade_no);
							payInfo.setPayWay(CommonParam.PAY_BY_ALI);
							if (StringUtils.isNotBlank(order.getStuId())) {
								payInfo.setStuId(order.getStuId());
								payInfo.setStuName(order.getStuName());
							}
							if (StringUtils.isNotBlank(order.getCoachId())) {//有可能为空, 
								payInfo.setCoachId(order.getCoachId());
								payInfo.setCoachName(order.getCoachName());
								
								//更新教练账户余额
								userAccountService.reward(order.getCoachId(), Double.parseDouble(total_amount));
								
								
							}
							payInfo.setPayflag(CommonParam.PAY_YES);
							payInfo.setRefundflag(CommonParam.REFUND_NO);
							payInfo.setPayTime(new Date());
							payInfo.setPayAmount(Double.parseDouble(total_amount));
							payInfo.setStatus(CommonParam.REWARD_STATUS_DONGJIEZHONG);
							
							payInfoService.save(payInfo);
							
							
							
							writer = response.getWriter();
							writer.write("success");
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					
					
					System.out.println("success");
				}
				
				
				
				
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
			
		
		
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 会员抢课
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/robCourse")
	public ResultMap robCourse(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		String orderId = param.getOrderId();



		if (StringUtils.isBlank(orderId) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
	
		Order order = orderService.get(orderId);
		UserGym userGym=new UserGym();
		userGym.setUserId(user.getId());
		userGym.setGymId(order.getGymId());
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		List<UserGym> userGymList= userGymService.findList(userGym);
		if(userGymList!=null&&userGymList.size()>0){
			if(userGymList.get(0).getFreezeFlag().equals("1")){
				return map.setState(CommonParam.STATE_ERROR).setMsg("该用户被冻结");
			}else if(userGymList.get(0).getRestNumber()==null||userGymList.get(0).getRestNumber()==0){
                return map.setState(CommonParam.STATE_ERROR).setMsg("剩余课程0节");
            }
		}else{
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
		}

		if (!CommonParam.ORDER_STATUS_DAIQUEREN.equals(order.getStatus())) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("已被其他会员抢先,订单已失效"); 
		}
		
		UserCourse userCourse = userCourseService.findCourseByFatherCourse(user.getId(),order.getCourseId());
		if (userCourse == null || "1".equals(userCourse.getDelFlag())) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("该课程已不存在,请选择其他课程");
		}
		
		//判断学员在约课时间是否有空
		String duration = userCourse.getDuration().longValue()+"";
		RestTime condition = new RestTime();
		condition.setBeginTime(order.getAppointmentTime());
		condition.setDuration(duration);
		condition.setUserId(user.getId());
		boolean checkTimeConflict = restTimeService.checkTimeConflict(condition);
		if (checkTimeConflict) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("您在选课时间已有其他安排,请另选时间");
		}
		
		
		
		
		order.setStuId(user.getId());
		order.setStuName(user.getRealName());
		order.setRobTime(new Date());
		order.setStatus(CommonParam.ORDER_STATUS_DAISHANGKE);
		order.setStuPhone(user.getPhone());
		order.setStuSex(user.getSex());
		order.setStuImgUrl(user.getHeadImgUrl());
		order.setCourseId(userCourse.getId());
		
		int result = orderService.updateRobOrder(order);
		if (result == 1) {
			
			//TODO 发消息
			
			//更新学员课程状态 
			userCourse.setFinishFlag(CommonParam.COURSE_DAISHANGKE);
			userCourseService.save(userCourse);


			UserGym userGym1=userGymList.get(0);//扣除上课数
			userGym1.setRestNumber(userGym1.getRestNumber()-1);
			userGymService.update(userGym1);


			//发送消息
			AppUser coach = userService.get(order.getCoachId());

			msgService.sendRobStu(user,coach,order,userCourse);
			//发短信
			userService.sendAppointment(order);
			
			return map.setState(CommonParam.STATE_OK).setMsg("抢课成功");
			
		}else {
			return map.setState(CommonParam.STATE_ERROR).setMsg("已被其他会员抢先,订单已失效");
		}
		
		
	}
	
	
	/**
	 * 教练一对多约课
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/generateInviteStusOrder")
	public ResultMap generateInviteStusOrder(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		String courseId = param.getCourseId();
		String userIds = param.getUserIds();
		Date date = param.getDate();
		AppUser user = (AppUser) request.getAttribute("user");
		if (StringUtils.isBlank(courseId) || StringUtils.isBlank(userIds) || date == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		Date nowTime = new Date();
		if (nowTime.getTime()>=date.getTime()) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("预约时间不能小于当前时间");
		}
		
		//获取教练所在健身房
		Gym gym = userGymService.getGymByCoachId(user);
		if (gym == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("您暂无绑定健身房");
		}
		
		//获取母课程
		Course course = courseService.get(courseId);
		if (course == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("不存在该课程");
		}
		
		//获取所有会员的姓名,用","隔开
		String stuNames = "";
		AppUser au = new AppUser();
		au.setActivateFlag(CommonParam.ACTIVE_YES);
		String[] idArr = userIds.split(",");
		List<String> idList = Arrays.asList(idArr);
		au.setIncludeIds(idList);
		List<AppUser> users = userService.findList(au);
		for (AppUser u : users) {
			stuNames += u.getRealName()+",";
		}
		stuNames = stuNames.substring(0, stuNames.lastIndexOf(','));
		
		//生成订单
		OrderUtil orderUtil = new OrderUtil();
		String oderNum = orderUtil.makeOrderNum(null);



		Order order = new Order();
		UserGym userGym=new UserGym();
		userGym.setGymId(gym.getId());
		userGym.setUserId(idList.get(0));
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		List<UserGym> userGymList= userGymService.findList(userGym);
		if(userGymList!=null&&userGymList.size()>0){
			order.setMemberPrice(userGymList.get(0).getUnitPrice());
		}
		userGym.setGymId(gym.getId());
		userGym.setUserId(user.getId());
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		List<UserGym> userGymList2= userGymService.findList(userGym);
		if(userGymList2!=null&&userGymList2.size()>0){
			if(userGymList2.get(0).getCommissionRatio()!=null){
				order.setCoachProportion(userGymList2.get(0).getCommissionRatio().toString());
			}

		}

		order.setIsGym("0");
		order.setOrderNum(oderNum);
		order.setGymId(gym.getId());
		order.setStuIds(userIds);
		order.setStuNames(stuNames);
		order.setCoachId(user.getId());
		order.setCoachName(user.getRealName());
		order.setCoachImgUrl(user.getHeadImgUrl());
		order.setCoachSex(user.getSex());
		order.setType(CommonParam.ORDER_TYPE_ANPAI);
		order.setAppointmentTime(date);
		order.setClassName(course.getName());
		order.setCourseId(courseId);//父课程id
		order.setCourseDuration(course.getDuration()+"");
		order.setVersion(1);
		order.setStatus(CommonParam.ORDER_STATUS_DAIQUEREN);
		order.setGymName(gym.getName());
		order.setIsConfirm(CommonParam.ORDER_CONFIRM_NO);
		order.setCanEvaluate(CommonParam.ORDER_CAN_ENVALUATE_YES);
		
		orderService.save(order);
		
		
		//发送"教练排课"通知
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (String stuId : idArr) {
				String nickName = user.getRealName()==null?CommonParam.USER_DEFAULT_NICKNAME : user.getRealName();
				String content = "您的教练\""+nickName+"\"约您于"+sdf.format(date)+"到健身房上课-"+course.getName()+"，快去看看吧,查看路径：订单-待接单";
				
				msgService.sendSystem(stuId, content);
				
				Msg msg = new Msg();
				
				msg.setContent(content);
				msg.setTitle("\"教练排课\"通知");
				msg.setToUserId(stuId);
				msg.setFromUserId("1");
				//msg.setToUserName(u.getNickName()!=null?u.getNickName():CommonParam.USER_DEFAULT_NICKNAME);
				msg.setFromUserName("系统");
				msg.setIsRead(CommonParam.MSG_NOT_READ);
				msg.setToUserType(CommonParam.USER_TYPE_SIJIAO);
				msg.setMsgType(CommonParam.MSG_TYPE_ORDER);
				
				msgService.save(msg);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙...");
		}
		
		
		order.setGym(gym);
		return map.setState(CommonParam.STATE_OK).setData(order);
	}
	
	
	
	
	
	/**
	 * 会员接受一对一排课
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/acceptAnpaiOrder")
	public ResultMap acceptAnpaiOrder(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		try {
			AppUser user = (AppUser) request.getAttribute("user");


			String orderId = param.getOrderId();
			String gymId = param.getGymId();
			if (StringUtils.isBlank(orderId) || StringUtils.isBlank(gymId) ) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
			}
			UserGym userGym=new UserGym();
			userGym.setUserId(user.getId());
			userGym.setGymId(param.getGymId());
			userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
			List<UserGym> userGymList= userGymService.findList(userGym);
			if(userGymList!=null&&userGymList.size()>0){
				if(userGymList.get(0).getFreezeFlag().equals("1")){
					return map.setState(CommonParam.STATE_ERROR).setMsg("该用户被冻结");
				}else if(userGymList.get(0).getRestNumber()==null||userGymList.get(0).getRestNumber()==0){
                    return map.setState(CommonParam.STATE_ERROR).setMsg("剩余课程0节");
                }
			}else{
				return map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
			}

			Order order = orderService.get(orderId);
			if (order == null || "1".equals(order.getDelFlag())) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("该订单不存在"); 
			}
			if (!CommonParam.ORDER_STATUS_DAIQUEREN.equals(order.getStatus()) ) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("请检查订单状态"); 
			}
			
			UserCourse userCourse = userCourseService.get(order.getCourseId());
			if (userCourse == null || "1".equals(userCourse.getDelFlag())) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("该课程已不存在,请选择其他课程"); 
			}
			
			//判断预约人是否有空 
			String duration = userCourse.getDuration().longValue()+"";
			RestTime condition = new RestTime();
			condition.setBeginTime(order.getAppointmentTime());
			condition.setGymId(gymId);
			condition.setDuration(duration);
			condition.setUserId(user.getId());
			boolean checkTimeConflict = restTimeService.checkTimeConflictExcludeOrder(condition, orderId);
			if (checkTimeConflict) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("您在选课时间已有其他安排,请另选时间");
			}

			UserGym userGym1=userGymList.get(0);//扣除上课数
			userGym1.setRestNumber(userGym1.getRestNumber()-1);
			userGymService.update(userGym1);

			
			order.setStatus(CommonParam.ORDER_STATUS_DAISHANGKE);
			orderService.save(order);
			
			//更新课程状态
			UserCourse uc = userCourseService.get(order.getCourseId());
			uc.setFinishFlag(CommonParam.COURSE_DAISHANGKE);
			userCourseService.save(uc);
			
			Gym gym = gymService.get(gymId);
			order.setGym(gym);
			
			//发送消息
			AppUser coach = userService.get(order.getCoachId());
			msgService.sendAcceptCourse(user, coach, order, uc);
			//发短信
			userService.sendAppointment(order);
			
			return map.setState(CommonParam.STATE_OK).setData(order);
		} catch (Exception e) {
			e.printStackTrace();
			return map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙");
		}
	
	
	}
	
	
	
	
	/**
	 * 会员拒绝一对一排课
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/refuseAnpaiOrder")
	public ResultMap refuseAnpaiOrder(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
	
		String orderId = param.getOrderId();
		String gymId = param.getGymId();
		if (StringUtils.isBlank(orderId) || StringUtils.isBlank(gymId) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		Order order = orderService.get(orderId);
		if (!CommonParam.ORDER_STATUS_DAIQUEREN.equals(order.getStatus()) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请检查订单状态"); 
		}
		
		
		order.setCancelor(user.getId());
		order.setCancelTime(new Date());
		order.setStatus(CommonParam.ORDER_STATUS_YIQUXIAO);
		orderService.save(order);
		
		//更新课程状态
		/*UserCourse uc = userCourseService.get(order.getCourseId());
		uc.setFinishFlag("0");
		userCourseService.save(uc);*/
		
		//发送消息
		AppUser coach = userService.get(order.getCoachId());
		UserCourse userCourse = userCourseService.get(order.getCourseId());
		msgService.sendRefuseCourse(user,coach,order,userCourse);
		
		Gym gym = gymService.get(gymId);
		order.setGym(gym);
		
		return map.setState(CommonParam.STATE_OK).setData(order);
	
	
	}
	
	
	
	/**
	 * 生成教练一对一排课订单
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/generateAnpaiOrder")
	public ResultMap generateAnpaiOrder(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
	
		//String gymId = param.getGymId();
			Date date = param.getDate();
			String courseId = param.getCourseId();
			String userId = param.getUserId();
			
			if (date == null || StringUtils.isBlank(courseId) || StringUtils.isBlank(userId)) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("必传参数为空");
			}
			Date nowTime = new Date();
			if (nowTime.getTime()>=date.getTime()) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("预约时间不能小于当前时间");
			}
			AppUser stu = userService.get(userId);
			if (stu==null) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("预约学员不存在");
			}
			
			try {
				
				UserCourse userCourse = userCourseService.get(courseId);
				if (userCourse == null || BaseEntity.DEL_FLAG_DELETE.equals(userCourse.getDelFlag())) {
					return map.setState(CommonParam.STATE_ERROR).setMsg("预约课程不存在");
				}
				String gymId = userCourse.getGymId();
				
				List<String> gymCoachList = userGymService.findCoachListByGymId(gymId);
				if (!gymCoachList.contains(user.getId())) {
					return map.setState(CommonParam.STATE_ERROR).setMsg("您不在该健身房,无法约课");
				}
				
				
				//检查该课程是否已经预约过
				Order order2 = new Order();
				order2.setCourseId(courseId);
				List<String> excludeStatus = new ArrayList<String>();
				excludeStatus.add(CommonParam.ORDER_STATUS_YIQUXIAO);
				order2.setExcludeStatus(excludeStatus);
				List<Order> orderList = orderService.findList(order2);
				if (orderList!=null && orderList.size()>0) {
					return map.setState(CommonParam.STATE_ERROR).setMsg("该课程已被预约,请选择其他课程");
				}
				
				
				//判断教练在约课时间是否有空 
				String duration = userCourse.getDuration().longValue()+"";
				RestTime condition = new RestTime();
				condition.setBeginTime(date);
				condition.setGymId(gymId);
				condition.setDuration(duration);
				condition.setUserId(user.getId());
				boolean checkTimeConflict = restTimeService.checkTimeConflict(condition);
				if (checkTimeConflict) {
					return map.setState(CommonParam.STATE_ERROR).setMsg("您在选课时间已有其他安排,请另选时间");
				}
				
				//判断学员是否有空
				condition.setUserId(userId);
				if (restTimeService.checkTimeConflict(condition)) {
					return map.setState(CommonParam.STATE_ERROR).setMsg("学员在选课时间已有其他安排,请另选时间");
				}
				
				
				
				//生成订单
				OrderUtil orderUtil = new OrderUtil();
				String oderNum = orderUtil.makeOrderNum(null);



				Order order = new Order();
				UserGym userGym=new UserGym();
				userGym.setGymId(gymId);
				userGym.setUserId(userId);
				userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
				List<UserGym> userGymList= userGymService.findList(userGym);
				if(userGymList!=null&&userGymList.size()>0){
					order.setMemberPrice(userGymList.get(0).getUnitPrice());
				}
				userGym.setGymId(gymId);
				userGym.setUserId(user.getId());
				userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
				List<UserGym> userGymList2= userGymService.findList(userGym);
				if(userGymList2!=null&&userGymList2.size()>0){
					if(userGymList2.get(0).getCommissionRatio()!=null){
						order.setCoachProportion(userGymList2.get(0).getCommissionRatio().toString());
					}

				}
				order.setOrderNum(oderNum);
				order.setIsGym("0");
				order.setGymId(gymId);
				order.setStuId(userId);
				order.setStuName(stu.getRealName());
				order.setStuPhone(stu.getPhone());
				order.setStuImgUrl(stu.getHeadImgUrl());
				order.setStuSex(stu.getSex());
				order.setStuIds(userId);
				order.setCoachId(user.getId());
				order.setCoachPhone(user.getPhone());
				order.setCoachName(user.getRealName());
				order.setCoachImgUrl(user.getHeadImgUrl());
				order.setCoachSex(user.getSex());
				order.setType(CommonParam.ORDER_TYPE_ANPAI);
				order.setAppointmentTime(date);
				order.setClassName(userCourse.getName());
				order.setCourseDuration(userCourse.getDuration()+"");
				order.setCourseId(userCourse.getId());
				order.setVersion(1);
				order.setStatus(CommonParam.ORDER_STATUS_DAIQUEREN);
				order.setIsConfirm(CommonParam.ORDER_CONFIRM_NO);
				order.setCanEvaluate(CommonParam.ORDER_CAN_ENVALUATE_YES);
				
				Gym gymDb = gymService.get(gymId);
				order.setGymName(gymDb.getName());
				order.setGym(gymDb);
			
				orderService.save(order);
				
				
				//发系统消息
				msgService.sendAnPai(stu,user,order,userCourse);
				
				
				return map.setState(CommonParam.STATE_OK).setData(order);
				
			} catch (Exception e) {
				e.printStackTrace();
				return map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙");
			}
			
	
	}
	
	
	
	
	/**
	 * 回复评论
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/commentReply")
	public ResultMap commentReply(Comment param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		String id = param.getId();
		String reply = param.getReply();
		if (StringUtils.isBlank(id) || StringUtils.isBlank(reply)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		Comment comment = commentService.get(id);
		if (!user.getId().equals(comment.getCoachId())) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("您没有回复的资格");
		}
		
		comment.setReply(reply);
		comment.setReplyTime(new Date());
		
		commentService.save(comment);
		
		//发送评价回复通知
		AppUser toUser = userService.get(comment.getStuId());
		if (toUser!=null) {
			msgService.sendCommentReply(toUser, user, comment);
		}
	
		
		
		return map.setState(CommonParam.STATE_OK).setData(comment);
				
	}
	
	
	
	
	/**
	 * 查看评论
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/commentDetail")
	public ResultMap commentDetail(Comment param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		String id = param.getId();
		if (StringUtils.isBlank(id) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		Comment comment = commentService.get(id);
		return map.setState(CommonParam.STATE_OK).setData(comment);
				
	}
	
	/**
	 * 评价教练
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/comment")
	public ResultMap comment(Comment param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		String orderId = param.getOrderId();
		String gymId = param.getGymId();
		String label = param.getLabel();
		String content = param.getContent();
		if (StringUtils.isBlank(orderId) || StringUtils.isBlank(gymId) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		Order order = orderService.get(orderId);
		if (!CommonParam.ORDER_STATUS_DAIPINGJIA.equals(order.getStatus()) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("操作有误,请检查订单状态"); 
		}
		Comment c = new Comment();
		c.setOrderId(orderId);
		c.setStuId(order.getStuId());
		c.setStuName(user.getNickName());
		c.setContent(content);
		c.setLabel(label);
		c.setCommentTime(new Date());
		c.setCoachId(order.getCoachId());
		c.setCoachName(order.getCoachName());
		int major = param.getMajor() == null ? 5 : param.getMajor();  //默认5分
		int service = param.getService() == null ? 5 : param.getService();
		c.setMajor(major);
		c.setService(service);
		c.setTotal(major+service);
		
		commentService.save(c);
		
		order.setComment(c.getId());
		order.setStatus(CommonParam.ORDER_STATUS_YIWANCHENG);
		orderService.save(order);
		
		//更新教练评分
		commentService.updateCoachScore(major+service,order.getCoachId() );
		
		//发送打赏消息
		AppUser stu = userService.get(order.getStuId());
		msgService.sendReward(stu,user,order);
		
		
		Gym gym = gymService.get(gymId);
		order.setGym(gym);
		
		return map.setState(CommonParam.STATE_OK).setData(order);
		
	}
	
	
	
	
	/**
	 * 开始课程
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/startCourse")
	public ResultMap startCourse(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		String orderId = param.getOrderId();
		String gymId = param.getGymId();
		if (StringUtils.isBlank(orderId) || StringUtils.isBlank(gymId) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		Order order = orderService.get(orderId);
		if (!CommonParam.ORDER_STATUS_DAISHANGKE.equals(order.getStatus()) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("操作有误,请检查订单状态"); 
		}
		
		UserCourse userCourse = userCourseService.get(order.getCourseId());
		if (userCourse == null || BaseEntity.DEL_FLAG_DELETE.equals(userCourse.getDelFlag())) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("该课程已不存在,请选择其他课程"); 
		}
		
		//只有教练才可以上课
		if (!user.getId().equals(order.getCoachId())) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("您不是教练,无法开始上课"); 
		}
		
		try {
			
			//判断是否上课时间是否冲突
			
			/*当前时间距离预约开始时间若大于等于24小时，则不能点击“开始上课”，若小于24小时，则需判断当前时间+课程时长这段时间是否是空闲时间，
			若是则可以点击“开始上课”，若不是则不可以点击“开始上课”，同时提示教练“该时间段已被其他学员预约，不能开始上课哦~”*/
			
			Date nowTime = new Date();
			Date appointmentTime = order.getAppointmentTime();
			boolean flag = (nowTime.getTime()-appointmentTime.getTime())/(1000*60*60) >= 24 ;
			if (flag) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("离预约时间已过24小时,不能开始上课");
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String restDate = sdf.format(nowTime);
			
			long duration = Long.parseLong(order.getCourseDuration())*60*1000;
			Date endDate = restTimeService.getDateTimeByLong(nowTime, duration);
			
			//获取时间冲突的教练id列表
			RestTime condition = new RestTime();
			condition.setBeginTime(nowTime);
			condition.setEndTime(endDate);
			condition.setGymId(gymId);
			condition.setRestDate(sdf.parse(restDate));
			
			List<String> excludeIds = restTimeService.findBusyCoachListExcludeOrder(condition,orderId); 
			if (excludeIds.contains(user.getId())) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("该时间段已有其他安排,不能开始上课");
			}
			
			
			
			//判断学员是否有空 
			RestTime condition1 = new RestTime();
			condition1.setBeginTime(nowTime);
			condition1.setDuration(userCourse.getDuration().longValue()+"");
			condition1.setUserId(order.getStuId());
			condition1.setOrderId(orderId);
			if (restTimeService.checkTimeConflict(condition1)) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("会员在选课时间已有其他安排,请另选时间");
			}
			
			order.setStartTime(new Date());;
			order.setStatus(CommonParam.ORDER_STATUS_SHANGKEZHONG);
			orderService.save(order);
			
			
			Gym gym = gymService.get(gymId);
			order.setGym(gym);
			
			//发送开始上课通知
			AppUser stu = userService.get(order.getStuId());
			msgService.sendCourseStart(stu,user, order, userCourse);
			
			return map.setState(CommonParam.STATE_OK).setData(order);
			
		} catch (ParseException e) {
			e.printStackTrace();
			return map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙...");
		}
	}
	
	
	
	
	/**
	 * 结束课程
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/finishCourse")
	public ResultMap finishCourse(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		String orderId = param.getOrderId();
		String remarks = param.getRemarks();
		String gymId = param.getGymId();
		if (StringUtils.isBlank(orderId) || StringUtils.isBlank(gymId) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		Order order = orderService.get(orderId);
		if (!CommonParam.ORDER_STATUS_SHANGKEZHONG.equals(order.getStatus()) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("操作有误,请检查订单状态"); 
		}
		
		//只有教练才可以结束课程
		if (!user.getId().equals(order.getCoachId())) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("您不是教练,无法结束课程"); 
		}
		
		order.setStatus(CommonParam.ORDER_STATUS_DAIPINGJIA);
		order.setEndTime(new Date());
		order.setRemarks(remarks);
		orderService.save(order);
		
		//更新课程状态
		String courseId = order.getCourseId();
		UserCourse userCourse = userCourseService.get(courseId);
		if (userCourse != null) {
			userCourse.setFinishFlag(CommonParam.COURSE_YIWANCHENG);
			userCourseService.save(userCourse);
		}
		
		//发送消息
		AppUser stu = userService.get(order.getStuId());
		AppUser coach = userService.get(order.getCoachId());
		if (user.getId().equals(order.getStuId())) {
			msgService.sendCourseFinish(coach, user,order,userCourse);
		}else if (user.getId().equals(order.getCoachId())) {
			msgService.sendCourseFinish(stu, user,order,userCourse);
		}



		Gym gym = gymService.get(gymId);
		order.setGym(gym);
		
		return map.setState(CommonParam.STATE_OK).setData(order);
	}
	
	
	
	
	
	/**
	 * 取消订单
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/cancelOrder")
	public ResultMap cancelOrder(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		String orderId = param.getOrderId();
		String cancelReason = param.getCancelReason();
		String gymId = param.getGymId();
		if (StringUtils.isBlank(orderId)  || StringUtils.isBlank(gymId) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		Order order = orderService.get(orderId);
		if (order == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("该订单不存在~"); 
		}
		
		if (StringUtils.isBlank(order.getCoachId()) && !user.getId().equals(order.getStuId())) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("该订单不允许取消~");
		}
		if (!(CommonParam.ORDER_STATUS_DAISHANGKE.equals(order.getStatus()) || CommonParam.ORDER_STATUS_DAIQUEREN.equals(order.getStatus()) )) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("该订单不允许取消~"); 
		}
		if (StringUtils.isBlank(order.getStuId()) && !user.getId().equals(order.getCoachId())) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("该订单不允许取消~");
		}
		
		// 这里存在同时取消的情况
		String oldStatus = order.getStatus();
		order.setCancelor(user.getId());
		//order.setCancelReason(cancelReason);
		order.setCancelTime(new Date());
		order.setStatus(CommonParam.ORDER_STATUS_YIQUXIAO);
		int result = orderService.updateRobOrder(order);
		if (result == 1) {
			UserGym userGym=new UserGym();
			userGym.setUserId(user.getId());
			userGym.setGymId(gymId);
			userGym.setRelation(CommonParam.USER_TYPE_SIJIAO);
			userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
			List<UserGym> userGymList= userGymService.findList(userGym);
			if(userGymList!=null&&userGymList.size()>0){
				userGym=userGymList.get(0);
				userGym.setRestNumber(userGym.getRestNumber()+1);
				userGymService.update(userGym);
			}
			//更新课程状态
			UserCourse uc = userCourseService.get(order.getCourseId());
			if (uc != null) {
				uc.setFinishFlag("0");
				userCourseService.save(uc);
				
				//发送消息
				if (CommonParam.ORDER_STATUS_DAISHANGKE.equals(oldStatus)) {//待确认的订单不需要发送消息
					AppUser stu = userService.get(order.getStuId());
					AppUser coach = userService.get(order.getCoachId());
					if (user.getId().equals(order.getStuId())) {
						msgService.sendCancelOrder(coach,stu,order, uc);
					}else if(user.getId().equals(order.getCoachId())) {
						msgService.sendCancelOrder(stu,coach,order, uc);
					}
				}
				
			}
			
			
			//检查是否需要退款
			PayInfo payInfo = payInfoService.findByOrderNum(order.getOrderNum());
			if (payInfo != null && CommonParam.REFUND_NO.equals(payInfo.getRefundflag())) {
				boolean refundFlag = false;
				
				if (CommonParam.PAY_BY_ALI.equals(payInfo.getPayWay())) {
					refundFlag = orderService.aliRefund(order, payInfo);
				}
				if (CommonParam.PAY_BY_WX.equals(payInfo.getPayWay())) {
					refundFlag = orderService.wxRefund(order, payInfo);
				}
				
				if (refundFlag ) {
					
					//检查是否需要更新教练账户
					if (StringUtils.isNotBlank(payInfo.getCoachId())) {
						userAccountService.refund(payInfo.getCoachId(),payInfo.getPayAmount());
					}
				}
				
				
			}
			
		
			
		}else {
			return map.setState(CommonParam.STATE_ERROR).setMsg("该订单已取消~"); 
		}
		
		
		Gym gym = gymService.get(gymId);
		order.setGym(gym);
		
		return map.setState(CommonParam.STATE_OK).setData(order);
	}
	
	
	/**
	 * 教练抢单
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/robOrder")
	public ResultMap robOrder(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		try {
			AppUser user = (AppUser) request.getAttribute("user");

			String id = param.getId();
			if (StringUtils.isBlank(id) ) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
			}
			
			Order order = orderService.get(id);
			if (!CommonParam.ORDER_STATUS_DAIQUEREN.equals(order.getStatus())) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("已被其他教练抢先,订单已失效"); 
			}
			UserCourse userCourse = userCourseService.get(order.getCourseId());
			if (userCourse == null || "1".equals(userCourse.getDelFlag())) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("该订单课程已不存在,请选择其他订单"); 
			}
			
			//判断教练在约课时间是否有空 
			String duration = userCourse.getDuration().longValue()+"";
			RestTime condition1 = new RestTime();
			condition1.setBeginTime(order.getAppointmentTime());
			condition1.setDuration(duration);
			condition1.setUserId(user.getId());
			if (restTimeService.checkTimeConflict(condition1)) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("您在选课时间已有其他安排,请另选时间");
			}
			
			
			
			order.setCoachId(user.getId());
			order.setCoachName(user.getRealName());
			order.setRobTime(new Date());
			order.setStatus(CommonParam.ORDER_STATUS_DAISHANGKE);
			order.setCoachPhone(user.getPhone());
			order.setCoachSex(user.getSex());
			order.setCoachImgUrl(user.getHeadImgUrl());
			
			Gym gym = userGymService.getGymByCoachId(user);
			
			UserGym condition = new UserGym();
			condition.setUserId(user.getId());
			condition.setGymId(gym.getId());
			condition.setBindFlag(CommonParam.USER_GYM_BIND_YES);
			List<UserGym> list = userGymService.findList(condition);
			if (list!=null && list.size()>0) {
				order.setCoachLabel(list.get(0).getCoachLabel());
				if(list.get(0).getCommissionRatio()!=null){
					order.setCoachProportion(list.get(0).getCommissionRatio().toString());
				}

			}
			
			
			int result = orderService.updateRobOrder(order);
			if (result == 1) {
				
				//检查是否更新打赏信息
				PayInfo payInfo = payInfoService.findByOrderNum(order.getOrderNum());
				if (payInfo!=null) {
					payInfo.setCoachId(user.getId());
					payInfo.setCoachName(user.getNickName());
					payInfoService.save(payInfo);
					
					userAccountService.reward(user.getId(), payInfo.getPayAmount());
					
				}
				
				
				//更新学员课程状态 
				if (userCourse!=null) {
					userCourse.setFinishFlag(CommonParam.COURSE_DAISHANGKE);
					userCourseService.save(userCourse);
					
					//发送消息
					AppUser stu = userService.get(order.getStuId());
					msgService.sendRobCourse(stu,user,order,userCourse);
					//发短信
					userService.sendAppointment(order);
				}
				
				return map.setState(CommonParam.STATE_OK).setMsg("抢单成功");
			}else {
				return map.setState(CommonParam.STATE_ERROR).setMsg("已被其他教练抢先,订单已失效");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙...");
		}
		
	}
	
	
	
	
	/**
	 * 查看订单详情
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/orderDetail")
	public ResultMap orderDetail(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		
		String id = param.getId();
		if (StringUtils.isBlank(id) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		Order order = orderService.get(id);
		/*System.out.println("user.getId()======"+user.getId());
		System.out.println("order.getStuId()======"+order.getStuId());
		System.out.println("order.getCoachId()======"+order.getCoachId());
		if (!(user.getId().equals(order.getStuId()) || user.getId().equals(order.getCoachId()))) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("该订单不允许查看哦~");
		}*/
		
		String gymId = order.getGymId();
		
		Gym gym = gymService.get(gymId);
		gym.setLoginName(null);
		gym.setPassword(null);
		order.setGym(gym);
		
		return map.setState(CommonParam.STATE_OK).setData(order);
	}
	
	
	
	
	/**
	 * 查看订单列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/orderList")
	public ResultMap orderList(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		
		String userType = param.getUserType();
		String status = param.getStatus();
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		if (StringUtils.isBlank(userType) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
	
		List<Order> list = null;
		
		//status:
		// 教练：1:待抢单、2:待上课、3:待确认、4:上课中、5:已完成、6:已取消
		//会员：1:待接单、2:待确认（含接受排课和抢课）3:待上课、4:上课中、5:已完成、6:已取消
		if (CommonParam.USER_TYPE_JIAOLIAN.equals(userType)) {
			Gym gym = userGymService.getGymByCoachId(user);
			if (gym == null) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("系统异常");
			}
			if (StringUtils.isBlank(status)) {
				Order order = new Order();
				order.setOrNullFlag(true); //表示允许coachId为空
				order.setCoachId(user.getId());
				order.setGymId(gym.getId());
				
				list = orderService.findList(order);
			}
			if ("1".equals(status)) {
				Order order = new Order();
				order.setStatus(CommonParam.ORDER_STATUS_DAIQUEREN);
				order.setType(CommonParam.ORDER_TYPE_YIJIAN);
				order.setGymId(gym.getId());
				
				list = orderService.findList(order);
			}
			if ("2".equals(status)) {
				Order order = new Order();
				List<String> statusList = new ArrayList<String>();
				statusList.add(CommonParam.ORDER_STATUS_DAISHANGKE);
				order.setIncludeStatus(statusList);
				order.setCoachId(user.getId());
				order.setGymId(gym.getId());
				
				list = orderService.findList(order);
			}
			if ("3".equals(status)) {
				Order order = new Order();
				order.setType(CommonParam.ORDER_TYPE_ANPAI);
				order.setStatus(CommonParam.ORDER_STATUS_DAIQUEREN);
				order.setCoachId(user.getId());
				order.setGymId(gym.getId());
				
				list = orderService.findList(order);
			}
			if ("4".equals(status)) {
				Order order = new Order();
				List<String> statusList = new ArrayList<String>();
				statusList.add(CommonParam.ORDER_STATUS_SHANGKEZHONG);
				order.setIncludeStatus(statusList);
				order.setCoachId(user.getId());
				order.setGymId(gym.getId());
				
				list = orderService.findList(order);
			}
			if ("5".equals(status)) {
				Order order = new Order();
				List<String> statusList = new ArrayList<String>();
				statusList.add(CommonParam.ORDER_STATUS_DAIPINGJIA);
				statusList.add(CommonParam.ORDER_STATUS_YIWANCHENG);
				order.setIncludeStatus(statusList);
				order.setCoachId(user.getId());
				order.setGymId(gym.getId());
				
				list = orderService.findList(order);
			}
			if ("6".equals(status)) {
				Order order = new Order();
				List<String> statusList = new ArrayList<String>();
				statusList.add(CommonParam.ORDER_STATUS_YIQUXIAO);
				order.setIncludeStatus(statusList);
				order.setCoachId(user.getId());
				order.setGymId(gym.getId());
				
				list = orderService.findList(order);
			}
			
		//status:
		//教练：1:待抢单、2:待上课、3:待确认、4:上课中、5:已完成、6:已取消
		//会员：1:待接单、2:待确认（含接受排课和抢课）、3:待上课、4:上课中、5:已完成、6:已取消	
		}else if (CommonParam.USER_TYPE_SIJIAO.equals(userType)) {//私教会员列表
			if (StringUtils.isBlank(status)) {
				Order order = new Order();
				order.setStuId(user.getId());
				order.setContainFlag(true);
				//TODO 需要判断教练排课是否对应自己的课程表
				order.setAllCheckFlag(true);
				
				list = orderService.findList(order);
			}
			if ("1".equals(status)) {
				Order order = new Order();
				order.setType(CommonParam.ORDER_TYPE_YIJIAN);
				order.setStatus(CommonParam.ORDER_STATUS_DAIQUEREN);
				order.setStuId(user.getId());
				
				list = orderService.findList(order);
			}
			if ("2".equals(status)) {
				Order order = new Order();
				order.setType(CommonParam.ORDER_TYPE_ANPAI);
				order.setStatus(CommonParam.ORDER_STATUS_DAIQUEREN);
				order.setStuId(user.getId());
				order.setContainFlag(true);
				
				list = orderService.findList(order);
			}
			if ("3".equals(status)) {
				Order order = new Order();
				
				List<String> statusList = new ArrayList<String>();
				statusList.add(CommonParam.ORDER_STATUS_DAISHANGKE);
				order.setIncludeStatus(statusList);
				order.setStuId(user.getId());
				
				list = orderService.findList(order);
			}
			if ("4".equals(status)) {
				Order order = new Order();
				
				List<String> statusList = new ArrayList<String>();
				statusList.add(CommonParam.ORDER_STATUS_SHANGKEZHONG);
				order.setIncludeStatus(statusList);
				order.setStuId(user.getId());
				
				list = orderService.findList(order);
			}
			if ("5".equals(status)) {
				Order order = new Order();
				
				List<String> statusList = new ArrayList<String>();
				statusList.add(CommonParam.ORDER_STATUS_DAIPINGJIA);
				statusList.add(CommonParam.ORDER_STATUS_YIWANCHENG);
				order.setIncludeStatus(statusList);
				order.setStuId(user.getId());
				
				list = orderService.findList(order);
			}
			if ("6".equals(status)) {
				Order order = new Order();
				
				List<String> statusList = new ArrayList<String>();
				statusList.add(CommonParam.ORDER_STATUS_YIQUXIAO);
				order.setIncludeStatus(statusList);
				order.setStuId(user.getId());
				
				list = orderService.findList(order);
			}
			
			
			
		}else{
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求用户类型有误");
		}
		
		
		PageBean<Order> pageBean = new PageBean<Order>();
		if (list!=null && list.size()>0) {
			pageBean = new PageBean<Order>(currentPage, pageSize, list.size());
			pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
			
		}
		//return map.setState(CommonParam.STATE_OK).setData(pageBean);
		return map.setState(CommonParam.STATE_OK).setData(pageBean);
			
		
		
		
		
	}
	
	
	/**
	 * 生成会员一键约课订单
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/generateOneKeyOrder")
	public ResultMap generateOneKeyOrder(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");

		//String gymId = param.getGymId();
		Date date = param.getDate();
		String courseId = param.getCourseId();

		UserGym userGym=new UserGym();
		if (StringUtils.isBlank(courseId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		UserCourse userCourse1= userCourseService.get(courseId);
		userGym.setUserId(user.getId());
		userGym.setGymId(userCourse1.getGymId());
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		List<UserGym> userGymList= userGymService.findList(userGym);
		if(userGymList!=null&&userGymList.size()>0){
			if(userGymList.get(0).getFreezeFlag().equals("1")){
				return map.setState(CommonParam.STATE_ERROR).setMsg("该用户被冻结");
			}else if(userGymList.get(0).getRestNumber()==null||userGymList.get(0).getRestNumber()==0){
				return map.setState(CommonParam.STATE_ERROR).setMsg("剩余课程0节");
			}
		}else{
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
		}


		
		if (date == null || StringUtils.isBlank(courseId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		Date nowTime = new Date();
		if (nowTime.getTime()>=date.getTime()) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("预约时间不能小于当前时间");
		}
		
		
		try {
			
			UserCourse userCourse = userCourseService.get(courseId);
			if (userCourse == null || !user.getId().equals(userCourse.getUserId()) ) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("课程不存在,请检查");
			}
			String gymId = userCourse.getGymId();
			
			//检查该课程是否已经预约过
			Order order2 = new Order();
			order2.setCourseId(courseId);
			List<String> excludeStatus = Arrays.asList(CommonParam.ORDER_STATUS_YIQUXIAO);
			order2.setExcludeStatus(excludeStatus);
			List<Order> orderList = orderService.findList(order2);
			if (orderList!=null && orderList.size()>0) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("该课程已预约");
			}
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String restDate = sdf.format(date);
			
			long duration = userCourse.getDuration().longValue()*60*1000;
			Date endDate = restTimeService.getDateTimeByLong(date, duration);
			
			
			
			//判断预约人是否有空 
			RestTime condition1 = new RestTime();
			condition1.setBeginTime(date);
			condition1.setEndTime(endDate);
			condition1.setGymId(gymId);
			condition1.setRestDate(sdf.parse(restDate));
			
			List<String> busyIds = restTimeService.findBusyUserList(condition1);
			if (busyIds!=null && busyIds.size()>0) {
				for (String id : busyIds) {
					if (user.getId().equals(id)) {
						return map.setState(CommonParam.STATE_ERROR).setMsg("您在选课时间已有其他安排,请另选时间");
					}
				}
			}



			UserGym userGym1=userGymList.get(0);//扣除上课数
			userGym1.setRestNumber(userGym1.getRestNumber()-1);
			userGymService.update(userGym1);
			
			//通知所有有空闲时间的教练 
			//获取指定健身房的所有教练id列表
			List<String> coachIds = userGymService.findCoachListByGymId(gymId);
			
			//获取时间冲突的教练id列表
			RestTime condition = new RestTime();
			condition.setBeginTime(date);
			condition.setEndTime(endDate);
			condition.setGymId(gymId);
			condition.setRestDate(sdf.parse(restDate));
			List<String> excludeIds = restTimeService.findBusyCoachList(condition); 
			//指定为教练类型
			List<String> includeUserTypes = new ArrayList<String>(); 
			includeUserTypes.add(CommonParam.USER_TYPE_SIJIAO_JIAOLIAN);
			includeUserTypes.add(CommonParam.USER_TYPE_JIAOLIAN);
			
			//查询可以接单的教练
			AppUser appUser = new AppUser();
			appUser.setActivateFlag("1");
			appUser.setExcludeIds(excludeIds);
			appUser.setIncludeUserTypes(includeUserTypes);
			appUser.setIncludeIds(coachIds);
			
			List<AppUser> userList = userService.findList(appUser);
			
			//群发系统消息
			for (AppUser u : userList) {
				String nickName = user.getRealName()==null?CommonParam.USER_DEFAULT_NICKNAME : user.getRealName();
				String content = "会员"+nickName+"发起了一键约课"+sdf1.format(date)+"来上第"+userCourse.getSeq()+"节-"+userCourse.getName()+",快点去抢单吧!手慢了会被别的教练抢走了哦~查看路径：订单-待接单";
				
				msgService.sendSystem(u.getId(), content);
				
				Msg msg = new Msg();
				
				msg.setContent(content);
				msg.setTitle("\"约课抢单\"通知");
				msg.setToUserId(u.getId());
				msg.setFromUserId(CommonParam.APP_SYSTEM_ID);
				msg.setToUserName(u.getRealName()!=null?u.getRealName():CommonParam.USER_DEFAULT_NICKNAME);
				msg.setFromUserName(CommonParam.APP_SYSTEM_NAME);
				msg.setIsRead(CommonParam.MSG_NOT_READ);
				msg.setToUserType(CommonParam.USER_TYPE_JIAOLIAN);
				msg.setMsgType(CommonParam.MSG_TYPE_ORDER);
				
				msgService.save(msg);
				
			}
			
			
			
			//生成订单
			OrderUtil orderUtil = new OrderUtil();
			String oderNum = orderUtil.makeOrderNum(null);
			
			Order order = new Order();

			if(userGymList!=null&&userGymList.size()>0){
				order.setMemberPrice(userGymList.get(0).getUnitPrice());
			}



			order.setIsGym("0");
			order.setOrderNum(oderNum);
			order.setGymId(gymId);
			order.setStuId(user.getId());
			order.setStuName(user.getRealName());
			order.setStuPhone(user.getPhone());
			order.setStuImgUrl(user.getHeadImgUrl());
			order.setStuSex(user.getSex());
			order.setType(CommonParam.ORDER_TYPE_YIJIAN);
			order.setAppointmentTime(date);
			order.setClassName(userCourse.getName());
			order.setCourseDuration(userCourse.getDuration()+"");
			order.setCourseId(userCourse.getId());
			order.setVersion(1);
			order.setStatus(CommonParam.ORDER_STATUS_DAIQUEREN);
			order.setIsConfirm(CommonParam.ORDER_CONFIRM_NO);
			order.setCanEvaluate(CommonParam.ORDER_CAN_ENVALUATE_YES);
			
			
			Gym gymDb = gymService.get(gymId);
			order.setGymName(gymDb.getName());
			order.setGym(gymDb);
		
			//TODO 平均接单时间需要计算(单位:分钟),暂时写死
			order.setAverageRobTime(15);
			
			
			orderService.save(order);
			
			//更新学员课程状态 
			if (userCourse!=null) {
				userCourse.setFinishFlag(CommonParam.COURSE_YUYUEZHONG);
				userCourseService.save(userCourse);
			}
			
			return map.setState(CommonParam.STATE_OK).setData(order);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙");
		}
		
		
	}
	
	
	
	
	
	
	
	
	/**
	 * 生成会员约课订单
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/generateAppointmentOrder")
	public ResultMap generateAppointmentOrder(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
		UserGym userGym=new UserGym();
		if (StringUtils.isBlank(param.getCourseId()) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
        AppUser user2=new AppUser();
        user2.setId(param.getCoachId());
        Gym gym= userGymService.getGymByCoachId(user2);
		userGym.setUserId(user.getId());
		userGym.setGymId(gym.getId());
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		List<UserGym> userGymList= userGymService.findList(userGym);
		if(userGymList!=null&&userGymList.size()>0){
			if(userGymList.get(0).getFreezeFlag().equals("1")){
				return map.setState(CommonParam.STATE_ERROR).setMsg("该用户被冻结");
			}else if(userGymList.get(0).getRestNumber()==null||userGymList.get(0).getRestNumber()==0){
                return map.setState(CommonParam.STATE_ERROR).setMsg("剩余课程0节");
            }
		}else{
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
		}

		//String gymId = param.getGymId();
		Date date = param.getDate();
		String courseId = param.getCourseId();
		String coachId = param.getCoachId();
		
		if (date == null || StringUtils.isBlank(courseId) || StringUtils.isBlank(coachId)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
		}
		
		Date nowTime = new Date();
		if (nowTime.getTime()>=date.getTime()) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("预约时间不能小于当前时间");
		}
		
		try {
			AppUser coachUser = userService.get(coachId);
			
			UserCourse userCourse = userCourseService.get(courseId);
			if (userCourse == null || BaseEntity.DEL_FLAG_DELETE.equals(userCourse.getDelFlag())) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("课程不存在");
			}
			String gymId = userCourse.getGymId();
			
			
			//检查该课程是否已经预约过
			Order order2 = new Order();
			order2.setCourseId(courseId);
			List<String> ids = new ArrayList<String>();
			ids.add(CommonParam.ORDER_STATUS_YIQUXIAO);
			order2.setExcludeStatus(ids);
			List<Order> orderList = orderService.findList(order2);
			if (orderList!=null && orderList.size()>0) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("该课程已预约");
			}
			
			
			//判断教练在约课时间是否仍然有空, 因为前面是列出了空闲教练的列表, 但是中间间隔时间不定, 可能教练时间有变化 
			String duration = userCourse.getDuration().longValue()+"";
			RestTime condition = new RestTime();
			condition.setBeginTime(date);
			condition.setGymId(gymId);
			condition.setDuration(duration);
			condition.setUserId(coachId);
			boolean checkTimeConflict = restTimeService.checkTimeConflict(condition);
			if (checkTimeConflict) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("教练在选课时间已有其他安排,请另选时间");
			}
			
			//判断学员是否有空
			condition.setUserId(user.getId());
			if (restTimeService.checkTimeConflict(condition)) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("您在选课时间已有其他安排,请另选时间");
			}


			UserGym userGym1=userGymList.get(0);//扣除上课数
			userGym1.setRestNumber(userGym1.getRestNumber()-1);
			userGymService.update(userGym1);
			
			//生成订单
			OrderUtil orderUtil = new OrderUtil();
			String oderNum = orderUtil.makeOrderNum(null);
			
			Order order = new Order();
			if(userGymList!=null&&userGymList.size()>0){
				order.setMemberPrice(userGymList.get(0).getUnitPrice());
			}

			userGym.setGymId(gymId);
			userGym.setUserId(coachId);
			userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
			List<UserGym> userGymList2= userGymService.findList(userGym);
			if(userGymList2!=null&&userGymList2.size()>0){
				if(userGymList2.get(0).getCommissionRatio()!=null){
					order.setCoachProportion(userGymList2.get(0).getCommissionRatio().toString());
				}

			}

			order.setIsGym("0");
			order.setOrderNum(oderNum);
			order.setGymId(gymId);
			order.setStuId(user.getId());
			order.setStuName(user.getRealName());
			order.setStuPhone(user.getPhone());
			order.setStuImgUrl(user.getHeadImgUrl());
			order.setStuSex(user.getSex());
			order.setCoachId(coachId);
			order.setCoachName(coachUser.getRealName());
			order.setCoachPhone(coachUser.getPhone());
			order.setCoachSex(coachUser.getSex());
			order.setType(CommonParam.ORDER_TYPE_YUYUE);
			order.setAppointmentTime(date);
			order.setClassName(userCourse.getName());
			order.setCoachLabel(coachUser.getLabel());
			order.setCoachImgUrl(coachUser.getHeadImgUrl());
			order.setCourseDuration(userCourse.getDuration()+"");
			order.setCourseId(userCourse.getId());
			order.setVersion(1);
			order.setStatus(CommonParam.ORDER_STATUS_DAISHANGKE);
			order.setIsConfirm(CommonParam.ORDER_CONFIRM_NO);
			order.setCanEvaluate(CommonParam.ORDER_CAN_ENVALUATE_YES);
			
			Gym gymDb = gymService.get(gymId);
			order.setGymName(gymDb.getName());
			
			orderService.save(order);
			
			
			order.setGym(gymDb);
			
			//更新学员课程状态 
			if (userCourse!=null) {
				userCourse.setFinishFlag(CommonParam.COURSE_DAISHANGKE);
				userCourseService.save(userCourse);
			}
			
			
			//发送消息
			msgService.sendAppoinmentCourse(user,coachUser,order,userCourse);
			//发短信
			userService.sendAppointment(order);
			
			return map.setState(CommonParam.STATE_OK).setData(order);

			
			
		} catch (Exception e) {
			e.printStackTrace();
			return map.setState(CommonParam.STATE_ERROR).setMsg("服务器忙");
		}
		
		
	}
	
	

	
    
	
}

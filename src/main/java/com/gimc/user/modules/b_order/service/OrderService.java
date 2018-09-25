/**
 * 
 */
package com.gimc.user.modules.b_order.service;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_order.entity.Order;
import com.gimc.user.modules.b_pay_info.entity.PayInfo;
import com.gimc.user.modules.b_pay_info.service.PayInfoService;
import com.gimc.user.modules.b_user.entity.AppUser;

import wxpay.MyConfig;
import wxpay.WXPay;

import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_order.dao.OrderDao;

/**
 * 订单Service
 * @author linhaomiao
 * @version 2018-06-01
 */
@Service
@Transactional(readOnly = true)
public class OrderService extends CrudService<OrderDao, Order> {

	
	@Autowired
	private PayInfoService payInfoService;
	
	@Autowired
	private OrderDao orderdao;
	
	public void updateStatusFor(Order order){
		orderdao.updateStatus(order);
	}
	
	public void UpdateStatus(Order order){
		orderdao.updateStatus(order);
	}
	
	public Order get(String id) {
		return super.get(id);
	}
	
	public List<Order> findList(Order order) {
		return super.findList(order);
	}
	
	public Page<Order> findPage(Page<Order> page, Order order) {
		return super.findPage(page, order);
	}
	public Page<Order> settlementList(Page<Order> page, Order order) {
		order.setPage(page);
		page.setList(orderdao.settlementList(order));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(Order order) {
		super.save(order);
	}
	
	@Transactional(readOnly = false)
	public void delete(Order order) {
		super.delete(order);
	}
	
	@Transactional(readOnly = false)
	public int updateRobOrder(Order order) {
		return dao.updateRobOrder(order);
	}

	
	/**
	 * 获取教练累计课程
	 * @param user
	 * @return
	 */
	public int findCoachTotalCourseNum(AppUser user) {
		
		Order order = new Order();
		order.setCoachId(user.getId());
		List<String> includeStatus = Arrays.asList(CommonParam.ORDER_STATUS_DAIPINGJIA,CommonParam.ORDER_STATUS_YIWANCHENG);
		order.setIncludeStatus(includeStatus);
		List<Order> list = dao.findList(order);
		if (list!=null && list.size()>0) {
			return list.size();
		}
		
		return 0;
	}

	/**
	 * 查看学员健身房排行
	 * @return
	 */
	public List<Order> findStuRankList(String gymId) {
		
		Order condition = new Order();
		condition.setGymId(gymId);
		condition.setRankObject("stu_id");
		List<String> includeStatus = Arrays.asList(CommonParam.ORDER_STATUS_DAIPINGJIA,CommonParam.ORDER_STATUS_YIWANCHENG);
		condition.setIncludeStatus(includeStatus);
		
		List<Order> list = dao.findRankList(condition);
		
		return list;
	}
	
	
	/**
	 * 查看教练健身房排行
	 * @return
	 */
	public List<Order> findCoachRankList(String gymId) {
		
		Order condition = new Order();
		condition.setGymId(gymId);
		condition.setRankObject("coach_id");
		List<String> includeStatus = Arrays.asList(CommonParam.ORDER_STATUS_DAIPINGJIA,CommonParam.ORDER_STATUS_YIWANCHENG);
		condition.setIncludeStatus(includeStatus);
		
		List<Order> list = dao.findRankList(condition);
		
		return list;
	}
	
	public Order findByOrderNum(String orderNum){
		
		Order order = new Order();
		order.setOrderNum(orderNum);
		List<Order> list = dao.findList(order);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		
		return null;
	}
	
	/**
	 * 支付宝退款
	 * @param order
	 * @param payInfo
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean aliRefund(Order order, PayInfo payInfo){
		
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(CommonParam.ALI_GATEWAYURL,CommonParam.ALI_APPID,CommonParam.ALI_PRIVATE_KEY,"json","utf-8",CommonParam.ALI_PUBLIC_KEY,CommonParam.ALI_SIGN_TYPE);
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			request.setBizContent("{" +
			//"\"out_trade_no\":\""+order.getOrderNum()+"\"," +
			"\"trade_no\":\""+payInfo.getAliTradeNo()+"\"," +
			"\"refund_amount\":"+payInfo.getPayAmount()+"," +
		//	"\"refund_currency\":\"USD\"," +
			"\"refund_reason\":\"取消订单退款\"" +
//			"\"out_request_no\":\"HZ01RF001\"," +
//			"\"operator_id\":\"OP001\"," +
//			"\"store_id\":\"NJ_S_001\"," +
//			"\"terminal_id\":\"NJ_T_001\"," +
//			"      \"goods_detail\":[{" +
//			"        \"goods_id\":\"apple-01\"," +
//			"\"alipay_goods_id\":\"20010001\"," +
//			"\"goods_name\":\"ipad\"," +
//			"\"quantity\":1," +
//			"\"price\":2000," +
//			"\"goods_category\":\"34543238\"," +
//			"\"body\":\"特价手机\"," +
//			"\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
			"        }]" +
			"  }");
			AlipayTradeRefundResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				
				payInfo.setRefundTime(new Date());
				payInfo.setRefundAmount(payInfo.getPayAmount());
				payInfo.setRefundflag(CommonParam.REFUND_YES);
				payInfo.setStatus(CommonParam.REWARD_STATUS_YITUIHUI);
				
				payInfoService.save(payInfo);
				
				System.out.println("调用成功");
				return true;
			} else {
				System.out.println("调用失败");
				return false;
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return false;
		}
		
		
		
		
	}
	
	
	/**
	 * 微信退款
	 * @param order
	 * @param payInfo
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean wxRefund(Order order, PayInfo payInfo){
		
		try {
		
			
            String notify_url = CommonParam.WX_REFUND_NOTIFY_URL; //通知地址
            String out_trade_no = order.getOrderNum();         //商户订单号
            int total_fee =(int)(payInfo.getPayAmount()*100); //交易金额,单位为分
          
            
            MyConfig config = new MyConfig();
            WXPay wxpay = new WXPay(config);//部分参数设置在MyConfig类

            Map<String, String> data = new HashMap<String, String>();
            data.put("notify_url", notify_url);
            data.put("out_refund_no", out_trade_no);
            data.put("out_trade_no", out_trade_no);
            data.put("refund_fee", total_fee+"");  
            data.put("total_fee", total_fee+"");

            
            Map<String, String> resp = wxpay.refund(data);
			
            String return_code = resp.get("return_code");//此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	        String result_code = resp.get("result_code");//业务结果
            
            if ("SUCCESS".equalsIgnoreCase(return_code) && "SUCCESS".equalsIgnoreCase(result_code)) {
            	payInfo.setRefundTime(new Date());
				payInfo.setRefundAmount(payInfo.getPayAmount());
				payInfo.setRefundflag(CommonParam.REFUND_YES);
				payInfo.setStatus(CommonParam.REWARD_STATUS_YITUIHUI);
				
				payInfoService.save(payInfo);
				
				System.out.println("调用成功");
				return true;
			} else {
				System.out.println("调用失败");
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
		
		
	}
	
	
	
	public List<Order> searchOrder(Order order){
		return dao.searchOrder(order);
	}
	
	
}
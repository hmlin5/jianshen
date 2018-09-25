/**
 * 
 */
package com.gimc.user.modules.b_pay_info.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 支付信息Entity
 * @author linhaomiao
 * @version 2018-06-30
 */
public class PayInfo extends DataEntity<PayInfo> {
	
	private static final long serialVersionUID = 1L;
	private String orderNum;		// 订单号
	private String payWay;		// 支付方式,1:支付宝, 2: 微信
	private String aliTradeNo;		// 支付宝交易号
	private String wxTradeNo;		// 微信交易号
	private String payflag;		// 是否已支付,0:否, 1:是
	private double payAmount;		// 支付金额
	private Date payTime;		// 支付时间
	private String refundflag;		// 是否退款,0:否, 1:是
	private double refundAmount;		// 退款金额
	private Date refundTime;		// 退款时间
	private String stuId;		// 学员编号
	private String stuName;		// 学员昵称
	private String coachId;		// 教练编号
	private String coachName;		// 教练昵称
	private String status; //打赏状态,1:冻结中,2:可使用,3:已退回
	private Date defrostTime; //解冻时间
	
	//传递参数
	private Date beginPayTime;		// 开始 支付时间
	private Date endPayTime;		// 结束 支付时间
	private Date beginRefundTime;		// 开始 退款时间
	private Date endRefundTime;		// 结束 退款时间
	private String payDetail; //打赏详情,比如:收到"李四"打赏
	
	public PayInfo() {
		super();
	}

	public PayInfo(String id){
		super(id);
	}

	@Length(min=0, max=128, message="订单号长度必须介于 0 和 128 之间")
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	@Length(min=0, max=16, message="支付方式,1:支付宝, 2: 微信长度必须介于 0 和 16 之间")
	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	
	@Length(min=0, max=128, message="支付宝交易号长度必须介于 0 和 128 之间")
	public String getAliTradeNo() {
		return aliTradeNo;
	}

	public void setAliTradeNo(String aliTradeNo) {
		this.aliTradeNo = aliTradeNo;
	}
	
	@Length(min=0, max=128, message="微信交易号长度必须介于 0 和 128 之间")
	public String getWxTradeNo() {
		return wxTradeNo;
	}

	public void setWxTradeNo(String wxTradeNo) {
		this.wxTradeNo = wxTradeNo;
	}
	
	@Length(min=0, max=1, message="是否已支付,0:否, 1:是长度必须介于 0 和 1 之间")
	public String getPayflag() {
		return payflag;
	}

	public void setPayflag(String payflag) {
		this.payflag = payflag;
	}
	
	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	@Length(min=0, max=1, message="是否退款,0:否, 1:是长度必须介于 0 和 1 之间")
	public String getRefundflag() {
		return refundflag;
	}

	public void setRefundflag(String refundflag) {
		this.refundflag = refundflag;
	}
	
	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	
	@Length(min=0, max=11, message="学员编号长度必须介于 0 和 11 之间")
	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	
	@Length(min=0, max=11, message="教练编号长度必须介于 0 和 11 之间")
	public String getCoachId() {
		return coachId;
	}

	public void setCoachId(String coachId) {
		this.coachId = coachId;
	}
	
	public Date getBeginPayTime() {
		return beginPayTime;
	}

	public void setBeginPayTime(Date beginPayTime) {
		this.beginPayTime = beginPayTime;
	}
	
	public Date getEndPayTime() {
		return endPayTime;
	}

	public void setEndPayTime(Date endPayTime) {
		this.endPayTime = endPayTime;
	}
		
	public Date getBeginRefundTime() {
		return beginRefundTime;
	}

	public void setBeginRefundTime(Date beginRefundTime) {
		this.beginRefundTime = beginRefundTime;
	}
	
	public Date getEndRefundTime() {
		return endRefundTime;
	}

	public void setEndRefundTime(Date endRefundTime) {
		this.endRefundTime = endRefundTime;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getPayDetail() {
		return payDetail;
	}

	public void setPayDetail(String payDetail) {
		this.payDetail = payDetail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getDefrostTime() {
		return defrostTime;
	}

	public void setDefrostTime(Date defrostTime) {
		this.defrostTime = defrostTime;
	}
		
}
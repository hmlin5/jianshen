/**
 * 
 */
package com.gimc.user.modules.b_withdraw.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 提现订单管理Entity
 * @author gu
 * @version 2018-05-09
 */
public class CashOrder extends DataEntity<CashOrder> {
	
	private static final long serialVersionUID = 1L;
	private String orderNum;		// 提现订单号
	private Integer gymId;		// 健身房id
	private String gymName;		// 健身房
	private Integer coachId;		// 教练id
	private String coachName;		// 提现教练名
	private Date applyTime;		// 申请时间
	private String phone;		// 教练手机号
	private String bankAccount;		// 教练银行账号
	private String withdrawAmount;		// 提现金额
	private String withdrawStatus;		// 提现状态
	private Date beginApplyTime;		// 开始 申请时间
	private Date endApplyTime;		// 结束 申请时间
	
	public CashOrder() {
		super();
	}

	public CashOrder(String id){
		super(id);
	}

	@Length(min=0, max=255, message="提现订单号长度必须介于 0 和 255 之间")
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	public Integer getGymId() {
		return gymId;
	}

	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}
	
	@Length(min=0, max=255, message="健身房长度必须介于 0 和 255 之间")
	public String getGymName() {
		return gymName;
	}

	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	
	public Integer getCoachId() {
		return coachId;
	}

	public void setCoachId(Integer coachId) {
		this.coachId = coachId;
	}
	
	@Length(min=0, max=128, message="提现教练名长度必须介于 0 和 128 之间")
	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	@Length(min=0, max=128, message="教练手机号长度必须介于 0 和 128 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=128, message="教练银行账号长度必须介于 0 和 128 之间")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public String getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(String withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	
	@Length(min=0, max=2, message="提现状态长度必须介于 0 和 2 之间")
	public String getWithdrawStatus() {
		return withdrawStatus;
	}

	public void setWithdrawStatus(String withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}
	
	public Date getBeginApplyTime() {
		return beginApplyTime;
	}

	public void setBeginApplyTime(Date beginApplyTime) {
		this.beginApplyTime = beginApplyTime;
	}
	
	public Date getEndApplyTime() {
		return endApplyTime;
	}

	public void setEndApplyTime(Date endApplyTime) {
		this.endApplyTime = endApplyTime;
	}
		
}
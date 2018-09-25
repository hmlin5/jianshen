/**
 * 
 */
package com.gimc.user.modules.b_user_withdraw.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 用户提现记录Entity
 * @author linhaomiao
 * @version 2018-07-04
 */
public class UserWithdraw extends DataEntity<UserWithdraw> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String userName;		// 用户姓名
	private String userPhone;		// 用户手机
	private String withdrawNo;		// 提现单号
	private double withdrawAmount;		// 提现金额
	private Date withdrawTime;		// 提现时间
	private String status;		// 提现状态,1:申请中,2:银行处理中,3,提现成功,4,提现失败,5,已取消
	private String headBankId;		// 提现总行id
	private String headBankName;		// 提现总行名称
	private String branchBankId;		// 提现分行id
	private String branchBankName;		// 提现分行名称
	private String bankCardNo;		// 提现分行卡号
	private String applicant;		// 申请人
	private String failReason;		// 失败原因
	private String dealer;		// 处理人
	private Date dealTime;		// 处理时间
	
	
	//传递参数
	private Date beginWithdrawTime;		// 开始 提现时间
	private Date endWithdrawTime;		// 结束 提现时间
	private String type;
	
	public UserWithdraw() {
		super();
	}

	public UserWithdraw(String id){
		super(id);
	}

	@Length(min=0, max=11, message="用户id长度必须介于 0 和 11 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=32, message="用户姓名长度必须介于 0 和 32 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=32, message="用户手机长度必须介于 0 和 32 之间")
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	@Length(min=0, max=64, message="提现单号长度必须介于 0 和 64 之间")
	public String getWithdrawNo() {
		return withdrawNo;
	}

	public void setWithdrawNo(String withdrawNo) {
		this.withdrawNo = withdrawNo;
	}
	
	public double getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(Date withdrawTime) {
		this.withdrawTime = withdrawTime;
	}
	
	@Length(min=0, max=32, message="提现状态,1:申请中,2:银行处理中,3,提现成功,4,提现失败,5,已取消长度必须介于 0 和 32 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=11, message="提现总行id长度必须介于 0 和 11 之间")
	public String getHeadBankId() {
		return headBankId;
	}

	public void setHeadBankId(String headBankId) {
		this.headBankId = headBankId;
	}
	
	@Length(min=0, max=64, message="提现总行名称长度必须介于 0 和 64 之间")
	public String getHeadBankName() {
		return headBankName;
	}

	public void setHeadBankName(String headBankName) {
		this.headBankName = headBankName;
	}
	
	@Length(min=0, max=11, message="提现分行id长度必须介于 0 和 11 之间")
	public String getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(String branchBankId) {
		this.branchBankId = branchBankId;
	}
	
	@Length(min=0, max=64, message="提现分行名称长度必须介于 0 和 64 之间")
	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}
	
	@Length(min=0, max=64, message="提现分行卡号长度必须介于 0 和 64 之间")
	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	
	@Length(min=0, max=32, message="申请人长度必须介于 0 和 32 之间")
	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	
	@Length(min=0, max=255, message="失败原因长度必须介于 0 和 255 之间")
	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	
	@Length(min=0, max=32, message="处理人长度必须介于 0 和 32 之间")
	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	
	public Date getBeginWithdrawTime() {
		return beginWithdrawTime;
	}

	public void setBeginWithdrawTime(Date beginWithdrawTime) {
		this.beginWithdrawTime = beginWithdrawTime;
	}
	
	public Date getEndWithdrawTime() {
		return endWithdrawTime;
	}

	public void setEndWithdrawTime(Date endWithdrawTime) {
		this.endWithdrawTime = endWithdrawTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
		
}
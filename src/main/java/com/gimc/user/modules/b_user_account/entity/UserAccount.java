/**
 * 
 */
package com.gimc.user.modules.b_user_account.entity;

import com.gimc.user.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 用户账户Entity
 * @author linhaomiao
 * @version 2018-06-30
 */
public class UserAccount extends DataEntity<UserAccount> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private double totalAmount;		// 总额
	private double usableAmount;		// 可提现总额
	private double withdrawAmount;		// 已提现总额
	private double freezeAmount;		// 冻结总额
	private String payPwd;		// 支付密码
	private String defaultBankCardId; //默认提现银行卡
	
	public UserAccount() {
		super();
	}

	public UserAccount(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getUsableAmount() {
		return usableAmount;
	}

	public void setUsableAmount(double usableAmount) {
		this.usableAmount = usableAmount;
	}

	public double getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	@Length(min=0, max=128, message="支付密码长度必须介于 0 和 128 之间")
	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public double getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(double freezeAmount) {
		this.freezeAmount = freezeAmount;
	}


	public String getDefaultBankCardId() {
		return defaultBankCardId;
	}

	public void setDefaultBankCardId(String defaultBankCardId) {
		this.defaultBankCardId = defaultBankCardId;
	}
	
}
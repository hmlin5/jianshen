/**
 * 
 */
package com.gimc.user.modules.b_user_bank.entity;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 用户绑定银行Entity
 * @author linhaomiao
 * @version 2018-07-04
 */
public class UserBank extends DataEntity<UserBank> {
	
	private static final long serialVersionUID = 1L;
	private String headBankId;		// 开户银行id
	private String headBankName;		// 开户银行名称
	private String branchBank;		// 分行
	private String ownerId;		// 持卡人id
	private String ownerName;		// 持卡人姓名
	private String cardNo;		// 卡号
	
	public UserBank() {
		super();
	}

	public UserBank(String id){
		super(id);
	}

	@Length(min=0, max=11, message="开户银行id长度必须介于 0 和 11 之间")
	public String getHeadBankId() {
		return headBankId;
	}

	public void setHeadBankId(String headBankId) {
		this.headBankId = headBankId;
	}
	
	@Length(min=0, max=64, message="开户银行名称长度必须介于 0 和 64 之间")
	public String getHeadBankName() {
		return headBankName;
	}

	public void setHeadBankName(String headBankName) {
		this.headBankName = headBankName;
	}
	
	@Length(min=0, max=64, message="分行长度必须介于 0 和 64 之间")
	public String getBranchBank() {
		return branchBank;
	}

	public void setBranchBank(String branchBank) {
		this.branchBank = branchBank;
	}
	
	@Length(min=0, max=11, message="持卡人id长度必须介于 0 和 11 之间")
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	@Length(min=0, max=64, message="持卡人姓名长度必须介于 0 和 64 之间")
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	@Length(min=0, max=64, message="卡号长度必须介于 0 和 64 之间")
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
}
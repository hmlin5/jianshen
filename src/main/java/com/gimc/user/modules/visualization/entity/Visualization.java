/**
 * 
 */
package com.gimc.user.modules.visualization.entity;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 今日实时Entity
 * @author xxx
 * @version 2018-07-17
 */
public class Visualization extends DataEntity<Visualization> {
	
	private static final long serialVersionUID = 1L;
	private String gymNum;		// 健身房数，用来得知是否有添加
	private String coachNum;		// 教练数，新增教练（激活或者未激活的都算&middot;&middot;）
	private String coachLogin;		// 教练登录数，当前时间内
	private String privateUser;		// 私教会员数，新增的会员
	private String userActivation;		// 会员激活量，新激活的会员数量
	private String privateLogin;		// 私教会员登录量
	private String contractOrder;		// 约单订单数量
	private String contractSuccess;		// 约单成功人数
	private String robbingOrder;		// 抢单数量
	private String robbingSuccess;		// 抢单成功数量
	private String arrangeOrder;		// 安排订单数
	private String arrangeSuccess;		// 安排订单成功数量
	
	public Visualization() {
		super();
	}

	public Visualization(String id){
		super(id);
	}

	@Length(min=0, max=100, message="健身房数，用来得知是否有添加长度必须介于 0 和 100 之间")
	public String getGymNum() {
		return gymNum;
	}

	public void setGymNum(String gymNum) {
		this.gymNum = gymNum;
	}
	
	@Length(min=0, max=100, message="教练数，新增教练（激活或者未激活的都算&middot;&middot;）长度必须介于 0 和 100 之间")
	public String getCoachNum() {
		return coachNum;
	}

	public void setCoachNum(String coachNum) {
		this.coachNum = coachNum;
	}
	
	@Length(min=0, max=100, message="教练登录数，当前时间内长度必须介于 0 和 100 之间")
	public String getCoachLogin() {
		return coachLogin;
	}

	public void setCoachLogin(String coachLogin) {
		this.coachLogin = coachLogin;
	}
	
	@Length(min=0, max=120, message="私教会员数，新增的会员长度必须介于 0 和 120 之间")
	public String getPrivateUser() {
		return privateUser;
	}

	public void setPrivateUser(String privateUser) {
		this.privateUser = privateUser;
	}
	
	@Length(min=0, max=100, message="会员激活量，新激活的会员数量长度必须介于 0 和 100 之间")
	public String getUserActivation() {
		return userActivation;
	}

	public void setUserActivation(String userActivation) {
		this.userActivation = userActivation;
	}
	
	@Length(min=0, max=110, message="私教会员登录量长度必须介于 0 和 110 之间")
	public String getPrivateLogin() {
		return privateLogin;
	}

	public void setPrivateLogin(String privateLogin) {
		this.privateLogin = privateLogin;
	}
	
	@Length(min=0, max=200, message="约单订单数量长度必须介于 0 和 200 之间")
	public String getContractOrder() {
		return contractOrder;
	}

	public void setContractOrder(String contractOrder) {
		this.contractOrder = contractOrder;
	}
	
	@Length(min=0, max=160, message="约单成功人数长度必须介于 0 和 160 之间")
	public String getContractSuccess() {
		return contractSuccess;
	}

	public void setContractSuccess(String contractSuccess) {
		this.contractSuccess = contractSuccess;
	}
	
	@Length(min=0, max=200, message="抢单数量长度必须介于 0 和 200 之间")
	public String getRobbingOrder() {
		return robbingOrder;
	}

	public void setRobbingOrder(String robbingOrder) {
		this.robbingOrder = robbingOrder;
	}
	
	@Length(min=0, max=160, message="抢单成功数量长度必须介于 0 和 160 之间")
	public String getRobbingSuccess() {
		return robbingSuccess;
	}

	public void setRobbingSuccess(String robbingSuccess) {
		this.robbingSuccess = robbingSuccess;
	}
	
	@Length(min=0, max=200, message="安排订单数长度必须介于 0 和 200 之间")
	public String getArrangeOrder() {
		return arrangeOrder;
	}

	public void setArrangeOrder(String arrangeOrder) {
		this.arrangeOrder = arrangeOrder;
	}
	
	@Length(min=0, max=160, message="安排订单成功数量长度必须介于 0 和 160 之间")
	public String getArrangeSuccess() {
		return arrangeSuccess;
	}

	public void setArrangeSuccess(String arrangeSuccess) {
		this.arrangeSuccess = arrangeSuccess;
	}
	
}
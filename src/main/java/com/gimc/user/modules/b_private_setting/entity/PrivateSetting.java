/**
 * 
 */
package com.gimc.user.modules.b_private_setting.entity;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 隐私设置Entity
 * @author linhaomiao
 * @version 2018-06-03
 */
public class PrivateSetting extends DataEntity<PrivateSetting> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String messagePerson;		// 私聊消息发送权限, 1:关注我的, 2, 双向关注的
	private String phonePublicLevel;		// 获取手机号权限, 1:双向关注, 2:关注我的, 3: 仅自己, 4:公开,5:仅课程主管与其他私教可见, 6:仅课程主管, 7:仅会员(名下和服务过的会员)
	private String messsageOrder;		// 是否接收订单更新推送,0:接收, 1:不接收
	private String messageCourseTip;		// 是否接收上课提醒消息推送,0:接收, 1:不接收
	
	public PrivateSetting() {
		super();
	}

	public PrivateSetting(String id){
		super(id);
	}

	@Length(min=0, max=11, message="用户id长度必须介于 0 和 11 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=16, message="私聊消息发送权限, 1:关注我的, 2, 双向关注的长度必须介于 0 和 16 之间")
	public String getMessagePerson() {
		return messagePerson;
	}

	public void setMessagePerson(String messagePerson) {
		this.messagePerson = messagePerson;
	}
	
	@Length(min=0, max=16, message="获取手机号权限, 1:双向关注, 2:关注我的, 3: 仅自己, 4:公开长度必须介于 0 和 16 之间")
	public String getPhonePublicLevel() {
		return phonePublicLevel;
	}

	public void setPhonePublicLevel(String phonePublicLevel) {
		this.phonePublicLevel = phonePublicLevel;
	}
	
	@Length(min=0, max=1, message="是否接收订单更新推送,0:接收, 1:不接收长度必须介于 0 和 1 之间")
	public String getMesssageOrder() {
		return messsageOrder;
	}

	public void setMesssageOrder(String messsageOrder) {
		this.messsageOrder = messsageOrder;
	}
	
	@Length(min=0, max=1, message="是否接收上课提醒消息推送,0:接收, 1:不接收长度必须介于 0 和 1 之间")
	public String getMessageCourseTip() {
		return messageCourseTip;
	}

	public void setMessageCourseTip(String messageCourseTip) {
		this.messageCourseTip = messageCourseTip;
	}
	
}
/**
 * 
 */
package com.gimc.user.modules.b_msg.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gimc.user.common.persistence.DataEntity;

/**
 * 融云消息Entity
 * @author linhaomiao
 * @version 2018-05-18
 */
public class Msg extends DataEntity<Msg> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String toUserType;		// 接收用户类型
	private String toUserId;		// 接收用户id
	private String toUserName;		// 接收用户姓名
	private String fromUserId;		// 发送用户id
	private String fromUserName;		// 发送用户姓名
	private String content;		// 内容
	private String isRead;		// 是否已读(0:未读, 1:已读)
	private String msgType;		// 消息类型 ,1:订单消息, 2:系统消息
	private Date createDate;   //消息时间
	
	public Msg() {
		super();
	}
	
	

	public Msg(String id){
		super(id);
	}

	@Length(min=0, max=128, message="标题长度必须介于 0 和 128 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=6, message="接收用户类型长度必须介于 0 和 6 之间")
	public String getToUserType() {
		return toUserType;
	}

	public void setToUserType(String toUserType) {
		this.toUserType = toUserType;
	}
	
	@Length(min=0, max=11, message="接收用户id长度必须介于 0 和 11 之间")
	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	
	@Length(min=0, max=64, message="接收用户姓名长度必须介于 0 和 64 之间")
	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	
	@Length(min=0, max=11, message="发送用户id长度必须介于 0 和 11 之间")
	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	
	@Length(min=0, max=64, message="发送用户姓名长度必须介于 0 和 64 之间")
	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	
	@Length(min=0, max=1000, message="内容长度必须介于 0 和 1000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=1, message="是否已读(0:未读, 1:已读)长度必须介于 0 和 1 之间")
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
	@Length(min=0, max=16, message="消息类型长度必须介于 0 和 16 之间")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
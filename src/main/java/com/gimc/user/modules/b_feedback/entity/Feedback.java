/**
 * 
 */
package com.gimc.user.modules.b_feedback.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

import com.drew.lang.annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 反馈列表Entity
 * @author gu
 * @version 2018-05-08
 */
public class Feedback extends DataEntity<Feedback> {
	
	private static final long serialVersionUID = 1L;
	private String feedbackType;		// 反馈类型
	private Date feedbackTime;		// 反馈时间
	private String feedbackPhone;		// 反馈手机号
	private String feedbackPerson;		// 反馈人昵称
	private String feedbackTitle;		// 反馈标题
	private String feedbackContent;		// 反馈内容
	private String replyStatus;		// 回复状态
	private Date replyTime;		// 回复时间
	private String replyPerson;		// 回复人
	private String replyContent;		// 回复内容
	private Date beginFeedbackTime;		// 开始 反馈时间
	private Date endFeedbackTime;		// 结束 反馈时间
	private String pictureUrl;		// 图片链接,多个以","隔开
	private String feedbackUserId;  //反馈人id
	
	
	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Feedback() {
		super();
	}

	public Feedback(String id){
		super(id);
	}

	@Length(min=0, max=2, message="反馈类型长度必须介于 0 和 2 之间")
	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	
	@Length(min=0, max=128, message="反馈手机号长度必须介于 0 和 128 之间")
	public String getFeedbackPhone() {
		return feedbackPhone;
	}

	public void setFeedbackPhone(String feedbackPhone) {
		this.feedbackPhone = feedbackPhone;
	}
	
	@Length(min=0, max=255, message="反馈人长度必须介于 0 和 255 之间")
	public String getFeedbackPerson() {
		return feedbackPerson;
	}

	public void setFeedbackPerson(String feedbackPerson) {
		this.feedbackPerson = feedbackPerson;
	}
	
	@Length(min=0, max=255, message="反馈标题长度必须介于 0 和 255 之间")
	public String getFeedbackTitle() {
		return feedbackTitle;
	}

	public void setFeedbackTitle(String feedbackTitle) {
		this.feedbackTitle = feedbackTitle;
	}
	
	public String getFeedbackContent() {
		return feedbackContent;
	}

	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}
	
	@Length(min=0, max=2, message="回复状态长度必须介于 0 和 2 之间")
	public String getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	
	@NotBlank(message="回复人不能为空")
	@Length(min=0, max=255, message="回复人长度必须介于 0 和 255 之间")
	public String getReplyPerson() {
		return replyPerson;
	}

	public void setReplyPerson(String replyPerson) {
		this.replyPerson = replyPerson;
	}
	
	@NotBlank(message="回复内容不能为空")
	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	
	public Date getBeginFeedbackTime() {
		return beginFeedbackTime;
	}

	public void setBeginFeedbackTime(Date beginFeedbackTime) {
		this.beginFeedbackTime = beginFeedbackTime;
	}
	
	public Date getEndFeedbackTime() {
		return endFeedbackTime;
	}

	public void setEndFeedbackTime(Date endFeedbackTime) {
		this.endFeedbackTime = endFeedbackTime;
	}

	public String getFeedbackUserId() {
		return feedbackUserId;
	}

	public void setFeedbackUserId(String feedbackUserId) {
		this.feedbackUserId = feedbackUserId;
	}
		
}
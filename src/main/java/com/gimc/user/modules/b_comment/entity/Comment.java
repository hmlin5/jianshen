/**
 * 
 */
package com.gimc.user.modules.b_comment.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 评价Entity
 * @author linhaomiao
 * @version 2018-06-06
 */
public class Comment extends DataEntity<Comment> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单id
	private String stuId;		// 会员id
	private String stuName;		// 会员姓名
	private Date commentTime;		// 评价时间
	private String coachId;		// 教练id
	private String coachName;		// 教练姓名
	private Date replyTime;		// 回复时间
	private String content;	 	//评论内容
	private String label;	 	//评论标签
	private String reply;	 	//回复内容
	private Integer major;		// 专业评星:1-5
	private Integer service;		// 服务评星:1-5
	private Integer total;		// 总分
	
	
	
	//传递参数
	private Date beginCommentTime;		// 开始 评价时间
	private Date endCommentTime;		// 结束 评价时间
	private Date beginReplyTime;		// 开始 回复时间
	private Date endReplyTime;		// 结束 回复时间
	private String gymId;   //健身房id
	private String sex; 
	private String headImgUrl; 
	
	public Comment() {
		super();
	}

	public Comment(String id){
		super(id);
	}

	@Length(min=0, max=11, message="订单编号长度必须介于 0 和 11 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=11, message="会员id长度必须介于 0 和 11 之间")
	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	
	@Length(min=0, max=64, message="会员姓名长度必须介于 0 和 64 之间")
	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	
	@Length(min=0, max=11, message="教练id长度必须介于 0 和 11 之间")
	public String getCoachId() {
		return coachId;
	}

	public void setCoachId(String coachId) {
		this.coachId = coachId;
	}
	
	@Length(min=0, max=64, message="教练姓名长度必须介于 0 和 64 之间")
	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	
	public Integer getMajor() {
		return major;
	}

	public void setMajor(Integer major) {
		this.major = major;
	}
	
	public Integer getService() {
		return service;
	}

	public void setService(Integer service) {
		this.service = service;
	}
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public Date getBeginCommentTime() {
		return beginCommentTime;
	}

	public void setBeginCommentTime(Date beginCommentTime) {
		this.beginCommentTime = beginCommentTime;
	}
	
	public Date getEndCommentTime() {
		return endCommentTime;
	}

	public void setEndCommentTime(Date endCommentTime) {
		this.endCommentTime = endCommentTime;
	}
		
	public Date getBeginReplyTime() {
		return beginReplyTime;
	}

	public void setBeginReplyTime(Date beginReplyTime) {
		this.beginReplyTime = beginReplyTime;
	}
	
	public Date getEndReplyTime() {
		return endReplyTime;
	}

	public void setEndReplyTime(Date endReplyTime) {
		this.endReplyTime = endReplyTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGymId() {
		return gymId;
	}

	public void setGymId(String gymId) {
		this.gymId = gymId;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
		
}
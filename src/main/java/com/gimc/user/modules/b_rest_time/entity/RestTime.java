/**
 * 
 */
package com.gimc.user.modules.b_rest_time.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 休息时间Entity
 * @author linhaomiao
 * @version 2018-05-25
 */
public class RestTime extends DataEntity<RestTime> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 教练id
	private String gymId;		// 健身房id
	private String coachName;   //教练姓名
	private Date restStartTime;		// 休息开始时间
	private Date restEndTime;		// 休息结束时间
	private String duration;		// 持续时间
	private String dayOfWeek;		// 星期
	private Date restDate;		// 休息年月日
	private String allDayRest;		// 是否全天休息(0:否, 1是)
	
	
	//传递参数
	private boolean sqlCheckTimeConflictFlag = false;  //数据库查询标识, 表示是否需要判断两个时间段是否有交集, 默认false表示不判断, true表示需要判断
	private Date beginTime;   // 判断时间是否有交集传递的开始时间
	private Date endTime;  	// 判断时间是否有交集传递的结束时间
	private String orderId;   //判断时间冲突需要排除的订单id
	
	public RestTime() {
		super();
	}

	public RestTime(String id){
		super(id);
	}

	@Length(min=0, max=11, message="教练id长度必须介于 0 和 11 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=10, message="健身房id长度必须介于 0 和 10 之间")
	public String getGymId() {
		return gymId;
	}

	public void setGymId(String gymId) {
		this.gymId = gymId;
	}
	
	@JsonFormat(pattern = "HH:mm")
	public Date getRestStartTime() {
		return restStartTime;
	}

	public void setRestStartTime(Date restStartTime) {
		this.restStartTime = restStartTime;
	}
	
	@JsonFormat(pattern = "HH:mm")
	public Date getRestEndTime() {
		return restEndTime;
	}

	public void setRestEndTime(Date restEndTime) {
		this.restEndTime = restEndTime;
	}
	
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	@Length(min=0, max=11, message="星期长度必须介于 0 和 11 之间")
	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRestDate() {
		return restDate;
	}

	public void setRestDate(Date restDate) {
		this.restDate = restDate;
	}
	
	@Length(min=0, max=1, message="是否全天休息(0:否, 1是)长度必须介于 0 和 1 之间")
	public String getAllDayRest() {
		return allDayRest;
	}

	public void setAllDayRest(String allDayRest) {
		this.allDayRest = allDayRest;
	}

	public boolean isSqlCheckTimeConflictFlag() {
		return sqlCheckTimeConflictFlag;
	}

	public void setSqlCheckTimeConflictFlag(boolean sqlCheckTimeConflictFlag) {
		this.sqlCheckTimeConflictFlag = sqlCheckTimeConflictFlag;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	
}
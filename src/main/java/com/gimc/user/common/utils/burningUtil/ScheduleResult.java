package com.gimc.user.common.utils.burningUtil;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ScheduleResult {

	private static final long serialVersionUID = 1L;
	
	private String userName;  //占用时间的用户名, 可以是我自己, 可以是订单的会员姓名
	private Date startTime;  //开始时间
	private Date endTime;   //结束时间
	private String statusName;  //状态名称, 比如"休息中","待上课"
	private Long timeLong;  //排序用
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Long getTimeLong() {
		return timeLong;
	}
	public void setTimeLong(Long timeLong) {
		this.timeLong = timeLong;
	}
	
	
	
}

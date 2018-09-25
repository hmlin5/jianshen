package com.gimc.user.modules.b_user_withdraw.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gimc.user.common.persistence.DataEntity;

public class MoneyDetail  extends DataEntity<MoneyDetail>{

	private static final long serialVersionUID = 1L;
	private String userId; //用户id
	private String content; //内容
	private String no; //单号
	private double amount; //总额
	private Date time; //时间
	private String type; //余额类型
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}

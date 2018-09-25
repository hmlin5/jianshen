/**
 * 
 */
package com.gimc.user.modules.b_user.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 短信验证码Entity
 * @author 短信验证码
 * @version 2018-01-08
 */
public class SysCheckCode extends DataEntity<SysCheckCode> {
	
	private static final long serialVersionUID = 1L;
	private String phoneNum;		// phone_num
	private String email;		// email
	private String checkCode;		// check_code
	private Date updateTime;		// update_time
	
	public SysCheckCode() {
		super();
	}

	public SysCheckCode(String id){
		super(id);
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	@Length(min=0, max=128, message="email长度必须介于 0 和 128 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=32, message="check_code长度必须介于 0 和 32 之间")
	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="update_time不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
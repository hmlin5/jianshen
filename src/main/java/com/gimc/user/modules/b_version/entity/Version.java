/**
 * 
 */
package com.gimc.user.modules.b_version.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.gimc.user.common.persistence.DataEntity;

/**
 * app版本Entity
 * @author linhaomiao
 * @version 2018-05-08
 */
public class Version extends DataEntity<Version> {
	
	private static final long serialVersionUID = 1L;
	private String versionNum;		// version_num
	private String versionUrl;		// version_url
	private String content;		// content
	private String deviceType;		// device_type
	private String mustUpdate;		// must_update
	private Date dateTime;		// date_time
	
	public Version() {
		super();
	}

	public Version(String id){
		super(id);
	}

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
	
	@Length(min=0, max=256, message="version_url长度必须介于 0 和 256 之间")
	public String getVersionUrl() {
		return versionUrl;
	}

	public void setVersionUrl(String versionUrl) {
		this.versionUrl = versionUrl;
	}
	
	@Length(min=0, max=1024, message="content长度必须介于 0 和 1024 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=1, message="device_type长度必须介于 0 和 1 之间")
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	@Length(min=0, max=1, message="must_update长度必须介于 0 和 1 之间")
	public String getMustUpdate() {
		return mustUpdate;
	}

	public void setMustUpdate(String mustUpdate) {
		this.mustUpdate = mustUpdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
}
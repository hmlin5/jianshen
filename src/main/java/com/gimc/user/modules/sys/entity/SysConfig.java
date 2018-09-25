/**
 * 
 */
package com.gimc.user.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 配置参数表Entity
 * @author xf
 * @version 2018-01-08
 */
public class SysConfig extends DataEntity<SysConfig> {
	
	private static final long serialVersionUID = 1L;
	private String prokey;		// prokey
	private String provalue;		// provalue
	private String mark;		// mark
	private String sign;		// 1000：date类型
	private String flag;		// flag
	private String remark;		// remark
	private String starttime;		// 开始时间
	private String endtime;		// 结束时间
	private Date updateTime;		// update_time
	
	public SysConfig() {
		super();
	}

	public SysConfig(String id){
		super(id);
	}

	@Length(min=0, max=255, message="prokey长度必须介于 0 和 255 之间")
	public String getProkey() {
		return prokey;
	}

	public void setProkey(String prokey) {
		this.prokey = prokey;
	}
	
	@Length(min=0, max=255, message="provalue长度必须介于 0 和 255 之间")
	public String getProvalue() {
		return provalue;
	}

	public void setProvalue(String provalue) {
		this.provalue = provalue;
	}
	
	@Length(min=0, max=255, message="mark长度必须介于 0 和 255 之间")
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	
	@Length(min=0, max=2, message="1000：date类型长度必须介于 0 和 2 之间")
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	@Length(min=0, max=2, message="flag长度必须介于 0 和 2 之间")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Length(min=0, max=255, message="remark长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=32, message="开始时间长度必须介于 0 和 32 之间")
	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	
	@Length(min=0, max=32, message="结束时间长度必须介于 0 和 32 之间")
	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
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
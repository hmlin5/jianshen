/**
 * 
 */
package com.gimc.user.modules.b_bmi_course.entity;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;

/**
 * bmi-推荐课程关系Entity
 * @author linhaomiao
 * @version 2018-05-13
 */
public class BmiCourse extends DataEntity<BmiCourse> {
	
	private static final long serialVersionUID = 1L;
	private String creatorType;		// 标准制定者,1:总后台,2:健身房,3:教练
	private Double zengjiMin;		// 增肌区间最小值
	private Double zengjiMax;		// 增肌区间最大值
	private Double suxingMin;		// 塑型区间最小值
	private Double suxingMax;		// 塑型区间最大值
	private Double jianzhiMin;		// 减脂区间最小值
	private Double jianzhiMax;		// 减脂区间最大值
	private String toUserId;		// 适用用户id
	private String userId;       //设置者id
	private Integer zengjiBase;//增肌基数
	private Integer suxingBase;//塑型基数
	private Integer jianzhiBase;//减值基数
	
	
	public Integer getZengjiBase() {
		return zengjiBase;
	}

	public void setZengjiBase(Integer zengjiBase) {
		this.zengjiBase = zengjiBase;
	}

	public Integer getSuxingBase() {
		return suxingBase;
	}

	public void setSuxingBase(Integer suxingBase) {
		this.suxingBase = suxingBase;
	}

	public Integer getJianzhiBase() {
		return jianzhiBase;
	}

	public void setJianzhiBase(Integer jianzhiBase) {
		this.jianzhiBase = jianzhiBase;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BmiCourse() {
		super();
	}

	public BmiCourse(String id){
		super(id);
	}

	@Length(min=0, max=1, message="标准制定者,1:总后台,2:健身房,3:教练长度必须介于 0 和 1 之间")
	public String getCreatorType() {
		return creatorType;
	}

	public void setCreatorType(String creatorType) {
		this.creatorType = creatorType;
	}
	
	public Double getZengjiMin() {
		return zengjiMin;
	}

	public void setZengjiMin(Double zengjiMin) {
		this.zengjiMin = zengjiMin;
	}
	
	public Double getZengjiMax() {
		return zengjiMax;
	}

	public void setZengjiMax(Double zengjiMax) {
		this.zengjiMax = zengjiMax;
	}
	
	public Double getSuxingMin() {
		return suxingMin;
	}

	public void setSuxingMin(Double suxingMin) {
		this.suxingMin = suxingMin;
	}
	
	public Double getSuxingMax() {
		return suxingMax;
	}

	public void setSuxingMax(Double suxingMax) {
		this.suxingMax = suxingMax;
	}
	
	public Double getJianzhiMin() {
		return jianzhiMin;
	}

	public void setJianzhiMin(Double jianzhiMin) {
		this.jianzhiMin = jianzhiMin;
	}
	
	public Double getJianzhiMax() {
		return jianzhiMax;
	}

	public void setJianzhiMax(Double jianzhiMax) {
		this.jianzhiMax = jianzhiMax;
	}
	
	@Length(min=0, max=11, message="适用用户id长度必须介于 0 和 11 之间")
	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	
}
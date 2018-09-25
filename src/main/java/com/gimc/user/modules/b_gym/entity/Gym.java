/**
 * 
 */
package com.gimc.user.modules.b_gym.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.modules.b_bmi_course.entity.BmiCourse;
import com.gimc.user.modules.sys.entity.Area;
import com.gimc.user.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gimc.user.common.persistence.DataEntity;

/**
 * 健身房Entity
 * @author linhaomiao
 * @version 2018-05-03
 */
public class Gym extends DataEntity<Gym> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 健身房名称
	private Integer userId;		// 健身房用户id
	private String loginName;		// 账号
	private String password;		// 密码
	private String servicePhone;		// 客服电话
	private String intro;		// 简介
	private String location;		// 位置
	private String province;		// 省
	private String city;		// 市
	private String district;		// 区
	private String longitute;		// 健身房经度
	private String latitute;		// 健身房纬度
	private String freezingState;		//冻结状态 0:正常, 1:冻结
	private String areaCode;//区号
	private String mondayTime;//周一营业时间, 以"-"分开, 前面是开始时间, 后面是结束时间
	private String tuesdayTime;//周二
	private String wednesdayTime;//周三
	private String thursdayTime;//周四
	private String fridayTime;//周五
	private String saturdayTime;//周六
	private String sundayTime;//周日
	private String imgUrl;//健身房logo
	private Double memberPrice;//会员价格
	
	
	
	//传递参数
	private int distance; //健身房与用户距离
	private int courseTime;		// 用户健身时长
	private int rank;	//排名
	private String burningGoal; //健身目标
	@JsonIgnore
	private BmiCourse bmiCourse;
	@JsonIgnore
	private Area area;
	@JsonIgnore
	private List<Gym> imgList = new ArrayList<Gym>();
	
	
	
	
	public Gym(String imgUrl, String hyperlink) {
		super();
		this.imgUrl = imgUrl;
		this.hyperlink = hyperlink;
	}

	public List<Gym> getImgList() {
		return imgList;
	}

	public void setImgList(List<Gym> imgList) {
		this.imgList = imgList;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getHyperlink() {
		return hyperlink;
	}

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}

	private String hyperlink;//图片链接
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getFreezingState() {
		return freezingState;
	}

	public void setFreezingState(String freezingState) {
		this.freezingState = freezingState;
	}

	public Gym() {
		super();
	}

	public Gym(String id){
		super(id);
	}

	@Length(min=0, max=64, message="健身房名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Length(min=0, max=64, message="账号长度必须介于 0 和 64 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Length(min=0, max=128, message="密码长度必须介于 0 和 128 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=32, message="客服电话长度必须介于 0 和 32 之间")
	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}
	
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	@Length(min=0, max=64, message="位置长度必须介于 0 和 64 之间")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Length(min=0, max=32, message="省长度必须介于 0 和 32 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=32, message="市长度必须介于 0 和 32 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=32, message="区长度必须介于 0 和 32 之间")
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	@Length(min=0, max=32, message="健身房经度长度必须介于 0 和 32 之间")
	public String getLongitute() {
		return longitute;
	}

	public void setLongitute(String longitute) {
		this.longitute = longitute;
	}
	
	@Length(min=0, max=32, message="健身房纬度长度必须介于 0 和 32 之间")
	public String getLatitute() {
		return latitute;
	}

	public void setLatitute(String latitute) {
		this.latitute = latitute;
	}
	
	public BmiCourse getBmiCourse() {
		return bmiCourse;
	}

	public void setBmiCourse(BmiCourse bmiCourse) {
		this.bmiCourse = bmiCourse;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getMondayTime() {
		return mondayTime;
	}

	public void setMondayTime(String mondayTime) {
		this.mondayTime = mondayTime;
	}

	public String getTuesdayTime() {
		return tuesdayTime;
	}

	public void setTuesdayTime(String tuesdayTime) {
		this.tuesdayTime = tuesdayTime;
	}

	public String getWednesdayTime() {
		return wednesdayTime;
	}

	public void setWednesdayTime(String wednesdayTime) {
		this.wednesdayTime = wednesdayTime;
	}

	public String getThursdayTime() {
		return thursdayTime;
	}

	public void setThursdayTime(String thursdayTime) {
		this.thursdayTime = thursdayTime;
	}

	public String getFridayTime() {
		return fridayTime;
	}

	public void setFridayTime(String fridayTime) {
		this.fridayTime = fridayTime;
	}

	public String getSaturdayTime() {
		return saturdayTime;
	}

	public void setSaturdayTime(String saturdayTime) {
		this.saturdayTime = saturdayTime;
	}

	public String getSundayTime() {
		return sundayTime;
	}

	public void setSundayTime(String sundayTime) {
		this.sundayTime = sundayTime;
	}

	public int getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(int courseTime) {
		this.courseTime = courseTime;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getBurningGoal() {
		return burningGoal;
	}

	public void setBurningGoal(String burningGoal) {
		this.burningGoal = burningGoal;
	}

	public Double getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(Double memberPrice) {
		this.memberPrice = memberPrice;
	}
}
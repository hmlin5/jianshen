/**
 * 
 */
package com.gimc.user.modules.b_backtest.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gimc.user.common.persistence.DataEntity;

/**
 * 后台测试用户Entity
 * 
 * @author gu
 * @version 2018-05-05
 */
public class BackTest extends DataEntity<BackTest> {

	private static final long serialVersionUID = 1L;
	private String loginName; // 登录名
	private String password; // 密码
	private String phone; // 手机号
	private String email; // 邮箱
	private String nickName; // 昵称
	private String realName; // 真实姓名
	private String idCard; // 身份证号码
	private String sex; // 性别
	private String headImgUrl; // 头像地址
	private String addr; // 所在地址
	private String userType; // 用户类型
	private String followNum; // 关注数量
	private String followerNum; // 粉丝数量
	private Date openCardDate; // 开卡日期
	private String height; // 身高(cm)
	private String weight; // 体重(g)
	private String persionSign; // 个人签名
	private String persionSummary; // 个人简介
	private String courseSupervisor; // 课程主管
	private String courseNum; // 推荐课程数量
	private String bindGymNum; // 绑定的健身馆数量
	private String accountStatus; // 账号状态
	private String uniqueIdentify; // 融云唯一身份标识
	private Date registTime; // 注册时间
	private Date birthday; // 生日
	private Date loginTime; // 上次登录时间
	private Date beginRegistTime; // 开始 注册时间
	private Date endRegistTime; // 结束 注册时间

	public BackTest() {
		super();
	}

	public BackTest(String id) {
		super(id);
	}

	@Length(min = 0, max = 64, message = "登录名长度必须介于 0 和 64 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Length(min = 0, max = 128, message = "密码长度必须介于 0 和 128 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Length(min = 0, max = 32, message = "手机号长度必须介于 0 和 32 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 0, max = 64, message = "邮箱长度必须介于 0 和 64 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min = 0, max = 64, message = "昵称长度必须介于 0 和 64 之间")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Length(min = 0, max = 64, message = "真实姓名长度必须介于 0 和 64 之间")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Length(min = 0, max = 64, message = "身份证号码长度必须介于 0 和 64 之间")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Length(min = 0, max = 2, message = "性别长度必须介于 0 和 2 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Length(min = 0, max = 128, message = "头像地址长度必须介于 0 和 128 之间")
	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	@Length(min = 0, max = 255, message = "所在地址长度必须介于 0 和 255 之间")
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Length(min = 0, max = 2, message = "用户类型长度必须介于 0 和 2 之间")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Length(min = 0, max = 11, message = "关注数量长度必须介于 0 和 11 之间")
	public String getFollowNum() {
		return followNum;
	}

	public void setFollowNum(String followNum) {
		this.followNum = followNum;
	}

	@Length(min = 0, max = 11, message = "粉丝数量长度必须介于 0 和 11 之间")
	public String getFollowerNum() {
		return followerNum;
	}

	public void setFollowerNum(String followerNum) {
		this.followerNum = followerNum;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpenCardDate() {
		return openCardDate;
	}

	public void setOpenCardDate(Date openCardDate) {
		this.openCardDate = openCardDate;
	}

	@Length(min = 0, max = 11, message = "身高(cm)长度必须介于 0 和 11 之间")
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Length(min = 0, max = 11, message = "体重(g)长度必须介于 0 和 11 之间")
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPersionSign() {
		return persionSign;
	}

	public void setPersionSign(String persionSign) {
		this.persionSign = persionSign;
	}

	public String getPersionSummary() {
		return persionSummary;
	}

	public void setPersionSummary(String persionSummary) {
		this.persionSummary = persionSummary;
	}

	@Length(min = 0, max = 128, message = "课程主管长度必须介于 0 和 128 之间")
	public String getCourseSupervisor() {
		return courseSupervisor;
	}

	public void setCourseSupervisor(String courseSupervisor) {
		this.courseSupervisor = courseSupervisor;
	}

	@Length(min = 0, max = 11, message = "推荐课程数量长度必须介于 0 和 11 之间")
	public String getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}

	@Length(min = 0, max = 11, message = "绑定的健身馆数量长度必须介于 0 和 11 之间")
	public String getBindGymNum() {
		return bindGymNum;
	}

	public void setBindGymNum(String bindGymNum) {
		this.bindGymNum = bindGymNum;
	}

	@Length(min = 0, max = 2, message = "账号状态长度必须介于 0 和 2 之间")
	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	@Length(min = 0, max = 255, message = "融云唯一身份标识长度必须介于 0 和 255 之间")
	public String getUniqueIdentify() {
		return uniqueIdentify;
	}

	public void setUniqueIdentify(String uniqueIdentify) {
		this.uniqueIdentify = uniqueIdentify;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getBeginRegistTime() {
		return beginRegistTime;
	}

	public void setBeginRegistTime(Date beginRegistTime) {
		this.beginRegistTime = beginRegistTime;
	}

	public Date getEndRegistTime() {
		return endRegistTime;
	}

	public void setEndRegistTime(Date endRegistTime) {
		this.endRegistTime = endRegistTime;
	}

}
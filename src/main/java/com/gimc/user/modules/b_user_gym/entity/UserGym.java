/**
 * 
 */
package com.gimc.user.modules.b_user_gym.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;
import com.gimc.user.modules.b_user_inbody.entity.UserInbody;

/**
 * 用户健身房关系Entity
 * @author linhaomiao
 * @version 2018-05-11
 */
public class UserGym extends DataEntity<UserGym> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String gymId;		// 健身房id
	private String relation;		// 与健身房关系(1：注册用户2：私教用户3：普通用户4：私教, 5:教练+学员)
	private int courseTime;		// 用户健身时长
	private Date cardStartDate;	  //开卡日期
	private Date cardEndDate; //卡截止日期
	private String bindFlag; //绑定标识, 0:绑定, 1: 解绑
	private Integer userHeight; //用户身高
	private Double userWeight; //用户体重
	private String userName; //用户姓名
	private String userSex; //用户性别
	private Date userBirthDate; //用户生日
	private Date userOnBoardingTime; //入职时间
	private Date inputTime; //后台录入时间
	private String coachLabel; //教练标签
	private String coachId; //课程主管
	private String persistFlag; //是否为存量客户,0:是, 1:否
	private Date unbindTime; //解除绑定时间
	private String freezeFlag; //冻结标识, 0:否, 1:是
	private String courseRecommendFlag; //会员是否已经推荐课程,1:已推荐, 其他:否
	private Double commissionRatio ;  //提成比例
	private Integer restNumber;//剩余课程数
	private Date update_date;
	private String update_by;
	private String remarks;
	private Double unitPrice;//课程单价

	private UserInbody userInbody;
	
	
	//传递参数
	private Double beginCourseTime;		// 开始 用户健身时长
	private Double endCourseTime;		// 结束 用户健身时长
	private String userPhone; //用户手机
	private String userHeadImgUrl; //用户头像
	private Date userLoginTime;
	
	public UserGym() {
		super();
	}

	public UserGym(String id){
		super(id);
	}

	@Length(min=0, max=11, message="用户id长度必须介于 0 和 11 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=11, message="健身房id长度必须介于 0 和 11 之间")
	public String getGymId() {
		return gymId;
	}

	public void setGymId(String gymId) {
		this.gymId = gymId;
	}
	
	@Length(min=0, max=2, message="属于健身房教练0或者学员1长度必须介于 0 和 2 之间")
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	public int getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(int courseTime) {
		this.courseTime = courseTime;
	}
	
	public Double getBeginCourseTime() {
		return beginCourseTime;
	}

	public void setBeginCourseTime(Double beginCourseTime) {
		this.beginCourseTime = beginCourseTime;
	}
	
	public Double getEndCourseTime() {
		return endCourseTime;
	}

	public void setEndCourseTime(Double endCourseTime) {
		this.endCourseTime = endCourseTime;
	}

	public Date getCardStartDate() {
		return cardStartDate;
	}

	public void setCardStartDate(Date cardStartDate) {
		this.cardStartDate = cardStartDate;
	}

	public Date getCardEndDate() {
		return cardEndDate;
	}

	public void setCardEndDate(Date cardEndDate) {
		this.cardEndDate = cardEndDate;
	}

	public String getBindFlag() {
		return bindFlag;
	}

	public void setBindFlag(String bindFlag) {
		this.bindFlag = bindFlag;
	}

	public Integer getUserHeight() {
		return userHeight;
	}

	public void setUserHeight(Integer userHeight) {
		this.userHeight = userHeight;
	}

	public Double getUserWeight() {
		return userWeight;
	}

	public void setUserWeight(Double userWeight) {
		this.userWeight = userWeight;
	}

	public String getCoachId() {
		return coachId;
	}

	public void setCoachId(String coachId) {
		this.coachId = coachId;
	}

	public String getPersistFlag() {
		return persistFlag;
	}

	public void setPersistFlag(String persistFlag) {
		this.persistFlag = persistFlag;
	}

	public Date getUnbindTime() {
		return unbindTime;
	}

	public void setUnbindTime(Date unbindTime) {
		this.unbindTime = unbindTime;
	}

	public String getFreezeFlag() {
		return freezeFlag;
	}

	public void setFreezeFlag(String freezeFlag) {
		this.freezeFlag = freezeFlag;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public Date getUserBirthDate() {
		return userBirthDate;
	}

	public void setUserBirthDate(Date userBirthDate) {
		this.userBirthDate = userBirthDate;
	}

	public Date getUserOnBoardingTime() {
		return userOnBoardingTime;
	}

	public void setUserOnBoardingTime(Date userOnBoardingTime) {
		this.userOnBoardingTime = userOnBoardingTime;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public String getCoachLabel() {
		return coachLabel;
	}

	public void setCoachLabel(String coachLabel) {
		this.coachLabel = coachLabel;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserHeadImgUrl() {
		return userHeadImgUrl;
	}

	public void setUserHeadImgUrl(String userHeadImgUrl) {
		this.userHeadImgUrl = userHeadImgUrl;
	}

	public Date getUserLoginTime() {
		return userLoginTime;
	}

	public void setUserLoginTime(Date userLoginTime) {
		this.userLoginTime = userLoginTime;
	}

	public String getCourseRecommendFlag() {
		return courseRecommendFlag;
	}

	public void setCourseRecommendFlag(String courseRecommendFlag) {
		this.courseRecommendFlag = courseRecommendFlag;
	}

	public UserInbody getUserInbody() {
		return userInbody;
	}

	public void setUserInbody(UserInbody userInbody) {
		this.userInbody = userInbody;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setCreate_by(String remarks) {
		this.remarks = remarks;
	}

	public Double getCommissionRatio() {
		return commissionRatio;
	}

	public void setCommissionRatio(Double commissionRatio) {
		this.commissionRatio = commissionRatio;
	}

	public Integer getRestNumber() {
		return restNumber;
	}

	public void setRestNumber(Integer restNumber) {
		this.restNumber = restNumber;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
}
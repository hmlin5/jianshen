/**
 * 
 */
package com.gimc.user.modules.b_user_inbody.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gimc.user.common.persistence.DataEntity;

/**
 * 用户Inbody数据Entity
 * @author linhaomiao
 * @version 2018-06-14
 */
public class UserInbody extends DataEntity<UserInbody> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String coachId;		// 教练id
	private String gymId;		// 健身房id
	private Double height;		// 身高
	private Double weight;		// 体重
	private Double bmiIndex;		// BMI指数
	private String skeletalMuscle;		// 骨骼肌
	private String bodyFatRate;		// 体脂率
	private String bodyFat;		// 体脂肪
	private String whr;		// 腰臀比
	private String basalMetabolism;		// 基础代谢
	private String muscleControl;		// 肌肉控制数
	private String fatControl;		// 脂肪控制数
	private String visceralAdiposeGrade;		// 内脏脂肪等级
	private String healthAssessmentScore;		// 健康评估分数
	private String pelvicStability;		// 骨盆稳定
	private String coreActivation;		// 核心激活
	private String respiratoryReconstruction;		// 呼吸重建
	private Date testTime;  //测试时间
	private Double waistline; //腰围
	private Double hipline; //臀围
	
	
	//传递参数
	private String beginBmiIndex;		// 开始 BMI指数
	private String endBmiIndex;		// 结束 BMI指数
	private Date beginCreateDate;		// 开始 评估时间
	private Date endCreateDate;		// 结束 评估时间
	
	public UserInbody() {
		super();
	}

	public UserInbody(String id){
		super(id);
	}

	@Length(min=0, max=11, message="用户id长度必须介于 0 和 11 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=11, message="教练id长度必须介于 0 和 11 之间")
	public String getCoachId() {
		return coachId;
	}

	public void setCoachId(String coachId) {
		this.coachId = coachId;
	}
	
	@Length(min=0, max=11, message="健身房id长度必须介于 0 和 11 之间")
	public String getGymId() {
		return gymId;
	}

	public void setGymId(String gymId) {
		this.gymId = gymId;
	}
	
	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}
	
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	public Double getBmiIndex() {
		return bmiIndex;
	}

	public void setBmiIndex(Double bmiIndex) {
		this.bmiIndex = bmiIndex;
	}
	
	@Length(min=0, max=16, message="骨骼肌长度必须介于 0 和 16 之间")
	public String getSkeletalMuscle() {
		return skeletalMuscle;
	}

	public void setSkeletalMuscle(String skeletalMuscle) {
		this.skeletalMuscle = skeletalMuscle;
	}
	
	@Length(min=0, max=16, message="体脂率长度必须介于 0 和 16 之间")
	public String getBodyFatRate() {
		return bodyFatRate;
	}

	public void setBodyFatRate(String bodyFatRate) {
		this.bodyFatRate = bodyFatRate;
	}
	
	@Length(min=0, max=16, message="体脂肪长度必须介于 0 和 16 之间")
	public String getBodyFat() {
		return bodyFat;
	}

	public void setBodyFat(String bodyFat) {
		this.bodyFat = bodyFat;
	}
	
	@Length(min=0, max=16, message="腰臀比长度必须介于 0 和 16 之间")
	public String getWhr() {
		return whr;
	}

	public void setWhr(String whr) {
		this.whr = whr;
	}
	
	@Length(min=0, max=16, message="基础代谢长度必须介于 0 和 16 之间")
	public String getBasalMetabolism() {
		return basalMetabolism;
	}

	public void setBasalMetabolism(String basalMetabolism) {
		this.basalMetabolism = basalMetabolism;
	}
	
	@Length(min=0, max=16, message="肌肉控制数长度必须介于 0 和 16 之间")
	public String getMuscleControl() {
		return muscleControl;
	}

	public void setMuscleControl(String muscleControl) {
		this.muscleControl = muscleControl;
	}
	
	@Length(min=0, max=16, message="脂肪控制数长度必须介于 0 和 16 之间")
	public String getFatControl() {
		return fatControl;
	}

	public void setFatControl(String fatControl) {
		this.fatControl = fatControl;
	}
	
	@Length(min=0, max=16, message="内脏脂肪等级长度必须介于 0 和 16 之间")
	public String getVisceralAdiposeGrade() {
		return visceralAdiposeGrade;
	}

	public void setVisceralAdiposeGrade(String visceralAdiposeGrade) {
		this.visceralAdiposeGrade = visceralAdiposeGrade;
	}
	
	@Length(min=0, max=16, message="健康评估分数长度必须介于 0 和 16 之间")
	public String getHealthAssessmentScore() {
		return healthAssessmentScore;
	}

	public void setHealthAssessmentScore(String healthAssessmentScore) {
		this.healthAssessmentScore = healthAssessmentScore;
	}
	
	@Length(min=0, max=16, message="骨盆稳定长度必须介于 0 和 16 之间")
	public String getPelvicStability() {
		return pelvicStability;
	}

	public void setPelvicStability(String pelvicStability) {
		this.pelvicStability = pelvicStability;
	}
	
	@Length(min=0, max=16, message="核心激活长度必须介于 0 和 16 之间")
	public String getCoreActivation() {
		return coreActivation;
	}

	public void setCoreActivation(String coreActivation) {
		this.coreActivation = coreActivation;
	}
	
	@Length(min=0, max=16, message="呼吸重建长度必须介于 0 和 16 之间")
	public String getRespiratoryReconstruction() {
		return respiratoryReconstruction;
	}

	public void setRespiratoryReconstruction(String respiratoryReconstruction) {
		this.respiratoryReconstruction = respiratoryReconstruction;
	}
	
	public String getBeginBmiIndex() {
		return beginBmiIndex;
	}

	public void setBeginBmiIndex(String beginBmiIndex) {
		this.beginBmiIndex = beginBmiIndex;
	}
	
	public String getEndBmiIndex() {
		return endBmiIndex;
	}

	public void setEndBmiIndex(String endBmiIndex) {
		this.endBmiIndex = endBmiIndex;
	}
		
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	@JsonFormat(pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getTestTime() {
		return testTime;
	}

	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}

	public Double getWaistline() {
		return waistline;
	}

	public void setWaistline(Double waistline) {
		this.waistline = waistline;
	}

	public Double getHipline() {
		return hipline;
	}

	public void setHipline(Double hipline) {
		this.hipline = hipline;
	}
		
	
}
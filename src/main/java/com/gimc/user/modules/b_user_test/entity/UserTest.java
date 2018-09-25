/**
 * 
 */
package com.gimc.user.modules.b_user_test.entity;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;
import com.gimc.user.modules.b_user.entity.AppUser;

/**
 * 用户测试结果Entity
 * @author linhaomiao
 * @version 2018-05-13
 */
public class UserTest extends DataEntity<UserTest> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String userName;		// 真实姓名
	private String age;		// 年龄
	private String sex;		// 性别
	private String height;		// 身高(cm)
	private String weight;		// 体重(g)
	private String restHabbit;		// 作息习惯
	private String sportFrequency;		// 运动频率
	private String workDuration;		// 工作时长
	private String dietHabbit;		// 饮食日常
	private String burningGoal;		// 健身目标
	private String restHabbitResult;		// 作息习惯评测结果
	private String sportFrequencyResult;		// 运动频率评测结果
	private String workDurationResult;		// 工作时长评测结果
	private String dietHabbitResult;		// 饮食日常评测结果
	private String burningGoalResult;		// 健身目标评测结果
	private String bmiIndex;		// BMI指数
	private String recommendCourse;		// 推荐课程类型
	private String beginAge;		// 开始 年龄
	private String endAge;		// 结束 年龄
	
	private AppUser user;
	
	
	public UserTest() {
		super();
	}

	public UserTest(String id){
		super(id);
	}

	@Length(min=0, max=11, message="用户id长度必须介于 0 和 11 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=32, message="真实姓名长度必须介于 0 和 32 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=16, message="年龄长度必须介于 0 和 16 之间")
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=11, message="身高(cm)长度必须介于 0 和 11 之间")
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
	@Length(min=0, max=11, message="体重(g)长度必须介于 0 和 11 之间")
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	@Length(min=0, max=64, message="作息习惯长度必须介于 0 和 64 之间")
	public String getRestHabbit() {
		return restHabbit;
	}

	public void setRestHabbit(String restHabbit) {
		this.restHabbit = restHabbit;
	}
	
	@Length(min=0, max=64, message="运动频率长度必须介于 0 和 64 之间")
	public String getSportFrequency() {
		return sportFrequency;
	}

	public void setSportFrequency(String sportFrequency) {
		this.sportFrequency = sportFrequency;
	}
	
	@Length(min=0, max=64, message="工作时长长度必须介于 0 和 64 之间")
	public String getWorkDuration() {
		return workDuration;
	}

	public void setWorkDuration(String workDuration) {
		this.workDuration = workDuration;
	}
	
	@Length(min=0, max=64, message="饮食日常长度必须介于 0 和 64 之间")
	public String getDietHabbit() {
		return dietHabbit;
	}

	public void setDietHabbit(String dietHabbit) {
		this.dietHabbit = dietHabbit;
	}
	
	@Length(min=0, max=64, message="健身目标长度必须介于 0 和 64 之间")
	public String getBurningGoal() {
		return burningGoal;
	}

	public void setBurningGoal(String burningGoal) {
		this.burningGoal = burningGoal;
	}
	
	@Length(min=0, max=64, message="作息习惯评测结果长度必须介于 0 和 64 之间")
	public String getRestHabbitResult() {
		return restHabbitResult;
	}

	public void setRestHabbitResult(String restHabbitResult) {
		this.restHabbitResult = restHabbitResult;
	}
	
	@Length(min=0, max=64, message="运动频率评测结果长度必须介于 0 和 64 之间")
	public String getSportFrequencyResult() {
		return sportFrequencyResult;
	}

	public void setSportFrequencyResult(String sportFrequencyResult) {
		this.sportFrequencyResult = sportFrequencyResult;
	}
	
	@Length(min=0, max=64, message="工作时长评测结果长度必须介于 0 和 64 之间")
	public String getWorkDurationResult() {
		return workDurationResult;
	}

	public void setWorkDurationResult(String workDurationResult) {
		this.workDurationResult = workDurationResult;
	}
	
	@Length(min=0, max=64, message="饮食日常评测结果长度必须介于 0 和 64 之间")
	public String getDietHabbitResult() {
		return dietHabbitResult;
	}

	public void setDietHabbitResult(String dietHabbitResult) {
		this.dietHabbitResult = dietHabbitResult;
	}
	
	@Length(min=0, max=64, message="健身目标评测结果长度必须介于 0 和 64 之间")
	public String getBurningGoalResult() {
		return burningGoalResult;
	}

	public void setBurningGoalResult(String burningGoalResult) {
		this.burningGoalResult = burningGoalResult;
	}
	
	@Length(min=0, max=64, message="BMI指数长度必须介于 0 和 64 之间")
	public String getBmiIndex() {
		return bmiIndex;
	}

	public void setBmiIndex(String bmiIndex) {
		this.bmiIndex = bmiIndex;
	}
	
	@Length(min=0, max=64, message="推荐课程类型长度必须介于 0 和 64 之间")
	public String getRecommendCourse() {
		return recommendCourse;
	}

	public void setRecommendCourse(String recommendCourse) {
		this.recommendCourse = recommendCourse;
	}
	
	public String getBeginAge() {
		return beginAge;
	}

	public void setBeginAge(String beginAge) {
		this.beginAge = beginAge;
	}
	
	public String getEndAge() {
		return endAge;
	}

	public void setEndAge(String endAge) {
		this.endAge = endAge;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}
		
}
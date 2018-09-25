/**
 * 
 */
package com.gimc.user.modules.b_user_course.entity;

import java.util.List;
import java.util.Map;

import com.gimc.user.modules.sys.entity.Dict;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gimc.user.common.persistence.DataEntity;
import com.gimc.user.common.utils.burningUtil.CourseResult;
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_user_movement.entity.UserMovement;

/**
 * 会员推荐课程Entity
 * @author linhaomiao
 * @version 2018-05-30
 */
public class UserCourse extends DataEntity<UserCourse> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 会员id
	private String coachId;		// 教练id
	private String courseId;		// 课程id
	private int seq;		// 排序
	private String finishFlag;		// 是否已完成上课 1:表示已完成,2:表示已预约
	private String gymId;		// 健身房id
	private String catalogId;		// 分类id
	private String catalogName;		// 分类名称
	private String gymImgUrl;		// 健身房图片url
	private String recomendRage;		// 推荐范围
	private String name;		// 课程名称
	private String description;		// 课程描述
	private Integer movementNum;		// 动作总数
	private Double duration;		// 课程上课时长
	private String gymName;		// 健身房名称
	private String movementIds;		// 动作id
	private int recommendAge;		// 第几次推荐
	private String instrumentSummary;//器械汇总
	private Dict dict;//字典字段
	
	
	//传递参数
	private List<CourseResult> movements; //课程的分类动作列表
	@JsonIgnore
	private List<String> includeUserIds;  //包含的会员id
	
	public UserCourse() {
		super();
	}

	public UserCourse(String id){
		super(id);
	}

	@Length(min=0, max=64, message="会员id长度必须介于 0 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="教练id长度必须介于 0 和 64 之间")
	public String getCoachId() {
		return coachId;
	}

	public void setCoachId(String coachId) {
		this.coachId = coachId;
	}
	
	@Length(min=0, max=64, message="课程id长度必须介于 0 和 64 之间")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	@Length(min=0, max=11, message="排序长度必须介于 0 和 11 之间")
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	@Length(min=0, max=1, message="是否已完成上课长度必须介于 0 和 1 之间")
	public String getFinishFlag() {
		return finishFlag;
	}

	public void setFinishFlag(String finishFlag) {
		this.finishFlag = finishFlag;
	}
	
	@Length(min=0, max=64, message="健身房id长度必须介于 0 和 64 之间")
	public String getGymId() {
		return gymId;
	}

	public void setGymId(String gymId) {
		this.gymId = gymId;
	}
	
	@Length(min=0, max=11, message="分类id长度必须介于 0 和 11 之间")
	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	
	@Length(min=0, max=64, message="分类名称长度必须介于 0 和 64 之间")
	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	
	@Length(min=0, max=128, message="健身房图片url长度必须介于 0 和 128 之间")
	public String getGymImgUrl() {
		return gymImgUrl;
	}

	public void setGymImgUrl(String gymImgUrl) {
		this.gymImgUrl = gymImgUrl;
	}
	
	@Length(min=0, max=128, message="推荐范围长度必须介于 0 和 128 之间")
	public String getRecomendRage() {
		return recomendRage;
	}

	public void setRecomendRage(String recomendRage) {
		this.recomendRage = recomendRage;
	}
	
	@Length(min=0, max=128, message="课程名称长度必须介于 0 和 128 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=128, message="课程描述长度必须介于 0 和 128 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getMovementNum() {
		return movementNum;
	}

	public void setMovementNum(Integer movementNum) {
		this.movementNum = movementNum;
	}
	
	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}
	
	@Length(min=0, max=128, message="健身房名称长度必须介于 0 和 128 之间")
	public String getGymName() {
		return gymName;
	}

	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	
	@Length(min=0, max=128, message="动作id长度必须介于 0 和 128 之间")
	public String getMovementIds() {
		return movementIds;
	}

	public void setMovementIds(String movementIds) {
		this.movementIds = movementIds;
	}

	public int getRecommendAge() {
		return recommendAge;
	}

	public void setRecommendAge(int recommendAge) {
		this.recommendAge = recommendAge;
	}

	public List<CourseResult> getMovements() {
		return movements;
	}

	public void setMovements(List<CourseResult> movements) {
		this.movements = movements;
	}

	public List<String> getIncludeUserIds() {
		return includeUserIds;
	}

	public void setIncludeUserIds(List<String> includeUserIds) {
		this.includeUserIds = includeUserIds;
	}

	public Dict getDict() {
		return dict;
	}

	public void setDict(Dict dict) {
		this.dict = dict;
	}

	public String getInstrumentSummary() {
		return instrumentSummary;
	}

	public void setInstrumentSummary(String instrumentSummary) {
		this.instrumentSummary = instrumentSummary;
	}
}
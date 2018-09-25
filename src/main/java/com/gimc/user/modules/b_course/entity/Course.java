/**
 * 
 */
package com.gimc.user.modules.b_course.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gimc.user.common.persistence.DataEntity;
import com.gimc.user.common.utils.burningUtil.CourseResult;

/**
 * 课程Entity
 * @author linhaomiao
 * @version 2018-04-30
 */
public class Course extends DataEntity<Course> {
	
	private static final long serialVersionUID = 1L;
	private Integer catalogId;		// 分类id
	private String catalogIds;		// 分类id
	private String catalogName;		// 分类名称
	private Integer gymId;		// 所属健身房
	private String gymImgUrl;		// 健身房图片url
	private String recomendRage;		// 推荐范围
	private String name;		// 课程名称
	private String description;		// 课程描述
	private Integer movementNum;		// 动作总数
	private Double duration;		// 课程上课时长
	private String gymName;		// 健身房名称
	private String bCourseField01;		// 预留字段1
	private String bCourseField02;		// 预留字段2
	private String movementIds;		// 动作分类id
	
	//传递参数
	@JsonIgnore
	private List<String> includeIds;  //包含的id
	private List<CourseResult> movements; //课程的分类动作列表
	
	public String getMovementIds() {
		return movementIds;
	}

	public void setMovementIds(String movementIds) {
		this.movementIds = movementIds;
	}


	//视图层参数
	private String catalogFullName;
	
	public Course() {
		super();
	}

	public Course(String id){
		super(id);
	}

	
	
	
	public Integer getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}
	
	@Length(min=0, max=64, message="分类名称长度必须介于 0 和 64 之间")
	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	
	public Integer getGymId() {
		return gymId;
	}

	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}
	
	@Length(min=0, max=128, message="健身房图片url长度必须介于 0 和 128 之间")
	public String getGymImgUrl() {
		return gymImgUrl;
	}

	public void setGymImgUrl(String gymImgUrl) {
		this.gymImgUrl = gymImgUrl;
	}
	
	@Length(min=0, max=128, message="推荐范围长度必须介于 0 和 128 之间")
	@NotBlank(message="推荐范围不能为空")
	public String getRecomendRage() {
		return recomendRage;
	}

	
	public void setRecomendRage(String recomendRage) {
		this.recomendRage = recomendRage;
	}
	
	@Length(min=0, max=128, message="课程名称长度必须介于 0 和 128 之间")
	@NotBlank(message="课程名称不能为空")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank(message="课程描述不能为空")
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
	
	@NotNull(message="课程时长不能为空")
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
	
	@Length(min=0, max=64, message="预留字段1长度必须介于 0 和 64 之间")
	public String getBCourseField01() {
		return bCourseField01;
	}

	public void setBCourseField01(String bCourseField01) {
		this.bCourseField01 = bCourseField01;
	}
	
	@Length(min=0, max=64, message="预留字段2长度必须介于 0 和 64 之间")
	public String getBCourseField02() {
		return bCourseField02;
	}

	public void setBCourseField02(String bCourseField02) {
		this.bCourseField02 = bCourseField02;
	}

	public String getCatalogFullName() {
		return catalogFullName;
	}

	public void setCatalogFullName(String catalogFullName) {
		this.catalogFullName = catalogFullName;
	}

	@NotNull(message="课程分类不能为空")
	public String getCatalogIds() {
		return catalogIds;
	}

	
	public void setCatalogIds(String catalogIds) {
		this.catalogIds = catalogIds;
	}

	public List<String> getIncludeIds() {
		return includeIds;
	}

	public void setIncludeIds(List<String> includeIds) {
		this.includeIds = includeIds;
	}

	public List<CourseResult> getMovements() {
		return movements;
	}

	public void setMovements(List<CourseResult> movements) {
		this.movements = movements;
	}
	
}
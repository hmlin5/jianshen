/**
 * 
 */
package com.gimc.user.modules.b_order.entity;

import com.gimc.user.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gimc.user.common.persistence.DataEntity;
import com.gimc.user.modules.b_gym.entity.Gym;

/**
 * 订单Entity
 * @author linhaomiao
 * @version 2018-06-01
 */
public class Order extends DataEntity<Order> {
	
	private static final long serialVersionUID = 1L;
	private String orderNum;		// 订单编号
	private String gymName;		// 健身房名称
	private String stuId;		// 学员编号
	private String stuName;		// 学员姓名
	private String stuPhone;		// 学员手机
	private String stuLevel;		// 学员级别
	private String stuImgUrl;		// 学员头像
	private String stuSex;		// 学员性别
	private String coachId;		// 教练编号
	private String coachName;		// 教练姓名
	private String coachPhone;		// 教练手机号码
	private String coachSex;		// 教练性别
	private String type;		// 订单类型
	private String isConfirm;		// 管理员是否确认结算,0:未结算, 1:已结算
	private String canEvaluate;		// 用户是否可以评价, 0:可以, 1:否
	private String opRecord;		// 订单操作记录
	private Date appointmentTime;		// 预约时间
	private String className;		// 课程名称
	private String coachLabel;		// 教练标签
	private String coachImgUrl;		// 教练头像地址
	private String comment;		// 评价id
	private double addFee;		// 感谢费
	private Date startTime;		// 实际开始时间
	private Date endTime;		// 实际结束时间
	private String courseDuration;		// 上课时长
	private String courseId;		// 课程id
	private String gymId;		// 健身房id
	private String status;		// 订单状态
	private int version;		// 订单版本(抢单用)
	private String coachReply;  //教练对评价回复
	private String cancelor; //取消人
	private Date cancelTime; //取消时间
	private String cancelReason; //取消原因
	private String coachScore; //教练评分
	private Date RobTime; //教练抢单时间
	private String stuIds;		// 教练约课所有学员编号,以","隔开
	private String stuNames;		// 教练约课所有学员姓名,以","隔开
	private String refundFlag;  //退款标识, 0:不退款
	private Date gymTime;  //健身房结算时间
	private Date platformTime;  //平台结算时间
	private String isGym;//健身房结算状态
	private String oldIsGym;//修改健身房结算状态
	private String coachExtract;//教练提成
	private String platformExtract;//平台提成
	private String coachProportion;//教练比例
	private String platformProportion;//平台比例
	private Double  memberPrice;//会员价格

	
	//传递参数
	private Date beginAppointmentTime;		// 开始 预约时间
	private Date endAppointmentTime;		// 结束 预约时间
	private Gym gym; //订单健身房信息
	private int averageRobTime;
	@JsonIgnore
	private List<String> includeStatus;  //sql查询用, 包含状态集合
	@JsonIgnore
	private List<String> excludeStatus;  //sql查询用, 排除状态集合
	private boolean orNullFlag = false;     //sql查询"或"标识
	private boolean containFlag = false;   //sql查询"包含"标识
	private boolean allCheckFlag = false;   	//sql查询会员所有订单标识
	private int rank; // 排名
	private String totalCourseTime; // 总上课时长
	private String rankObject;  //sql排名的对象,学员stu_id或教练coach_id
	private String keyWord; //搜索关键词
	private String oldIsConfirm; //修改结算标识用,修改前状态
	
	public Order() {
		super();
	}

	public Order(String id){
		super(id);
	}
	@ExcelField(title="订单号", align=2, sort=10)
	@Length(min=0, max=128, message="订单编号长度必须介于 0 和 128 之间")
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	@ExcelField(title="健身房名称", align=2, sort=40)
	@Length(min=0, max=128, message="健身房名称长度必须介于 0 和 128 之间")
	public String getGymName() {
		return gymName;
	}

	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	
	@Length(min=0, max=11, message="学员编号长度必须介于 0 和 11 之间")
	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	@ExcelField(title="学员姓名", align=2, sort=50)
	@Length(min=0, max=32, message="学员姓名长度必须介于 0 和 32 之间")
	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	@ExcelField(title="学员手机", align=2, sort=60)
	@Length(min=0, max=32, message="学员手机长度必须介于 0 和 32 之间")
	public String getStuPhone() {
		return stuPhone;
	}

	public void setStuPhone(String stuPhone) {
		this.stuPhone = stuPhone;
	}
	
	@Length(min=0, max=64, message="学员级别长度必须介于 0 和 64 之间")
	public String getStuLevel() {
		return stuLevel;
	}

	public void setStuLevel(String stuLevel) {
		this.stuLevel = stuLevel;
	}
	
	@Length(min=0, max=128, message="学员头像长度必须介于 0 和 128 之间")
	public String getStuImgUrl() {
		return stuImgUrl;
	}

	public void setStuImgUrl(String stuImgUrl) {
		this.stuImgUrl = stuImgUrl;
	}
	
	@Length(min=0, max=1, message="学员性别长度必须介于 0 和 1 之间")
	public String getStuSex() {
		return stuSex;
	}

	public void setStuSex(String stuSex) {
		this.stuSex = stuSex;
	}
	
	@Length(min=0, max=11, message="教练编号长度必须介于 0 和 11 之间")
	public String getCoachId() {
		return coachId;
	}

	public void setCoachId(String coachId) {
		this.coachId = coachId;
	}
	@ExcelField(title="教练姓名", align=2, sort=70)
	@Length(min=0, max=32, message="教练姓名长度必须介于 0 和 32 之间")
	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	@ExcelField(title="教练手机", align=2, sort=80)
	@Length(min=0, max=32, message="教练手机号码长度必须介于 0 和 32 之间")
	public String getCoachPhone() {
		return coachPhone;
	}

	public void setCoachPhone(String coachPhone) {
		this.coachPhone = coachPhone;
	}
	
	@Length(min=0, max=1, message="教练性别长度必须介于 0 和 1 之间")
	public String getCoachSex() {
		return coachSex;
	}

	public void setCoachSex(String coachSex) {
		this.coachSex = coachSex;
	}
	@ExcelField(title="订单类型", align=2, sort=90,dictType = "order_type")
	@Length(min=0, max=2, message="订单类型长度必须介于 0 和 2 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=1, message="管理员是否确认长度必须介于 0 和 1 之间")
	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}
	
	@Length(min=0, max=1, message="用户是否可以评价长度必须介于 0 和 1 之间")
	public String getCanEvaluate() {
		return canEvaluate;
	}

	public void setCanEvaluate(String canEvaluate) {
		this.canEvaluate = canEvaluate;
	}
	
	public String getOpRecord() {
		return opRecord;
	}

	public void setOpRecord(String opRecord) {
		this.opRecord = opRecord;
	}
	@ExcelField(title="预约时间", align=2, sort=100)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	
	@Length(min=0, max=64, message="课程名称长度必须介于 0 和 64 之间")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	@Length(min=0, max=128, message="教练标签长度必须介于 0 和 128 之间")
	public String getCoachLabel() {
		return coachLabel;
	}

	public void setCoachLabel(String coachLabel) {
		this.coachLabel = coachLabel;
	}
	
	@Length(min=0, max=128, message="教练头像地址长度必须介于 0 和 128 之间")
	public String getCoachImgUrl() {
		return coachImgUrl;
	}

	public void setCoachImgUrl(String coachImgUrl) {
		this.coachImgUrl = coachImgUrl;
	}
	
	@Length(min=0, max=1000, message="学员评价长度必须介于 0 和 1000 之间")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public double getAddFee() {
		return addFee;
	}

	public void setAddFee(double addFee) {
		this.addFee = addFee;
	}
	@ExcelField(title="上课时间", align=2, sort=110)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@ExcelField(title="下课时间", align=2, sort=120)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getCourseDuration() {
		return courseDuration;
	}

	public void setCourseDuration(String courseDuration) {
		this.courseDuration = courseDuration;
	}
	
	@Length(min=0, max=11, message="课程id长度必须介于 0 和 11 之间")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	@Length(min=0, max=11, message="健身房id长度必须介于 0 和 11 之间")
	public String getGymId() {
		return gymId;
	}

	public void setGymId(String gymId) {
		this.gymId = gymId;
	}
	
	@Length(min=0, max=11, message="订单状态长度必须介于 0 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public Date getBeginAppointmentTime() {
		return beginAppointmentTime;
	}

	public void setBeginAppointmentTime(Date beginAppointmentTime) {
		this.beginAppointmentTime = beginAppointmentTime;
	}
	
	public Date getEndAppointmentTime() {
		return endAppointmentTime;
	}

	public void setEndAppointmentTime(Date endAppointmentTime) {
		this.endAppointmentTime = endAppointmentTime;
	}

	public String getCoachReply() {
		return coachReply;
	}

	public void setCoachReply(String coachReply) {
		this.coachReply = coachReply;
	}

	public String getCancelor() {
		return cancelor;
	}

	public void setCancelor(String cancelor) {
		this.cancelor = cancelor;
	}

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCoachScore() {
		return coachScore;
	}

	public void setCoachScore(String coachScore) {
		this.coachScore = coachScore;
	}

	public Date getRobTime() {
		return RobTime;
	}

	public void setRobTime(Date robTime) {
		RobTime = robTime;
	}

	public Gym getGym() {
		return gym;
	}

	public void setGym(Gym gym) {
		this.gym = gym;
	}

	public int getAverageRobTime() {
		return averageRobTime;
	}

	public void setAverageRobTime(int averageRobTime) {
		this.averageRobTime = averageRobTime;
	}

	public List<String> getIncludeStatus() {
		return includeStatus;
	}

	public void setIncludeStatus(List<String> includeStatus) {
		this.includeStatus = includeStatus;
	}

	public boolean isOrNullFlag() {
		return orNullFlag;
	}

	public void setOrNullFlag(boolean orNullFlag) {
		this.orNullFlag = orNullFlag;
	}

	public List<String> getExcludeStatus() {
		return excludeStatus;
	}

	public void setExcludeStatus(List<String> excludeStatus) {
		this.excludeStatus = excludeStatus;
	}

	public String getStuIds() {
		return stuIds;
	}

	public void setStuIds(String stuIds) {
		this.stuIds = stuIds;
	}

	public String getStuNames() {
		return stuNames;
	}

	public void setStuNames(String stuNames) {
		this.stuNames = stuNames;
	}

	public boolean isContainFlag() {
		return containFlag;
	}

	public void setContainFlag(boolean containFlag) {
		this.containFlag = containFlag;
	}

	public boolean isAllCheckFlag() {
		return allCheckFlag;
	}

	public void setAllCheckFlag(boolean allCheckFlag) {
		this.allCheckFlag = allCheckFlag;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getRankObject() {
		return rankObject;
	}

	public void setRankObject(String rankObject) {
		this.rankObject = rankObject;
	}

	public String getTotalCourseTime() {
		return totalCourseTime;
	}

	public void setTotalCourseTime(String totalCourseTime) {
		this.totalCourseTime = totalCourseTime;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getOldIsConfirm() {
		return oldIsConfirm;
	}

	public void setOldIsConfirm(String oldIsConfirm) {
		this.oldIsConfirm = oldIsConfirm;
	}
	@ExcelField(title="健身房结算日期", align=2, sort=30)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getGymTime() {
		return gymTime;
	}

	public void setGymTime(Date gymTime) {
		this.gymTime = gymTime;
	}
	@ExcelField(title="平台结算日期", align=2, sort=20)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getPlatformTime() {
		return platformTime;
	}

	public void setPlatformTime(Date platformTime) {
		this.platformTime = platformTime;
	}

	public String getIsGym() {
		return isGym;
	}

	public void setIsGym(String isGym) {
		this.isGym = isGym;
	}

	public String getOldIsGym() {
		return oldIsGym;
	}

	public void setOldIsGym(String oldIsGym) {
		this.oldIsGym = oldIsGym;
	}
	@ExcelField(title="教练提成", align=2, sort=140)
	public String getCoachExtract() {
		return coachExtract;
	}

	public void setCoachExtract(String coachExtract) {
		this.coachExtract = coachExtract;
	}
	@ExcelField(title="平台提成", align=2, sort=160)
	public String getPlatformExtract() {
		return platformExtract;
	}

	public void setPlatformExtract(String platformExtract) {
		this.platformExtract = platformExtract;
	}
	@ExcelField(title="教练提成比例", align=2, sort=130)
	public String getCoachProportion() {
		return coachProportion;
	}

	public void setCoachProportion(String coachProportion) {
		this.coachProportion = coachProportion;
	}
	@ExcelField(title="平台提成比例", align=2, sort=150)
	public String getPlatformProportion() {
		return platformProportion;
	}

	public void setPlatformProportion(String platformProportion) {
		this.platformProportion = platformProportion;
	}
	@ExcelField(title="课程价格", align=2, sort=125)
	public Double getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(Double memberPrice) {
		this.memberPrice = memberPrice;
	}
}
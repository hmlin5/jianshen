/**
 * 
 */
package com.gimc.user.modules.b_user.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gimc.user.common.persistence.DataEntity;
import com.gimc.user.modules.b_bmi_course.entity.BmiCourse;
import com.gimc.user.modules.b_user_gym.entity.UserGym;

/**
 * 用户Entity
 * 
 * @author gu
 * @version 2018-05-08
 */
public class AppUser extends DataEntity<AppUser> {

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
	private String bgImgUrl; // app"我的"界面背景图片地址
	private String coachImgUrl; // app个人中心教练背景图片地址
	private String stuImgUrl; // app个人中心学员背景图片地址
	private String activeGymId; // 活跃健身房id
	private Date birthday; // 生日
	private Date registTime; // 注册日期
	private Date loginTime; // 上一次登录时间
	private Date activeTime; // 激活时间
	private String userType; // 用户类型
	private Date openCardTime; // 开卡日期
	private String endCardTime; // 开卡截止日期
	private String gymEntryTime; // 健身房录入用户时间
	private Integer assessment;//是否评估
	private Integer courseCount; // 推荐课程数量
	private Integer gymCount; // 绑定健身房数量
	private Integer height; // 身高(cm)
	private Integer weight; // 体重(g)
	private String signature; // 个人签名
	private String intro; // 个人介绍(教练必填完善资料)
	private String lockFlag; // 是否账户被锁定
	private String province; // 所在省
	private String city; // 所在市
	private String district; // 所在地区
	private String bgType; // 背景类型
	private Integer followNum; // 关注数量
	private Integer followerNum; // 粉丝数量
	private String token; // app登录凭证
	private String rcToken; // 融云用户标识
	private String label; // 标签
	private String phonePrivateLevel; // 手机号码可见范围
	private String msgReceiveLevel; // 接收消息范围
	private Integer chargerId; // 课程主管id
	private String chargerName; // 教练名字
	private String activateFlag; // 是否已激活
	private String coachFlag; // 教练身份 , 0:否, 1:是
	private String stuFlag; // 学员身份, 0:否, 1:是
	private String source; // 来源, 0:后台录入, 1:app注册
	private String firstVisitStuFlag; // 是否第一次访问学员系统, 0:是,1:不是
	private String firstVisitCoachFlag; // 是否第一次访问教练系统, 0:是,1:不是
	private String burningGoal;		// 健身目标
	private double coachScore;		// 教练平均评分

	//传递参数
	private Date beginRegistTime;		// 开始 注册日期
	private Date endRegistTime;		// 结束 注册日期
	private BmiCourse bmiCourse;
	private Integer relation;//  0学员1教练
	private Double heightMeter; //m为单位的身高
	private Double weightKilo;  //千克为单位的体重
	private UserGym userGym;
	private String name;
	private String gymId;
	private Date cardStartDate;	  //开卡日期
	private Date cardEndDate; //卡截止日期
	private String bindFlag; //绑定标识, 0:绑定, 1: 解绑
	private Integer userHeight; //用户身高
	private Integer userWeight; //用户体重
	private String coachId; //课程主管
	private String persistFlag; //是否为存量客户,0:是, 1:否
	private String unbindTime; //解除绑定时间
	private String freezeFlag; //冻结标识, 0:否, 1:是
	private String homePageType; //主页类型
	@JsonIgnore
	private List<String> excludeIds;
	@JsonIgnore
	private List<String> includeIds;
	@JsonIgnore
	private List<String> includeUserTypes;
	private String relationFlag;  //关注关系, 1:未关注, 2, 已关注, 3, 互相关注
	private double usableMoney = 0; //教练可用余额
	private int receivedMoneyNum = 0;//教练受赏记录数
	private int myCoachNum = 0;//我的私教数
	private int myStuNum = 0;//我的会员数
	private int giveMoneyNum = 0;//打赏记录数
	private Date vipDeadLine;  //会员截止日期
	private int leftCourseNum; //会员剩余私教课
	private int totalCourseNum; //教练累计课程数
	private String meFlag; //是否本人查看个人主页, 0:不是, 1:是
	private String gymName; //作为教练所在的健身房名称
	private String msgContent;  //消息内容
	private String msgTitle; //消息标题
	
	public Integer getAssessment() {
		return assessment;
	}

	public void setAssessment(Integer assessment) {
		this.assessment = assessment;
	}

	public BmiCourse getBmiCourse() {
		return bmiCourse;
	}

	public void setBmiCourse(BmiCourse bmiCourse) {
		this.bmiCourse = bmiCourse;
	}

	public AppUser() {
		super();
	}

	public AppUser(String id) {
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

	@Length(min = 0, max = 64, message = "手机号长度必须介于 0 和 64 之间")
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

	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	
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

	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	
	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	@JsonFormat(pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpenCardTime() {
		return openCardTime;
	}

	public void setOpenCardTime(Date openCardTime) {
		this.openCardTime = openCardTime;
	}

	public Integer getCourseCount() {
		return courseCount;
	}

	public void setCourseCount(Integer courseCount) {
		this.courseCount = courseCount;
	}

	public Integer getGymCount() {
		return gymCount;
	}

	public void setGymCount(Integer gymCount) {
		this.gymCount = gymCount;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	
	public String getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}

	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	
	public String getBgType() {
		return bgType;
	}

	public void setBgType(String bgType) {
		this.bgType = bgType;
	}

	public Integer getFollowNum() {
		return followNum;
	}

	public void setFollowNum(Integer followNum) {
		this.followNum = followNum;
	}

	public Integer getFollowerNum() {
		return followerNum;
	}

	public void setFollowerNum(Integer followerNum) {
		this.followerNum = followerNum;
	}

	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Length(min = 0, max = 128, message = "融云用户标识长度必须介于 0 和 128 之间")
	public String getRcToken() {
		return rcToken;
	}

	public void setRcToken(String rcToken) {
		this.rcToken = rcToken;
	}

	@Length(min = 0, max = 256, message = "标签长度必须介于 0 和 256 之间")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
	public String getPhonePrivateLevel() {
		return phonePrivateLevel;
	}

	public void setPhonePrivateLevel(String phonePrivateLevel) {
		this.phonePrivateLevel = phonePrivateLevel;
	}

	
	public String getMsgReceiveLevel() {
		return msgReceiveLevel;
	}

	public void setMsgReceiveLevel(String msgReceiveLevel) {
		this.msgReceiveLevel = msgReceiveLevel;
	}

	public Integer getChargerId() {
		return chargerId;
	}

	public void setChargerId(Integer chargerId) {
		this.chargerId = chargerId;
	}

	@Length(min = 0, max = 128, message = "教练名字长度必须介于 0 和 128 之间")
	public String getChargerName() {
		return chargerName;
	}

	public void setChargerName(String chargerName) {
		this.chargerName = chargerName;
	}

	
	public String getActivateFlag() {
		return activateFlag;
	}

	public void setActivateFlag(String activateFlag) {
		this.activateFlag = activateFlag;
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

	public String getCoachFlag() {
		return coachFlag;
	}

	public void setCoachFlag(String coachFlag) {
		this.coachFlag = coachFlag;
	}

	public String getStuFlag() {
		return stuFlag;
	}

	public void setStuFlag(String stuFlag) {
		this.stuFlag = stuFlag;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFirstVisitStuFlag() {
		return firstVisitStuFlag;
	}

	public void setFirstVisitStuFlag(String firstVisitStuFlag) {
		this.firstVisitStuFlag = firstVisitStuFlag;
	}

	public String getFirstVisitCoachFlag() {
		return firstVisitCoachFlag;
	}

	public void setFirstVisitCoachFlag(String firstVisitCoachFlag) {
		this.firstVisitCoachFlag = firstVisitCoachFlag;
	}

	public String getBgImgUrl() {
		return bgImgUrl;
	}

	public void setBgImgUrl(String bgImgUrl) {
		this.bgImgUrl = bgImgUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public UserGym getUserGym() {
		return userGym;
	}

	public void setUserGym(UserGym userGym) {
		this.userGym = userGym;
	}
	
	
	public String getEndCardTime() {
		return endCardTime;
	}

	public void setEndCardTime(String endCardTime) {
		this.endCardTime = endCardTime;
	}

	public String getGymEntryTime() {
		return gymEntryTime;
	}

	public void setGymEntryTime(String gymEntryTime) {
		this.gymEntryTime = gymEntryTime;
	}

	public String getGymId() {
		return gymId;
	}

	public void setGymId(String gymId) {
		this.gymId = gymId;
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

	public Integer getUserWeight() {
		return userWeight;
	}

	public void setUserWeight(Integer userWeight) {
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

	public String getUnbindTime() {
		return unbindTime;
	}

	public void setUnbindTime(String unbindTime) {
		this.unbindTime = unbindTime;
	}

	public String getFreezeFlag() {
		return freezeFlag;
	}

	public void setFreezeFlag(String freezeFlag) {
		this.freezeFlag = freezeFlag;
	}

	public String getBurningGoal() {
		return burningGoal;
	}

	public void setBurningGoal(String burningGoal) {
		this.burningGoal = burningGoal;
	}

	public String getCoachImgUrl() {
		return coachImgUrl;
	}

	public void setCoachImgUrl(String coachImgUrl) {
		this.coachImgUrl = coachImgUrl;
	}

	public String getStuImgUrl() {
		return stuImgUrl;
	}

	public void setStuImgUrl(String stuImgUrl) {
		this.stuImgUrl = stuImgUrl;
	}

	public String getActiveGymId() {
		return activeGymId;
	}

	public void setActiveGymId(String activeGymId) {
		this.activeGymId = activeGymId;
	}

	public double getCoachScore() {
		return coachScore;
	}

	public void setCoachScore(double coachScore) {
		this.coachScore = coachScore;
	}

	public List<String> getExcludeIds() {
		return excludeIds;
	}

	public void setExcludeIds(List<String> excludeIds) {
		this.excludeIds = excludeIds;
	}

	public List<String> getIncludeUserTypes() {
		return includeUserTypes;
	}

	public void setIncludeUserTypes(List<String> includeUserTypes) {
		this.includeUserTypes = includeUserTypes;
	}

	public List<String> getIncludeIds() {
		return includeIds;
	}

	public void setIncludeIds(List<String> includeIds) {
		this.includeIds = includeIds;
	}

	public Double getHeightMeter() {
		return heightMeter;
	}

	public void setHeightMeter(Double heightMeter) {
		this.heightMeter = heightMeter;
	}

	public Double getWeightKilo() {
		return weightKilo;
	}

	public void setWeightKilo(Double weightKilo) {
		this.weightKilo = weightKilo;
	}

	public String getRelationFlag() {
		return relationFlag;
	}

	public void setRelationFlag(String relationFlag) {
		this.relationFlag = relationFlag;
	}

	public double getUsableMoney() {
		return usableMoney;
	}

	public void setUsableMoney(double usableMoney) {
		this.usableMoney = usableMoney;
	}

	public int getReceivedMoneyNum() {
		return receivedMoneyNum;
	}

	public void setReceivedMoneyNum(int receivedMoneyNum) {
		this.receivedMoneyNum = receivedMoneyNum;
	}

	public int getMyCoachNum() {
		return myCoachNum;
	}

	public void setMyCoachNum(int myCoachNum) {
		this.myCoachNum = myCoachNum;
	}

	public int getGiveMoneyNum() {
		return giveMoneyNum;
	}

	public void setGiveMoneyNum(int giveMoneyNum) {
		this.giveMoneyNum = giveMoneyNum;
	}

	@JsonFormat(pattern = "yyyy/MM/dd")
	public Date getVipDeadLine() {
		return vipDeadLine;
	}

	public void setVipDeadLine(Date vipDeadLine) {
		this.vipDeadLine = vipDeadLine;
	}

	public int getLeftCourseNum() {
		return leftCourseNum;
	}

	public void setLeftCourseNum(int leftCourseNum) {
		this.leftCourseNum = leftCourseNum;
	}

	public String getHomePageType() {
		return homePageType;
	}

	public void setHomePageType(String homePageType) {
		this.homePageType = homePageType;
	}

	public int getTotalCourseNum() {
		return totalCourseNum;
	}

	public void setTotalCourseNum(int totalCourseNum) {
		this.totalCourseNum = totalCourseNum;
	}

	public int getMyStuNum() {
		return myStuNum;
	}

	public void setMyStuNum(int myStuNum) {
		this.myStuNum = myStuNum;
	}

	public String getMeFlag() {
		return meFlag;
	}

	public void setMeFlag(String meFlag) {
		this.meFlag = meFlag;
	}

	public String getGymName() {
		return gymName;
	}

	public void setGymName(String gymName) {
		this.gymName = gymName;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	
	
}
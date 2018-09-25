package com.gimc.user.common.utils.burningUtil;

import java.util.Date;

public class RequestMap {
	
	//教练休息时间
	private String id; //休息时间编号
	private String gymId; //健身房id
	private Date startTime; //休息开始时间
	private double duration; //持续时间
	private String allDayRest; //是否全天休息, 0:否,1:是
	private Date date;  //需要查询的日期
	
	//分页参数
	private int currentPage;  //当前页
	private int pageSize;   //页码大小
	
	//课程
	private String courseId;
	private String movementId; 
	private String movementIds; 
	private String catalogId;
	
	//订单
	private String coachId;
	private String userType;
	private String status;
	private String name;
	private String cancelReason;
	private String orderId; 
	private String remarks;
	private String userIds;
	private Double addFee;
	private String keyword;
	
	//消息,关注
	private String userId;
	private String toUserId; //发送消息对方的用户id
	private String msgType; //查看消息类型
	private String msgId;
	private String meFlag; 
	private String meId; // 我的id
	
	//打赏
	private String rewardType;
	private String type;
	
	//用户信息
	private String phoneNum;
	private String newPwd;
	private String checkCode;
	private String payPwd;
	
	//余额账户
	private String headBankId;
	private String branchBankName;
	private String owner;
	private String cardNo;
	private Double withdrawAmount;
	private String bankCardId;
	
	public String getGymId() {
		return gymId;
	}
	public void setGymId(String gymId) {
		this.gymId = gymId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public String getAllDayRest() {
		return allDayRest;
	}
	public void setAllDayRest(String allDayRest) {
		this.allDayRest = allDayRest;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public int getCurrentPage() {
		
		return currentPage < 1 ? 1 : currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getPageSize() {
		return pageSize < 1 ? 10 : pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCoachId() {
		return coachId;
	}
	public void setCoachId(String coachId) {
		this.coachId = coachId;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMeFlag() {
		return meFlag;
	}
	public void setMeFlag(String meFlag) {
		this.meFlag = meFlag;
	}
	public String getMeId() {
		return meId;
	}
	public void setMeId(String meId) {
		this.meId = meId;
	}
	public String getMovementId() {
		return movementId;
	}
	public void setMovementId(String movementId) {
		this.movementId = movementId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public String getMovementIds() {
		return movementIds;
	}
	public void setMovementIds(String movementIds) {
		this.movementIds = movementIds;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public Double getAddFee() {
		return addFee;
	}
	public void setAddFee(Double addFee) {
		this.addFee = addFee;
	}
	public String getRewardType() {
		return rewardType;
	}
	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getHeadBankId() {
		return headBankId;
	}
	public void setHeadBankId(String headBankId) {
		this.headBankId = headBankId;
	}
	public String getBranchBankName() {
		return branchBankName;
	}
	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getPayPwd() {
		return payPwd;
	}
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}
	public Double getWithdrawAmount() {
		return withdrawAmount;
	}
	public void setWithdrawAmount(Double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	public String getBankCardId() {
		return bankCardId;
	}
	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}

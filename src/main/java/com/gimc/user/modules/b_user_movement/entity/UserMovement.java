/**
 * 
 */
package com.gimc.user.modules.b_user_movement.entity;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 用户推荐动作Entity
 * @author linhaomiao
 * @version 2018-05-30
 */
public class UserMovement extends DataEntity<UserMovement> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 动作名称
	private String catalogIds;		// 所属分类id
	private String catalogNames;		// 所属分类名称
	private String catalog1Id;		// 一级分类id
	private String catalog1Name;		// 一级分类名称
	private String catalog2Id;		// 二级分类id
	private String catalog2Name;		// 二级分类名称
	private String difficulty;		// 动作难度
	private String equipmentType;		// 是否使用器材
	private String zengjiGroupNum;		// 增肌区间动作组数
	private String suxingGroupNum;		// 塑型区间动作组数
	private String jianzhiGroupNum;		// 减脂区间动作组数
	private String zengjiMovementNum;		// 增肌区间动作次数
	private String suxingMovementNum;		// 塑型区间动作次数
	private String jianzhiMovementNum;		// 减脂区间动作次数
	private String prepareImgUrl;		// 预备动作图片url
	private String actionImgUrl;		// 执行动作图片url
	private String introduction;		// 动作说明
	private String endMainPoint;		// 结束动作要领
	private String coachTip;		// 教练提示
	private String commonFault;		// 常见错误
	private String prepare;		// 预备
	private String action;		// 执行
	private String pelvicStability;		// 骨盆稳定, 1:未设定, 2:是, 3:否
	private String coreActivation;		// 核心激活, 1:未设定, 2:是, 3:否
	private String respiratoryReconstruction;		// 呼吸重建, 1:未设定, 2:是, 3:否
	private String groupNum;	//指定区间动作组数
	private String movementNum; //指定区间动作次数
	private String parent;  //父动作id
	private String type;  //推荐类型,1:增肌, 2:塑型, 3:减脂
	
	//传递参数
	private String instrumentSummary;//器械汇总
	
	public UserMovement() {
		super();
	}

	public UserMovement(String id){
		super(id);
	}

	@Length(min=0, max=64, message="动作名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=11, message="一级分类id长度必须介于 0 和 11 之间")
	public String getCatalog1Id() {
		return catalog1Id;
	}

	public void setCatalog1Id(String catalog1Id) {
		this.catalog1Id = catalog1Id;
	}
	
	@Length(min=0, max=64, message="一级分类名称长度必须介于 0 和 64 之间")
	public String getCatalog1Name() {
		return catalog1Name;
	}

	public void setCatalog1Name(String catalog1Name) {
		this.catalog1Name = catalog1Name;
	}
	
	@Length(min=0, max=11, message="二级分类id长度必须介于 0 和 11 之间")
	public String getCatalog2Id() {
		return catalog2Id;
	}

	public void setCatalog2Id(String catalog2Id) {
		this.catalog2Id = catalog2Id;
	}
	
	@Length(min=0, max=64, message="二级分类名称长度必须介于 0 和 64 之间")
	public String getCatalog2Name() {
		return catalog2Name;
	}

	public void setCatalog2Name(String catalog2Name) {
		this.catalog2Name = catalog2Name;
	}
	
	@Length(min=0, max=4, message="动作难度长度必须介于 0 和 2 之间")
	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	
	@Length(min=0, max=2, message="是否使用器材长度必须介于 0 和 2 之间")
	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	
	@Length(min=0, max=32, message="增肌区间动作组数长度必须介于 0 和 32 之间")
	public String getZengjiGroupNum() {
		return zengjiGroupNum;
	}

	public void setZengjiGroupNum(String zengjiGroupNum) {
		this.zengjiGroupNum = zengjiGroupNum;
	}
	
	@Length(min=0, max=32, message="塑型区间动作组数长度必须介于 0 和 32 之间")
	public String getSuxingGroupNum() {
		return suxingGroupNum;
	}

	public void setSuxingGroupNum(String suxingGroupNum) {
		this.suxingGroupNum = suxingGroupNum;
	}
	
	@Length(min=0, max=32, message="减脂区间动作组数长度必须介于 0 和 32 之间")
	public String getJianzhiGroupNum() {
		return jianzhiGroupNum;
	}

	public void setJianzhiGroupNum(String jianzhiGroupNum) {
		this.jianzhiGroupNum = jianzhiGroupNum;
	}
	
	@Length(min=0, max=32, message="增肌区间动作次数长度必须介于 0 和 32 之间")
	public String getZengjiMovementNum() {
		return zengjiMovementNum;
	}

	public void setZengjiMovementNum(String zengjiMovementNum) {
		this.zengjiMovementNum = zengjiMovementNum;
	}
	
	@Length(min=0, max=32, message="塑型区间动作次数长度必须介于 0 和 32 之间")
	public String getSuxingMovementNum() {
		return suxingMovementNum;
	}

	public void setSuxingMovementNum(String suxingMovementNum) {
		this.suxingMovementNum = suxingMovementNum;
	}
	
	@Length(min=0, max=32, message="减脂区间动作次数长度必须介于 0 和 32 之间")
	public String getJianzhiMovementNum() {
		return jianzhiMovementNum;
	}

	public void setJianzhiMovementNum(String jianzhiMovementNum) {
		this.jianzhiMovementNum = jianzhiMovementNum;
	}
	
	
	
	@Length(min=0, max=256, message="动作说明长度必须介于 0 和 256 之间")
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	@Length(min=0, max=256, message="结束动作要领长度必须介于 0 和 256 之间")
	public String getEndMainPoint() {
		return endMainPoint;
	}

	public void setEndMainPoint(String endMainPoint) {
		this.endMainPoint = endMainPoint;
	}
	
	@Length(min=0, max=256, message="教练提示长度必须介于 0 和 256 之间")
	public String getCoachTip() {
		return coachTip;
	}

	public void setCoachTip(String coachTip) {
		this.coachTip = coachTip;
	}
	
	@Length(min=0, max=256, message="常见错误长度必须介于 0 和 256 之间")
	public String getCommonFault() {
		return commonFault;
	}

	public void setCommonFault(String commonFault) {
		this.commonFault = commonFault;
	}
	
	@Length(min=0, max=256, message="预备长度必须介于 0 和 256 之间")
	public String getPrepare() {
		return prepare;
	}

	public void setPrepare(String prepare) {
		this.prepare = prepare;
	}
	
	@Length(min=0, max=256, message="执行长度必须介于 0 和 256 之间")
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	@Length(min=0, max=2, message="骨盆稳定长度必须介于 0 和 2 之间")
	public String getPelvicStability() {
		return pelvicStability;
	}

	public void setPelvicStability(String pelvicStability) {
		this.pelvicStability = pelvicStability;
	}
	
	@Length(min=0, max=2, message="核心激活长度必须介于 0 和 2 之间")
	public String getCoreActivation() {
		return coreActivation;
	}

	public void setCoreActivation(String coreActivation) {
		this.coreActivation = coreActivation;
	}
	
	@Length(min=0, max=2, message="呼吸重建长度必须介于 0 和 2 之间")
	public String getRespiratoryReconstruction() {
		return respiratoryReconstruction;
	}

	public void setRespiratoryReconstruction(String respiratoryReconstruction) {
		this.respiratoryReconstruction = respiratoryReconstruction;
	}

	public String getCatalogIds() {
		return catalogIds;
	}

	public void setCatalogIds(String catalogIds) {
		this.catalogIds = catalogIds;
	}

	public String getCatalogNames() {
		return catalogNames;
	}

	public void setCatalogNames(String catalogNames) {
		this.catalogNames = catalogNames;
	}

	public String getPrepareImgUrl() {
		return prepareImgUrl;
	}

	public void setPrepareImgUrl(String prepareImgUrl) {
		this.prepareImgUrl = prepareImgUrl;
	}

	public String getActionImgUrl() {
		return actionImgUrl;
	}

	public void setActionImgUrl(String actionImgUrl) {
		this.actionImgUrl = actionImgUrl;
	}

	public String getMovementNum() {
		return movementNum;
	}

	public void setMovementNum(String movementNum) {
		this.movementNum = movementNum;
	}

	public String getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}

	public String getInstrumentSummary() {
		return instrumentSummary;
	}

	public void setInstrumentSummary(String instrumentSummary) {
		this.instrumentSummary = instrumentSummary;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
/**
 * 
 */
package com.gimc.user.modules.b_group.entity;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 用户分组Entity
 * @author linhaomiao
 * @version 2018-06-07
 */
public class Group extends DataEntity<Group> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型, 1: 关注, 2:粉丝, 3: 教练, 4: 学员
	private String userId;		// 用户id
	private String defaultFlag;		// 是否默认分组 0;不是 1:是
	private String name;		// 分组名称
	
	public Group() {
		super();
	}

	public Group(String id){
		super(id);
	}

	@Length(min=0, max=16, message="类型, 1: 关注, 2:粉丝, 3: 教练, 4: 学员长度必须介于 0 和 16 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=11, message="用户id长度必须介于 0 和 11 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=1, message="是否默认分组 0;不是 1:是长度必须介于 0 和 1 之间")
	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	
	@Length(min=0, max=64, message="分组名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
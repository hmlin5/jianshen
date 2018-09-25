/**
 * 
 */
package com.gimc.user.modules.b_user_group.entity;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 用户分组关系Entity
 * @author linhaomiao
 * @version 2018-06-07
 */
public class UserGroup extends DataEntity<UserGroup> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String groupId;		// 分组id
	
	public UserGroup() {
		super();
	}

	public UserGroup(String id){
		super(id);
	}

	@Length(min=0, max=11, message="用户id长度必须介于 0 和 11 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=11, message="分组id长度必须介于 0 和 11 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
}
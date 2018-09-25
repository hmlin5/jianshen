/**
 * 
 */
package com.gimc.user.modules.b_follow_relation.entity;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 关注关系Entity
 * @author linhaomiao
 * @version 2018-06-04
 */
public class FollowRelation extends DataEntity<FollowRelation> {
	
	private static final long serialVersionUID = 1L;
	private String followId;		// 关注者id
	private String followerId;		// 被关注者id
	
	public FollowRelation() {
		super();
	}

	public FollowRelation(String id){
		super(id);
	}

	@Length(min=0, max=11, message="关注者id长度必须介于 0 和 11 之间")
	public String getFollowId() {
		return followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
	}
	
	@Length(min=0, max=11, message="被关注者id长度必须介于 0 和 11 之间")
	public String getFollowerId() {
		return followerId;
	}

	public void setFollowerId(String followerId) {
		this.followerId = followerId;
	}
	
}
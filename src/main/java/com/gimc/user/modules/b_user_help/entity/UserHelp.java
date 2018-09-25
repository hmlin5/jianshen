/**
 * 
 */
package com.gimc.user.modules.b_user_help.entity;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 用户帮助Entity
 * @author linhaomiao
 * @version 2018-09-03
 */
public class UserHelp extends DataEntity<UserHelp> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 内容
	private String type;		// 类型
	
	public UserHelp() {
		super();
	}

	public UserHelp(String id){
		super(id);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=16, message="类型长度必须介于 0 和 16 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
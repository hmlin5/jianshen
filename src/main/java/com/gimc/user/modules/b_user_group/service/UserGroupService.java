/**
 * 
 */
package com.gimc.user.modules.b_user_group.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_user_group.entity.UserGroup;
import com.gimc.user.modules.b_user_group.dao.UserGroupDao;

/**
 * 用户分组关系Service
 * @author linhaomiao
 * @version 2018-06-07
 */
@Service
@Transactional(readOnly = true)
public class UserGroupService extends CrudService<UserGroupDao, UserGroup> {

	public UserGroup get(String id) {
		return super.get(id);
	}
	
	public List<UserGroup> findList(UserGroup userGroup) {
		return super.findList(userGroup);
	}
	
	public Page<UserGroup> findPage(Page<UserGroup> page, UserGroup userGroup) {
		return super.findPage(page, userGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(UserGroup userGroup) {
		super.save(userGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserGroup userGroup) {
		super.delete(userGroup);
	}
	
}
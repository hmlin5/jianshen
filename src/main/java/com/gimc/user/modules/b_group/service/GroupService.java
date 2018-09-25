/**
 * 
 */
package com.gimc.user.modules.b_group.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_group.entity.Group;
import com.gimc.user.modules.b_group.dao.GroupDao;

/**
 * 用户分组Service
 * @author linhaomiao
 * @version 2018-06-07
 */
@Service
@Transactional(readOnly = true)
public class GroupService extends CrudService<GroupDao, Group> {

	public Group get(String id) {
		return super.get(id);
	}
	
	public List<Group> findList(Group group) {
		return super.findList(group);
	}
	
	public Page<Group> findPage(Page<Group> page, Group group) {
		return super.findPage(page, group);
	}
	
	@Transactional(readOnly = false)
	public void save(Group group) {
		super.save(group);
	}
	
	@Transactional(readOnly = false)
	public void delete(Group group) {
		super.delete(group);
	}
	
}
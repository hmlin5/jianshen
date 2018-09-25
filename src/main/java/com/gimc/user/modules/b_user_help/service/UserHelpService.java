/**
 * 
 */
package com.gimc.user.modules.b_user_help.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_user_help.entity.UserHelp;
import com.gimc.user.modules.b_user_help.dao.UserHelpDao;

/**
 * 用户帮助Service
 * @author linhaomiao
 * @version 2018-09-03
 */
@Service
@Transactional(readOnly = true)
public class UserHelpService extends CrudService<UserHelpDao, UserHelp> {

	public UserHelp get(String id) {
		return super.get(id);
	}
	
	public List<UserHelp> findList(UserHelp userHelp) {
		return super.findList(userHelp);
	}
	
	public Page<UserHelp> findPage(Page<UserHelp> page, UserHelp userHelp) {
		return super.findPage(page, userHelp);
	}
	
	@Transactional(readOnly = false)
	public void save(UserHelp userHelp) {
		super.save(userHelp);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserHelp userHelp) {
		super.delete(userHelp);
	}
	
}
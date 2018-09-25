/**
 * 
 */
package com.gimc.user.modules.b_user_inbody.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_user_inbody.entity.UserInbody;
import com.gimc.user.modules.b_user_inbody.dao.UserInbodyDao;

/**
 * 用户Inbody数据Service
 * @author linhaomiao
 * @version 2018-06-14
 */
@Service
@Transactional(readOnly = true)
public class UserInbodyService extends CrudService<UserInbodyDao, UserInbody> {
    
	@Autowired
	private UserInbodyDao userInbodyDao;
	
	public UserInbody updateInbodyData(UserInbody userInbody){
	    return userInbodyDao.TestUpdate(userInbody);
	}
	
	public void InsertUserInbody(UserInbody userInbody){
		userInbodyDao.insertInbody(userInbody);
	}
	
	public UserInbody get(String id) {
		return super.get(id);
	}
	
	public UserInbody getUserInbody(String id){
		return userInbodyDao.getUserInbody(id);
	}
	
	public List<UserInbody> findList(UserInbody userInbody) {
		return super.findList(userInbody);
	}
	
	public Page<UserInbody> findPage(Page<UserInbody> page, UserInbody userInbody) {
		return super.findPage(page, userInbody);
	}
	
	@Transactional(readOnly = false)
	public void save(UserInbody userInbody) {
		super.save(userInbody);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserInbody userInbody) {
		super.delete(userInbody);
	}
	
}
/**
 * 
 */
package com.gimc.user.modules.b_user_bank.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_user_bank.entity.UserBank;
import com.gimc.user.modules.b_user_bank.dao.UserBankDao;

/**
 * 用户绑定银行Service
 * @author linhaomiao
 * @version 2018-07-04
 */
@Service
@Transactional(readOnly = true)
public class UserBankService extends CrudService<UserBankDao, UserBank> {

	public UserBank get(String id) {
		return super.get(id);
	}
	
	public List<UserBank> findList(UserBank userBank) {
		return super.findList(userBank);
	}
	
	public Page<UserBank> findPage(Page<UserBank> page, UserBank userBank) {
		return super.findPage(page, userBank);
	}
	
	@Transactional(readOnly = false)
	public void save(UserBank userBank) {
		super.save(userBank);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserBank userBank) {
		super.delete(userBank);
	}
	
	
}
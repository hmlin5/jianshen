/**
 * 
 */
package com.gimc.user.modules.b_user_withdraw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_user_withdraw.entity.MoneyDetail;
import com.gimc.user.modules.b_user_withdraw.entity.UserWithdraw;
import com.gimc.user.modules.b_user_withdraw.dao.UserWithdrawDao;

/**
 * 用户提现记录Service
 * @author linhaomiao
 * @version 2018-07-04
 */
@Service
@Transactional(readOnly = true)
public class UserWithdrawService extends CrudService<UserWithdrawDao, UserWithdraw> {

	public UserWithdraw get(String id) {
		return super.get(id);
	}
	
	public List<UserWithdraw> findList(UserWithdraw userWithdraw) {
		return super.findList(userWithdraw);
	}
	
	public Page<UserWithdraw> findPage(Page<UserWithdraw> page, UserWithdraw userWithdraw) {
		return super.findPage(page, userWithdraw);
	}
	
	@Transactional(readOnly = false)
	public void save(UserWithdraw userWithdraw) {
		super.save(userWithdraw);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserWithdraw userWithdraw) {
		super.delete(userWithdraw);
	}
	
	/**
	 * 查询提现与打赏列表
	 * @param userWithdraw
	 * @return
	 */
	public List<MoneyDetail> findWithdrawAndPayInfoList( UserWithdraw userWithdraw){
		return dao.findWithdrawAndPayInfoList(userWithdraw);
	}
}
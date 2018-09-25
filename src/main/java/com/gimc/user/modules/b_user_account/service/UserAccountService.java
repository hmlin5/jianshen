/**
 * 
 */
package com.gimc.user.modules.b_user_account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.BaseEntity;
import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_user_account.entity.UserAccount;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_user.dao.AppUserDao;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user_account.dao.UserAccountDao;

/**
 * 用户账户Service
 * @author linhaomiao
 * @version 2018-06-30
 */
@Service
@Transactional(readOnly = true)
public class UserAccountService extends CrudService<UserAccountDao, UserAccount> {

	
	@Autowired
	private AppUserDao appUserDao;
	
	
	public UserAccount get(String id) {
		return super.get(id);
	}
	
	public List<UserAccount> findList(UserAccount userAccount) {
		return super.findList(userAccount);
	}
	
	public Page<UserAccount> findPage(Page<UserAccount> page, UserAccount userAccount) {
		return super.findPage(page, userAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(UserAccount userAccount) {
		super.save(userAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserAccount userAccount) {
		super.delete(userAccount);
	}

	
	public UserAccount findByUserId(AppUser user) {
		
		UserAccount account = new UserAccount();
		account.setUserId(user.getId());
		List<UserAccount> list = dao.findList(account);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		
		return null;
	}

	
	
	/**
	 * 更新用户账户余额--提现
	 * @param changeAmount
	 */
	@Transactional(readOnly = false)
	public void withdraw(UserAccount uc,double changeAmount) {
		
		double usableAmount = uc.getUsableAmount();
		double totalAmount = uc.getTotalAmount();
		double withdrawAmount = uc.getWithdrawAmount();
	//	double freezeAmount = uc.getFreezeAmount();
		
		if (usableAmount<changeAmount) {
			return ;
		}
		usableAmount -= changeAmount;
		withdrawAmount += changeAmount;
		totalAmount -= changeAmount;
		
		uc.setTotalAmount(totalAmount);
		uc.setUsableAmount(usableAmount);
		uc.setWithdrawAmount(withdrawAmount);
		
		save(uc);
	}
	
	
	/**
	 * 更新用户账户余额--打赏
	 * @param changeAmount
	 */
	@Transactional(readOnly = false)
	public void reward(String userId,double changeAmount) {
		
		AppUser user = appUserDao.get(userId);
		if (user==null || BaseEntity.DEL_FLAG_DELETE.equals(user.getDelFlag())) {
			return;
		}
		UserAccount uc = findByUserId(user);
		if (uc == null || BaseEntity.DEL_FLAG_DELETE.equals(uc.getDelFlag())) {
			return;
		}
		if (changeAmount<=0) {
			return;
		}
		
		//double usableAmount = uc.getUsableAmount();
		double totalAmount = uc.getTotalAmount();
		//double withdrawAmount = uc.getWithdrawAmount();
		double freezeAmount = uc.getFreezeAmount();
		
		freezeAmount += changeAmount;
		totalAmount += changeAmount;
		
		uc.setTotalAmount(totalAmount);
		uc.setFreezeAmount(freezeAmount);
		
		save(uc);
	}

	
	/**
	 * 更新用户账户余额--退款
	 * @param changeAmount
	 */
	@Transactional(readOnly = false)
	public void refund(String userId, double changeAmount) {
		AppUser user = appUserDao.get(userId);
		if (user==null || BaseEntity.DEL_FLAG_DELETE.equals(user.getDelFlag())) {
			return;
		}
		UserAccount uc = findByUserId(user);
		if (uc == null || BaseEntity.DEL_FLAG_DELETE.equals(uc.getDelFlag())) {
			return;
		}
		if (changeAmount<=0) {
			return;
		}
		
		//double usableAmount = uc.getUsableAmount();
		double totalAmount = uc.getTotalAmount();
		//double withdrawAmount = uc.getWithdrawAmount();
		double freezeAmount = uc.getFreezeAmount();
		
		freezeAmount -= changeAmount;
		totalAmount -= changeAmount;
		
		uc.setTotalAmount(totalAmount);
		uc.setFreezeAmount(freezeAmount);
		
		save(uc);
		
	}
	
	
	
	/**
	 * 更新用户账户余额--冻结转可用
	 * @param changeAmount
	 */
	@Transactional(readOnly = false)
	public void freezeToUsable(String userId, double changeAmount) {
		AppUser user = appUserDao.get(userId);
		if (user==null || BaseEntity.DEL_FLAG_DELETE.equals(user.getDelFlag())) {
			return;
		}
		UserAccount uc = findByUserId(user);
		if (uc == null || BaseEntity.DEL_FLAG_DELETE.equals(uc.getDelFlag())) {
			return;
		}
		if (changeAmount<=0) {
			return;
		}
		
		double usableAmount = uc.getUsableAmount();
		//double totalAmount = uc.getTotalAmount();
		//double withdrawAmount = uc.getWithdrawAmount();
		double freezeAmount = uc.getFreezeAmount();
		
		if (freezeAmount<changeAmount) {
			return;
		}
		
		freezeAmount -= changeAmount;
		usableAmount += changeAmount;
		
		uc.setUsableAmount(usableAmount);
		uc.setFreezeAmount(freezeAmount);
		
		save(uc);
		
	}
	
	
	
}
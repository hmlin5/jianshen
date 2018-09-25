/**
 * 
 */
package com.gimc.user.modules.b_user_test.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_user_test.entity.UserTest;
import com.gimc.user.modules.b_user_test.dao.UserTestDao;

/**
 * 用户测试结果Service
 * @author linhaomiao
 * @version 2018-05-13
 */
@Service
@Transactional(readOnly = true)
public class UserTestService extends CrudService<UserTestDao, UserTest> {

	public UserTest get(String id) {
		return super.get(id);
	}
	
	public List<UserTest> findList(UserTest userTest) {
		return super.findList(userTest);
	}
	
	public Page<UserTest> findPage(Page<UserTest> page, UserTest userTest) {
		return super.findPage(page, userTest);
	}
	
	@Transactional(readOnly = false)
	public void save(UserTest userTest) {
		super.save(userTest);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserTest userTest) {
		super.delete(userTest);
	}

	
	/**
	 * 根据评测用户查询评测结果
	 * @param userId
	 * @return
	 */
	public UserTest findByUserId(String userId) {
		
		UserTest userTest = new UserTest();
		userTest.setUserId(userId);
		List<UserTest> list = dao.findList(userTest);
		if (list!=null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
	
}
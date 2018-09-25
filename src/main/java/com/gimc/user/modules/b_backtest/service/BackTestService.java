/**
 * 
 */
package com.gimc.user.modules.b_backtest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_backtest.entity.BackTest;
import com.gimc.user.modules.b_backtest.dao.BackTestDao;

/**
 * 后台测试用户Service
 * @author gu
 * @version 2018-05-05
 */
@Service
@Transactional(readOnly = true)
public class BackTestService extends CrudService<BackTestDao, BackTest> {

	public BackTest get(String id) {
		return super.get(id);
	}
	
	public List<BackTest> findList(BackTest backTest) {
		return super.findList(backTest);
	}
	
	public Page<BackTest> findPage(Page<BackTest> page, BackTest backTest) {
		return super.findPage(page, backTest);
	}
	
	@Transactional(readOnly = false)
	public void save(BackTest backTest) {
		super.save(backTest);
	}
	
	@Transactional(readOnly = false)
	public void delete(BackTest backTest) {
		super.delete(backTest);
	}
	
}
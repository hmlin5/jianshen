/**
 * 
 */
package com.gimc.user.modules.b_withdraw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_withdraw.entity.CashOrder;
import com.gimc.user.modules.b_withdraw.dao.CashOrderDao;

/**
 * 提现订单管理Service
 * @author gu
 * @version 2018-05-09
 */
@Service
@Transactional(readOnly = true)
public class CashOrderService extends CrudService<CashOrderDao, CashOrder> {

	public CashOrder get(String id) {
		return super.get(id);
	}
	
	public List<CashOrder> findList(CashOrder cashOrder) {
		return super.findList(cashOrder);
	}
	
	public Page<CashOrder> findPage(Page<CashOrder> page, CashOrder cashOrder) {
		return super.findPage(page, cashOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(CashOrder cashOrder) {
		super.save(cashOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(CashOrder cashOrder) {
		super.delete(cashOrder);
	}
	
}
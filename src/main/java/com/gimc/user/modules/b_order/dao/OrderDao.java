/**
 * 
 */
package com.gimc.user.modules.b_order.dao;

import java.util.List;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_order.entity.Order;

/**
 * 订单DAO接口
 * @author linhaomiao
 * @version 2018-06-01
 */
@MyBatisDao
public interface OrderDao extends CrudDao<Order> {
	
	int updateRobOrder(Order order);
	
	List<Order> findRankList(Order order);
	
	List<Order> settlementList(Order order);
	List<Order> searchOrder(Order order);

	void updateStatus(Order order);
	void updateStatusFor(Order order);
	
}
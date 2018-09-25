/**
 * 
 */
package com.gimc.user.modules.b_rest_time.dao;

import java.util.List;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_rest_time.entity.RestTime;

/**
 * 休息时间DAO接口
 * @author linhaomiao
 * @version 2018-05-25
 */
@MyBatisDao
public interface RestTimeDao extends CrudDao<RestTime> {

	/**
	 * 批量删除
	 * @param list
	 */
	void deleteList(List<RestTime> list);
	
	/**
	 * 去重查询
	 * @param restTime
	 * @return
	 */
	List<String> findDistinctList(RestTime restTime);
	
}
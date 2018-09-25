/**
 * 
 */
package com.gimc.user.modules.b_feedback.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_feedback.entity.Feedback;

/**
 * 反馈列表DAO接口
 * @author gu
 * @version 2018-05-08
 */
@MyBatisDao
public interface FeedbackDao extends CrudDao<Feedback> {
	
}
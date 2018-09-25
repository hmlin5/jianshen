/**
 * 
 */
package com.gimc.user.modules.b_feedback.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_feedback.entity.Feedback;
import com.gimc.user.modules.b_feedback.dao.FeedbackDao;

/**
 * 反馈列表Service
 * @author gu
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly = true)
public class FeedbackService extends CrudService<FeedbackDao, Feedback> {

	public Feedback get(String id) {
		return super.get(id);
	}
	
	public List<Feedback> findList(Feedback feedback) {
		return super.findList(feedback);
	}
	
	public Page<Feedback> findPage(Page<Feedback> page, Feedback feedback) {
		return super.findPage(page, feedback);
	}
	
	@Transactional(readOnly = false)
	public void save(Feedback feedback) {
		super.save(feedback);
	}
	
	@Transactional(readOnly = false)
	public void delete(Feedback feedback) {
		super.delete(feedback);
	}
	
}
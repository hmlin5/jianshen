/**
 * 
 */
package com.gimc.user.modules.b_comment.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_comment.entity.Comment;
import com.gimc.user.modules.b_user.dao.AppUserDao;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_comment.dao.CommentDao;

/**
 * 评价Service
 * @author linhaomiao
 * @version 2018-06-06
 */
@Service
@Transactional(readOnly = true)
public class CommentService extends CrudService<CommentDao, Comment> {

	@Autowired
	private AppUserDao userDao;
	
	
	public Comment get(String id) {
		return super.get(id);
	}
	
	public List<Comment> findList(Comment comment) {
		return super.findList(comment);
	}
	
	public Page<Comment> findPage(Page<Comment> page, Comment comment) {
		return super.findPage(page, comment);
	}
	
	@Transactional(readOnly = false)
	public void save(Comment comment) {
		super.save(comment);
	}
	
	@Transactional(readOnly = false)
	public void delete(Comment comment) {
		super.delete(comment);
	}

	/**
	 * 查找指定教练的所有评论列表
	 * @param user
	 * @return
	 * 	 */
	public List<Comment> findCoachCommentList(AppUser user) {
		Comment condition = new Comment();
		condition.setCoachId(user.getId());
		return dao.findCoachCommentList(condition);
	}

	/**
	 * 更新教练评分
	 * @param score
	 * @param coachId
	 */
	@Transactional(readOnly = false)
	public void updateCoachScore(int score, String coachId) {
		AppUser user = userDao.get(coachId);
		if (user==null) {
			return;
		}
		
		
		//double coachScore =  user.getCoachScore() > 0 ? user.getCoachScore() : 0.0;
		double newScore = 0.0;
		Comment comment = new Comment();
		comment.setCoachId(coachId);
		List<Comment> list = dao.findList(comment);
		if (list!=null && list.size()>0) {
			
			int num = 0;
			
			for (Comment c : list) {
				if (c.getTotal()>0) {
					num ++ ;
					newScore += c.getTotal();
				}
			}
			
			newScore = (newScore+score)/(num+1);
			
			BigDecimal b = new BigDecimal(newScore);  
			newScore = b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			user.setCoachScore(newScore);
			userDao.update(user);
			
		}else {
			user.setCoachScore(score);
			userDao.update(user);
		}
		
	}
	
}
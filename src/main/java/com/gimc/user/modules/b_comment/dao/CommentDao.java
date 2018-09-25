/**
 * 
 */
package com.gimc.user.modules.b_comment.dao;

import java.util.List;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_comment.entity.Comment;

/**
 * 评价DAO接口
 * @author linhaomiao
 * @version 2018-06-06
 */
@MyBatisDao
public interface CommentDao extends CrudDao<Comment> {
	
	List<Comment> findCoachCommentList(Comment comment);
}
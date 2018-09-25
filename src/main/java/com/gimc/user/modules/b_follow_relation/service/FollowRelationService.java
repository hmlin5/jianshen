/**
 * 
 */
package com.gimc.user.modules.b_follow_relation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_follow_relation.entity.FollowRelation;
import com.gimc.user.modules.b_follow_relation.dao.FollowRelationDao;

/**
 * 关注关系Service
 * @author linhaomiao
 * @version 2018-06-04
 */
@Service
@Transactional(readOnly = true)
public class FollowRelationService extends CrudService<FollowRelationDao, FollowRelation> {

	public FollowRelation get(String id) {
		return super.get(id);
	}
	
	public List<FollowRelation> findList(FollowRelation followRelation) {
		return super.findList(followRelation);
	}
	
	public Page<FollowRelation> findPage(Page<FollowRelation> page, FollowRelation followRelation) {
		return super.findPage(page, followRelation);
	}
	
	@Transactional(readOnly = false)
	public void save(FollowRelation followRelation) {
		super.save(followRelation);
	}
	
	@Transactional(readOnly = false)
	public void delete(FollowRelation followRelation) {
		super.delete(followRelation);
	}
	
	/**
	 * 判断两个用户之间的关注关系, 
	 * 返回"00"表示两个用户之间没有关注, 
	 * 返回"10"表示userId用户关注了toUserId用户, 
	 * 返回"01"表示toUserId用户关注了userId用户,
	 * 返回"11"表示互相关注
	 * @param userId
	 * @param toUserId
	 * @return
	 */
	public String checkFollowRelation(String userId, String toUserId){
		
		FollowRelation condition = new FollowRelation();
		condition.setFollowId(userId);
		condition.setFollowerId(toUserId);
		List<FollowRelation> list = dao.findList(condition);
		
		FollowRelation condition2 = new FollowRelation();
		condition2.setFollowId(toUserId);
		condition2.setFollowerId(userId);
		List<FollowRelation> list2 = dao.findList(condition2);
		
		String first = "0";
		String second = "0";
		
		if (list!=null && list.size()>0) {
			first = "1";
		}
		if (list2!=null && list2.size()>0) {
			second = "1";
		}
		
		return first+second;
	}
	
	/**
	 * 获取用户的关注数
	 * @param userId
	 * @return
	 */
	public int getUserFollowNum(String userId){
		
		FollowRelation condition = new FollowRelation();
		condition.setFollowId(userId);
		List<FollowRelation> list = dao.findList(condition);
		int num = 0;
		if (list!=null && list.size() > 0) {
			num = list.size();
		}
		
		return num;
	}
	
	/**
	 * 获取用户的粉丝数
	 * @param userId
	 * @return
	 */
	public int getUserFollowerNum(String userId){
		
		FollowRelation condition = new FollowRelation();
		condition.setFollowerId(userId);
		List<FollowRelation> list = dao.findList(condition);
		int num = 0;
		if (list!=null && list.size() > 0) {
			num = list.size();
		}
		
		return num;
	}
	
	
}
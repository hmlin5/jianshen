/**
 * 
 */
package com.gimc.user.modules.b_follow_relation.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_follow_relation.entity.FollowRelation;

/**
 * 关注关系DAO接口
 * @author linhaomiao
 * @version 2018-06-04
 */
@MyBatisDao
public interface FollowRelationDao extends CrudDao<FollowRelation> {
	
}
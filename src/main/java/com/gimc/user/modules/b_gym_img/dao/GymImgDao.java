/**
 * 
 */
package com.gimc.user.modules.b_gym_img.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_gym_img.entity.GymImg;

/**
 * 健身房图片DAO接口
 * @author linhaomiao
 * @version 2018-05-16
 */
@MyBatisDao
public interface GymImgDao extends CrudDao<GymImg> {
	
}
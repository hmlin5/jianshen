/**
 * 
 */
package com.gimc.user.modules.visualization.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.visualization.entity.Visualization;

/**
 * 今日实时DAO接口
 * @author xxx
 * @version 2018-07-17
 */
@MyBatisDao
public interface VisualizationDao extends CrudDao<Visualization> {
	
}
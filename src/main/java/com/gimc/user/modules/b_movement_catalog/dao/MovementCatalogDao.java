/**
 * 
 */
package com.gimc.user.modules.b_movement_catalog.dao;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_movement_catalog.entity.MovementCatalog;

/**
 * 动作分类DAO接口
 * @author linhaomiao
 * @version 2018-05-02
 */
@MyBatisDao
public interface MovementCatalogDao extends CrudDao<MovementCatalog> {
	
}
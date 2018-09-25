/**
 * 
 */
package com.gimc.user.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.service.TreeService;
import com.gimc.user.modules.sys.dao.AreaDao;
import com.gimc.user.modules.sys.entity.Area;
import com.gimc.user.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @author QiuZhu
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	public List<Area> findAll(Area area){
		return UserUtils.getAreaList(area);
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
}

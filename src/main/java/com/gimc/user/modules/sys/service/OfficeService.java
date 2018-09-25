/**
 * 
 */
package com.gimc.user.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.service.TreeService;
import com.gimc.user.modules.sys.dao.OfficeDao;
import com.gimc.user.modules.sys.entity.Office;
import com.gimc.user.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author QiuZhu
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			office.setParentIds(office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public Office selectName(String name) {
		
		if(name != null){
			return dao.selectName(name);
		}
		return null;
	}
	
	@Transactional(readOnly = false)
	public Office getParent(String id) {
		
		if(id != null){
			return dao.getParent(id);
		}
		return null;
	}
	
	@Transactional(readOnly = false)	
	public Office selectCode(String code){
		
		if(code != null){
			return dao.selectCode(code);
		}
		return null;
		
	}
	
	
}

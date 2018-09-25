/**
 * 
 */
package com.gimc.user.modules.b_version.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_version.entity.Version;
import com.gimc.user.modules.b_version.dao.VersionDao;

/**
 * app版本Service
 * @author linhaomiao
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly = true)
public class VersionService extends CrudService<VersionDao, Version> {

	public Version get(String id) {
		return super.get(id);
	}
	
	public List<Version> findList(Version version) {
		return super.findList(version);
	}
	
	public Page<Version> findPage(Page<Version> page, Version version) {
		return super.findPage(page, version);
	}
	
	@Transactional(readOnly = false)
	public void save(Version version) {
		super.save(version);
	}
	
	@Transactional(readOnly = false)
	public void delete(Version version) {
		super.delete(version);
	}
	
}
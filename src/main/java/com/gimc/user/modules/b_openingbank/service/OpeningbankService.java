/**
 * 
 */
package com.gimc.user.modules.b_openingbank.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_openingbank.entity.Openingbank;
import com.gimc.user.modules.b_openingbank.dao.OpeningbankDao;

/**
 * 开户行管理Service
 * @author gu
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly = true)
public class OpeningbankService extends CrudService<OpeningbankDao, Openingbank> {

	public Openingbank get(String id) {
		return super.get(id);
	}
	
	public List<Openingbank> findList(Openingbank openingbank) {
		return super.findList(openingbank);
	}
	
	public Page<Openingbank> findPage(Page<Openingbank> page, Openingbank openingbank) {
		return super.findPage(page, openingbank);
	}
	
	@Transactional(readOnly = false)
	public void save(Openingbank openingbank) {
		super.save(openingbank);
	}
	
	@Transactional(readOnly = false)
	public void delete(Openingbank openingbank) {
		super.delete(openingbank);
	}
	
}
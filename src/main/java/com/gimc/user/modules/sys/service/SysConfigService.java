/**
 * 
 */
package com.gimc.user.modules.sys.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.config.SysConfigData;
import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.sys.entity.SysConfig;
import com.gimc.user.modules.sys.dao.SysConfigDao;

/**
 * 配置参数表Service
 * @author xf
 * @version 2018-01-08
 */
@Service
@Transactional(readOnly = true)
public class SysConfigService extends CrudService<SysConfigDao, SysConfig> {

	//融云配置项
	@Value("${rc_appKey}")  
    public  String rc_appKey; 
	
	@Value("${rc_appSecret}")  
	public  String rc_appSecret; 
	
	
	public SysConfig get(String id) {
		return super.get(id);
	}
	
	public List<SysConfig> findList(SysConfig sysConfig) {
		return super.findList(sysConfig);
	}
	
	public Page<SysConfig> findPage(Page<SysConfig> page, SysConfig sysConfig) {
		return super.findPage(page, sysConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(SysConfig sysConfig) {
		super.save(sysConfig);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysConfig sysConfig) {
		super.delete(sysConfig);
	}
	
	public void initSysConfigParam(){
		List<SysConfig> list = super.findList(null);
		
		if(CollectionUtils.isNotEmpty(list)){
			for(SysConfig sc : list){
				SysConfigData.sysConfigMap.put(sc.getProkey(), sc);
			}
			System.out.println(SysConfigData.sysConfigMap);
		}
	}
	
}
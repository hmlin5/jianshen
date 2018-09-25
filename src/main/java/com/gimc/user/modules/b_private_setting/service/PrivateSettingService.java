/**
 * 
 */
package com.gimc.user.modules.b_private_setting.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_private_setting.entity.PrivateSetting;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_private_setting.dao.PrivateSettingDao;

/**
 * 隐私设置Service
 * @author linhaomiao
 * @version 2018-06-03
 */
@Service
@Transactional(readOnly = true)
public class PrivateSettingService extends CrudService<PrivateSettingDao, PrivateSetting> {
	
	@Autowired
	private PrivateSettingDao privateDao;

	public PrivateSetting get(String id) {
		return super.get(id);
	}
	
	public List<PrivateSetting> findList(PrivateSetting privateSetting) {
		return super.findList(privateSetting);
	}
	
	public Page<PrivateSetting> findPage(Page<PrivateSetting> page, PrivateSetting privateSetting) {
		return super.findPage(page, privateSetting);
	}
	
	@Transactional(readOnly = false)
	public void save(PrivateSetting privateSetting) {
		super.save(privateSetting);
	}
	
	@Transactional(readOnly = false)
	public void delete(PrivateSetting privateSetting) {
		super.delete(privateSetting);
	}
	
	public PrivateSetting findByUserId(String userId){
		
		PrivateSetting condition = new PrivateSetting();
		condition.setUserId(userId);
		List<PrivateSetting> list = dao.findList(condition);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		
		return null;
	}
	
	//根据返回来的int值做判断
	@Transactional(readOnly=false)
	public int checkPrivate(AppUser user){
		PrivateSetting ps=privateDao.getUserId(user.getId());
	    int messageOrder=Integer.parseInt(ps.getMesssageOrder());
	    int messageCourse=Integer.parseInt(ps.getMessageCourseTip());
	    
	    if(messageOrder==0&&messageCourse==0){
	    	return 1;
	    }else if(messageOrder==0&&messageCourse==1){
	    	return 2;
	    }else if(messageOrder==1&&messageCourse==0){
	    	return 3;
	    }else if(messageOrder==1&&messageCourse==1){
	    	return 4;
	    }
		return 5;
	}
	
	
	/**
	 * 检查用户是否设置接收订单状态改变通知
	 * @param userId
	 * @return
	 */
	public boolean checkOrderNotify(String userId){
		PrivateSetting ps = findByUserId(userId);
		if (ps==null) {
			return false;
		}
		if (CommonParam.MESSAGE_RECEIVED.equals(ps.getMesssageOrder())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查用户是否设置接收上课提醒通知
	 * @param userId
	 * @return
	 */
	public boolean checkCourseNotify(String userId){
		PrivateSetting ps = findByUserId(userId);
		if (ps==null) {
			return false;
		}
		if (CommonParam.MESSAGE_RECEIVED.equals(ps.getMessageCourseTip())) {
			return true;
		}
		return false;
	}
	
}
/**
 * 
 */
package com.gimc.user.modules.b_gym.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_gym.dao.GymDao;

/**
 * 健身房Service
 * @author linhaomiao
 * @version 2018-05-03
 */
@Service
@Transactional(readOnly = true)
public class GymService extends CrudService<GymDao, Gym> {

	public Gym get(String id) {
		return super.get(id);
	}
	
	public List<Gym> findList(Gym gym) {
		return super.findList(gym);
	}
	
	public Page<Gym> findPage(Page<Gym> page, Gym gym) {
		return super.findPage(page, gym);
	}
	
	@Transactional(readOnly = false)
	public void save(Gym gym) {
		super.save(gym);
	}
	
	@Transactional(readOnly = false)
	public void delete(Gym gym) {
		super.delete(gym);
	}
	
	public Gym findByUserId(String userId) {
		Gym condition = new Gym();
		condition.setUserId(Integer.parseInt(userId));
		List<Gym> list = findList(condition);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		
		
		return null;
	}
	
}
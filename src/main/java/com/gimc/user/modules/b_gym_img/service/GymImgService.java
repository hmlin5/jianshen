/**
 * 
 */
package com.gimc.user.modules.b_gym_img.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_gym_img.entity.GymImg;
import com.gimc.user.modules.b_gym_img.dao.GymImgDao;

/**
 * 健身房图片Service
 * @author linhaomiao
 * @version 2018-05-16
 */
@Service
@Transactional(readOnly = true)
public class GymImgService extends CrudService<GymImgDao, GymImg> {

	public GymImg get(String id) {
		return super.get(id);
	}
	
	public List<GymImg> findList(GymImg gymImg) {
		return super.findList(gymImg);
	}
	
	public Page<GymImg> findPage(Page<GymImg> page, GymImg gymImg) {
		return super.findPage(page, gymImg);
	}
	
	@Transactional(readOnly = false)
	public void save(GymImg gymImg) {
		super.save(gymImg);
	}
	
	@Transactional(readOnly = false)
	public void delete(GymImg gymImg) {
		super.delete(gymImg);
	}
	
}
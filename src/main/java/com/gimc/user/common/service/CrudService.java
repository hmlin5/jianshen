package com.gimc.user.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.DataEntity;
import com.gimc.user.common.persistence.Page;
import com.gimc.user.modules.b_bmi_course.entity.BmiCourse;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user_gym.entity.UserGym;

/**
 * Service基类
 * 
 * @author QiuZhu
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>>
		extends BaseService {

	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;

	/**
	 * 获取单条数据
	 * 
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return dao.get(id);
	}
	
	public T getInt(int id){
		return dao.getInt(id);
	}

	/**
	 * 获取单条数据
	 * 
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return dao.get(entity);
	}

	/**
	 * 查询列表数据
	 * 
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}

	/**
	 * 查询分页数据
	 * 
	 * @param page
	 *            分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findList(entity));
		return page;
	}

	/**
	 * 保存数据（插入或更新）
	 * 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (entity.getIsNewRecord()) {
			entity.preInsert();
			dao.insert(entity);
		} else {
			entity.preUpdate();
			dao.update(entity);
		}
	}

	@Transactional(readOnly = false)
	public void update(T entity) {
		entity.preUpdate();
		dao.update(entity);
	}
	@Transactional(readOnly = false)
	public void updateAll(T entity) {
		entity.preUpdate();
		dao.updateAll(entity);
	}

	/**
	 * 保存数据（插入或更新）
	 * 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void add(T entity) {
		if (entity.getIsNewRecord()) {
			entity.preInsert();
			dao.insert(entity);
		} else {
			dao.update(entity);
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		dao.delete(entity);
	}
	@Transactional(readOnly = false)
	public void deletes(T entity) {
		// TODO Auto-generated method stub
		dao.deletes(entity);
	}

	

}

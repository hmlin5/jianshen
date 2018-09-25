/**
 * 
 */
package com.gimc.user.modules.b_user.dao;

import java.util.List;

import com.gimc.user.common.persistence.CrudDao;
import com.gimc.user.common.persistence.annotation.MyBatisDao;
import com.gimc.user.modules.b_user.entity.AppUser;

/**
 * 用户DAO接口
 * 
 * @author gu
 * @version 2018-05-08
 */
@MyBatisDao
public interface AppUserDao extends CrudDao<AppUser> {

	public void lock(AppUser appUser);
	public List<AppUser> findGymUserList(AppUser appUser);
	public List<AppUser> findListWithPassWord(AppUser appUser);
    AppUser getStuId(String stuId);
    
    public List<String> getCoachName(String userId);

}
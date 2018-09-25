/**
 * 
 */
package com.gimc.user.modules.b_pay_info.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_pay_info.entity.PayInfo;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_pay_info.dao.PayInfoDao;

/**
 * 支付信息Service
 * @author linhaomiao
 * @version 2018-06-30
 */
@Service
@Transactional(readOnly = true)
public class PayInfoService extends CrudService<PayInfoDao, PayInfo> {

	public PayInfo get(String id) {
		return super.get(id);
	}
	
	public List<PayInfo> findList(PayInfo payInfo) {
		return super.findList(payInfo);
	}
	
	public Page<PayInfo> findPage(Page<PayInfo> page, PayInfo payInfo) {
		return super.findPage(page, payInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(PayInfo payInfo) {
		super.save(payInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(PayInfo payInfo) {
		super.delete(payInfo);
	}
	
	public PayInfo findByOrderNum(String orderNum){
		
		PayInfo payInfo = new PayInfo();
		payInfo.setOrderNum(orderNum);
		List<PayInfo> list = dao.findList(payInfo);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		
		return null;
	}

	/**
	 * 获取学员的打赏记录
	 * @param user
	 * @return
	 */
	public List<PayInfo> findStuPayInfoList(AppUser user) {
		
		PayInfo condition = new PayInfo();
		condition.setPayflag(CommonParam.PAY_YES);
		condition.setRefundflag(CommonParam.REFUND_NO);
		condition.setStuId(user.getId());
		List<PayInfo> list = dao.findList(condition);
		
		return list;
	}

	/**
	 * 获取教练的受赏记录
	 * @param user
	 * @return
	 */
	public List<PayInfo> findCoachPayInfoList(AppUser user) {
		PayInfo condition = new PayInfo();
		condition.setPayflag(CommonParam.PAY_YES);
		//condition.setRefundflag(CommonParam.REFUND_NO);
		condition.setCoachId(user.getId());
		List<PayInfo> list = dao.findList(condition);
		return list;
	}
	
}
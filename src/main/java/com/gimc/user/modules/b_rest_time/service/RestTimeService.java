/**
 * 
 */
package com.gimc.user.modules.b_rest_time.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.modules.b_rest_time.entity.RestTime;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_order.dao.OrderDao;
import com.gimc.user.modules.b_order.entity.Order;
import com.gimc.user.modules.b_rest_time.dao.RestTimeDao;

/**
 * 休息时间Service
 * @author linhaomiao
 * @version 2018-05-25
 */
@Service
@Transactional(readOnly = true)
public class RestTimeService extends CrudService<RestTimeDao, RestTime> {

	@Autowired
	private OrderDao orderDao;
	
	public RestTime get(String id) {
		return super.get(id);
	}
	
	public List<RestTime> findList(RestTime restTime) {
		return super.findList(restTime);
	}
	
	public Page<RestTime> findPage(Page<RestTime> page, RestTime restTime) {
		return super.findPage(page, restTime);
	}
	
	@Transactional(readOnly = false)
	public void save(RestTime restTime) {
		super.save(restTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(RestTime restTime) {
		super.delete(restTime);
	}

	/**
	 * 批量删除
	 * @param list
	 */
	@Transactional(readOnly = false)
	public void deleteList(List<RestTime> list) {
		if (list != null && list.size()>0) {
			dao.deleteList(list);
		}
		
	}

	/**
	 * 
	 * 
	 * 检查时间是否有冲突,包括休息时间和上课时间,返回true表示有时间重复
	 * @param condition:beginTime,duration(分钟为单位),userId三个必传,gymId选传,
	 * @return
	 */
	public boolean checkTimeConflict(RestTime condition) {
		try {
			Date date = condition.getBeginTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String checkDay = sdf.format(date);
			long duration = Long.parseLong(condition.getDuration())*60*1000;
			Date endTime = getDateTimeByLong(date, duration);
			
			condition.setEndTime(endTime);
			condition.setRestDate(sdf.parse(checkDay));
			
			List<String> busyIds = findBusyUserList(condition);
			if (busyIds.contains(condition.getUserId())) {
				return true;
			}
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	
	/**
	 * 
	 * 
	 * 检查时间是否有冲突,包括休息时间和上课时间,返回true表示有时间重复(主要为添加全天休息是判断用,这时不判断休息时间是否有重复)
	 * @param condition:beginTime,duration(分钟为单位),userId三个必传,gymId选传,
	 * @return
	 */
	public boolean checkTimeConflictAllDayRest(RestTime condition) {
		try {
			Date date = condition.getBeginTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String checkDay = sdf.format(date);
			long duration = Long.parseLong(condition.getDuration())*60*1000;
			Date endTime = getDateTimeByLong(date, duration);
			
			condition.setEndTime(endTime);
			condition.setRestDate(sdf.parse(checkDay));
			
			List<String> busyIds = findBusyUserListAllDayRest(condition);
			if (busyIds.contains(condition.getUserId())) {
				return true;
			}
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	
	
	
	
	/**
	 * 
	 * 
	 * 检查时间是否有冲突,包括休息时间和上课时间,返回true表示有时间重复,不包括指定订单
	 * @param condition:beginTime,duration(分钟为单位),userId三个必传,gymId选传,
	 * @return
	 */
	public boolean checkTimeConflictExcludeOrder(RestTime condition,String orderId) {
		try {
			Date date = condition.getBeginTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String checkDay = sdf.format(date);
			long duration = Long.parseLong(condition.getDuration())*60*1000;
			Date endTime = getDateTimeByLong(date, duration);
			
			condition.setEndTime(endTime);
			condition.setRestDate(sdf.parse(checkDay));
			
			List<String> busyIds = findBusyUserListExcludeOrder(condition,orderId);
			if (busyIds.contains(condition.getUserId())) {
				return true;
			}
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	

	/**
	 * 查询指定健身房指定时间忙的教练,RestTime中包含教练的id(去重)和已被约课的教练(返回的用户id中可能会存在重复,但不影响)
	 * @param condition
	 * @return
	 */
	public List<String> findBusyCoachList(RestTime condition) {
		//查询休息时间冲突的教练id
		condition.setSqlCheckTimeConflictFlag(true);//设置标识让判断查询生效
		condition.setAllDayRest("1");
		List<String> list = dao.findDistinctList(condition);
		
		//查询与订单时间冲突的教练
		long courseBeginTime = condition.getBeginTime().getTime();
		long courseEndTime = condition.getEndTime().getTime();
		Order order = new Order();
		order.setGymId(condition.getGymId());
		List<String> excludeIds = Arrays.asList(CommonParam.ORDER_STATUS_YIQUXIAO);
		order.setExcludeStatus(excludeIds);
		List<Order> orders = orderDao.findList(order);
		for (Order o : orders) {
			Date appointmentTime = o.getAppointmentTime();
			String courseDuration = o.getCourseDuration();
			
			if (StringUtils.isBlank(courseDuration)) {
				continue;
			}
			long durationLong = Long.parseLong(courseDuration);
			
			long startTime = appointmentTime.getTime();
			long endTime = appointmentTime.getTime()+durationLong*60*1000;
			
			if (!(courseEndTime<=startTime || courseBeginTime >= endTime)) {
				list.add(o.getCoachId());
			}
		}
		
		return list;
	}
	
	
	/**
	 * 查询指定健身房指定时间忙的教练,不包括指定订单号,RestTime中包含教练的id(去重)和已被约课的教练(返回的用户id中可能会存在重复,但不影响)
	 * @param condition
	 * @return
	 */
	public List<String> findBusyCoachListExcludeOrder(RestTime condition,String orderId) {
		//查询休息时间冲突的教练id
		condition.setSqlCheckTimeConflictFlag(true);//设置标识让判断查询生效
		condition.setAllDayRest("1");
		List<String> list = dao.findDistinctList(condition);
		
		//查询与订单时间冲突的教练
		long courseBeginTime = condition.getBeginTime().getTime();
		long courseEndTime = condition.getEndTime().getTime();
		Order order = new Order();
		order.setGymId(condition.getGymId());
		List<String> excludeStatus = Arrays.asList(CommonParam.ORDER_STATUS_YIQUXIAO);
		order.setExcludeStatus(excludeStatus);
		List<Order> orders = orderDao.findList(order);
		for (Order o : orders) {
			if (o.getId().equals(orderId)) {
				continue;
			}
			Date appointmentTime = o.getAppointmentTime();
			String courseDuration = o.getCourseDuration();
			
			if (StringUtils.isBlank(courseDuration)) {
				continue;
			}
			long durationLong = Long.parseLong(courseDuration);
			
			long startTime = appointmentTime.getTime();
			long endTime = appointmentTime.getTime()+durationLong*60*1000;
			
			if (!(courseEndTime<startTime || courseBeginTime > endTime)) {
				list.add(o.getCoachId());
			}
		}
		
		return list;
	}
	
	
	
	
	/**
	 * 查询指定时间忙的会员id列表
	 * @param condition
	 * @return
	 */
	public List<String> findBusyUserList(RestTime condition) {
		
		List<String> list = null;
		//查询休息时间冲突的教练id
		condition.setSqlCheckTimeConflictFlag(true);//设置标识让判断查询生效
		condition.setAllDayRest("1");
		list = dao.findDistinctList(condition);
		if (list == null ) {
			list = new ArrayList<String>();
		}
		
		//查询与订单时间冲突的会员
		long courseBeginTime = condition.getBeginTime().getTime();
		long courseEndTime = condition.getEndTime().getTime();
		Order order = new Order();
		List<String> excludeIds = Arrays.asList(CommonParam.ORDER_STATUS_YIQUXIAO);
		order.setExcludeStatus(excludeIds);
		
		List<Order> orders = orderDao.findList(order);
		for (Order o : orders) {
			Date appointmentTime = o.getAppointmentTime();
			String courseDuration = o.getCourseDuration();
			
			if (StringUtils.isNotBlank(condition.getOrderId())) {//指定order的不包含
				continue;
			}
			
			if (StringUtils.isBlank(courseDuration)) {
				continue;
			}
			long durationLong = Long.parseLong(courseDuration);
			
			long startTime = appointmentTime.getTime();
			long endTime = appointmentTime.getTime()+durationLong*60*1000;
			
			if (!(courseEndTime<=startTime || courseBeginTime >= endTime)) {
				list.add(o.getCoachId());
				list.add(o.getStuId());
			}
		}
		
		return list;
	}
	
	/**
	 * 查询指定时间忙的会员id列表(设置全天休息特殊用)
	 * @param condition
	 * @return
	 */
	public List<String> findBusyUserListAllDayRest(RestTime condition) {
		
		List<String> list =  new ArrayList<String>();
		
		
		//查询与订单时间冲突的会员
		long courseBeginTime = condition.getBeginTime().getTime();
		long courseEndTime = condition.getEndTime().getTime();
		Order order = new Order();
		List<String> excludeIds = Arrays.asList(CommonParam.ORDER_STATUS_YIQUXIAO);
		order.setExcludeStatus(excludeIds);
		
		List<Order> orders = orderDao.findList(order);
		for (Order o : orders) {
			Date appointmentTime = o.getAppointmentTime();
			String courseDuration = o.getCourseDuration();
			
			if (StringUtils.isNotBlank(condition.getOrderId())) {//指定order的不包含
				continue;
			}
			
			if (StringUtils.isBlank(courseDuration)) {
				continue;
			}
			long durationLong = Long.parseLong(courseDuration);
			
			long startTime = appointmentTime.getTime();
			long endTime = appointmentTime.getTime()+durationLong*60*1000;
			
			if (!(courseEndTime<=startTime || courseBeginTime >= endTime)) {
				list.add(o.getCoachId());
				list.add(o.getStuId());
			}
		}
		
		return list;
	}
	
	
	
	
	/**
	 * 查询指定时间忙的会员id列表,,不包括指定订单号
	 * @param condition
	 * @return
	 */
	public List<String> findBusyUserListExcludeOrder(RestTime condition, String orderId) {
		
		List<String> list = null;
		//查询休息时间冲突的教练id
		condition.setSqlCheckTimeConflictFlag(true);//设置标识让判断查询生效
		condition.setAllDayRest("1");
		list = dao.findDistinctList(condition);
		if (list == null ) {
			list = new ArrayList<String>();
		}
		
		//查询与订单时间冲突的会员
		long courseBeginTime = condition.getBeginTime().getTime();
		long courseEndTime = condition.getEndTime().getTime();
		Order order = new Order();
		List<String> excludeIds = Arrays.asList(CommonParam.ORDER_STATUS_YIQUXIAO);
		order.setExcludeStatus(excludeIds);
		
		List<Order> orders = orderDao.findList(order);
		for (Order o : orders) {
			if (o.getId().equals(orderId)) {
				continue;
			}
			
			Date appointmentTime = o.getAppointmentTime();
			String courseDuration = o.getCourseDuration();
			
			if (StringUtils.isNotBlank(condition.getOrderId())) {//指定order的不包含
				continue;
			}
			
			if (StringUtils.isBlank(courseDuration)) {
				continue;
			}
			long durationLong = Long.parseLong(courseDuration);
			
			long startTime = appointmentTime.getTime();
			long endTime = appointmentTime.getTime()+durationLong*60*1000;
			
			if (!(courseEndTime<=startTime || courseBeginTime >= endTime)) {
				list.add(o.getCoachId());
				list.add(o.getStuId());
			}
		}
		
		return list;
	}
	
	
	
	
	public Date getDateTimeByLong(Date startTime, long duration){
		
		try {
			long time = startTime.getTime();
			long end = time + duration;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(end);
			
			String format = sdf.format(date);
			Date parse = sdf.parse(format);
			return parse;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
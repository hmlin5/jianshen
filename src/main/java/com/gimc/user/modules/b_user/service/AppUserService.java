/**
 * 
 */
package com.gimc.user.modules.b_user.service;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import com.gimc.user.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gimc.user.common.config.SysConfigData;
import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.service.CrudService;
import com.gimc.user.common.utils.JavaSmsApi;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user.entity.SysCheckCode;
import com.gimc.user.modules.sys.service.SystemService;
import com.gimc.user.modules.b_bmi_course.dao.BmiCourseDao;
import com.gimc.user.modules.b_bmi_course.entity.BmiCourse;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_order.dao.OrderDao;
import com.gimc.user.modules.b_order.entity.Order;
import com.gimc.user.modules.b_user.dao.AppUserDao;
import com.gimc.user.modules.b_user.dao.SysCheckCodeDao;
import com.gimc.user.modules.b_user_gym.dao.UserGymDao;
import com.gimc.user.modules.b_user_gym.entity.UserGym;
import com.gimc.user.modules.b_user_gym.service.UserGymService;

/**
 * 用户Service
 * @author gu
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly = true)
public class AppUserService extends CrudService<AppUserDao, AppUser> {
	
	@Autowired
	private SysCheckCodeDao sysCheckCodeDao;
	@Autowired
	private AppUserDao appUserDao;
	@Autowired
	private BmiCourseDao bmiCourseDao;
	@Autowired
	private UserGymDao userGymDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private UserGymService userGymService;
	
	//111111
	public List<String> getCoachNameForUser(String userId){
		return appUserDao.getCoachName(userId);
	}
	
	public AppUser getStuId(String stuId){
		return appUserDao.getStuId(stuId);
	}
	
	public AppUser get(String id) {
		return super.get(id);
	}
	
	public List<AppUser> findList(AppUser appUser) {
		return super.findList(appUser);
	}
	
	public Page<AppUser> findPage(Page<AppUser> page, AppUser appUser) {
		return super.findPage(page, appUser);
	}
	
	@Transactional(readOnly = false)
	public void save(AppUser appUser) {
		super.save(appUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(AppUser appUser) {
		super.delete(appUser);
	}
	
	@Transactional(readOnly = false)
	public void lock(AppUser appUser) {
		appUserDao.lock(appUser);
	}
	
	
	public AppUser selectUserByPhone(String phone){
		
		AppUser condition = new AppUser();
		condition.setPhone(phone);
		List<AppUser> list = dao.findList(condition);
		if (list != null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	
	
	
	
	
	
	
	//----------------  验证码 ------------------
	public  synchronized String canSendCheckCode(String phoneNum) {
		try {
			SysCheckCode param = new SysCheckCode();
			param.setPhoneNum(phoneNum);
			
			Page<SysCheckCode> page = new Page<SysCheckCode>();
			page.setOrderBy(" CREATE_DATE desc ");
			param.setPage(page);
			
			List<SysCheckCode> checkCodes = this.sysCheckCodeDao.findList(param);
			
			if(checkCodes!=null && checkCodes.size()>0){
				SysCheckCode cc = checkCodes.get(0);
				if(cc.getPhoneNum()!=null){
					if(cc.getCreateDate().getTime()+1*60*1000>new Date().getTime()){
						return cc.getCheckCode();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional(readOnly = false)
	public String sendCheckCode(String phoneNum) {
		String checkCode = "";
		try {
			Random random = new Random();
			int x = random.nextInt(8999);
			x = x+1000;
			checkCode = x+"";
			
			SysCheckCode ck = new SysCheckCode();
			if(StringUtils.isNotBlank(phoneNum)){
				ck.setPhoneNum(phoneNum);
			}
			ck.setCreateDate(new Date());
			ck.setCheckCode(checkCode);
			this.sysCheckCodeDao.insert(ck);
			
			if(StringUtils.isNotBlank(phoneNum)){
				 //设置模板ID，如使用1号模板:【#company#】您的验证码是#code#
		        String apikey = "d17fc632af0ad59fab8581ded2e43549";
		    	long tpl_id = 1;
		    	String mobile = phoneNum;
		        //设置对应的模板变量值
		        //如果变量名或者变量值中带有#&=%中的任意一个特殊符号，需要先分别进行urlencode编码
		        //如code值是#1234#,需作如下编码转换
		        String codeValue = URLEncoder.encode(checkCode, "utf-8");
		        String tpl_value = "#code#=" + codeValue + "&#company#="+CommonParam.CHECK_CODE_TAB_NAME;
		        //模板发送的调用示例
		        System.out.println(JavaSmsApi.tplSendSms(apikey, tpl_id, tpl_value, mobile));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkCode;
	}
	
	 public SysCheckCode getCheckCode(String phoneNum) {
		SysCheckCode param = new SysCheckCode();
		param.setPhoneNum(phoneNum);
		
		Page<SysCheckCode> page = new Page<SysCheckCode>();
		page.setOrderBy(" CREATE_DATE desc ");
		param.setPage(page);
		
		List<SysCheckCode> cks = this.sysCheckCodeDao.findList(param);
		
		if(cks!=null&&cks.size()>0){
			return cks.get(0);
		}else{
			return null;
		}
	}

	public AppUser selectUserByLoginNameAndPwd(String phoneNum, String pwd) {
		
		AppUser condition = new AppUser();
		condition.setPhone(phoneNum);
		condition.setPassword(SystemService.entryptPassword(pwd));
		List<AppUser> findList = dao.findList(condition);
		if (findList != null && findList.size()>0) {
			return findList.get(0);
		}
		return null;
	}

	
	public AppUser selectUserByAccessToken(String accessToken) {
		
		AppUser condition = new AppUser();
		condition.setToken(accessToken);
		List<AppUser> list = dao.findList(condition);
		if (list != null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据手机号查询已激活的用户
	 * @param phoneNum
	 * @return
	 */
	public AppUser selectActivatedUserByPhone(String phoneNum) {
		
		AppUser condition = new AppUser();
		condition.setPhone(phoneNum);
		condition.setActivateFlag("1");
		List<AppUser> list = dao.findListWithPassWord(condition);
		if (list != null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据bmi指数得到相应推荐课程  //TODO 需要查询健身房是否定制,和是否有个人定制推荐数
	 * @param bmiIndex
	 * @return
	 */
	public String getCourseByBmiIndex(double bmiIndex) {
		
		BmiCourse condition = new BmiCourse();
		condition.setCreatorType("1");
		List<BmiCourse> list = bmiCourseDao.findList(condition);
		if (list!=null && list.size()>0) {
			BmiCourse bmiCourse = list.get(0);
			if (bmiIndex<bmiCourse.getZengjiMax()) {
				return "1";
			}else if (bmiIndex>=bmiCourse.getJianzhiMin()) {
				return "3";
			}else{
				return "2";
			}
		}
		
		return null;
	}
	
	/**
	 * 根据bmi指数得到相应推荐课程数量 //TODO 需要查询健身房是否定制,和是否有个人定制推荐数
	 * @param bmiIndex
	 * @return
	 */
	public int getCourseNumByBmiIndex(double bmiIndex) {
		
		BmiCourse condition = new BmiCourse();
		condition.setCreatorType("1");
		List<BmiCourse> list = bmiCourseDao.findList(condition);
		if (list!=null && list.size()>0) {
			BmiCourse bmiCourse = list.get(0);
			if (bmiIndex<bmiCourse.getZengjiMax()) {
				return bmiCourse.getZengjiBase();
			}else if (bmiIndex>=bmiCourse.getJianzhiMin()) {
				return bmiCourse.getJianzhiBase();
			}else{
				return bmiCourse.getSuxingBase();
			}
		}
		
		return 0;
	}
	
	
	

	public Page<AppUser> findGymUserPage(Page<AppUser> page, AppUser appUser) {
		appUser.setPage(page);
		page.setList(dao.findGymUserList(appUser));
		return page;
		
	}

	
	/**
	 * 查看教练所有会员
	 * @param user
	 * @return
	 */
	public List<AppUser> findMyStuList(AppUser user) {
		if (user == null || StringUtils.isBlank(user.getId())) {
			return null;
		}
		
		List<String> includeIds = new ArrayList<String>();  
		
		UserGym userGym = new UserGym();
		userGym.setCoachId(user.getId());
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		//userGym.setFreezeFlag(CommonParam.FREEZE_NO);
		List<UserGym> list = userGymDao.findList(userGym);
		
		Order order = new Order();
		
		List<String> includeStatus = new ArrayList<String>(); 
		includeStatus.add(CommonParam.ORDER_STATUS_DAIPINGJIA);
		includeStatus.add(CommonParam.ORDER_STATUS_YIWANCHENG);
		
		order.setIncludeStatus(includeStatus);
		order.setCoachId(user.getId());
		
		List<Order> orders = orderDao.findList(order);
		
		if ((list == null || list.size() < 1) && (orders == null || orders.size() < 1)) {
			return null;
		}
		
		for (UserGym ug : list) {
			includeIds.add(ug.getUserId());
		}
		for (Order o : orders) {
			includeIds.add(o.getStuId());
		}
		
		
		AppUser appUser = new AppUser();
		appUser.setIncludeIds(includeIds);
		appUser.setActivateFlag(CommonParam.ACTIVE_YES);
		appUser.setNickName(user.getName());
		List<AppUser> users = dao.findList(appUser);
		
		return users;
	}

	
	/**
	 * 查看教练在自己健身房的所有会员
	 * @param user
	 * @return
	 */
	public List<AppUser> findMyStuListInGym(AppUser user) {
		if (user == null || StringUtils.isBlank(user.getId())) {
			return null;
		}
		
		List<String> includeIds = new ArrayList<String>();  
		
		UserGym userGym = new UserGym();
		userGym.setCoachId(user.getId());
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		//userGym.setFreezeFlag(CommonParam.FREEZE_NO);
		List<UserGym> list = userGymDao.findList(userGym);
		
		Order order = new Order();
		
		List<String> includeStatus = new ArrayList<String>(); 
		includeStatus.add(CommonParam.ORDER_STATUS_DAIPINGJIA);
		includeStatus.add(CommonParam.ORDER_STATUS_YIWANCHENG);
		
		order.setIncludeStatus(includeStatus);
		order.setCoachId(user.getId());
		order.setGymId(user.getGymId());//只查询与教练同一个健身房的会员
		
		List<Order> orders = orderDao.findList(order);
		
		if ((list == null || list.size() < 1) && (orders == null || orders.size() < 1)) {
			return null;
		}
		
		for (UserGym ug : list) {
			includeIds.add(ug.getUserId());
		}
		for (Order o : orders) {
			includeIds.add(o.getStuId());
		}
		
		
		AppUser appUser = new AppUser();
		appUser.setIncludeIds(includeIds);
		appUser.setActivateFlag(CommonParam.ACTIVE_YES);
		appUser.setNickName(user.getName());
		List<AppUser> users = dao.findList(appUser);
		
		return users;
	}
	
	
	/**
	 * 查看会员的所有教练
	 * @param user
	 * @return
	 */
	public List<AppUser> findMyCoachList(AppUser user) {
		if (user == null || StringUtils.isBlank(user.getId())) {
			return null;
		}
		
		List<String> includeIds = new ArrayList<String>();  
		
		UserGym userGym = new UserGym();
		userGym.setUserId(user.getId());
		userGym.setBindFlag(CommonParam.USER_GYM_BIND_YES);
		//userGym.setFreezeFlag(CommonParam.FREEZE_NO);
		List<UserGym> list = userGymDao.findList(userGym);
		
		Order order = new Order();
		
		List<String> includeStatus = new ArrayList<String>(); 
		includeStatus.add(CommonParam.ORDER_STATUS_DAIPINGJIA);
		includeStatus.add(CommonParam.ORDER_STATUS_YIWANCHENG);
		
		order.setIncludeStatus(includeStatus);
		order.setStuId(user.getId());
		
		List<Order> orders = orderDao.findList(order);
		
		if ((list == null || list.size() < 1) && (orders == null || orders.size() < 1)) {
			return null;
		}
		
		for (UserGym ug : list) {
			includeIds.add(ug.getCoachId());
		}
		for (Order o : orders) {
			includeIds.add(o.getCoachId());
		}
		
		
		AppUser appUser = new AppUser();
		appUser.setIncludeIds(includeIds);
		appUser.setActivateFlag(CommonParam.ACTIVE_YES);
		appUser.setNickName(user.getName());
		List<AppUser> users = dao.findList(appUser);
		
		for (AppUser au : users) {
			Gym gym = userGymService.getGymByCoachId(au);
			if (gym!=null) {
				au.setGymName(gym.getName());
			}
			
		}
		
		return users;
	}
	
	

	/**
	 * 根据手机号码生成昵称
	 * @param phone
	 * @return
	 */
	public String generateNickNameByPhone(String phone){
		
		if (phone.length()>=11) {
			  phone = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
		}
		
		return phone;
	}
	
	

	/*
	 * 
	 * 读取文件
	 * */
	
	public String[] getFile(String path){
        File file=new File(path);
        String[] result=null;
        try {
            HWPFDocument hw=new HWPFDocument(new FileInputStream(file));
            WordExtractor wordExtractor=new WordExtractor(hw);
            result=wordExtractor.getParagraphText();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


	public String sendAppointment(Order order) {
		try {

				//设置模板ID，如使用1号模板:【#company#】您的验证码是#code#
				String apikey = "d17fc632af0ad59fab8581ded2e43549";
				long tpl_id = 2412918;
				String stuPhone = order.getStuPhone();
				String coachPhone = order.getCoachPhone();
				//设置对应的模板变量值
				//如果变量名或者变量值中带有#&=%中的任意一个特殊符号，需要先分别进行urlencode编码
				//如code值是#1234#,需作如下编码转换
				//String codeValue = URLEncoder.encode(checkCode, "utf-8");
				Calendar beforeTime = Calendar.getInstance();
				beforeTime.setTime(order.getAppointmentTime());
				beforeTime.add(Calendar.MINUTE, -15);// 15分钟之前的时间
				Date beforeTimes=beforeTime.getTime();
			    String dateWeek= "("+DateUtil.dateToWeek(order.getAppointmentTime())+")";
				String tpl_value = "#name#=" + order.getStuName() + "&#time#=" + DateUtil.formateDate2Str(order.getAppointmentTime(),DateUtil._MMddHHmm)+dateWeek+"&#beforeTimes#=" + DateUtil.formateDate2Str(beforeTimes,DateUtil.HHmm)+"&#gym#=" + order.getGymName();
				//模板发送的调用示例
				System.out.println(JavaSmsApi.tplSendSms(apikey, tpl_id, tpl_value, coachPhone));
			    tpl_id=2414342;
			    tpl_value = "#coachName#=" + order.getCoachName() + "&#time#=" + DateUtil.formateDate2Str(order.getAppointmentTime(),DateUtil._MMddHHmm)+dateWeek+"&#beforeTimes#=" + DateUtil.formateDate2Str(beforeTimes,DateUtil.HHmm)+"&#gym#=" + order.getGymName();
				System.out.println(JavaSmsApi.tplSendSms(apikey, tpl_id, tpl_value, stuPhone));


		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
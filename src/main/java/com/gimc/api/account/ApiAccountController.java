package com.gimc.api.account;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gimc.user.common.config.Global;
import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.common.utils.burningUtil.ListSortUtil;
import com.gimc.user.common.utils.burningUtil.LocationUtils;
import com.gimc.user.common.utils.burningUtil.OrderUtil;
import com.gimc.user.common.utils.burningUtil.PageBean;
import com.gimc.user.common.utils.burningUtil.RequestMap;
import com.gimc.user.common.utils.burningUtil.ResultMap;
import com.gimc.user.modules.b_bmi_course.service.BmiCourseService;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_gym.service.GymService;
import com.gimc.user.modules.b_gym_img.entity.GymImg;
import com.gimc.user.modules.b_gym_img.service.GymImgService;
import com.gimc.user.modules.b_openingbank.entity.Openingbank;
import com.gimc.user.modules.b_openingbank.service.OpeningbankService;
import com.gimc.user.modules.b_order.entity.Order;
import com.gimc.user.modules.b_order.service.OrderService;
import com.gimc.user.modules.b_pay_info.entity.PayInfo;
import com.gimc.user.modules.b_pay_info.service.PayInfoService;
import com.gimc.user.modules.b_rest_time.entity.RestTime;
import com.gimc.user.modules.b_rest_time.service.RestTimeService;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user.entity.SysCheckCode;
import com.gimc.user.modules.b_user.service.AppUserService;
import com.gimc.user.modules.b_user_account.entity.UserAccount;
import com.gimc.user.modules.b_user_account.service.UserAccountService;
import com.gimc.user.modules.b_user_bank.entity.UserBank;
import com.gimc.user.modules.b_user_bank.service.UserBankService;
import com.gimc.user.modules.b_user_course.entity.UserCourse;
import com.gimc.user.modules.b_user_course.service.UserCourseService;
import com.gimc.user.modules.b_user_gym.entity.UserGym;
import com.gimc.user.modules.b_user_gym.service.UserGymService;
import com.gimc.user.modules.b_user_test.entity.UserTest;
import com.gimc.user.modules.b_user_test.service.UserTestService;
import com.gimc.user.modules.b_user_withdraw.dao.UserWithdrawDao;
import com.gimc.user.modules.b_user_withdraw.entity.MoneyDetail;
import com.gimc.user.modules.b_user_withdraw.entity.UserWithdraw;
import com.gimc.user.modules.b_user_withdraw.service.UserWithdrawService;
import com.gimc.user.modules.sys.service.SystemService;


@Controller
@RequestMapping("/api/account")
public class ApiAccountController {
	
	
	@Autowired
	private AppUserService userService;
	
	
	@Autowired
	private GymService gymService;
	@Autowired
	private GymImgService gymImgService;
	@Autowired
	private UserGymService userGymService;
	@Autowired
	private UserTestService userTestService;
	@Autowired
	private RestTimeService restTimeService;
	@Autowired
	private UserCourseService userCourseService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserBankService userBankService;
	@Autowired
	private OpeningbankService bankService;
	@Autowired
	private UserWithdrawService userWithdrawService;
	@Autowired
	private UserWithdrawDao userWithdrawDao;
	@Autowired
	private PayInfoService payInfoService;
	
	
	/**
	 * 教练查看打赏或者提现详情
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/moneyDetail")
	public ResultMap moneyDetail(RequestMap param, HttpServletRequest request){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		String type = param.getType();  //type=reward表示查询打赏详情,type=withdraw表示查询提现详情
		String id = param.getId();
		
		if (StringUtils.isBlank(type)|| StringUtils.isBlank(id)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		if (!("reward".equalsIgnoreCase(type) || "withdraw".equalsIgnoreCase(type))) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请输入正确的type:reward或者withdraw");
		}
		
		if ("reward".equalsIgnoreCase(type)) {
			PayInfo payInfo = payInfoService.get(id);
			if (!user.getId().equals(payInfo.getCoachId())) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("无权限");
			}
			return map.setState(CommonParam.STATE_OK).setData(payInfo);
		}
		if ("withdraw".equalsIgnoreCase(type)) {
			UserWithdraw userWithdraw = userWithdrawService.get(id);
			if (!user.getId().equals(userWithdraw.getUserId())) {
				return map.setState(CommonParam.STATE_ERROR).setMsg("无权限");
			}
			return map.setState(CommonParam.STATE_OK).setData(userWithdraw);
		}
			
		
		return map.setState(CommonParam.STATE_ERROR);
		
	}
	
	
	
	
	
	/**
	 * 余额明细列表
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/moneyDetailList")
	public ResultMap checkPayPwdSetting(RequestMap param, HttpServletRequest request){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		String type = param.getType();  //type=reward表示查询收入,type=withdraw表示查询支出, type为null表示查询所有
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		
		
		UserWithdraw condition = new UserWithdraw();
		condition.setUserId(user.getId());
		condition.setType(type);
		List<MoneyDetail> list = userWithdrawService.findWithdrawAndPayInfoList(condition);
		
		//分页
		PageBean<MoneyDetail> pageBean = new PageBean<MoneyDetail>();
		if (list!=null && list.size()>0) {
			pageBean = new PageBean<MoneyDetail>(currentPage, pageSize, list.size());
			pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
			
		}
			
		return map.setState(CommonParam.STATE_OK).setData(pageBean);
		
	}
	
	
	/**
	 * 判断用户是否设置了支付密码
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/checkPayPwdSetting")
	public ResultMap checkPayPwdSetting(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		UserAccount uc = userAccountService.findByUserId(user);
		if (uc == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("账户不存在, 请联系后台管理员");
		}
		String pwdFlag = "0";
		if (StringUtils.isNotBlank(uc.getPayPwd())) {
			pwdFlag = "1";
		}
			
		return map.setState(CommonParam.STATE_OK).setData(pwdFlag);
		
	}
	
	/**
	 * 提现
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/withdraw")
	public ResultMap withdraw(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		String payPwd = param.getPayPwd();
		String bankCardId = param.getBankCardId();
		
		if (StringUtils.isBlank(bankCardId) || StringUtils.isBlank(payPwd) || param.getWithdrawAmount() == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		double withdrawAmount = param.getWithdrawAmount();
		
		
		UserAccount uc = userAccountService.findByUserId(user);
		if (!SystemService.validatePassword(payPwd, uc.getPayPwd())) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("支付密码错误");
		}
		
		UserBank userBank = userBankService.get(bankCardId);
		if (!user.getId().equals(userBank.getOwnerId()) ) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("该银行卡与您不是绑定关系,请确认");
		}
		
		double usableAmount = uc.getUsableAmount();
		if (usableAmount<withdrawAmount) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("可用余额不足");
		}
		
		//更新账户信息
		userAccountService.withdraw(uc,withdrawAmount);
		
		//更新提现记录
		UserWithdraw userWithDraw = new UserWithdraw();
		userWithDraw.setBankCardNo(userBank.getCardNo());
		userWithDraw.setBranchBankId(userBank.getId());
		userWithDraw.setBranchBankName(userBank.getBranchBank());
		userWithDraw.setHeadBankId(userBank.getHeadBankId());
		userWithDraw.setUserPhone(user.getPhone());
		userWithDraw.setApplicant(userService.generateNickNameByPhone(user.getPhone()));
		userWithDraw.setUserName(userBank.getOwnerName());
		userWithDraw.setStatus(CommonParam.WITHDRAW_STATUS_SHENQINGZHONG);
		userWithDraw.setUserId(user.getId());
		
		OrderUtil orderUtil = new OrderUtil();
		String withdrawNo = orderUtil.makeOrderNum(null);
		userWithDraw.setWithdrawNo(withdrawNo);//申请号
		userWithDraw.setWithdrawAmount(withdrawAmount);
		userWithDraw.setWithdrawTime(new Date());
		
		try {
			userWithdrawService.save(userWithDraw);//设置了提现单号唯一, 可能会有提现单号重复错误
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map.setState(CommonParam.STATE_OK);
	}
	
	
	
	
	/**
	 * 解绑提现银行卡
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/unbindBankCard")
	public ResultMap unbindBankCard(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		String id = param.getId();
		String payPwd = param.getPayPwd();
		
		if (StringUtils.isBlank(id) || StringUtils.isBlank(payPwd)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		UserBank userBank = userBankService.get(id);
		if (!user.getId().equals(userBank.getOwnerId())) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("您没有权限解绑该银行卡");
		}
		
		UserAccount uc = userAccountService.findByUserId(user);
		if (StringUtils.isBlank(uc.getPayPwd())) {
			return map.setState(CommonParam.STATE_DATA_PWD_NULL).setMsg("您尚未设置支付密码");
		}
		if (!SystemService.validatePassword(payPwd, uc.getPayPwd())) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("密码错误");
		}
		userBankService.delete(userBank);
		
	
		return map.setState(CommonParam.STATE_OK);
	}
	
	
	
	/**
	 * 选择提现银行卡列表
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/withdrawBankCardList")
	public ResultMap withdrawBankCardList(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		AppUser user = (AppUser) request.getAttribute("user");
		
		UserBank userBank = new UserBank();
		userBank.setOwnerId(user.getId());
		
		List<UserBank> list = userBankService.findList(userBank);
	
		return map.setState(CommonParam.STATE_OK).setData(list);
	}
	
	
	
	
	/**
	 * 添加提现银行卡
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/addBankCard")
	public ResultMap addBankCard(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
	
		
		String checkCode = param.getCheckCode();
		String headBankId = param.getHeadBankId();
		String branchBankName = param.getBranchBankName();
		String cardNo = param.getCardNo();
		String owner = param.getOwner();
		
		Openingbank headBank = bankService.get(headBankId);
		if (headBank == null) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求异常");
		}
		
		if (StringUtils.isBlank(headBankId) || StringUtils.isBlank(branchBankName) || StringUtils.isBlank(cardNo) || StringUtils.isBlank(owner)) {
			return map.setState(CommonParam.STATE_ERROR).setMsg("请求参数为空");
		}
		
		String phoneNum = user.getPhone();
		SysCheckCode ck = userService.getCheckCode(phoneNum);
		if(ck==null || !ck.getCheckCode().equals(checkCode)){
			return map.setState(CommonParam.STATE_ERROR).setMsg("验证码不正确！");
		}
		
		UserBank userBank = new UserBank();
		userBank.setCardNo(cardNo);
		userBank.setOwnerId(user.getId());
		userBank.setOwnerName(owner);
		userBank.setBranchBank(branchBankName);
		userBank.setHeadBankId(headBankId);
		userBank.setHeadBankName(headBank.getOpeningbankName());
		
		userBankService.save(userBank);
		
		return map.setState(CommonParam.STATE_OK);
		
	}
	
	/**
	 * 查询持卡人姓名
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/findBankCardOwner")
	public ResultMap findBankCardOwner(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
	
		UserGym condition = new UserGym();
		condition.setUserId(user.getId());
		List<UserGym> list = userGymService.findList(condition);
		String owner = "";
		if (list!=null && list.size()>0) {
			owner = list.get(0).getUserName();
		}
		return map.setState(CommonParam.STATE_OK).setData(owner);
		
	}
	
	
	
	/**
	 * 获取开户银行列表
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/headBankList")
	public ResultMap headBankList(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		int currentPage = param.getCurrentPage();
		int pageSize = param.getPageSize();
		
		List<Openingbank> list = bankService.findList(new Openingbank());
		
		//分页
		PageBean<Openingbank> pageBean = new PageBean<Openingbank>();
		if (list!=null && list.size()>0) {
			pageBean = new PageBean<Openingbank>(currentPage, pageSize, list.size());
			pageBean.setPageData(list.subList(pageBean.getStartIndex(), (pageBean.getStartIndex()+pageSize) > list.size() ? list.size() : (pageBean.getStartIndex()+pageSize)));
			
		}
	
		return map.setState(CommonParam.STATE_OK).setData(pageBean);
	}
	
	
	
	/**
	 * 获取用户默认银行卡信息
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/myDefaultBankCard")
	public ResultMap myDefaultBankCard(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
	
		UserAccount uc = userAccountService.findByUserId(user);
		
		String defaultBankCardId = uc.getDefaultBankCardId();
		UserBank userBank = userBankService.get(defaultBankCardId);
		if (StringUtils.isBlank(defaultBankCardId) || userBank == null || (userBank!=null && "1".equals(userBank.getDelFlag())) ) {//以防逻辑删除没更新用户账号信息
			UserBank condition = new UserBank();
			condition.setOwnerId(user.getId());
			List<UserBank> list = userBankService.findList(condition);
			if (list == null || list.size() < 1) {
				return map.setState(CommonParam.STATE_DATA_NOT_EXIST).setMsg("名下尚未有绑定银行卡,请先绑定");//安卓端根据msg判断是否有绑定银行卡,msg有变动需要通知安卓端
			}
			
			uc.setDefaultBankCardId(list.get(0).getId());
			userAccountService.save(uc);
			
			return map.setState(CommonParam.STATE_OK).setData(list.get(0));
		}
		
	
		/*if (userBank!=null && "1".equals(userBank.getDelFlag())) {//以防逻辑删除没更新用户账号信息
			UserBank condition = new UserBank();
			condition.setOwnerId(user.getId());
			List<UserBank> list = userBankService.findList(condition);
			if (list == null || list.size() < 1) {
				return map.setState(CommonParam.STATE_DATA_NOT_EXIST).setMsg("名下尚未有绑定银行卡,请先绑定");
			}
			
			uc.setDefaultBankCardId(list.get(0).getId());
			userAccountService.save(uc);
			
			return map.setState(CommonParam.STATE_OK).setData(list.get(0));
		}*/
		
		return map.setState(CommonParam.STATE_OK).setData(userBank);
		
	}
	
	
	
	/**
	 * 查询用户余额详情
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobile/accountDetail")
	public ResultMap accountDetail(RequestMap param, HttpServletRequest request,HttpServletResponse response){
		ResultMap map = new ResultMap();
		
		AppUser user = (AppUser) request.getAttribute("user");
	
		UserAccount uc = userAccountService.findByUserId(user);
		uc.setPayPwd(null);
		return map.setState(CommonParam.STATE_OK).setData(uc);
		
	}
	
	
	
	
	
	
	
	
	
	
    
	
}

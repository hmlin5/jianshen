package com.gimc.user.common.constant;



/**
 * 系统程序代码 
 */
public enum SysStatusCode {
	
	//200 - 300之间表示程序错误
	/**
	 * 操作成功
	 */
	SYS_OK("200","操作成功"),
	/**
	 * 操作失败
	 */
	SYS_FAIL("201","操作失败"),
	/**
	 * 登录超时
	 */
	SYS_LOGIN_TIMEOUT("202","登录超时"),
	/**
	 * id错误
	 */
	SYS_ID_ERROR("203","id错误"),
	/**
	 * 未登录
	 */
	SYS_NOT_LOGIN("204","未登录"),
	/**
	 * 密码错误
	 */
	SYS_PWD_ERROR("205","密码错误"),
	/**
	 * 手机号已存在
	 */
	SYS_PHONE_EXISTS("206","手机号已存在"),
	/**
	 * 账户不存在
	 */
	SYS_NOT_USER("208","账户不存在"),
	/**
	 * 校验码错误或过期
	 */
	SYS_CHECKCODE_ERROR("209","校验码错误或过期"),  
	/**
	 * 图形验证码错误
	 */
	SYS_VALIDCODE_ERROR("210","验证码错误"),	
	/**
	 * 超过短信最大限制
	 */
	SYS_SMS_LIMIT("211","该号码短信发送超过限制"), 
	/**
	 * 账户已停用
	 */
	SYS_NO_UNENABLE("212","账户已停用"),
	/**
	 * 手机号不合法
	 */
	SYS_PHONE_NOTLEGAL("214","手机号不合法"),
	/**
	 * 账号不正确
	 */
	SYS_NO_ERROR("216","账号不正确"),
	/**
	 * 参数错误
	 */
	SYS_PARAM_ERROR("217","参数错误"),
	/**
	 * 用户无权限
	 */
	SYS_WITHOUT_PERMISSION("218","用户无权限"),
	/**
	 * 数额溢出或太低
	 */
	SYS_AMOUNT_ERROR("219","数额溢出或太低"),
	/**
	 * 没有对应的数据
	 */
	SYS_NO_CORRESPONDING_DATA("220","没有对应的数据"),
	
	/** 非法请求 **/
	SYS_ILLEGAL_REQUEST("223","非法请求"),
	/**
	 * 程序错误
	 */
	SYS_ERROR("300","程序异常");

    private String KEY ;
    private String VALUE;
    
    SysStatusCode(String KEY, String VALUE){
    	this.KEY = KEY;
    	this.VALUE = VALUE;
    }

	public String getKEY() {
		return KEY;
	}

	public String getVALUE() {
		return VALUE;
	}



	public static SysStatusCode valueOfCode(String code) {
		for (SysStatusCode e : SysStatusCode.values()) {
			if (e.getKEY().equals(code)) {
				return e;
			}
		}
		return null;
	}
	
	public static String get(String key) {
		for (SysStatusCode e : SysStatusCode.values()) {
			if (e.getKEY().equals(key)) {
				return e.getVALUE();
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.print(SysStatusCode.SYS_OK.KEY);
	}

}

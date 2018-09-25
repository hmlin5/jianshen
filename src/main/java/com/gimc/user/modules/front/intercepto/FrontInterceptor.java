package com.gimc.user.modules.front.intercepto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gimc.user.common.config.Global;
import com.gimc.user.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.gimc.user.modules.sys.utils.UserUtils;

public class FrontInterceptor extends HandlerInterceptorAdapter {
    
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean isTrue = false;
		try {
			boolean handlerOk = super.preHandle(request, response, handler);
			if (handlerOk) {
				String url = request.getRequestURI();
				String param = request.getQueryString();
				System.out.println(url+"?"+param);
				if (url.endsWith("/f/") || url.endsWith("/f")
						|| url.endsWith("/f/login") // 用户退回登录接口
						|| url.endsWith("/f/userLogin") // 用户退回登录接口
						|| url.endsWith("/f/logout") // 用户退回登录接口
						|| url.endsWith("/f/sms/sendValidateMsg")
						|| url.endsWith("f/modifyPassword")
						|| url.startsWith("/userfiles/")
						|| url.startsWith("/f/forget")) // 过滤登陆接口{
				{
					isTrue = true;
				} else {
					Principal principal = UserUtils.getPrincipal();
						// 如果已经登录，则跳转到管理首页
					if(principal != null){
						isTrue = true;
					}else{
						isTrue = true;
						/*String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+ Global.getFrontPath()+"/login"; 
						response.sendRedirect(basePath);*/
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			
		}
		return isTrue;
	}
	
	/**
	 * 后处理（调用了Service并返回ModelAndView，但未进行页面渲染） 修改ModelAndView
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 返回处理 可以根据ex是否为null判断是否发生了异常，进行日志记录
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
}

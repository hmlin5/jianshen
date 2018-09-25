/**
 * 
 */
package com.gimc.user.modules.sys.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gimc.user.common.service.BaseService;
import com.gimc.user.common.utils.SpringContextHolder;
import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.common.utils.UserAgentUtils;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_user.entity.AppUser;
import com.gimc.user.modules.b_user.service.AppUserService;
import com.google.gson.Gson;

/**
 * 手机端视图拦截器
 * @author QiuZhu
 * @version 2014-9-1
 */
public class MobileInterceptor extends BaseService implements HandlerInterceptor {
	
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {

		Map<String, Object> map = new HashMap<String,Object>();
		Gson gson = new Gson();
		
		AppUserService userService = SpringContextHolder.getBean(AppUserService.class);
		//获取app登录令牌
		String accessToken = request.getParameter("accessToken");
		
		
		    PrintWriter out = null;  
		    try {  
		    	//只有出现异常异常请求才设置response, 不然正常情况下也设置response的话,传递到controller的response不能正常返回数据(这里是返回的时候会收不到报文),原因待搞明白
		        if (StringUtils.isBlank(accessToken)) {
		        	response.setCharacterEncoding("UTF-8");  
				    response.setContentType("application/json; charset=utf-8");  
				    out = response.getWriter(); 
				    
					map.put("state",  CommonParam.STATE_TOKEN_NULL);
					map.put("msg", "请求异常");
					out.append(gson.toJson(map));
					return false;
				}
				AppUser	user = userService.selectUserByAccessToken(accessToken);
				if(user == null){
					
					response.setCharacterEncoding("UTF-8");  
				    response.setContentType("application/json; charset=utf-8");  
				    out = response.getWriter(); 
				    
					map.put("state",  CommonParam.STATE_TOKEN_DEPRECATED);
					map.put("msg", "登录已过期");
					out.append(gson.toJson(map));
					return false;
				}
		          
				request.setAttribute("user", user);
				
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    } finally {  
		        if (out != null) {  
		            out.close();  
		        }  
		    }
		return true;
	}
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
		/*if (modelAndView != null){
			// 如果是手机或平板访问的话，则跳转到手机视图页面。
			if(UserAgentUtils.isMobileOrTablet(request) && !StringUtils.startsWithIgnoreCase(modelAndView.getViewName(), "redirect:")){
				modelAndView.setViewName("mobile/" + modelAndView.getViewName());
			}
		}*/
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		
	}

}

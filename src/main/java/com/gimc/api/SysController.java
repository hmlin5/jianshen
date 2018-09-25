package com.gimc.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.modules.b_constant.CommonParam;
import com.gimc.user.modules.b_version.entity.Version;
import com.gimc.user.modules.b_version.service.VersionService;


@Controller
@RequestMapping("/api/version")
public class SysController {
	
	//Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private VersionService versionService;
	
//	@Autowired
//	private SysService sysService;
	
	@ResponseBody
	@RequestMapping("/getVersion")
	public Map<String, Object> getNewVersion(String deviceType, Model model) throws Exception{
		
		//String deviceType = request.getParameter("deviceType"); //0：安卓, 1:苹果
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		String msg = "";
		map.put("state", CommonParam.STATE_OK);
		map.put("msg", msg);
		map.put("data", null);		
		
		
		
		if(StringUtils.isNotBlank(deviceType)){
			Version condition = new Version();
			condition.setDeviceType(deviceType);
			List<Version> svs = versionService.findList(condition);
			if(svs!=null&&svs.size()>0){
				Version v =  svs.get(0);
				svs = new ArrayList<Version>();
				svs.add(v);
				
				map.put("data", svs);
			}else {
				map.put("state", CommonParam.STATE_ERROR);
				map.put("msg", "没有符合条件的版本");
			}
		}else{
			map.put("state", CommonParam.STATE_ERROR);
			map.put("msg", "参数为空");
		}
		
		//String jsonResult = JSON.toJSONString(map);
		//logger.info("最新版本jsonResult:"+jsonResult); 
		
		return map;
	}
	
}

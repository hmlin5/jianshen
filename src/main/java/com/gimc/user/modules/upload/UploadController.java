/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.gimc.user.modules.upload;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gimc.user.common.web.BaseController;


/**
 * 新闻表Controller
 * @author linhaomiao
 * @version 2018-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/file")
public class UploadController extends BaseController {

	
	@Value("${userfiles.basedir}")  
    public  String MULTIMEDIA_PATH; 
	
	@Value("${RESOURECE_URL}")  
	public  String RESOURECE_URL;
	
	
	
	
	@RequestMapping(value = {"/upload"})
	@ResponseBody
	public Map<String, Object> uploadFile(@RequestParam("file") CommonsMultipartFile uploadFile, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		try {
			
			String year = Calendar.getInstance().get(Calendar.YEAR)+"";
			String month = Calendar.getInstance().get(Calendar.MONTH)+1+"";
			String day = Calendar.getInstance().get(Calendar.DATE)+"";
				// 设定上传文件保存的目录 
				//String savePath = req.getSession().getServletContext().getRealPath("/userfiles/bid");  
			String savePath = MULTIMEDIA_PATH+year+"/"+month+"/"+day ; 
			String urlPath = RESOURECE_URL+year+"/"+month+"/"+day ; 
				//如果保存路径不存在则创建
				File f = new File(savePath);  
				if (!f.exists()) {
					f.mkdirs();  
				}
			
//				MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
//				MultipartFile uploadFile = request.getFile("upload");
				if(!uploadFile.isEmpty()){//如果上传文件不为空
				
					String fileName = uploadFile.getOriginalFilename();//上传文件的名称
					int size = (int) uploadFile.getSize();//上传文件的大小
					//上传文件后缀
					String shortAccessName = fileName.substring(fileName.lastIndexOf("."));
					
				//	String saveName = UUID.randomUUID().toString().replace("-", "")+"_"+fileName;//保存到服务器端的文件名称
					String saveName = UUID.randomUUID().toString().replace("-", "") + shortAccessName;//保存到服务器端的文件名称
					String realPath = savePath+"/"+saveName;//上传文件在服务器端的真实路径
					File saveFile = new File(realPath);
					
					uploadFile.transferTo(saveFile);//保存上传文件
			
					map.put("data", urlPath+"/"+saveName);
					
				}
			
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "服务器忙，请稍后再试！");
			map.put("data", null);
			e.printStackTrace();
		} 
		
		return map;
	}

	

}
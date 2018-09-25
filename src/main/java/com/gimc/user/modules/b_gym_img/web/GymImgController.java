/**
 * 
 */
package com.gimc.user.modules.b_gym_img.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gimc.user.common.config.Global;
import com.gimc.user.common.persistence.Page;
import com.gimc.user.common.web.BaseController;
import com.gimc.user.common.utils.StringUtils;
import com.gimc.user.modules.b_gym.entity.Gym;
import com.gimc.user.modules.b_gym.service.GymService;
import com.gimc.user.modules.b_gym_img.entity.GymImg;
import com.gimc.user.modules.b_gym_img.service.GymImgService;
import com.gimc.user.modules.sys.entity.User;
import com.gimc.user.modules.sys.utils.UserUtils;

/**
 * 健身房图片Controller
 * @author linhaomiao
 * @version 2018-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/b_gym_img/gymImg")
public class GymImgController extends BaseController {

	@Autowired
	private GymImgService gymImgService;
	@Autowired
	private GymService gymService;
	@ModelAttribute
	public GymImg get(@RequestParam(required=false) String id) {
		GymImg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gymImgService.get(id);
		}
		if (entity == null){
			entity = new GymImg();
		}
		return entity;
	}
	
	@RequiresPermissions("b_gym_img:gymImg:view")
	@RequestMapping(value = {"list", ""})
	public String list(GymImg gymImg, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		Gym gym = gymService.findByUserId(user.getId());
		gymImg.setGymId(gym.getId());
		Page<GymImg> page = gymImgService.findPage(new Page<GymImg>(request, response), gymImg); 
		model.addAttribute("page", page);
		return "modules/b_gym_img/gymImgList";
	}

	@RequiresPermissions("b_gym_img:gymImg:view")
	@RequestMapping(value = "form")
	public String form(GymImg gymImg, Model model) {
		model.addAttribute("gymImg", gymImg);
		return "modules/b_gym_img/gymImgForm";
	}

	@RequiresPermissions("b_gym_img:gymImg:edit")
	@RequestMapping(value = "save")
	public String save(GymImg gymImg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gymImg)){
			return form(gymImg, model);
		}
		User user = UserUtils.getUser();
		
		Gym gym = gymService.findByUserId(user.getId());
		gymImg.setGymId(gym.getId());
		gymImg.setUpdateBy(new User(user.getId()));
		gymImg.setCreateBy(new User(user.getId()));
		gymImgService.save(gymImg);
		addMessage(redirectAttributes, "保存健身房图片成功");
		return "redirect:"+Global.getAdminPath()+"/b_gym_img/gymImg/?repage";
	}
	
	@RequiresPermissions("b_gym_img:gymImg:edit")
	@RequestMapping(value = "delete")
	public String delete(GymImg gymImg, RedirectAttributes redirectAttributes) {
		gymImgService.delete(gymImg);
		addMessage(redirectAttributes, "删除健身房图片成功");
		return "redirect:"+Global.getAdminPath()+"/b_gym_img/gymImg/?repage";
	}

}
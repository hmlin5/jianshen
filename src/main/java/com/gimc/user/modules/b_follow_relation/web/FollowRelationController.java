/**
 * 
 */
package com.gimc.user.modules.b_follow_relation.web;

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
import com.gimc.user.modules.b_follow_relation.entity.FollowRelation;
import com.gimc.user.modules.b_follow_relation.service.FollowRelationService;

/**
 * 关注关系Controller
 * @author linhaomiao
 * @version 2018-06-04
 */
@Controller
@RequestMapping(value = "${adminPath}/b_follow_relation/followRelation")
public class FollowRelationController extends BaseController {

	@Autowired
	private FollowRelationService followRelationService;
	
	@ModelAttribute
	public FollowRelation get(@RequestParam(required=false) String id) {
		FollowRelation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = followRelationService.get(id);
		}
		if (entity == null){
			entity = new FollowRelation();
		}
		return entity;
	}
	
	@RequiresPermissions("b_follow_relation:followRelation:view")
	@RequestMapping(value = {"list", ""})
	public String list(FollowRelation followRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FollowRelation> page = followRelationService.findPage(new Page<FollowRelation>(request, response), followRelation); 
		model.addAttribute("page", page);
		return "modules/b_follow_relation/followRelationList";
	}

	@RequiresPermissions("b_follow_relation:followRelation:view")
	@RequestMapping(value = "form")
	public String form(FollowRelation followRelation, Model model) {
		model.addAttribute("followRelation", followRelation);
		return "modules/b_follow_relation/followRelationForm";
	}

	@RequiresPermissions("b_follow_relation:followRelation:edit")
	@RequestMapping(value = "save")
	public String save(FollowRelation followRelation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, followRelation)){
			return form(followRelation, model);
		}
		followRelationService.save(followRelation);
		addMessage(redirectAttributes, "保存关注关系成功");
		return "redirect:"+Global.getAdminPath()+"/b_follow_relation/followRelation/?repage";
	}
	
	@RequiresPermissions("b_follow_relation:followRelation:edit")
	@RequestMapping(value = "delete")
	public String delete(FollowRelation followRelation, RedirectAttributes redirectAttributes) {
		followRelationService.delete(followRelation);
		addMessage(redirectAttributes, "删除关注关系成功");
		return "redirect:"+Global.getAdminPath()+"/b_follow_relation/followRelation/?repage";
	}

}
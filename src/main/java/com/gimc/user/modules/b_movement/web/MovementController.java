/**
 * 
 */
package com.gimc.user.modules.b_movement.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gimc.user.modules.b_user_movement.entity.UserMovement;
import com.gimc.user.modules.b_user_movement.service.UserMovementService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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
import com.gimc.user.modules.b_movement.entity.Movement;
import com.gimc.user.modules.b_movement.service.MovementService;
import com.gimc.user.modules.b_movement_catalog.entity.MovementCatalog;
import com.gimc.user.modules.b_movement_catalog.service.MovementCatalogService;
import com.google.common.collect.Lists;

/**
 * 动作Controller
 * @author linhaomiao
 * @version 2018-05-03
 */
@Controller
@RequestMapping(value = "${adminPath}/b_movement/movement")
public class MovementController extends BaseController {

	@Autowired
	private MovementService movementService;
	@Autowired
	private UserMovementService userMovementService;
	
	@Autowired
	private MovementCatalogService movementCatalogService;
	
	@ModelAttribute
	public Movement get(@RequestParam(required=false) String id) {
		Movement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = movementService.get(id);
		}
		if (entity == null){
			entity = new Movement();
		}
		return entity;
	}
	
	@RequiresPermissions("b_movement:movement:view")
	@RequestMapping(value = {"list", ""})
	public String list(Movement movement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Movement> page = movementService.findPage(new Page<Movement>(request, response), movement); 
		model.addAttribute("page", page);
		return "modules/b_movement/movementList";
	}

	@RequiresPermissions("b_movement:movement:view")
	@RequestMapping(value = "form")
	public String form(Movement movement, Model model) {
		
		List<MovementCatalog> list = Lists.newArrayList();
		List<MovementCatalog> sourceList = movementCatalogService.findList(new MovementCatalog());
		MovementCatalog.sortList(list, sourceList, MovementCatalog.getRootId(), true);
		
		
		model.addAttribute("catalogList", list);
		
		
		model.addAttribute("movement", movement);
		return "modules/b_movement/movementForm";
	}

	@RequiresPermissions("b_movement:movement:edit")
	@RequestMapping(value = "save")
	public String save(Movement movement, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, movement)){
			return form(movement, model);
		}
		
		
		/*if (movement.getCatalogIds()) {
			
		}*/
		//动作修改后同步
		if(!movement.getIsNewRecord()){
            UserMovement userMovement=new UserMovement();
            BeanUtils.copyProperties(movement,userMovement);
            userMovement.setParent(movement.getId());
            userMovement.setType("1");
            userMovement.setGroupNum(movement.getZengjiGroupNum());
            userMovement.setMovementNum(movement.getZengjiMovementNum());
            userMovementService.updateUserMovement(userMovement);
            userMovement.setType("2");
            userMovement.setGroupNum(movement.getSuxingGroupNum());
            userMovement.setMovementNum(movement.getSuxingMovementNum());
            userMovementService.updateUserMovement(userMovement);
            userMovement.setType("3");
            userMovement.setGroupNum(movement.getJianzhiGroupNum());
            userMovement.setMovementNum(movement.getJianzhiMovementNum());
            userMovementService.updateUserMovement(userMovement);
		}
		movementService.save(movement);
		addMessage(redirectAttributes, "保存动作成功");
		return "redirect:"+Global.getAdminPath()+"/b_movement/movement/?repage";
	}
	
	@RequiresPermissions("b_movement:movement:edit")
	@RequestMapping(value = "delete")
	public String delete(Movement movement, RedirectAttributes redirectAttributes) {
		movementService.delete(movement);
		addMessage(redirectAttributes, "删除动作成功");
		return "redirect:"+Global.getAdminPath()+"/b_movement/movement/?repage";
	}

}
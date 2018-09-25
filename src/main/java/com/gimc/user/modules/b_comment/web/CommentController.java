/**
 * 
 */
package com.gimc.user.modules.b_comment.web;

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
import com.gimc.user.modules.b_comment.entity.Comment;
import com.gimc.user.modules.b_comment.service.CommentService;

/**
 * 评价Controller
 * @author linhaomiao
 * @version 2018-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/b_comment/comment")
public class CommentController extends BaseController {

	@Autowired
	private CommentService commentService;
	
	@ModelAttribute
	public Comment get(@RequestParam(required=false) String id) {
		Comment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = commentService.get(id);
		}
		if (entity == null){
			entity = new Comment();
		}
		return entity;
	}
	
	@RequiresPermissions("b_comment:comment:view")
	@RequestMapping(value = {"list", ""})
	public String list(Comment comment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Comment> page = commentService.findPage(new Page<Comment>(request, response), comment); 
		model.addAttribute("page", page);
		return "modules/b_comment/commentList";
	}

	@RequiresPermissions("b_comment:comment:view")
	@RequestMapping(value = "form")
	public String form(Comment comment, Model model) {
		model.addAttribute("comment", comment);
		return "modules/b_comment/commentForm";
	}

	@RequiresPermissions("b_comment:comment:edit")
	@RequestMapping(value = "save")
	public String save(Comment comment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, comment)){
			return form(comment, model);
		}
		commentService.save(comment);
		addMessage(redirectAttributes, "保存评价成功");
		return "redirect:"+Global.getAdminPath()+"/b_comment/comment/?repage";
	}
	
	@RequiresPermissions("b_comment:comment:edit")
	@RequestMapping(value = "delete")
	public String delete(Comment comment, RedirectAttributes redirectAttributes) {
		commentService.delete(comment);
		addMessage(redirectAttributes, "删除评价成功");
		return "redirect:"+Global.getAdminPath()+"/b_comment/comment/?repage";
	}

}
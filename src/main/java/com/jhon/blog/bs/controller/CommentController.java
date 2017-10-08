package com.jhon.blog.bs.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jhon.blog.bs.domain.BlogDO;
import com.jhon.blog.bs.domain.CommentDO;
import com.jhon.blog.bs.domain.UserDO;
import com.jhon.blog.bs.service.BlogService;
import com.jhon.blog.bs.service.CommentService;
import com.jhon.blog.bs.util.ConstraintViolationExceptionHandler;
import com.jhon.blog.bs.vo.Response;

/**
 * <p>功能描述</br> 评论控制器 </p>
 * @className  CommentController
 * @author  jiangy19
 * @date  2017年10月2日 下午7:51:39
 * @version  v1.0
 */
@Controller
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * <p> 功能描述：获取评论列表 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午7:52:44
	 * @param blogId
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping
	public String listComments(@RequestParam(value="blogId",required=true) Long blogId, Model model) {
		
		BlogDO blog = blogService.getBlogById(blogId);
		List<CommentDO> comments = blog.getComments();
		
		/* 判断操作用户是否是评论的所有者 **/
		String commentOwner = "";
		if (SecurityContextHolder.getContext().getAuthentication() !=null 
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser"))
		{
			UserDO principal = (UserDO)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			if (principal != null) {
				commentOwner = principal.getUsername();
			} 
		}
		
		model.addAttribute("commentOwner", commentOwner);
		model.addAttribute("comments", comments);
		return "/userspace/blog :: #mainContainerReplace";
	}
	
	/**
	 * <p> 功能描述：发表评论 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午7:55:27
	 * @param blogId
	 * @param commentContent
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Response> createComment(Long blogId, String commentContent) {
 
		try {
			blogService.createComment(blogId, commentContent);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	
	/**
	 * <p> 功能描述：删除评论 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午7:55:59
	 * @param id
	 * @param blogId
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Response> delete(@PathVariable("id") Long id, Long blogId) {
		
		boolean isOwner = false;
		UserDO user = commentService.getCommentById(id).getUser();
		
		/* 判断操作用户是否是评论的所有者 **/
		if (SecurityContextHolder.getContext().getAuthentication() !=null 
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) 
		{
			UserDO principal = (UserDO)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			if (principal !=null && user.getUsername().equals(principal.getUsername())) {
				isOwner = true;
			} 
		} 
		
		if (!isOwner) {
			return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
		}
		
		try {
			blogService.removeComment(blogId, id);
			commentService.removeComment(id);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
}

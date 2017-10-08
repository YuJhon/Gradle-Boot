package com.jhon.blog.bs.controller;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jhon.blog.bs.domain.UserDO;
import com.jhon.blog.bs.service.BlogService;
import com.jhon.blog.bs.service.StarService;
import com.jhon.blog.bs.util.ConstraintViolationExceptionHandler;
import com.jhon.blog.bs.vo.Response;

/**
 * <p>
 * 功能描述</br>
 * 点赞控制器
 * </p>
 * 
 * @className StarController
 * @author jiangy19
 * @date 2017年10月2日 下午7:44:40
 * @version v1.0
 */
@Controller
@RequestMapping("/stars")
public class StarController {

	@Autowired
	private StarService starService;

	@Autowired
	private BlogService blogService;

	/**
	 * <p>
	 * 功能描述：点赞
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 下午7:47:33
	 * @param blogId
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')") // 游客是不能点赞的
	public ResponseEntity<Response> createStar(Long blogId) {
		try {
			blogService.createStar(blogId);
		} catch (ConstraintViolationException e) {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		return ResponseEntity.ok().body(new Response(true, "点赞成功", null));
	}

	/**
	 * <p>
	 * 功能描述：删除点赞
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 下午7:48:06
	 * @param blogId
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')") // 游客是不能点赞的
	public ResponseEntity<Response> deleteStar(@PathVariable("id") Long id, Long blogId) {
		
		boolean isOwner = false;
		UserDO user = starService.getStarById(id).getUser();
		
		// 判断操作用户是否是点赞的所有者
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() 
				&& !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) 
		{
			UserDO principal = (UserDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal != null && user.getUsername().equals(principal.getUsername())) {
				isOwner = true;
			}
		}

		if (!isOwner) {
			return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
		}

		try {
			blogService.removeStar(blogId, id);
			starService.removeStar(id);
		} catch (ConstraintViolationException e) {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		return ResponseEntity.ok().body(new Response(true, "取消点赞成功", null));
	}
}

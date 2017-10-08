package com.jhon.blog.bs.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jhon.blog.bs.domain.CategoryDO;
import com.jhon.blog.bs.domain.UserDO;
import com.jhon.blog.bs.service.CategoryService;
import com.jhon.blog.bs.util.ConstraintViolationExceptionHandler;
import com.jhon.blog.bs.vo.CategoryVO;
import com.jhon.blog.bs.vo.Response;

/**
 * <p>
 * 功能描述</br>
 * 分类控制器
 * </p>
 * 
 * @className CategoryController
 * @author jiangy19
 * @date 2017年10月2日 下午7:27:07
 * @version v1.0
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * <p>
	 * 功能描述：获取分类列表
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 下午7:31:21
	 * @param username
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping
	public String listCategories(@RequestParam(value = "username", required = true) String username, Model model) {
		UserDO user = (UserDO) userDetailsService.loadUserByUsername(username);
		List<CategoryDO> categories = categoryService.listCategory(user);

		boolean isOwner = false;

		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !SecurityContextHolder
						.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			UserDO principal = (UserDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal != null && user.getUsername().equals(principal.getUsername())) {
				isOwner = true;
			}
		}

		model.addAttribute("isCategoryOwner", isOwner);
		model.addAttribute("categories", categories);
		return "/userspace/u :: #categoryReplace";
	}

	/**
	 * <p>
	 * 功能描述：创建分类
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 下午7:33:51
	 * @param categoryVO
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@PostMapping
	@PreAuthorize("authentication.name.equals(#categoryVO.username)")
	public ResponseEntity<Response> create(@RequestBody CategoryVO categoryVO) {
		String username = categoryVO.getUsername();
		CategoryDO category = categoryVO.getCategory();

		UserDO user = (UserDO) userDetailsService.loadUserByUsername(username);
		try {
			category.setUser(user);
			categoryService.saveCategory(category);

		} catch (ConstraintViolationException e) {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		return ResponseEntity.ok().body(new Response(true, "处理成功"));
	}

	/**
	 * <p>
	 * 功能描述：删除分类
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 下午7:40:29
	 * @param username
	 * @param id
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> delete(String username, @PathVariable("id") Long id) {
		try {
			categoryService.removeCategory(id);
		} catch (ConstraintViolationException e) {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}

	/**
	 * <p> 功能描述：跳转到编辑分类的页面 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午7:42:56
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/edit")
	public String getCatalogEdit(Model model) {
		CategoryDO category = new CategoryDO(null, null);
		model.addAttribute("category", category);
		return "/userspace/categoryedit";
	}

	/**
	 * <p> 功能描述：根据Id获取分类信息 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午7:43:02
	 * @param id
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/edit/{id}")
	public String getCatalogById(@PathVariable("id") Long id, Model model) {
		CategoryDO category = categoryService.getCategoryById(id);
		model.addAttribute("category", category);
		return "/userspace/categoryedit";
	}

}

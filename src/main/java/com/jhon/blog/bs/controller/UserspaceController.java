package com.jhon.blog.bs.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jhon.blog.bs.domain.BlogDO;
import com.jhon.blog.bs.domain.CategoryDO;
import com.jhon.blog.bs.domain.StarsDO;
import com.jhon.blog.bs.domain.UserDO;
import com.jhon.blog.bs.service.BlogService;
import com.jhon.blog.bs.service.CategoryService;
import com.jhon.blog.bs.service.UserService;
import com.jhon.blog.bs.util.ConstraintViolationExceptionHandler;
import com.jhon.blog.bs.vo.Response;

/**
 * <p>
 * 功能描述</br>
 * 用户主页控制器
 * </p>
 * 
 * @className UserspaceController
 * @author jiangy19
 * @date 2017年10月1日 上午10:35:14
 * @version v1.0
 */
@Controller
@RequestMapping("/u")
public class UserspaceController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private BlogService blogService;

	@Autowired
	private CategoryService categoryService;
	
	@Value("file.server.url")
	private String fileServerUrl;

	/**
	 * <p>
	 * 功能描述：查询当前用户的博客和个人信息
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 上午9:32:30
	 * @param username
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/{username}")
	public String userSpace(@PathVariable("username") String username, Model model) {
		UserDO user = (UserDO) userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return "redirect:/u/" + username + "/blogs";
	}

	/**
	 * <p>
	 * 功能描述：用户简档
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 上午9:36:33
	 * @param username
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/{username}/profile")
	@PreAuthorize("authentication.name.equals(#username)")
	public ModelAndView profile(@PathVariable("username") String username, Model model) {
		UserDO user = (UserDO) userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		model.addAttribute("fileServerUrl","");
		return new ModelAndView("/userspace/profile", "userModel", model);
	}

	/**
	 * <p>
	 * 功能描述：保存个人设置
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 上午9:38:55
	 * @param username
	 * @param user
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@PostMapping("/{username}/profile")
	@PreAuthorize("authentication.name.equals(#username)")
	public String saveProfile(@PathVariable("username") String username, UserDO user) {
		UserDO originalUser = userService.getUserById(user.getId());
		originalUser.setEmail(user.getEmail());
		originalUser.setName(user.getName());

		/** 判断密码是否有变更 **/
		String rawPassword = originalUser.getPassword();
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		boolean isMatch = encoder.matches(rawPassword, encodedPassword);
		if (!isMatch) {
			originalUser.setEncodePassword(user.getPassword());
		}
		userService.saveUser(originalUser);
		return "redirect:/u/" + username + "/profile";
	}

	/**
	 * <p>
	 * 功能描述：获取用户的头像
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 上午9:50:21
	 * @param username
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/{username}/avatar")
	@PreAuthorize("authentication.name.equals(#username)")
	public ModelAndView avatar(@PathVariable("username") String username, Model model) {
		UserDO user = (UserDO) userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return new ModelAndView("/userspace/avatar", "userModel", model);
	}

	/**
	 * <p>
	 * 功能描述：保存头像
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 上午9:55:27
	 * @param username
	 * @param user
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@PostMapping("/{username}/avatar")
	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> savaAvatar(@PathVariable("username") String username, @RequestBody UserDO user) {
		String avatarUrl = user.getAvatar();
		UserDO originalUser = userService.getUserById(user.getId());
		originalUser.setAvatar(avatarUrl);
		userService.saveUser(originalUser);
		return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
	}

	/**
	 * <p>
	 * 功能描述：获取当前用户的博客
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 下午4:32:34
	 * @param username
	 * @param order
	 * @param categoryId
	 * @param keyword
	 * @param async
	 * @param pageIndex
	 * @param pageSize
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/{username}/blogs")
	public String listBlogsByOrders(@PathVariable("username") String username,
			@RequestParam(value = "order", required = false, defaultValue = "new") String order,
			@RequestParam(value = "category", required = false, defaultValue = "") Long categoryId,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "async", required = false) boolean async,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, Model model) {

		UserDO user = (UserDO) userDetailsService.loadUserByUsername(username);

		Page<BlogDO> page = null;

		if (categoryId != null && categoryId > 0) {
			CategoryDO category = categoryService.getCategoryById(categoryId);
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			page = blogService.listBlogsByCategory(category, pageable);
		} else if (order.equals("hot")) {
			Sort sort = new Sort(Direction.DESC, "readSize", "commentSize", "starSize");
			Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
			page = blogService.listBlogsByTitleStarAndSort(user, keyword, pageable);
		} else if (order.equals("new")) {
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			page = blogService.listBlogsByTitleStar(user, keyword, pageable);
		}

		List<BlogDO> list = page.getContent();
		model.addAttribute("user", user);
		model.addAttribute("order", order);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("blogList", list);

		return (async == true ? "/userspace/u :: #mainContainerReplace" : "/userspace/u");
	}

	/**
	 * <p>
	 * 功能描述：获取当前用户对某一篇博客的情况
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 下午7:07:06
	 * @param username
	 * @param id
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/{username}/blogs/{id}")
	public String getBlogById(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
		UserDO principle = null;
		BlogDO blog = blogService.getBlogById(id);

		/** 每次读取，可以简单的认为阅读量增加1 **/
		blogService.readingIncrease(id);

		/** 判断当前用户是否是作者 **/
		boolean isBlogOwner = false;
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !SecurityContextHolder
						.getContext().getAuthentication().getPrincipal().toString().equals("anonymouseUser")) {
			principle = (UserDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principle != null && username.equals(principle.getUsername())) {
				isBlogOwner = true;
			}
		}

		/** 判断操作用户的点赞情况 **/
		List<StarsDO> stars = blog.getStars();
		StarsDO currentStar = null;
		if (principle != null) {
			for (StarsDO starsDO : stars) {
				if (starsDO.getUser().getUsername().equals(principle.getUsername())) {
					currentStar = starsDO;
					break;
				}
			}
		}

		model.addAttribute("isBlogOwner", isBlogOwner);
		model.addAttribute("blogModel", blog);
		model.addAttribute("currentStar", currentStar);

		return "/userspace/blog";
	}

	/**
	 * <p>
	 * 功能描述：删除博客操作
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 下午7:09:08
	 * @param username
	 * @param id
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@DeleteMapping("/{username}/blogs/{id}")
	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {
		try {
			blogService.removeBlog(id);
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		String redirectUrl = "/u/" + username + "/blogs";
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}

	/**
	 * <p>
	 * 功能描述：跳转到新建博客的界面
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 下午7:15:45
	 * @param username
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/{username}/blogs/edit")
	@PreAuthorize("authentication.name.equals(#username)")
	public ModelAndView createBlog(@PathVariable("username") String username, Model model) {
		UserDO user = (UserDO) userDetailsService.loadUserByUsername(username);
		List<CategoryDO> categoryList = categoryService.listCategory(user);
		model.addAttribute("blog", new BlogDO(null, null, null));
		model.addAttribute("categories", categoryList);
		model.addAttribute("user", user);
		return new ModelAndView("/userspace/blogedit", "blogModel", model);
	}

	/**
	 * <p>
	 * 功能描述：跳转到编辑博客的页面
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 下午7:18:26
	 * @param username
	 * @param id
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/{username}/blogs/edit/{id}")
	@PreAuthorize("authentication.name.equals(#username)")
	public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
		UserDO user = (UserDO) userDetailsService.loadUserByUsername(username);
		List<CategoryDO> categoryList = categoryService.listCategory(user);
		model.addAttribute("blog", blogService.getBlogById(id));
		model.addAttribute("categories", categoryList);
		return new ModelAndView("/userspace/blogedit", "blogModel", model);
	}

	/**
	 * <p>
	 * 功能描述：保存博客
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月2日 下午7:20:50
	 * @param username
	 * @param blog
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@PostMapping("/{username}/blogs/edit")
	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody BlogDO blog) {
		/** 对Category 进行空处理 **/
		if (blog.getCategory().getId() == null) {
			return ResponseEntity.ok().body(new Response(false, "未选择分类"));
		}
		try {
			if (blog.getId() != null) {
				BlogDO originalBlog = blogService.getBlogById(blog.getId());
				originalBlog.setTitle(blog.getTitle());
				originalBlog.setContent(blog.getContent());
				originalBlog.setSummary(blog.getSummary());
				originalBlog.setCategory(blog.getCategory());
				originalBlog.setTags(blog.getTags());
				blogService.saveBlog(originalBlog);
			} else {
				UserDO user = (UserDO) userDetailsService.loadUserByUsername(username);
				blog.setUser(user);
				blogService.saveBlog(blog);
			}
		} catch (ConstraintViolationException e) {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}
}

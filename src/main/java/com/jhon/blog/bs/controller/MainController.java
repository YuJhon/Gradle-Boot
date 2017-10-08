package com.jhon.blog.bs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jhon.blog.bs.domain.AuthorityDO;
import com.jhon.blog.bs.domain.UserDO;
import com.jhon.blog.bs.service.AuthorityService;
import com.jhon.blog.bs.service.UserService;

/**
 * <p>
 * 功能描述</br>
 * 主控制器
 * </p>
 * 
 * @className MainController
 * @author jiangy19
 * @date 2017年10月1日 上午10:23:29
 * @version v1.0
 */
@Controller
public class MainController {

	private static final Long ROLE_USER_AUTHORITY_ID = 2L;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityService authorityService;

	/**
	 * <p>
	 * 功能描述：跳转到首页
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月1日 下午9:58:43
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/")
	public String root() {
		return "redirect:/index";
	}

	/**
	 * <p>
	 * 功能描述：跳转到首页
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月1日 下午9:58:43
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/index")
	public String index() {
		return "redirect:/blogs";
	}

	/**
	 * <p>
	 * 功能描述：跳转到登录页面
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月1日 下午9:58:21
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	/**
	 * <p>
	 * 功能描述：登陆失败处理
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月1日 下午9:58:09
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "登陆失败，用户名或密码错误");
		return "login";
	}

	/**
	 * <p>
	 * 功能描述：跳转到注册页面
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月1日 下午9:56:50
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/register")
	public String register() {
		return "login";
	}

	/**
	 * <p>
	 * 功能描述：进行注册操作，成功之后跳转到登陆页面
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月1日 下午9:57:01
	 * @param user
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@PostMapping("/register")
	public String registerUser(UserDO user) {
		List<AuthorityDO> authorities = new ArrayList<>();
		authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
		user.setAuthorities(authorities);
		userService.saveUser(user);
		return "redirect:/login";
	}

	/**
	 * <p>
	 * 功能描述：跳转到搜索页面
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月1日 下午9:57:55
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/search")
	public String search() {
		return "search";
	}

}

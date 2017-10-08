package com.jhon.blog.bs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jhon.blog.bs.domain.AuthorityDO;
import com.jhon.blog.bs.domain.UserDO;
import com.jhon.blog.bs.service.AuthorityService;
import com.jhon.blog.bs.service.UserService;
import com.jhon.blog.bs.util.ConstraintViolationExceptionHandler;
import com.jhon.blog.bs.vo.Response;

/**
 * <p>功能描述</br> 用户控制器 </p>
 * @className  UserController
 * @author  jiangy19
 * @date  2017年10月2日 下午3:50:55
 * @version  v1.0
 */
@RestController
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService  authorityService;

	/**
	 * <p> 功能描述：分页查询用户信息</p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午3:58:23
	 * @param async
	 * @param pageIndex
	 * @param pageSize
	 * @param name
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping
	public ModelAndView list(@RequestParam(value="async",required =false) boolean async,
			@RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
			@RequestParam(value="name",required=false,defaultValue="") String name,
			Model model) {
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<UserDO> page = userService.listUsersByNameLike(name, pageable);
		List<UserDO> list = page.getContent();
		
		model.addAttribute("page", page);
		model.addAttribute("userList", list);
		return new ModelAndView(async==true?"users/list :: #mainContainerReplace":"users/list", "userModel", model);
	}
	
	/**
	 * <p> 功能描述：跳转到添加页面</p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午3:58:18
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping("/add")
	public ModelAndView createForm(Model model) {
		model.addAttribute("user", new UserDO(null, null, null, null));
		return new ModelAndView("users/add", "userModel", model);
	}
	
	/**
	 * <p> 功能描述：新建用户 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午4:00:00
	 * @param user
	 * @param authorityId
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@PostMapping
	public ResponseEntity<Response> create(UserDO user,Long authorityId){
		List<AuthorityDO> authorities = new ArrayList<>();
		authorities.add(authorityService.getAuthorityById(authorityId));
		user.setAuthorities(authorities);
		
		if(user.getId() == null){
			user.setEncodePassword(user.getPassword());
		}else{
			/** 判断密码是否有变更 **/
			UserDO originalUser = userService.getUserById(user.getId());
			String rawPassword = originalUser.getPassword();
			PasswordEncoder  encoder = new BCryptPasswordEncoder();
			String encodePasswd = encoder.encode(user.getPassword());
			boolean isMatch = encoder.matches(rawPassword, encodePasswd);
			if (!isMatch) {
				user.setEncodePassword(user.getPassword());
			}else {
				user.setPassword(user.getPassword());
			}
		}
		try {
			userService.saveUser(user);
		}  catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", user));
	}
	
	/**
	 * <p> 功能描述：删除用户  </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午4:06:25
	 * @param id
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Response> delete(@PathVariable("id") Long id){
		try {
			userService.removeUser(id);
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		return ResponseEntity.ok().body(new Response(true, "处理成功"));
	}
	
	/**
	 * <p> 功能描述：跳转编辑用户信息页面 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午4:08:18
	 * @param id
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping(value="edit/{id}")
	public ModelAndView modifyForm(@PathVariable("id") Long id,Model model){
		UserDO user = userService.getUserById(id);
		model.addAttribute("user", user);
		return new ModelAndView("users/edit","userModel",model);
	}
}

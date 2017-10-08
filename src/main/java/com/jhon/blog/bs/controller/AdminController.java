package com.jhon.blog.bs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jhon.blog.bs.vo.Menu;

/**
 * <p>
 * 功能描述</br>
 * 用户控制器 
 * </p>
 * 
 * @className AdminController
 * @author jiangy19
 * @date 2017年10月1日 下午1:27:37
 * @version v1.0
 */
@Controller
@RequestMapping("/admins")
public class AdminController {

	/**
	 * <p> 功能描述：获取后台用户管理主界面  </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:53:17
	 * @param model
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	@GetMapping
	public ModelAndView listUsers(Model model) {
		List<Menu> list = new ArrayList<>();
		list.add(new Menu("用户管理", "/users"));
		model.addAttribute("list", list);
		return new ModelAndView("/admins/index", "model", model);
	}
}

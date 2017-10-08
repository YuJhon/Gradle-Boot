package com.jhon.blog.bs.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>功能描述</br> 菜单实体 </p>
 * @className  Menu
 * @author  jiangy19
 * @date  2017年10月1日 下午8:32:52
 * @version  v1.0
 */
@Data
public class Menu implements Serializable{

	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
	private static final long serialVersionUID = 8531812205237622983L;
	
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 菜单链接 
	 */
	private String url;

	public Menu(String name, String url) {
		this.name = name;
		this.url = url;
	}
}

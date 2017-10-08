package com.jhon.blog.bs.vo;

import java.io.Serializable;

import com.jhon.blog.bs.domain.CategoryDO;

import lombok.Data;

/**
 * <p>功能描述</br> TODO</p>
 * @className  CategoryVO
 * @author  jiangy19
 * @date  2017年10月1日 下午8:36:42
 * @version  v1.0
 */
@Data
public class CategoryVO implements Serializable{
	
	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
	private static final long serialVersionUID = -5311956106951595161L;

	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 类目 
	 */
	private CategoryDO category;

	public CategoryVO(String username, CategoryDO category) {
		this.username = username;
		this.category = category;
	}
}

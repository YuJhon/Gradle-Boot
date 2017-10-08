package com.jhon.blog.bs.vo;

import java.io.Serializable;

import lombok.Data;
/**
 * <p>功能描述</br> 标签 </p>
 * @className  TagVO
 * @author  jiangy19
 * @date  2017年10月1日 下午8:34:55
 * @version  v1.0
 */
@Data
public class TagVO implements Serializable{

	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
	private static final long serialVersionUID = -283750313696545901L;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 点赞数量
	 */
	private Long count;
	
	
	public TagVO(String name, Long count) {
		this.name = name;
		this.count = count;
	}
}

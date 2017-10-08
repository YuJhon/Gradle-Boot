package com.jhon.blog.bs.vo;

import lombok.Data;

/**
 * <p>功能描述</br> 统一返回对象 </p>
 * @className  Response
 * @author  jiangy19
 * @date  2017年10月1日 下午1:56:02
 * @version  v1.0
 */
@Data
public class Response {
	
	private boolean success;
	
	private String message;
	
	private Object body;

	public Response(boolean success, String message, Object body) {
		this.success = success;
		this.message = message;
		this.body = body;
	}

	public Response(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	
}

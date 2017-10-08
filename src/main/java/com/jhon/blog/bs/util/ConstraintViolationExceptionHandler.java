package com.jhon.blog.bs.util;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 功能描述</br>
 * 校验异常的处理器
 * </p>
 * 
 * @className ConstraintViolationExceptionHandler
 * @author jiangy19
 * @date 2017年10月1日 下午1:59:07
 * @version v1.0
 */
public class ConstraintViolationExceptionHandler {

	/**
	 * <p>
	 * 功能描述：获取批量处理信息
	 * </p>
	 * 
	 * @author jiangy19
	 * @date 2017年10月1日 下午2:05:26
	 * @param e
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	public static String getMessage(ConstraintViolationException e) {
		List<String> msgList = new ArrayList<>();
		for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
			msgList.add(constraintViolation.getMessage());
		}
		String message = StringUtils.join(msgList.toArray(), ";");
		return message;
	}
}

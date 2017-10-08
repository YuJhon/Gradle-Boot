package com.jhon.blog.bs.service;

import com.jhon.blog.bs.domain.CommentDO;

/**
 * <p>
 * 功能描述</br>
 * 服务接口
 * </p>
 * 
 * @className CommentService
 * @author jiangy19
 * @date 2017年10月1日 下午8:43:06
 * @version v1.0
 */
public interface CommentService {
	
	/**
	 * <p> 功能描述：根据id获取查询评论 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:43:52
	 * @param id
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	CommentDO getCommentById(Long id);
	
	/**
	 * <p> 功能描述：删除评论 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:43:57
	 * @param id
	 * @version v1.0
	 * @since V1.0
	 */
	void removeComment(Long id);
}

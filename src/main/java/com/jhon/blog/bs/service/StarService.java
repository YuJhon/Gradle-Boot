package com.jhon.blog.bs.service;

import com.jhon.blog.bs.domain.StarsDO;

/**
 * <p>功能描述</br> 点赞服务接口 </p>
 * @className  StarService
 * @author  jiangy19
 * @date  2017年10月1日 下午9:06:20
 * @version  v1.0
 */
public interface StarService {

	/**
	 * <p> 功能描述：根据id获取赞  </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:07:15
	 * @param id
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	StarsDO getStarById(Long id);
	
	/**
	 * <p> 功能描述：删除的点赞 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:07:19
	 * @param id
	 * @version v1.0
	 * @since V1.0
	 */
	void removeStar(Long id);
}

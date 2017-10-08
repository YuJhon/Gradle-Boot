package com.jhon.blog.bs.service;

import com.jhon.blog.bs.domain.AuthorityDO;

/**
 * <p>功能描述</br> 权限服务接口 </p>
 * @className  AuthorityService
 * @author  jiangy19
 * @date  2017年10月1日 下午8:38:37
 * @version  v1.0
 */
public interface AuthorityService {
	
	/**
	 * <p> 功能描述：根据id获取Authority </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:39:08
	 * @param id 用户Id
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	AuthorityDO getAuthorityById(Long id);
}

package com.jhon.blog.bs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhon.blog.bs.domain.AuthorityDO;
import com.jhon.blog.bs.repository.AuthorityRepository;
import com.jhon.blog.bs.service.AuthorityService;

/**
 * <p>
 * 功能描述</br>
 * 权限业务服务接口实现类 
 * </p>
 * 
 * @className AuthorityServiceImpl
 * @author jiangy19
 * @date 2017年10月1日 下午8:41:05
 * @version v1.0
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public AuthorityDO getAuthorityById(Long id) {
		return authorityRepository.getOne(id);
	}

}

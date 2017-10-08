package com.jhon.blog.bs.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhon.blog.bs.domain.StarsDO;
import com.jhon.blog.bs.repository.StarsRepository;
import com.jhon.blog.bs.service.StarService;

/**
 * <p>功能描述</br> 点赞的业务逻辑实现类 </p>
 * @className  StarServiceImpl
 * @author  jiangy19
 * @date  2017年10月1日 下午9:09:33
 * @version  v1.0
 */
@Service
public class StarServiceImpl implements StarService{

	@Autowired
	private StarsRepository starsRepository;
	
	@Override
	public StarsDO getStarById(Long id) {
		return starsRepository.findOne(id);
	}

	@Override
	@Transactional
	public void removeStar(Long id) {
		starsRepository.delete(id);
	}
}

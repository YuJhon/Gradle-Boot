package com.jhon.blog.bs.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhon.blog.bs.domain.CategoryDO;
import com.jhon.blog.bs.domain.UserDO;
import com.jhon.blog.bs.repository.CategoryRepository;
import com.jhon.blog.bs.service.CategoryService;

/**
 * <p>功能描述</br>类目业务逻辑接口实现 </p>
 * 
 * @className CategoryServiceImpl
 * @author jiangy19
 * @date 2017年10月1日 下午8:59:00
 * @version v1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	@Transactional
	public CategoryDO saveCategory(CategoryDO category) {
		/** 判断重复 **/
		List<CategoryDO> list = categoryRepository.findByUserAndName(category.getUser(), category.getName());
		if(list != null && list.size()>0){
			throw new IllegalArgumentException("该分类已经存在了");
		}
		return categoryRepository.save(category);
	}

	@Override
	@Transactional
	public void removeCategory(Long id) {
		categoryRepository.delete(id);
	}

	@Override
	public CategoryDO getCategoryById(Long id) {
		return categoryRepository.getOne(id);
	}

	@Override
	public List<CategoryDO> listCategory(UserDO user) {
		return categoryRepository.findByUser(user);
	}
}

package com.jhon.blog.bs.service;

import java.util.List;

import com.jhon.blog.bs.domain.CategoryDO;
import com.jhon.blog.bs.domain.UserDO;

/**
 * <p>功能描述</br> 类目服务类 </p>
 * @className  CategroyService
 * @author  jiangy19
 * @date  2017年10月1日 下午8:52:00
 * @version  v1.0
 */
public interface CategoryService {
	
	/**
	 * <p> 功能描述：保存类目 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:53:20
	 * @param category
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	CategoryDO saveCategory(CategoryDO category);
	
	/**
	 * <p> 功能描述：删除类目 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:53:25
	 * @param id
	 * @version v1.0
	 * @since V1.0
	 */
	void removeCategory(Long id);
	
	/**
	 * <p> 功能描述：获取类目</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:55:09
	 * @param id
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	CategoryDO getCategoryById(Long id);
	
	/**
	 * <p> 功能描述：通过用户获取Category列表</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:55:14
	 * @param user
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	List<CategoryDO> listCategory(UserDO user);
}

package com.jhon.blog.bs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhon.blog.bs.domain.CategoryDO;
import com.jhon.blog.bs.domain.UserDO;

/**
 * <p>功能描述</br> 博客类目 </p>
 * @className  CategoryRepository
 * @author  jiangy19
 * @date  2017年10月1日 下午8:28:17
 * @version  v1.0
 */
public interface CategoryRepository extends JpaRepository<CategoryDO, Long> {
	
	/**
	 * <p> 功能描述：根据用户查询类目 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:29:27
	 * @param user 用户
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	List<CategoryDO> findByUser(UserDO user);
	
	/**
	 * <p> 功能描述：根据用户和类目的名称查询记录 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:29:31
	 * @param user 用户
	 * @param name 类目名称 
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	List<CategoryDO> findByUserAndName(UserDO user, String name);
}

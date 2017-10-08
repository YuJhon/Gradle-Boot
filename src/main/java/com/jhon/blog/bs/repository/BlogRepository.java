package com.jhon.blog.bs.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jhon.blog.bs.domain.BlogDO;
import com.jhon.blog.bs.domain.CategoryDO;
import com.jhon.blog.bs.domain.UserDO;

/**
 * <p>功能描述</br> 博客仓库 </p>
 * @className  BlogRepository
 * @author  jiangy19
 * @date  2017年10月1日 下午8:17:23
 * @version  v1.0
 */
public interface BlogRepository extends JpaRepository<BlogDO, Long>{
	
	/**
	 * <p> 功能描述：根据用户名分页查询博客 列表 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:18:53
	 * @param user 用户
	 * @param title 博客标题
	 * @param pageable 分页
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
//	@Deprecated
//	Page<BlogDO> findByUserAndTitleLikeAndOrderByCreateTimeDesc(UserDO user,String title,Pageable pageable);
	
	/**
	 * <p> 功能描述：根据用户名分页查询博客列表</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:20:24
	 * @param user 用户
	 * @param title 博客标题
	 * @param pageable 分页
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	Page<BlogDO> findByUserAndTitleLike(UserDO user,String title,Pageable pageable);
	
	/**
	 * <p> 功能描述：根据用户名分页查询博客列表 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:22:53
	 * @param title
	 * @param user
	 * @param tags
	 * @param user2
	 * @param pageable
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	Page<BlogDO> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title,UserDO user,String tags,UserDO user2,Pageable pageable);
	
	/**
	 * <p> 功能描述：根据用户名分页查询博客列表 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:24:01
	 * @param category
	 * @param pageable
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	Page<BlogDO> findByCategory(CategoryDO category,Pageable pageable);
}

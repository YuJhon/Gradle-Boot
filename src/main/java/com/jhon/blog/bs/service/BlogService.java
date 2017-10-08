package com.jhon.blog.bs.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jhon.blog.bs.domain.BlogDO;
import com.jhon.blog.bs.domain.CategoryDO;
import com.jhon.blog.bs.domain.UserDO;

/**
 * <p>功能描述</br> 博客业务逻辑接口定义 </p>
 * @className  BlogService
 * @author  jiangy19
 * @date  2017年10月1日 下午9:11:14
 * @version  v1.0
 */
public interface BlogService {
	
	/**
	 * <p> 功能描述：保存blog</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:14:37
	 * @param blog
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	BlogDO saveBlog(BlogDO blog);
	
	/**
	 * <p> 功能描述：删除Blog</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:14:52
	 * @param id
	 * @version v1.0
	 * @since V1.0
	 */
	void removeBlog(Long id);
	/**
	 * <p> 功能描述：根据id查询Blog</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:15:08
	 * @param id
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	BlogDO getBlogById(Long id);
	
	/**
	 * <p> 功能描述：根据用户名进行分页模糊查询</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:15:24
	 * @param user
	 * @param title
	 * @param pageable
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	Page<BlogDO> listBlogsByTitleStar(UserDO user,String title,Pageable pageable);
	
	/**
	 * <p> 功能描述：根据用户名进行分页模糊查询（最热）</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:17:11
	 * @param suser
	 * @param title
	 * @param pageable
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	Page<BlogDO> listBlogsByTitleStarAndSort(UserDO suser, String title, Pageable pageable);
	
	/**
	 * <p> 功能描述：根据博客类目查询</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:17:53
	 * @param category
	 * @param pageable
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	Page<BlogDO> listBlogsByCategory(CategoryDO category, Pageable pageable);
	
	/**
	 * <p> 功能描述：阅读量的递增 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:19:03
	 * @param id
	 * @version v1.0
	 * @since V1.0
	 */
	void readingIncrease(Long id);
	
	/**
	 * <p> 功能描述：发表评论 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:19:08
	 * @param blogId
	 * @param commentContent
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	BlogDO createComment(Long blogId,String commentContent);
	
	/**
	 * <p> 功能描述：删除评论 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:20:39
	 * @param blogId
	 * @param commentId
	 * @version v1.0
	 * @since V1.0
	 */
	void removeComment(Long blogId,Long commentId);
	
	/**
	 * <p> 功能描述：点赞</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:20:43
	 * @param blogId
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	BlogDO createStar(Long blogId);
	
	/**
	 * <p> 功能描述：删除点赞 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午9:20:48
	 * @param blogId
	 * @param starId
	 * @version v1.0
	 * @since V1.0
	 */
	void removeStar(Long blogId,Long starId);
}

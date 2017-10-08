package com.jhon.blog.bs.service;
/**
 * <p>功能描述</br> 搜索的业务逻辑接口定义  </p>
 * @className  EsBlogService
 * @author  jiangy19
 * @date  2017年10月2日 下午8:14:25
 * @version  v1.0
 */

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jhon.blog.bs.domain.UserDO;
import com.jhon.blog.bs.domain.es.EsBlogDO;
import com.jhon.blog.bs.vo.TagVO;

public interface EsBlogService {
	
	/**
	 * <p> 功能描述：删除</p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午8:14:56
	 * @param id
	 * @version v1.0
	 * @since V1.0
	 */
	void removeEsBlog(String id);
	
	/**
	 * <p> 功能描述：更新</p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午8:15:22
	 * @param esBlog
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	EsBlogDO updateEsBlog(EsBlogDO esBlog);
	
	/**
	 * <p> 功能描述：查询记录 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午8:15:54
	 * @param blogId
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	EsBlogDO getEsBlogByBlogId(Long blogId);
	
	/**
	 * <p> 功能描述：最新博客列表，分页 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午8:16:46
	 * @param keyword
	 * @param pageable
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	Page<EsBlogDO> listNewestEsBlogs(String keyword, Pageable pageable);
	
	/**
	 * <p> 功能描述：最热博客列表 ，分页</p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午8:17:39
	 * @param keyword
	 * @param pageable
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	Page<EsBlogDO> listHotestEsBlogs(String keyword, Pageable pageable);
	
	/**
	 * <p> 功能描述：博客列表，分页 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午8:18:26
	 * @param pageable
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	Page<EsBlogDO> listEsBlogs(Pageable pageable);
	
	/**
	 * <p> 功能描述：最新前五 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午8:19:15
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	List<EsBlogDO> listTop5NewestEsBlogs();
	
	/**
	 * <p> 功能描述：最热前五 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午8:19:49
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	List<EsBlogDO> listTop5HotestEsBlogs();
	
	/**
	 * <p> 功能描述：最热前30标签 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午8:27:24
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	List<TagVO> listTop30Tags();
	
	/**
	 * <p> 功能描述：最热前12用户 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午8:20:15
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	List<UserDO> listTop12Users();
}

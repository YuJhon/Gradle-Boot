package com.jhon.blog.bs.repository.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.jhon.blog.bs.domain.es.EsBlogDO;
/**
 * <p>功能描述</br> 搜索的 仓库 </p>
 * @className  EsBlogRepository
 * @author  jiangy19
 * @date  2017年10月2日 下午8:10:16
 * @version  v1.0
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlogDO, String>{
	/**
	 * <p> 功能描述：模糊查询 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 下午8:12:21
	 * @param title
	 * @param summary
	 * @param content
	 * @param tags
	 * @param pageable
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	Page<EsBlogDO> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title,String summary,String content,String tags,Pageable pageable);
	
	EsBlogDO findByBlogId(Long blogId);
}

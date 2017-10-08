package com.jhon.blog.bs.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.jhon.blog.bs.domain.UserDO;
import com.jhon.blog.bs.domain.es.EsBlogDO;
import com.jhon.blog.bs.repository.es.EsBlogRepository;
import com.jhon.blog.bs.service.EsBlogService;
import com.jhon.blog.bs.service.UserService;
import com.jhon.blog.bs.vo.TagVO;
/**
 * <p>功能描述</br> 搜索博客的类 </p>
 * @className  EsBlogServiceImpl
 * @author  jiangy19
 * @date  2017年10月2日 下午8:03:25
 * @version  v1.0
 */
@Service
public class EsBlogServiceImpl implements EsBlogService{
	
	private static final Pageable TOP_5_PAGEABLE = new PageRequest(0, 5);
	
	private static final String EMPTY_KEYWORD = "";
	
	@Autowired
	private EsBlogRepository esBlogRepository;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private UserService userService;
	

	@Override
	public void removeEsBlog(String id) {
		esBlogRepository.delete(id);
	}

	@Override
	public EsBlogDO updateEsBlog(EsBlogDO esBlog) {
		return esBlogRepository.save(esBlog);
	}

	@Override
	public EsBlogDO getEsBlogByBlogId(Long blogId) {
		return esBlogRepository.findByBlogId(blogId);
	}

	@Override
	public Page<EsBlogDO> listNewestEsBlogs(String keyword, Pageable pageable) {
		Page<EsBlogDO> pages = null;
		Sort sort = new Sort(Direction.DESC,"createTime");
		if(pageable.getSort() == null){
			pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),sort);
		}
		pages = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);
		return pages;
	}

	@Override
	public Page<EsBlogDO> listHotestEsBlogs(String keyword, Pageable pageable) {
		Page<EsBlogDO> pages = null;
		Sort sort = new Sort(Direction.DESC,"readSize","commentSize","starSize","createTime");
		if(pageable.getSort() == null){
			pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),sort);
		}
		pages = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);
		return pages;
	}

	@Override
	public Page<EsBlogDO> listEsBlogs(Pageable pageable) {
		return esBlogRepository.findAll(pageable);
	}

	@Override
	public List<EsBlogDO> listTop5NewestEsBlogs() {
		Page<EsBlogDO> page = this.listNewestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
		return page.getContent();
	}

	@Override
	public List<EsBlogDO> listTop5HotestEsBlogs() {
		Page<EsBlogDO> page = this.listHotestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
		return page.getContent();
	}

	@Override
	public List<TagVO> listTop30Tags() {
		
		List<TagVO> list = new ArrayList<>();
		/** given **/
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchAllQuery())
				.withSearchType(SearchType.QUERY_THEN_FETCH)
				.withIndices("blog").withTypes("blog")
				.addAggregation(terms("tags").field("tags").order(Terms.Order.count(false)).size(30))
				.build();
		// when
		Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
			@Override
			public Aggregations extract(SearchResponse response) {
				return response.getAggregations();
			}
		});
		
		StringTerms modelTerms =  (StringTerms)aggregations.asMap().get("tags"); 
	        
        Iterator<Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        while (modelBucketIt.hasNext()) {
            Bucket actiontypeBucket = modelBucketIt.next();
 
            list.add(new TagVO(actiontypeBucket.getKey().toString(),
                    actiontypeBucket.getDocCount()));
        }
		return list;
	}
	
	
	@Override
	public List<UserDO> listTop12Users() {
		
		List<String> usernamelist = new ArrayList<>();
		/** given **/
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchAllQuery())
				.withSearchType(SearchType.QUERY_THEN_FETCH)
				.withIndices("blog").withTypes("blog")
				.addAggregation(terms("users").field("username").order(Terms.Order.count(false)).size(12))
				.build();
		// when
		Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
			@Override
			public Aggregations extract(SearchResponse response) {
				return response.getAggregations();
			}
		});
		
		StringTerms modelTerms =  (StringTerms)aggregations.asMap().get("users"); 
	        
        Iterator<Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        while (modelBucketIt.hasNext()) {
            Bucket actiontypeBucket = modelBucketIt.next();
            String username = actiontypeBucket.getKey().toString();
            usernamelist.add(username);
        }
        List<UserDO> list = userService.listUsersByUsernames(usernamelist);
        
		return list;
	}
}

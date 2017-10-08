package com.jhon.blog.bs.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jhon.blog.bs.domain.BlogDO;
import com.jhon.blog.bs.domain.CategoryDO;
import com.jhon.blog.bs.domain.CommentDO;
import com.jhon.blog.bs.domain.StarsDO;
import com.jhon.blog.bs.domain.UserDO;
import com.jhon.blog.bs.domain.es.EsBlogDO;
import com.jhon.blog.bs.repository.BlogRepository;
import com.jhon.blog.bs.service.BlogService;
import com.jhon.blog.bs.service.EsBlogService;

/**
 * <p>功能描述</br> 博客业务逻辑接口实现类 </p>
 * @className  BlogServiceImpl
 * @author  jiangy19
 * @date  2017年10月1日 下午9:21:49
 * @version  v1.0
 */
@Service
public class BlogServiceImpl implements BlogService{
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private EsBlogService esBlogService;

	@Override
	@Transactional
	public BlogDO saveBlog(BlogDO blog) {
		boolean isNew = (blog.getId() == null);
		EsBlogDO esBlog = null;
		
		BlogDO returnBlog = blogRepository.save(blog);
		
		if (isNew) {
			esBlog = new EsBlogDO(returnBlog);
		} else {
			esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
			esBlog.update(returnBlog);
		}
		
		esBlogService.updateEsBlog(esBlog);
		return returnBlog;
	}

	@Override
	@Transactional
	public void removeBlog(Long id) {
		blogRepository.delete(id);
		EsBlogDO esblog = esBlogService.getEsBlogByBlogId(id);
		esBlogService.removeEsBlog(esblog.getId());
	}

	@Override
	public BlogDO getBlogById(Long id) {
		return blogRepository.findOne(id);
	}

	@Override
	public Page<BlogDO> listBlogsByTitleStar(UserDO user, String title, Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		String tags = title;
		return blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title, user, tags, user, pageable);
	}

	@Override
	public Page<BlogDO> listBlogsByTitleStarAndSort(UserDO suser, String title, Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		return blogRepository.findByUserAndTitleLike(suser, title, pageable);
	}

	@Override
	public Page<BlogDO> listBlogsByCategory(CategoryDO category, Pageable pageable) {
		return blogRepository.findByCategory(category, pageable);
	}

	@Override
	public void readingIncrease(Long id) {
		BlogDO blogDO = blogRepository.findOne(id);
		blogDO.setReadSize(blogDO.getCommentSize()+1);
		this.saveBlog(blogDO);
	}

	@Override
	public BlogDO createComment(Long blogId, String commentContent) {
		BlogDO originalBlog = blogRepository.findOne(blogId);
		UserDO user = (UserDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CommentDO comment = new CommentDO(user, commentContent);
		originalBlog.addComent(comment);
		return this.saveBlog(originalBlog);
	}

	@Override
	public void removeComment(Long blogId, Long commentId) {
		BlogDO originalBlog = blogRepository.findOne(blogId);
		originalBlog.removeComment(commentId);
		this.saveBlog(originalBlog);
	}

	@Override
	public BlogDO createStar(Long blogId) {
		BlogDO originalBlog = blogRepository.findOne(blogId);
		UserDO user = (UserDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		StarsDO star = new StarsDO(user);
		boolean isExist = originalBlog.addStars(star);
		if (isExist) {
			throw new IllegalArgumentException("该用户已经点过赞了");
		}
		return this.saveBlog(originalBlog);
	}

	@Override
	public void removeStar(Long blogId, Long starId) {
		BlogDO originalBlog = blogRepository.findOne(blogId);
		originalBlog.removeStar(starId);
		this.saveBlog(originalBlog);
	}

}

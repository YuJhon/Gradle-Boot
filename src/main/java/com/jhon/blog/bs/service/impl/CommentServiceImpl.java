package com.jhon.blog.bs.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhon.blog.bs.domain.CommentDO;
import com.jhon.blog.bs.repository.CommentRepository;
import com.jhon.blog.bs.service.CommentService;

/**
 * <p>
 * 功能描述</br>
 * TODO
 * </p>
 * 
 * @className CommentServiceImpl
 * @author jiangy19
 * @date 2017年10月1日 下午8:45:31
 * @version v1.0
 */
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public CommentDO getCommentById(Long id) {
		return commentRepository.findOne(id);
	}

	@Override
	@Transactional
	public void removeComment(Long id) {
		commentRepository.delete(id);
	}
}

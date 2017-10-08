package com.jhon.blog.bs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhon.blog.bs.domain.CommentDO;

/**
 * <p>功能描述</br> 评论仓库 </p>
 * @className  CommentRepository
 * @author  jiangy19
 * @date  2017年10月1日 下午8:27:07
 * @version  v1.0
 */
public interface CommentRepository extends JpaRepository<CommentDO, Long>{

}

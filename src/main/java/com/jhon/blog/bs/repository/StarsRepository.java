package com.jhon.blog.bs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhon.blog.bs.domain.StarsDO;

/**
 * <p>
 * 功能描述</br>
 * 点赞的仓库
 * </p>
 * 
 * @className StarsRepository
 * @author jiangy19
 * @date 2017年10月1日 下午8:25:22
 * @version v1.0
 */
public interface StarsRepository extends JpaRepository<StarsDO, Long> {

}

package com.jhon.blog.bs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhon.blog.bs.domain.AuthorityDO;

/**
 * <p>功能描述</br> 权限仓库 </p>
 * @className  AuthorityRepository
 * @author  jiangy19
 * @date  2017年10月1日 下午8:16:37
 * @version  v1.0
 */
public interface AuthorityRepository extends JpaRepository<AuthorityDO, Long>{

}

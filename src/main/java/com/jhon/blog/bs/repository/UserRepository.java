package com.jhon.blog.bs.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jhon.blog.bs.domain.UserDO;


/**
 * <p>功能描述</br>用户信息数据访问层</p>
 * 
 * @className UserRepository
 * @author jiangy19
 * @date 2017年9月25日 下午10:47:10
 * @version v1.0
 */
public interface UserRepository extends JpaRepository<UserDO, Long>{
	
	/**
	 * <p> 功能描述：根据用户姓名查询用户列表 分页查询</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午1:41:14
	 * @param name 姓名
	 * @param pageable 分页参数
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	Page<UserDO> findByNameLike(String name,Pageable pageable);
	
	/**
	 * <p> 功能描述：根据用户账号查询用户</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午1:41:55
	 * @param username 用户名
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	UserDO findByUsername(String username);
	
	/**
	 * <p> 功能描述：根据名称列表查询 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午8:14:08
	 * @param usernames 用户名列表
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	List<UserDO> findByUsernameIn(Collection<String> usernames);
}

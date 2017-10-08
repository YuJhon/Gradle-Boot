package com.jhon.blog.bs.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jhon.blog.bs.domain.UserDO;

/**
 * <p>功能描述</br> 用户业务逻辑接口 </p>
 * @className  UserService
 * @author  jiangy19
 * @date  2017年10月1日 下午1:43:21
 * @version  v1.0
 */
public interface UserService {
	
	/**
	 * <p> 功能描述：保存或更新用户信息 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午1:44:26
	 * @param user
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	UserDO saveUser(UserDO user);
	
	/**
	 * <p> 功能描述：用户注册 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午1:44:32
	 * @param user
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	UserDO registerUser(UserDO user);
	
	/**
	 * <p> 功能描述：根据Id删除用户 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午1:44:41
	 * @param id
	 * @version v1.0
	 * @since V1.0
	 */
	void removeUser(Long id);
	
	/**
	 * <p> 功能描述：批量删除用户 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 上午9:44:08
	 * @param users
	 * @version v1.0
	 * @since V1.0
	 */
	void removeUsersInBatch(List<UserDO> users);
	
	/**
	 * <p> 功能描述：根据Id获取用户 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午1:45:33
	 * @param id
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	UserDO getUserById(Long id);
	
	/**
	 * <p> 功能描述：获取用户列表 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 上午9:44:53
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	List<UserDO> listUsers();
	
	/**
	 * <p> 功能描述：根据姓名 模糊 分页查询用户信息</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午1:45:38
	 * @param name
	 * @param pageable
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	Page<UserDO> listUsersByNameLike(String name,Pageable pageable);
	
	/**
	 * <p> 功能描述：根据用户名称查询列表 </p>
	 * @author  jiangy19
	 * @date  2017年10月2日 上午9:45:47
	 * @param usernames
	 * @return
	 * @version v1.0
	 * @since V1.0
	 */
	List<UserDO> listUsersByUsernames(Collection<String> usernames);
}

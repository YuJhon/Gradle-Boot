package com.jhon.blog.bs.service.impl;


import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhon.blog.bs.domain.UserDO;
import com.jhon.blog.bs.repository.UserRepository;
import com.jhon.blog.bs.service.UserService;

/**
 * <p>
 * 功能描述</br>
 * 用户业务逻辑接口实现类 
 * </p>
 * 
 * @className UserServiceImpl
 * @author jiangy19
 * @date 2017年10月1日 下午1:47:49
 * @version v1.0
 */
@Service
public class UserServiceImpl implements UserService,UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	@Override
	public UserDO saveUser(UserDO user) {
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public UserDO registerUser(UserDO user) {
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public void removeUser(Long id) {
		userRepository.delete(id);
	}

	@Override
	public UserDO getUserById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public Page<UserDO> listUsersByNameLike(String name, Pageable pageable) {
		name = "%"+name+"%";
		return userRepository.findByNameLike(name, pageable);
	}

	@Transactional
	@Override
	public void removeUsersInBatch(List<UserDO> users) {
		userRepository.deleteInBatch(users);
	}

	@Override
	public List<UserDO> listUsers() {
		return userRepository.findAll();
	}

	@Override
	public List<UserDO> listUsersByUsernames(Collection<String> usernames) {
		return userRepository.findByUsernameIn(usernames);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}
}

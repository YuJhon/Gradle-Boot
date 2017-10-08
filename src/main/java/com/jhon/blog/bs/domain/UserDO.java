package com.jhon.blog.bs.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

/**
 * <p>
 * 功能描述</br>
 * 用户实体信息
 * </p>
 * 
 * @className UserDO
 * @author jiangy19
 * @date 2017年9月25日 下午10:23:12
 * @version v1.0
 */
@Data
@Entity
@Table(name = "t_user")
public class UserDO implements UserDetails, Serializable {

	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
	private static final long serialVersionUID = -8406798082191410789L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 姓名
	 */
	@NotEmpty(message = "姓名不能为空")
	@Size(min = 2, max = 20)
	@Column(nullable = false, length = 20)
	private String name;

	/**
	 * 邮箱
	 */
	@NotEmpty(message = "邮箱不能为空")
	@Size(max = 50)
	@Email(message = "邮箱格式不对")
	@Column(nullable = false, length = 50, unique = true)
	private String email;

	/**
	 * 账号
	 */
	@NotEmpty(message = "账号不能为空")
	@Size(min = 3, max = 20)
	@Column(nullable = false, length = 20, unique = true)
	private String username;

	/**
	 * 密码
	 */
	@NotEmpty(message = "密码不能为空")
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String password;

	/**
	 * 头像
	 */
	@Column(length = 128)
	private String avatar;

	/**
	 * 权限
	 */
	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinTable(name = "t_user_authorities", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<AuthorityDO> authorities;

	protected UserDO() {
	}

	public UserDO(String name, String email, String username, String password) {
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public void setEncodePassword(String password) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePasswd = encoder.encode(password);
		this.password = encodePasswd;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		/** 获取用户的权限 **/
		List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
		for (GrantedAuthority grantedAuthority : this.authorities) {
			simpleGrantedAuthorities.add(new SimpleGrantedAuthority(grantedAuthority.getAuthority()));
		}
		return simpleGrantedAuthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

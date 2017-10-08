package com.jhon.blog.bs.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

/**
 * <p>
 * 功能描述</br>
 * 权限
 * </p>
 * 
 * @className AuthorityDO
 * @author jiangy19
 * @date 2017年10月1日 下午6:54:54
 * @version v1.0
 */
@Entity
@Table(name = "t_authority")
@Data
public class AuthorityDO implements GrantedAuthority, Serializable {

	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
	private static final long serialVersionUID = -8397199572981766312L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Override
	public String getAuthority() {
		return this.name;
	}

}

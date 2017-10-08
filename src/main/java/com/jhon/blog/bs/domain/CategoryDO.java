package com.jhon.blog.bs.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

/**
 * <p>
 * 功能描述</br>
 * 文章类型
 * </p>
 * 
 * @className CategoryDO
 * @author jiangy19
 * @date 2017年10月1日 下午7:14:37
 * @version v1.0
 */
@Entity
@Table(name = "t_category")
@Data
public class CategoryDO implements Serializable {

	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
	private static final long serialVersionUID = -3168849211192111257L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 类目的名称
	 */
	@NotEmpty(message = "类目的名称不能为空")
	@Size(min = 2, max = 30)
	@Column(nullable = false)
	private String name;

	/**
	 * 创建的用户
	 */
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserDO user;

	protected CategoryDO() {
	}

	public CategoryDO(UserDO user, String name) {
		this.name = name;
		this.user = user;
	}
}

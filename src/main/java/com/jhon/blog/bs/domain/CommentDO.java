package com.jhon.blog.bs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * 评论
 * </p>
 * 
 * @className CommentDO
 * @author jiangy19
 * @date 2017年10月1日 下午7:15:31
 * @version v1.0
 */
@Entity
@Table(name = "t_comment")
@Data
public class CommentDO implements Serializable {
	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
	private static final long serialVersionUID = 6251195389921695750L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 评论内容
	 */
	@NotEmpty(message = "评论的内容不能为空")
	@Size(min = 2, max = 500)
	@Column(nullable = false)
	private String content;
	
	/**
	 * 评论的用户
	 */
	@OneToOne(cascade=CascadeType.DETACH,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserDO user;

	/**
	 * 创建的时间
	 */
	@Column(nullable=false,name="create_time")
	@org.hibernate.annotations.CreationTimestamp
	private Timestamp createTime;

	protected CommentDO() {
	}
	
	public CommentDO(UserDO user, String content) {
		this.content = content;
		this.user = user;
	}
}

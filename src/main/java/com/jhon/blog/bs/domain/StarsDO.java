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

import lombok.Data;

/**
 * <p>功能描述</br> 点赞实体 </p>
 * @className  VoteDO
 * @author  jiangy19
 * @date  2017年10月1日 下午7:16:34
 * @version  v1.0
 */
@Entity
@Table(name="t_stars")
@Data
public class StarsDO implements Serializable{
	
	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
	private static final long serialVersionUID = -1340533919407190179L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 点赞用户
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

	protected StarsDO() {
	}
	
	public StarsDO(UserDO user) {
		this.user = user;
	}

}

package com.jhon.blog.bs.domain.es;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import com.jhon.blog.bs.domain.BlogDO;

import lombok.Data;

/**
 * <p>
 * 功能描述</br>
 * ES Blog
 * </p>
 * 
 * @className EsBlogDO
 * @author jiangy19
 * @date 2017年10月2日 下午8:04:05
 * @version v1.0
 */
@Document(indexName = "blog", type = "blog")
@XmlRootElement // MediaType 转为 XML
@Data
public class EsBlogDO implements Serializable {

	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
	private static final long serialVersionUID = 256323989043997174L;

	@Id // 主键
	private String id;

	@Field(index = FieldIndex.not_analyzed)
	private Long blogId; // Blog 的 id

	private String title;

	private String summary;

	private String content;

	@Field(index = FieldIndex.not_analyzed) // 不做全文检索字段
	private String username;
	
	@Field(index = FieldIndex.not_analyzed) // 不做全文检索字段
	private String avatar;
	
	@Field(index = FieldIndex.not_analyzed) // 不做全文检索字段
	private Timestamp createTime;
	
	@Field(index = FieldIndex.not_analyzed) // 不做全文检索字段
	private Integer readSize = 0; // 访问量、阅读量
	
	@Field(index = FieldIndex.not_analyzed) // 不做全文检索字段
	private Integer commentSize = 0; // 评论量
	
	@Field(index = FieldIndex.not_analyzed) // 不做全文检索字段
	private Integer starSize = 0; // 点赞量

	private String tags; // 标签
	
	
	protected EsBlogDO() {  // JPA 的规范要求无参构造函数；设为 protected 防止直接使用 
	}

	public EsBlogDO(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public EsBlogDO(Long blogId, String title, String summary, String content, String username, String avatar,Timestamp createTime,
			Integer readSize,Integer commentSize, Integer starSize , String tags) {
		this.blogId = blogId;
		this.title = title;
		this.summary = summary;
		this.content = content;
		this.username = username;
		this.avatar = avatar;
		this.createTime = createTime;
		this.readSize = readSize;
		this.commentSize = commentSize;
		this.starSize = starSize;
		this.tags = tags;
	}
	
	public EsBlogDO(BlogDO blog){
		this.blogId = blog.getId();
		this.title = blog.getTitle();
		this.summary = blog.getSummary();
		this.content = blog.getContent();
		this.username = blog.getUser().getUsername();
		this.avatar = blog.getUser().getAvatar();
		this.createTime = blog.getCreateTime();
		this.readSize = blog.getReadSize();
		this.commentSize = blog.getCommentSize();
		this.starSize = blog.getStarSize();
		this.tags = blog.getTags();
	}
 
	public void update(BlogDO blog){
		this.blogId = blog.getId();
		this.title = blog.getTitle();
		this.summary = blog.getSummary();
		this.content = blog.getContent();
		this.username = blog.getUser().getUsername();
		this.avatar = blog.getUser().getAvatar();
		this.createTime = blog.getCreateTime();
		this.readSize = blog.getReadSize();
		this.commentSize = blog.getCommentSize();
		this.starSize = blog.getStarSize();
		this.tags = blog.getTags();
	}
 
}

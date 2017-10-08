package com.jhon.blog.bs.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.elasticsearch.annotations.Document;

import com.youbenzi.mdtool.tool.MDTool;

/**
 * <p>
 * 功能描述</br>
 * 博客实体
 * </p>
 * 
 * @className BlogDO
 * @author jiangy19
 * @date 2017年10月1日 下午7:00:18
 * @version v1.0
 */
@Entity
@Table(name = "t_blog")
@Document(indexName = "blog", type = "blog")
public class BlogDO implements Serializable {

	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
	private static final long serialVersionUID = -1144054287571656912L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 标题
	 */
	@NotEmpty(message = "标题不能为空")
	@Size(min = 2, max = 50)
	@Column(nullable = false, length = 50)
	private String title;

	/**
	 * 摘要
	 */
	@NotEmpty(message = "摘要不能为空")
	@Size(min = 2, max = 300)
	@Column(nullable = false)
	private String summary;

	/**
	 * 文章内容 ，大对象，映射 MySQL 的 Long Text 类型
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@NotEmpty(message = "内容不能为空")
	@Size(min = 2)
	@Column(nullable = false)
	private String content;

	/**
	 * 将md装换为html, 大对象
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@NotEmpty(message = "内容不能为空")
	@Size(min = 2)
	@Column(nullable = false)
	private String htmlContent;

	/**
	 * 作者
	 */
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserDO user;

	/**
	 * 博客评论
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "t_blog_comment", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
	private List<CommentDO> comments;

	/**
	 * 博客点赞
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "t_blog_stars", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "stars_id", referencedColumnName = "id"))
	private List<StarsDO> stars;

	/**
	 * 博客类目
	 */
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private CategoryDO category;

	/**
	 * 访问量
	 */
	@Column(name = "read_size")
	private Integer readSize = 0;

	/**
	 * 评论量
	 */
	@Column(name = "comment_size")
	private Integer commentSize = 0;

	/**
	 * 点赞量
	 */
	@Column(name = "star_size")
	private Integer starSize = 0;

	/**
	 * 标签
	 */
	@Column(name = "tags", length = 100)
	private String tags;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = false)
	@org.hibernate.annotations.CreationTimestamp
	private Timestamp createTime;

	protected BlogDO() {
	}

	public BlogDO(String title, String summary, String content) {
		super();
		this.title = title;
		this.summary = summary;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		/** 将内容转换为html **/
		//this.htmlContent = Processor.process(content);
		this.htmlContent = MDTool.markdown2Html(content);
	}

	public UserDO getUser() {
		return user;
	}

	public void setUser(UserDO user) {
		this.user = user;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public Integer getReadSize() {
		return readSize;
	}

	public void setReadSize(Integer readSize) {
		this.readSize = readSize;
	}

	public Integer getCommentSize() {
		return commentSize;
	}

	public void setCommentSize(Integer commentSize) {
		this.commentSize = commentSize;
	}

	public Integer getStarSize() {
		return starSize;
	}

	public void setStarSize(Integer starSize) {
		this.starSize = starSize;
	}

	public List<CommentDO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDO> comments) {
		this.comments = comments;
		this.commentSize = this.comments.size();
	}
	/**
	 * <p> 功能描述：添加评论 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午7:30:31
	 * @param comment
	 * @version v1.0
	 * @since V1.0
	 */
	public void addComent(CommentDO comment){
		this.comments.add(comment);
		this.commentSize = this.comments.size();
	}
	/**
	 * <p> 功能描述：删除评论 【TODO】</p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午7:44:17
	 * @param commentId
	 * @version v1.0
	 * @since V1.0
	 */
	public void removeComment(Long commentId){
		for(int index=0;index <this.comments.size();index++){
			if(this.comments.get(index).getId().equals(commentId)){
				this.comments.remove(index);
				break;
			}
		}
		this.commentSize = this.comments.size();
	}
	
	/**
	 * <p> 功能描述：点赞 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午7:49:48
	 * @param star
	 * @version v1.0
	 * @since V1.0
	 */
	public boolean addStars(StarsDO star){
		boolean isExist = false;
		/** 判断是否重复 **/
		for(int index=0;index<this.stars.size();index++){
			if(this.stars.get(index).getId().equals(star.getUser().getId())){
				isExist = true;
				break;
			}
		}
		if (!isExist) {
			this.stars.add(star);
			this.starSize = this.stars.size();
		}
		return isExist;
	}
	
	/**
	 * <p> 功能描述：取消点赞 </p>
	 * @author  jiangy19
	 * @date  2017年10月1日 下午7:51:33
	 * @param starId
	 * @version v1.0
	 * @since V1.0
	 */
	public void removeStar(Long starId){
		for(int index=0;index <this.stars.size();index++){
			if(this.stars.get(index).getId().equals(starId)){
				this.stars.remove(index);
				break;
			}
		}
		this.starSize = this.stars.size();
	}
	
	public List<StarsDO> getStars(){
		return this.stars;
	}
	
	public void setStars(List<StarsDO> stars){
		this.stars = stars;
	}

	public CategoryDO getCategory() {
		return category;
	}

	public void setCategory(CategoryDO category) {
		this.category = category;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}

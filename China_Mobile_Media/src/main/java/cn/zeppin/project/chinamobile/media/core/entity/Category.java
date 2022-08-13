package cn.zeppin.project.chinamobile.media.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity;

/**
 * Category entity. 
 * 
 * @author Clark.R 2016.03.29
 */

@Entity
@Table(name = "category", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 377829351023598212L;

	// Fields
	
	private String id;
	private String name;
	private Integer level;
	private String parent;
	private String status;
	private String scode;
	private String creator;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public Category() {
		
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator") 
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "name", unique = true, nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "level", nullable = false)
	public Integer getLevel() {
		return this.level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
	/**
	 * 尝试不用hibernate的外键对象原因
	 * 1、外键对象全部读取效率低，高效率的lazy load配置复杂
	 * 2、利于自定义缓存层的实现（全部用dao调用实现关联对象读取）
	 * 3、对象json序列化涉及到对象循环引用引起的死循环的问题
	 * 4、control层返回对象基本不用多级对象直接生成的json格式，json返回值格式以扁平化结构为佳
	 * 5、复杂查询（尤其是含有统计数据的返回值列表）一般都是自定义的返回值格式
	 * 
	 * @author Clark.R 2016.03.29
	 */
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "parent")
//	public Category getParent() {
//		return this.parent;
//	}
	
	@Column(name = "parent", length = 36)
	public String getParent() {
		return this.parent;
	}
	
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "scode", nullable = false, length = 100)
	public String getScode() {
		return this.scode;
	}
	
	public void setScode(String scode) {
		this.scode = scode;
	}
	

	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
}

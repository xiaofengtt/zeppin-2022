/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @author Clark.R 2016年9月19日
 *
 * 尝试不用Hibernate的外键对象原因（所有外键字段全部以基本型数据而不是对象体现）
 * 1、外键对象全部读取效率低，高效率的lazy load配置复杂
 * 2、利于构建基于Uuid的缓存层的实现（全部用dao调用实现关联对象读取）
 * 3、对象Json序列化涉及到对象循环引用引起的死循环的问题
 * 4、Control层返回对象基本不用多级对象直接生成的Json格式，Json返回值格式以扁平化结构为佳
 * 5、复杂查询（尤其是含有统计数据的返回值列表）一般都是自定义的返回值格式
 * 
 * 
 * @description 【数据对象】后台管理员
 */

@Entity
@Table(name = "bk_operator", uniqueConstraints = { @UniqueConstraint(columnNames = "name"),  @UniqueConstraint(columnNames = "mobile"), @UniqueConstraint(columnNames = "email")})
public class BkOperator extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private String name;
	private String realname;
	private String password;
	private String role;
	private String mobile;
	private String email;
	private Timestamp createtime;
	private String creator;
	private String status;
	private Timestamp lockedtime;
	
	public class BkOperatorStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String LOCKED = "locked";
		public final static String DELETED = "deleted";
		public final static String UNOPEN = "unopen";
	}
	
	@Id
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator") 
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "name", unique = true, nullable = false, length = 50)
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "realname", nullable = false, length = 50)
	public String getRealname() {
		return realname;
	}


	public void setRealname(String realname) {
		this.realname = realname;
	}

	
	@Column(name = "password", nullable = false,  length = 256)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "role", nullable = false, length = 36)
	public String getRole() {
		return role;
	}
	
	
	public void setRole(String role) {
		this.role = role;
	}
	
	@Column(name = "mobile", unique = true, nullable = false,  length = 20)
	public String getMobile() {
		return mobile;
	}
	
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "email", length = 50)
	public String getEmail() {
		return email;
	}
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}
	
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}
	
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "lockedtime")
	public Timestamp getLockedtime() {
		return lockedtime;
	}


	public void setLockedtime(Timestamp lockedtime) {
		this.lockedtime = lockedtime;
	}


}

/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @author Clark.R 2016年12月27日
 *
 *         尝试不用Hibernate的外键对象原因（所有外键字段全部以基本型数据而不是对象体现） 1、外键对象全部读取效率低，高效率的lazy
 *         load配置复杂 2、利于构建基于Uuid的缓存层的实现（全部用dao调用实现关联对象读取）
 *         3、对象Json序列化涉及到对象循环引用引起的死循环的问题
 *         4、Control层返回对象基本不用多级对象直接生成的Json格式，Json返回值格式以扁平化结构为佳
 *         5、复杂查询（尤其是含有统计数据的返回值列表）一般都是自定义的返回值格式
 * 
 * 
 * @description 【数据对象】银行信息
 */

@Entity
@Table(name = "advert")
public class Advert extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698148359983723617L;

	private String uuid;
	private String title;
	private String picture;
	private String creator;
	private Timestamp createtime;
	private String status;

	public class AdvertStatus {
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
	}

	public Advert() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "uuid", unique = true, nullable = false)
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "title", unique = true, nullable = false, length = 200)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "picture", nullable = false, length = 36)
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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

	@Column(name = "status", nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

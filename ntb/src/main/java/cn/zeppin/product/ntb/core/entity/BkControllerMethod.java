/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @author Clark.R 2016年10月19日
 *
 * 尝试不用Hibernate的外键对象原因（所有外键字段全部以基本型数据而不是对象体现）
 * 1、外键对象全部读取效率低，高效率的lazy load配置复杂
 * 2、利于构建基于Uuid的缓存层的实现（全部用dao调用实现关联对象读取）
 * 3、对象Json序列化涉及到对象循环引用引起的死循环的问题
 * 4、Control层返回对象基本不用多级对象直接生成的Json格式，Json返回值格式以扁平化结构为佳
 * 5、复杂查询（尤其是含有统计数据的返回值列表）一般都是自定义的返回值格式
 * 
 * 
 * @description 【数据对象】后台Controller Method入库
 */

@Entity
@Table(name = "bk_controller_method", uniqueConstraints = {@UniqueConstraint(columnNames = {"controller","name"})})
public class BkControllerMethod extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5409414004847269943L;

	private String uuid;
	private String controller;
	private String name;
	private String description;
	
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "controller", unique = true, nullable = false, length = 36)
	public String getController() {
		return controller;
	}
	
	public void setController(String controller) {
		this.controller = controller;
	}
	
	@Column(name = "name", unique = true, nullable = false, length = 50)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "description", length = 200)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}



}

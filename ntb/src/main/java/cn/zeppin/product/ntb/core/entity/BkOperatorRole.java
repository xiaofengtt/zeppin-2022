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
 * @description 【数据对象】后台管理员角色
 */

@Entity
@Table(name = "bk_operator_role", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class BkOperatorRole extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5214064935006342791L;
	
	
	private String uuid;
	private String name;
	private String description;
	private Short weight;
	
	public class BkOperatorRoleUuid{
		public final static String SUPER_ADMIN = "4ebd77bc-3025-4657-bca7-cf684647c666";
		public final static String OPERATE_MANAGER = "0127fcbe-f57f-11e6-ac06-cacda7da5000";
		public final static String OPERATE_EDITOR = "49f5c6f5-f8d2-45c2-94bb-4036be1c4bb6";
		public final static String FINANCE_MANAGER = "0e15ae93-f57f-11e6-ac06-cacda7da5000";
		public final static String FINANCE_EDITOR = "0922a25d-f57f-11e6-ac06-cacda7da5000";
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
	
	@Column(name = "name", unique = true, nullable = false, length = 20)
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	

	@Column(name = "description", nullable = false, length = 20)
	public String getDescription() {
		return description;
	}
	
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "weight", nullable = false)
	public Short getWeight() {
		return weight;
	}
	
	
	public void setWeight(Short weight) {
		this.weight = weight;
	}
}

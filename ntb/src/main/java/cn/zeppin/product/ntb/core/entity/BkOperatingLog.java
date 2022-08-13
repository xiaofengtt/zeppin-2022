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
 * @description 【数据对象】后台操作日志
 */

@Entity
@Table(name = "bk_operator_log")
public class BkOperatingLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5384738141807388188L;

	private String uuid;
	private String operateType;
	private String operateTable;
	private String operateUuid;
	private Timestamp operateTime;
	private String operator;
	private String content;
	
	public class BkOperateType {
		public final static String LOGIN = "login";
		public final static String INSERT = "insert";
		public final static String UPDATE = "update";
		public final static String DELETED = "deleted";
		public final static String LOGOUT = "logout";
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

	@Column(name = "operate_type", nullable = false, length = 10)
	public String getOperateType() {
		return operateType;
	}


	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	@Column(name = "operate_table", length = 20)
	public String getOperateTable() {
		return operateTable;
	}


	public void setOperateTable(String operateTable) {
		this.operateTable = operateTable;
	}

	@Column(name = "operate_uuid", length = 36)
	public String getOperateUuid() {
		return operateUuid;
	}


	public void setOperateUuid(String operateUuid) {
		this.operateUuid = operateUuid;
	}

	@Column(name = "operate_time", nullable = false)
	public Timestamp getOperateTime() {
		return operateTime;
	}

	
	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "operator", nullable = false, length = 36)
	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "content", length = 1000)
	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	
}

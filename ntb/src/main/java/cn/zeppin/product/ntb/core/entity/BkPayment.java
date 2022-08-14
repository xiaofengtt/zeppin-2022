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
 * @description 【数据对象】支付方式
 */

@Entity
@Table(name = "bk_payment", uniqueConstraints = {@UniqueConstraint(columnNames = "payment")})
public class BkPayment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7141633696155630930L;

	private String uuid;
	private String payment;
	private String paymentDes;
	private Boolean flagSwitch;
	private Timestamp createtime;
	private String creator;
	private String status;
	
	public class BkPaymentStatus{
		public final static String NORMAL = "normal";
		public final static String DELETED = "deleted";
	}
	
	public class BkPaymentPayment{
		public static final String PAYMENT_WECHART = "wechart";
		public static final String PAYMENT_ALIPAY = "alipay";
		public static final String PAYMENT_CHANPAY = "chanpay";
		public static final String PAYMENT_FUQIANLA = "fuqianla";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	@Column(name = "payment", length = 20, nullable = false)
	public String getPayment() {
		return payment;
	}
	
	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	@Column(name = "flag_switch", nullable = false)
	public Boolean getFlagSwitch() {
		return flagSwitch;
	}
	
	public void setFlagSwitch(Boolean flagSwitch) {
		this.flagSwitch = flagSwitch;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "creator", length = 36, nullable = false)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "status", length = 10, nullable = false)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "payment_des", length = 50, nullable = false)
	public String getPaymentDes() {
		return paymentDes;
	}

	public void setPaymentDes(String paymentDes) {
		this.paymentDes = paymentDes;
	}
	
	
}

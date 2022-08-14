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
 * @description 【数据对象】融宝支付异步通知反馈消息
 */

@Entity
@Table(name = "realpal_notice_info")
public class RealpalNoticeInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7653870957041358522L;

	private String uuid;
	
	private Timestamp createtime;
	private String status;
	
	private String source;
	private String orderNum;
	private String batchNo;
	
	private String payType;
	
	public class RealpalNoticeInfoStatus{
		public final static String UNPRO = "unpro";
		public final static String SUCCESS = "success";
		public final static String FAIL = "fail";
	}
	
	public class RealpalNoticeInfoPayType{
		public final static String SHBX_TAKEOUT = "shbx_takeout";//社保熊用户提现
		public final static String SHBX_FILLIN = "shbx_fillin";//社保熊用户充值
		public final static String SHBX_PAY_SHEBAO = "shbx_pay_shebao";//社保熊用户缴费
		
		public final static String QCB_TAKEOUT = "qcb_takeout";//企财宝企业提现
		public final static String QCB_RECHARGE = "qcb_recharge";//企财宝企业提现
		public final static String EMP_TAKEOUT = "emp_takeout";//企财宝员工提现
		public final static String EMP_FILLIN = "emp_fillin";//企财宝用户充值
		public final static String TAKEOUT = "takeout";//用户提现
		public final static String FILLIN = "fillin";//用户充值
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "status", length = 20, nullable = false)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "source", length = 1000, nullable = false)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@Column(name = "order_num", length = 32)
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	@Column(name = "batch_no", length = 50)
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	@Column(name = "pay_type", length = 20, nullable = false)
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
}

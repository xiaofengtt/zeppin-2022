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
 * @description 【数据对象】应用内消息
 */

@Entity
@Table(name = "investor_information")
public class InvestorInformation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7103067181358004425L;
	private String uuid;
	private String information;
	private String infoTitle;
	private String infoText;
	private String investor;
	
	private Timestamp createtime;
	private String creator;
	private String status;
	
	public class InvestorInformationStatus{
		public final static String UNREAD = "unread";//未读
		public final static String NORMAL = "normal";//已读
		public final static String DELETED = "deleted";//已删除
	}
	
	public class InvestorInformationTitle{
		public final static String RECHARGE = "充值成功";//充值
		public final static String WITHDRAW = "提现成功";//提现
		public final static String BUY = "买入成功";//买入
		public final static String REGISTER = "注册成功";//注册
		public final static String COUPONADD = "优惠券发放";//优惠券发放
		public final static String COUPONEXP = "优惠券到期提醒";//优惠券到期提醒
		public final static String REDEEM = "赎回成功";//赎回
	}
	
	public class InvestorInformationModel{
		public static final String INFO_MODEL_REGISTER = "尊敬的用户您好：您已注册成功，牛投理财由衷欢迎您的到来。我们为您准备了1000元理财现金券和新手专享理财产品，您可即刻前往体验。";

	}
	
//	public class BkPaymentPayment{
//		public static final String PAYMENT_WECHART = "wechart";
//		public static final String PAYMENT_ALIPAY = "alipay";
//		public static final String PAYMENT_CHANPAY = "chanpay";
//		public static final String PAYMENT_FUQIANLA = "fuqianla";
//	}
	
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
	
	@Column(name = "info_title", nullable = false)
	public String getInfoTitle() {
		return infoTitle;
	}
	
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}
	
	@Column(name = "info_text", nullable = false)
	public String getInfoText() {
		return infoText;
	}

	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}

	@Column(name = "information")
	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}
	
	@Column(name = "investor", nullable = false)
	public String getInvestor() {
		return investor;
	}
	
	public void setInvestor(String investor) {
		this.investor = investor;
	}

}

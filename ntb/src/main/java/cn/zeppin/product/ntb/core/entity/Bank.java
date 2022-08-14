/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @author Clark.R 2016年12月27日
 *
 * 尝试不用Hibernate的外键对象原因（所有外键字段全部以基本型数据而不是对象体现）
 * 1、外键对象全部读取效率低，高效率的lazy load配置复杂
 * 2、利于构建基于Uuid的缓存层的实现（全部用dao调用实现关联对象读取）
 * 3、对象Json序列化涉及到对象循环引用引起的死循环的问题
 * 4、Control层返回对象基本不用多级对象直接生成的Json格式，Json返回值格式以扁平化结构为佳
 * 5、复杂查询（尤其是含有统计数据的返回值列表）一般都是自定义的返回值格式
 * 
 * 
 * @description 【数据对象】银行信息
 */

@Entity
@Table(name = "bank", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Bank extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698148359983723617L;
	
	private String uuid;
	private String name;
	private String logo;
	
	private String status;
	private Timestamp createtime;
	private String creator;
	
	private BigDecimal singleLimit;
	private BigDecimal dailyLimit;
	
	private String creditInquiryPhone;
	private String creditInquiryCommand;
	
	private String color;
	private String icon;
	
	private String shortName;
	private Boolean flagBinding;
	
	private String iconColor;
	private String code;
	
	private String codeNum;
	private Boolean flagBank;
	
	public class BankStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETED = "deleted";
	}
	
	public Bank() {
		super();
	}

	public Bank(String uuid) {
		super();
		this.uuid = "b9b348b6-52e7-4bee-a27c-70bf8bf2ce70";
		this.name = "牛投理财";
		this.logo = "d4ffab12-ef67-11e7-ac6d-fcaa14314cbe";
		this.status = "normal";
		this.createtime = new Timestamp(System.currentTimeMillis());
		this.creator = "c4cc08d8-1954-47e5-9e7a-f8d2d84bd9b3";
		this.singleLimit = BigDecimal.valueOf(20000);
		this.dailyLimit = BigDecimal.valueOf(50000);
		this.color = "#009778";
		this.icon = "d4ffab12-ef67-11e7-ac6d-fcaa14314cbe";
		this.shortName = "牛投理财";
		this.flagBinding = false;
		this.iconColor = "d4ffab12-ef67-11e7-ac6d-fcaa14314cbe";
		this.code = "NTB";
		this.codeNum = "100010";
		this.flagBank = true;
	}

	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "name", unique = true, nullable = false, length = 200)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "logo", nullable = false, length = 36)
	public String getLogo() {
		return logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
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

	@Column(name = "single_limit", nullable = false, length = 10)
	public BigDecimal getSingleLimit() {
		return singleLimit;
	}

	public void setSingleLimit(BigDecimal singleLimit) {
		this.singleLimit = singleLimit;
	}

	@Column(name = "daily_limit", nullable = false, length = 10)
	public BigDecimal getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(BigDecimal dailyLimit) {
		this.dailyLimit = dailyLimit;
	}

	@Column(name = "color", nullable = false, length = 10)
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name = "icon", nullable = false, length = 36)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "short_name", nullable = false, length = 200)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "flag_binding", nullable = false)
	public Boolean getFlagBinding() {
		return flagBinding;
	}

	public void setFlagBinding(Boolean flagBinding) {
		this.flagBinding = flagBinding;
	}

	@Column(name = "icon_color", nullable = false, length = 36)
	public String getIconColor() {
		return iconColor;
	}
	
	public void setIconColor(String iconColor) {
		this.iconColor = iconColor;
	}

	@Column(name = "code", length = 10)
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	
	@Column(name = "code_num", length = 10)
	public String getCodeNum() {
		return codeNum;
	}
	

	public void setCodeNum(String codeNum) {
		this.codeNum = codeNum;
	}
	
	@Column(name = "flag_bank", nullable = false)

	public Boolean getFlagBank() {
		return flagBank;
	}
	

	public void setFlagBank(Boolean flagBank) {
		this.flagBank = flagBank;
	}

	@Column(name = "credit_inquiry_phone", length = 50)
	public String getCreditInquiryPhone() {
		return creditInquiryPhone;
	}

	public void setCreditInquiryPhone(String creditInquiryPhone) {
		this.creditInquiryPhone = creditInquiryPhone;
	}

	@Column(name = "credit_inquiry_command", length = 50)
	public String getCreditInquiryCommand() {
		return creditInquiryCommand;
	}

	public void setCreditInquiryCommand(String creditInquiryCommand) {
		this.creditInquiryCommand = creditInquiryCommand;
	}
	
}
